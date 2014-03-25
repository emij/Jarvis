package client;

import voice.Command;


public class Main {
	
	public static void main(String[] args){
		Command command = new Command();
		SimpleGUI gui = new SimpleGUI(command);
		Thread thread = new Thread(gui);
		thread.start();
	}
}