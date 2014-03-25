package client;

import java.io.*;
import java.net.*; 
import java.util.Observable;
import java.util.Observer;

import voice.Command;



public class Client implements Observer {
	Socket clientSocket;
	ObjectOutputStream outToServer;
	String sentence;
	Command command;
	Inet4Address host;
	int port;
	
	public Client(Command command)  {
		this.command = command;
		command.addObserver(this);

	}
	public void makeConnection(Inet4Address host, int port)  {
		this.host = host;
		this.port = port;
		try {
			clientSocket = new Socket(host, port);
			outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
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
}