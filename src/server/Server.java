package server;

import java.io.IOException;
import java.net.*; 

import voice.Command;

/* Some ideas taken from 
 * Multithreaded Java WebServer
 * (C) 2001 Anders Gidenstam
 */

public class Server extends Thread {
	Command command;
	ServerSocket serverSocket;
	private int port;

	public Server(Command command, int port) {
		this.command = command;
		this.port = port;
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			// Wait for and process client server requests
			while (true) {
				// Wait for TCP connection
				Socket clientCommandSocket = serverSocket.accept();

				// Create an object to handle the request
				ClientConnection clientCommand  = new ClientConnection(command, clientCommandSocket);

				Thread thread = new Thread(clientCommand);
				thread.start();
			}
		} catch (Exception e) {
			System.out.println("fail");
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}