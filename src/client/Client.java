package client;

import java.io.*;
import java.net.*; 

public class Client {
	private Socket clientSocket;
	private ObjectOutputStream outToServer;
	private ObjectInputStream inFromServer;
	private InetAddress host;
	private String recievedCommand;
	private int port;
	private boolean connectionAccepted = false;
	
	public Client()  {

	}
	public void makeConnection(InetAddress host, int port)  {
		this.host = host;
		this.port = port;
		System.out.println("Making connection");
		try {
			clientSocket = new Socket(host, port);
			outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
			inFromServer = new ObjectInputStream(clientSocket.getInputStream());
			String hello = "HELLO";
			System.out.println(hello);
			outToServer.writeObject(hello);
			System.out.println("Writing to server");
			while(!connectionAccepted){
				
				try {
					recievedCommand = (String) inFromServer.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				if(recievedCommand != null && recievedCommand.length() != 0){
					if(recievedCommand.equalsIgnoreCase("ok")){
					// TODO implement security	
						System.out.println("Connection accepted");
						connectionAccepted = true;
					} else if (recievedCommand.equalsIgnoreCase("quit")){
						outToServer.close();
						inFromServer.close();
						clientSocket.close();
						System.out.println("Connection closed");
					}
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}
	public void writeToServer(Object obj){
		try {
			outToServer.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}