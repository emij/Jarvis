/*
 * Layer class between recognition code and classes for communication with hardware.
 * 
 */

package main;

import util.Command;
import voice.objectTags.Jarvis;



public class Main {
	
	public static void main(String[] args){
		Command command = new Command();
		Jarvis jarvis = new Jarvis(command);
		while(true){
			jarvis.record();
		}
	}
}
