package gr.aueb.cs.project.ds.dummyClient;

import java.net.Socket;
import java.net.UnknownHostException;

import gr.aueb.cs.project.ds.utils.Coordinates;
import gr.aueb.cs.project.ds.utils.DateTimeInfo;
import gr.aueb.cs.project.ds.utils.GeographicRectangle;
import gr.aueb.cs.project.ds.utils.Information;
import gr.aueb.cs.project.ds.utils.ReducerMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.io.ObjectInputStream;

public class DummyClient {
    private ReducerMessage resultFromReducer = null;
    private int port[] = new int[3];
    private Socket[] ApplicationSockets = null;
    private Socket client = null;
    private ServerSocket appServer = null;
  
    public void initialize(String Mappers[], Information info) {
        this.port[0] = 1200;
        this.port[1] = 1201;
        this.port[2] = 1202;

        Information[] mapperInfo = divideSpace(info.getGeographicRectangle(), info.getDateTimeInfo(), Mappers.length);
        connectToServer(Mappers, port, mapperInfo);
    }

    public Information[] divideSpace(GeographicRectangle location, DateTimeInfo dateTime, int numberOfMappers) {
        double latitudeMin = location.getMinCoordinates().getLatitute();
        double longitudeMin = location.getMinCoordinates().getLongitude();
        double latitudeMax = location.getMaxCoordinates().getLatitute();
        double longitudeMax = location.getMaxCoordinates().getLongitude();
        double latitudeDiff = location.getMaxCoordinates().getLatitute() - location.getMinCoordinates().getLatitute();

        double subLatitude = latitudeDiff / numberOfMappers;

        Information[] info = new Information[numberOfMappers];
        double latitudeFrom = latitudeMin;
        for (int i = 0; i < numberOfMappers; i++) {
             double tillLatitude = Math.min(latitudeMax, latitudeFrom + subLatitude);
             
             info[i] = createSubInformation(latitudeFrom, longitudeMin, longitudeMax, tillLatitude, dateTime);      
             latitudeFrom = tillLatitude;
        }

        return info;
    }

    public Information createSubInformation(double latitudeFrom, double longitudeMin, 
                                            double longitudeMax, double subLatitude, DateTimeInfo dateTime) {
        
        Coordinates coordinatesMin = new Coordinates(latitudeFrom, longitudeMin);
        Coordinates coordinatesMax = new Coordinates(subLatitude, longitudeMax);
        GeographicRectangle rectangle = new GeographicRectangle(coordinatesMin, coordinatesMax);
        
        return new Information(rectangle, dateTime);
    }

    public void connectToServer(String Mappers[], int port[], Information[] info) {
        ApplicationSockets = new Socket[Mappers.length];

        for (int i = 0; i < Mappers.length; i++) {
            try {
                ApplicationSockets[i] = new Socket(Mappers[i], port[i]);
            } catch (UnknownHostException e) {
                System.err.println("Host could not be found.");
            } catch (IOException e) {
                System.out.println(e);
            }
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(ApplicationSockets[i].getOutputStream());
                System.out.println("Sending rectangle to mapper: " + Mappers[i]);
                outputStream.writeObject(info[i]);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
               try {
                   ApplicationSockets[i].close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
            }
        }

    }

    public void waitForResponse(int port) {
        ObjectInputStream inputStream = null;
        try {
            appServer = new ServerSocket(port);
            client = appServer.accept();
            inputStream = new ObjectInputStream(client.getInputStream());
            System.out.println("Success received results from reducer.");
            try {
                resultFromReducer = (ReducerMessage) inputStream.readObject();
                resultFromReducer.getReducerResult()
                                 .entrySet()
                                 .stream()
                                 .sorted((a, b) -> a.getValue().getTotalNumberOfCheckInsToThisPlace() > b.getValue().getTotalNumberOfCheckInsToThisPlace() ? -1 :
                                         (a.getValue().getTotalNumberOfCheckInsToThisPlace() < b.getValue().getTotalNumberOfCheckInsToThisPlace() ? 1 : 0))
                                 .forEach(e -> System.out.println("\"" + e.getValue().getPlaceName() + "\" with a total of: " + 
                                          e.getValue().getTotalNumberOfCheckInsToThisPlace() + " check-ins"));
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                appServer.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        //Information info = new Information(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
        Information info = new Information("40.6","-74.3", "40.8", "-72.01", "2000-04-03", "19:00:09",  "2020-6-03","19:00:20");
        String[] mapperComputerIp = new String[1];
        
        mapperComputerIp[0] = "127.0.0.1";
        //mapperComputerIp[1] = args[9];
        //mapperComputerIp[2] = args[10];
        DummyClient app = new DummyClient();
        app.initialize(mapperComputerIp, info);
        app.waitForResponse(1300);
    }
    
    private DummyClient() {}
    
}