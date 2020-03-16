package gr.aueb.cs.project.ds.utils;


import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class mainServer  {
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private int portNumber = 0;
	private ObjectOutputStream output = null;
	private ObjectInputStream input = null;
	private Information information = null;
	
	private void initialize() {
		portNumber = 5321;

	}
	
	private void openServer() {
		openServerSocket();
		System.out.println("Server listening...");
		while(true) {
			try {
				clientSocket = serverSocket.accept();
				System.out.println("Client connected!");
				input = new ObjectInputStream(clientSocket.getInputStream());
				System.out.print("I have taken the input!");
				output = new ObjectOutputStream(clientSocket.getOutputStream());


				try {
					information = (Information) input.readObject();
					System.out.println(information);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				List<String> set = new ArrayList<>();
				set.add("http://wall--art.com/wp-content/uploads/2014/09/restaurants-icon-png.png");
				//dhmiourgia sample PlaceInformation, gia test 3 mono
				TopPlaceInformation info1 = new TopPlaceInformation(15,"111111","Starbucks","7",set,40.7127,-74.0030);
				TopPlaceInformation info2 = new TopPlaceInformation(20,"111112","CandyShop","8",set,40.7000,-74.0038);
				TopPlaceInformation info3 = new TopPlaceInformation(5,"111113","Cinema","12",set,40.8127,-74.0032);

				
				
				//ta vazoume se list
				ArrayList<TopPlaceInformation> places = new ArrayList<TopPlaceInformation>();
				places.add(info1);
				places.add(info2);
				places.add(info3);

				output.writeObject(places);
				System.out.println("The object is written"+places.get(0).getPlaceName());

				output.flush();

				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					input.close();
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private void openServerSocket() {
		try {
			serverSocket = new ServerSocket(portNumber, 10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		pazServer server = new pazServer();
		server.initialize();
		server.openServer();
	}
}
