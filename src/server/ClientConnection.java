package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import util.AbstractCommand;
import util.ClientCommand;
import util.Command;

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
				Object objectFromClient = inFromClient.readObject();
				if(objectFromClient instanceof String){
					String recievedCommand = (String)  objectFromClient;
					if(recievedCommand != null && recievedCommand.length() != 0){
						if(recievedCommand.compareTo("HELLO")==0){
							// TODO implement security	
							System.out.println("Connection recieved");
							String accepted = "OK";
							toClient.writeObject(accepted);
						} else if (recievedCommand.equalsIgnoreCase("quit")){
							break;
						}
					}
				} else  if (objectFromClient instanceof AbstractCommand){
					
					ClientCommand tempCommand = (ClientCommand) objectFromClient;
					 
					command.copyCommand(tempCommand);
					command.generateCommand();
					
				}

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

