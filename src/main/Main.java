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
			
			if (controller.isAsleep()){
				controller.goToSleep();
			}
		}
		
		
	}
}
