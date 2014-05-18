/*
 * Layer class between recognition code and classes for communication with hardware.
 * 
 */

package main;

import controller.JarvisController;
import server.Server;
import util.Command;
import voice.objectTags.Jarvis;
import core.Core;

public class Main {
	
	public static void main(String[] args){
		Core core = Core.INSTANCE;
		JarvisController controller = JarvisController.INSTANCE;
		Command serverCommand = new Command();
		Server server = new Server(serverCommand, 6789);
		server.start();
		Command command = new Command();
		//Jarvis jarvis = new Jarvis(command);
		
		/*while(true){
			jarvis.record();
			
			if (controller.isAsleep()){
				controller.goToSleep();
			}
			
		}
	*/
	}
}
