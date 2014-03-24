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
	public Client()  {   
		setupSockets();
		sentence = "lamp enable";
		command = new Command();
		command.addObserver(this);
		SimpleGUI gui = new SimpleGUI(command);
		Thread thread = new Thread(gui);
		thread.start();
	}
	private void setupSockets()  {
		try {
			clientSocket = new Socket("localhost", 6789);
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
		try {
			outToServer.writeObject(sentence);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

