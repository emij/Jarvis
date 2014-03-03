/*
 * Class for recognition and input from source
 */

package main;

import java.io.IOException;
import java.net.URL;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

public class Jarvis extends Thread{

	ConfigurationManager cm;
	Recognizer recognizer;
	Microphone microphone;
	

	public Jarvis(URL u){
		setConfiguration(u);
		setup();
	}
	
	public Jarvis(){
		setConfiguration(null);
		setup();
	}
	
	public void setConfiguration(URL u){
		if(u == null){
			try {
				cm = new ConfigurationManager(new URL("config.xml"));
			} catch (IOException | PropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				cm = new ConfigurationManager(u);
			} catch (IOException | PropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void setup(){
		try {
		    recognizer = (Recognizer) cm.lookup("recognizer");
		    microphone = (Microphone) cm.lookup("microphone");
		   
		    recognizer.allocate();
		}
		catch (PropertyException | InstantiationException | IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		if(microphone.startRecording()){
			while(true){
				System.out.println("Speak command please");
				
				Result r = recognizer.recognize();
				
				if(r != null){
					//TODO: notify new command
				}
				else{
					System.out.println("Cannot hear command, please try again");
				}
			}
		}
		else{
			System.out.println("Cannot start microphone");
			recognizer.deallocate();
			System.exit(1); //Error occurred
		}
	}
}
