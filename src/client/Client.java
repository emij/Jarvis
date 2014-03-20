package client;

import java.io.*;
import java.net.*; 


public class Client {

	public static void main(String argv[]) throws Exception  {   
		String sentence = "lamp enable";
		Socket clientSocket = new Socket("localhost", 6789);   
		ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());   
		outToServer.writeObject(sentence);
		clientSocket.close();
	} 
}
