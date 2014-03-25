package server;

import java.io.ObjectInputStream;
import java.net.Socket;

import voice.Command;

public class ClientConnection implements Runnable {
	Command command;
	Socket socket;
	ObjectInputStream inFromClient;


	public ClientConnection(Command command, Socket socket) throws Exception {
		this.command = command;
		this.socket = socket;
		inFromClient =                
				new ObjectInputStream(socket.getInputStream());
	}
	// Move creation of inputstream to constructor, fix do-while
	@Override
	public void run() {

		while(true) { // TODO fix exceptionhandling
			try {
				String recievedCommand = (String) inFromClient.readObject(); 
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

