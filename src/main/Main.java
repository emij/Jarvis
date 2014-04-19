/*
 * Layer class between recognition code and classes for communication with hardware.
 * 
 */

package main;

import server.Server;
import util.Command;
import voice.objectTags.Jarvis;
import core.Core;
import devices.Controller;

public class Main {
	
	public static void main(String[] args){
		Core core = Core.INSTANCE;
		Controller controller = Controller.getInstance();
		Command serverCommand = new Command();
		Server server = new Server(serverCommand, 6789);
		server.start();
		Command command = new Command();
		Jarvis jarvis = new Jarvis(command);
		
		while(true){
			jarvis.record();
			
			
			while(controller.isAsleep()){
				try {
					Thread.sleep(15*1000); //Halt for 15 s before checking if movement has been detected //TODO remove magic variable, decide suitable time to wait
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
}
