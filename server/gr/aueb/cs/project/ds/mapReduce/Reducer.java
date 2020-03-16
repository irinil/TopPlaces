package gr.aueb.cs.project.ds.mapReduce;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import gr.aueb.cs.project.ds.utils.CheckInInformation;
import gr.aueb.cs.project.ds.utils.MapperMessage;
import gr.aueb.cs.project.ds.utils.TopPlaceInformation;
import gr.aueb.cs.project.ds.utils.ReducerMessage;

public class Reducer {
    private final int topK = 10;
    private int port = 0;
    private Map<String, List<CheckInInformation>> mapperResults = null;

    public void initialize(String clientIp) {
        mapperResults = new TreeMap<>();
        port = 1100;
        openReducerServer(port, clientIp);
    }

    public void openReducerServer(int port, String ip) {
        ServerSocket server = null;
        Socket client = null;
        ObjectInputStream inputStream = null;
        MapperMessage[] message = new MapperMessage[1];
        
        try {
            server = new ServerSocket(port);
            for(int i = 0; i < 1; i++) {
                client = server.accept();
                System.out.println("CONNECTION ACCEPTED FROM MAPPER: " + i);                
                inputStream = new ObjectInputStream(client.getInputStream());
                message[i] = (MapperMessage)inputStream.readObject();
                System.out.println("MESSAGE ACCEPTED");
            } 
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int j = 0; j < 1; j++) {
            mapperResults.putAll((Map<String, List<CheckInInformation>>) message[j].getMapperResults());
        }

        sendResults(reduce(mapperResults),ip);
    }

    public Map<String, TopPlaceInformation> reduce(Map<String, List<CheckInInformation>> map) {
        Map<String, TopPlaceInformation> reducerResult = null;
//        reducerResult = map.entrySet()
//                           .parallelStream()
//                           .map(mapKeyValue -> mapKeyValue.getValue())
//                           .collect(Collectors.toList())
//                           .parallelStream()
//                           .map(PlaceInformation::new)
//                           .sorted((a, b) -> a.getTotalNumberOfCheckInsToThisPlace() > b.getTotalNumberOfCheckInsToThisPlace() ? -1 :
//                                   (a.getTotalNumberOfCheckInsToThisPlace() < b.getTotalNumberOfCheckInsToThisPlace() ? 1 : 0))
//                           .limit(topK)
//                           .collect(Collectors.toMap(place -> place.getPoi(), place -> place));

        return reducerResult;
    }

    public void sendResults(Map<String, TopPlaceInformation> map, String clientIp) {
        Socket client = null;
        ObjectOutputStream outputStream = null;
        ReducerMessage message = null;
        try {
            client = new Socket(clientIp, 1300);
       
            outputStream = new ObjectOutputStream(client.getOutputStream());
            System.out.println("Sending results to client: " + clientIp);
            message = new ReducerMessage(map);
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Reducer reducer = new Reducer();
        reducer.initialize("127.0.0.1");
    }

    private Reducer() {}
    
}

