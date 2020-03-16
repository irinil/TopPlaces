package gr.aueb.cs.project.ds.mapReduce;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import java.io.IOException;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import gr.aueb.cs.project.ds.utils.CheckInInformation;
import gr.aueb.cs.project.ds.utils.Information;
import gr.aueb.cs.project.ds.utils.MapperMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mapper {
    private final int numberOfAvailableProcessors = Runtime.getRuntime().availableProcessors();
    private final int topK = 10;
    static String reducerIp = null; 
    
    public void initialize(String portNum) {
        int port = Integer.valueOf(portNum);
        openMapperServer(port);
    }

    private void openMapperServer(int port) {
        ServerSocket providerSocket = null;
        Socket connection = null;
        ObjectInputStream inputStream = null;

        try {
            providerSocket = new ServerSocket(port);
            System.out.println("Mapper server listening...");
            connection = providerSocket.accept();
            System.out.println("Connection has been achieved");
            System.out.println("Waiting to read information from client");
            inputStream = new ObjectInputStream(connection.getInputStream());
            System.out.println("Success");
            
            Information information = (Information)inputStream.readObject();
            Map<String, List<CheckInInformation>> mapperResult = startMapping(information);
            System.out.println("Information were received successfully");
            sendToReduce(mapperResult);
            System.out.println("Connected to reducer successfully");
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                providerSocket.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private Map<String, List<CheckInInformation>> startMapping(Information information) {
        List<List<CheckInInformation>> checkInLists = null;
        Information[] splittedInformation = splitMapperLocation(information);
        return map(checkInLists, splittedInformation);
    }

    private Map<String, List<CheckInInformation>> map(List<List<CheckInInformation>> checkInLists, Information[] splittedInformation) {
        checkInLists = getCheckInsFromDatabase(splittedInformation);
        
        Map<String, List<CheckInInformation>> mapperResult =  null; 
        mapperResult =  (Map<String, List<CheckInInformation>>)checkInLists.parallelStream()
                                   .map(e -> e.stream()
                                              .collect(groupingBy(CheckInInformation::getPoi,
                                                                    mapping(checkIn -> checkIn, toList())))
                                              .entrySet()
                                              .stream()
                                              .sorted((a, b) -> a.getValue().size() > b.getValue().size() ? -1 :
                                                      (a.getValue().size() < b.getValue().size() ? 1 : 0))
                                              .limit(topK)
                                              .collect(Collectors.toMap(map -> map.getKey(),
                                                                        map -> map.getValue())))
                                    .flatMap(e -> e.entrySet().stream())
                                    .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
        
        return mapperResult;
    }

    private List<List<CheckInInformation>> getCheckInsFromDatabase(Information[] splittedInformation) {
        List<List<CheckInInformation>> splittedList = new ArrayList<>();
        
        for (int i = 0; i < numberOfAvailableProcessors; i++) {
            List<CheckInInformation> checkIns = connectionToDatabase(splittedInformation[i]);
            splittedList.add(checkIns);
        }
                
        return splittedList;
    }

    private void sendToReduce(Map<String, List<CheckInInformation>> mapperResult) {
        if(mapIsNotNull(mapperResult))
            startMapperClientToConnectWithReducerServer(mapperResult);
    }
    
    private void startMapperClientToConnectWithReducerServer(Map<String, List<CheckInInformation>> mapperResult) {
        Socket client = null;
        ObjectOutputStream outputStream = null;
        MapperMessage message = null;
        try {
            client = new Socket(reducerIp , 1100);              
            outputStream = new ObjectOutputStream(client.getOutputStream());
            message = new MapperMessage(mapperResult);
            outputStream.writeObject(message);
            outputStream.close();
            client.close();
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                outputStream.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    private boolean mapIsNotNull(Map<String, List<CheckInInformation>> mapperResult) {
        return mapperResult != null;
    }
    
    private Information[] splitMapperLocation(Information information) {
        Information[] splittedInformation = new Information[numberOfAvailableProcessors];
        double latitudeMin = information.getGeographicRectangle().getMinCoordinates().getLatitute();
        double latitudeMax = information.getGeographicRectangle().getMaxCoordinates().getLatitute();
        double longitudeMin = information.getGeographicRectangle().getMinCoordinates().getLongitude();
        double longitudeMax = information.getGeographicRectangle().getMaxCoordinates().getLongitude();
        
        double longitudeDiff = longitudeMax - longitudeMin;
        double breakApartLongitude = longitudeDiff / numberOfAvailableProcessors;
        
        double fromLongitude = longitudeMin;
        for(int i = 0; i < numberOfAvailableProcessors; i++) {
            splittedInformation[i] = new Information(latitudeMin, fromLongitude, 
                                                     latitudeMax, Math.min(longitudeMax, fromLongitude + breakApartLongitude), 
                                                     information.getDateTimeInfo());
            fromLongitude = fromLongitude +  breakApartLongitude;
        }
        
        return splittedInformation;
    }
    
    private List<CheckInInformation> connectionToDatabase(Information information) {
        List<CheckInInformation> checkIns = new ArrayList<>();
        String dbIpAddress = "83.212.117.76";
        String dbPortNumber = "3306";
        String dbName = "ds_systems_2016";
        String dbUsername = "omada31";
        String dbPassword = "omada31db";
        String latitudeMin = String.valueOf(information.getGeographicRectangle().getMinCoordinates().getLatitute());
        String longitudeMin = String.valueOf(information.getGeographicRectangle().getMinCoordinates().getLongitude());
        String latitudeMax = String.valueOf(information.getGeographicRectangle().getMaxCoordinates().getLatitute());
        String longitudeMax = String.valueOf(information.getGeographicRectangle().getMaxCoordinates().getLongitude());
        String fromDateTime = information.getDateTimeInfo().getFromDateTime();
        String toDateTime = information.getDateTimeInfo().getToDateTime();
        
        String dbURL = String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", 
                                     dbIpAddress, dbPortNumber, dbName, dbUsername, dbPassword);
        String dbClass = "com.mysql.jdbc.Driver";
        String sqlQuery = "SELECT POI, POI_name, POI_category, photos, latitude, longitude " + "FROM checkins "
                + "WHERE latitude BETWEEN " + latitudeMin + " AND " + latitudeMax 
                + "AND longitude BETWEEN " + longitudeMin + " AND " + longitudeMax + 
                " AND time BETWEEN'" + fromDateTime + "'" + " AND '" + toDateTime + "'"
                + "ORDER BY longitude DESC;";
        try {
            Class.forName(dbClass);
            Connection dbConnection = DriverManager.getConnection(dbURL);
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            getDataFromDb(checkIns, resultSet);
            dbConnection.close();
            return checkIns;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void getDataFromDb(List<CheckInInformation> checkIns, ResultSet resultSet) throws SQLException {
        final byte poi = 1, poiName = 2, poiCategory = 3, photo = 4, latitude = 5, longitude = 6;
        while (resultSet.next()) {
            checkIns.add(new CheckInInformation(resultSet.getString(poi), resultSet.getString(poiName), resultSet.getString(poiCategory),
                    resultSet.getString(photo), resultSet.getDouble(latitude), resultSet.getDouble(longitude)));
        }
    }

    public static void main(String[] args) {
        Mapper mapper = new Mapper();
        reducerIp = "127.0.0.1";
        mapper.initialize("1200");
    }
    
    private Mapper() {}
    
}
