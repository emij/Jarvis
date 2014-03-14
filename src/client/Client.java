package client;

import java.io.*;
import java.net.*; 


public class Client {


	public static void main(String argv[]) throws Exception  {   
		String sentence;
		while(true){
			BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));   
			Socket clientSocket = new Socket("localhost", 6789);   
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());   
			sentence = inFromUser.readLine();   
			outToServer.writeBytes(sentence + '\n');   
		} 
	}
}