package server;

import java.io.*; 
import java.net.*; 

import voice.Command;

public class Server extends Thread {
	Command command;
	ServerSocket welcomeSocket;

	public Server(Command command) {
		this.command = command;
	}

	public void run() {
		try {
			welcomeSocket = new ServerSocket(6789);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) { // TODO fix exceptionhandling
			try {
				Socket connectionSocket = welcomeSocket.accept();             
				BufferedReader inFromClient =                
						new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));             

				String recievedCommand = inFromClient.readLine(); 
				if(recievedCommand != null && recievedCommand.length() != 0){
					command.newCommand(recievedCommand);
				}
				System.out.println("Received: " + recievedCommand);
			} catch (Exception e){
				// TODO
				e.printStackTrace();
			}
		}
	}
}