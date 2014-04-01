package client;

import java.io.*;
import java.net.*; 
import java.util.Observable;
import java.util.Observer;

import voice.Command;



public class Client implements Observer {
	private Socket clientSocket;
	private ObjectOutputStream outToServer;
	private ObjectInputStream inFromServer;
	private String sentence;
	private Command command;
	private InetAddress host;
	private String recievedCommand;
	private int port;
	private boolean connectionAccepted = false;
	
	public Client(Command command)  {
		this.command = command;
		command.addObserver(this);

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
					if(recievedCommand.compareTo("OK")==0){
					// TODO implement security	
						System.out.println("Connection accepted");
						connectionAccepted = true;
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
	public void update(Observable o, Object arg) { // maybe should have synchronized here instead
		if (o instanceof Command){
			Command guiCommand = (Command)o;
			try {
				outToServer.writeObject(guiCommand.getCommand());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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