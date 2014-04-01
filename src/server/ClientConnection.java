package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import voice.Command;

public class ClientConnection implements Runnable {
	Command command;
	Socket socket;
	ObjectInputStream inFromClient;
	ObjectOutputStream toClient;


	public ClientConnection(Command command, Socket socket) throws Exception {
		this.command = command;
		this.socket = socket;
		inFromClient =                
				new ObjectInputStream(socket.getInputStream());
		toClient =
				new ObjectOutputStream(socket.getOutputStream());
	}
	// Move creation of inputstream to constructor, fix do-while
	@Override
	public void run() {

		while(true) { // TODO fix exceptionhandling
			try {
				String recievedCommand = (String) inFromClient.readObject(); 
				if(recievedCommand != null && recievedCommand.length() != 0){
					if(recievedCommand.compareTo("HELLO")==0){
					// TODO implement security	
						System.out.println("Connection recieved");
						String accepted = "OK";
						toClient.writeObject(accepted);
					} else if (recievedCommand.equalsIgnoreCase("quit")){
						break;
					} else {
					command.newCommand(recievedCommand);
					}
				}
				System.out.println("Received: " + recievedCommand);
				
			} catch (Exception e){
				// TODO
				e.printStackTrace();
			}
		}
		try {
			inFromClient.close();
			toClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

