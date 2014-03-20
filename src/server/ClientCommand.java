package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

import voice.Command;

public class ClientCommand implements Runnable {
	Command command;
	Socket socket;


	public ClientCommand(Command command, Socket socket) throws Exception {
		this.command = command;
		this.socket = socket;
	}

	@Override
	public void run() {

		//while(true) { // TODO fix exceptionhandling
			try {
				ObjectInputStream inFromClient =                
						new ObjectInputStream(socket.getInputStream());             

				String recievedCommand = (String) inFromClient.readObject(); 
				if(recievedCommand != null && recievedCommand.length() != 0){
					command.newCommand(recievedCommand);
				}
				System.out.println("Received: " + recievedCommand);
			} catch (Exception e){
				// TODO
				e.printStackTrace();
			}
		//}
	}
}

