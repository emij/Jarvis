/*
 * Layer class between recognition code and classes for communication with hardware.
 * 
 */

package voice;

public class Main {
	
	public static void main(String[] args){
		Command c = new Command();
		Jarvis j = new Jarvis(c);
		j.start();
	}
}
