package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

		while(true) { // TODO fix exceptionhandling
			try {
				BufferedReader inFromClient =                
						new BufferedReader(new InputStreamReader(socket.getInputStream()));             

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

