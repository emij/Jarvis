/* Class for recognition and input from source */

package main;

import java.io.IOException;
import java.net.URL;

import javax.speech.recognition.RuleGrammar;
import javax.speech.recognition.RuleParse;

import com.sun.speech.engine.recognition.BaseRecognizer;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.tools.tags.ActionTagsParser;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

public class Jarvis extends Thread{

	ConfigurationManager cm;
	BaseRecognizer b;
	Recognizer recognizer;
	Microphone microphone;

	public Jarvis(URL u){
		try{
			setConfiguration(u);
			setup();
		}
		catch(Exception e){
			//TODO: handle exceptions
			e.printStackTrace();
		}
	}
	
	public Jarvis(){
		try{
			setConfiguration(null);
			setup();
		}
		catch(Exception e){
			//TODO: handle exceptions
			e.printStackTrace();
		}
	}
	
	public void setConfiguration(URL u) throws IOException, PropertyException{
		if(u == null){
			cm = new ConfigurationManager(Jarvis.class.getResource("jarvis.config.xml"));
		}
		else{
			cm = new ConfigurationManager(u);
		}
	}
	
	private void setup() throws InstantiationException, PropertyException, IllegalStateException, IOException{
		recognizer = (Recognizer) cm.lookup("recognizer");
		microphone = (Microphone) cm.lookup("microphone");
		
		recognizer.allocate();
	}
	
	@Override
	public void run(){
		if(microphone.startRecording()){
			while(true){
				System.out.println("Speak command please");
				
				Result r = recognizer.recognize();
				
				if(r != null && r.getBestResultNoFiller().length() > 0){
					//TODO: notify new command
					System.out.println(r.getBestFinalResultNoFiller());
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

	// TODO: method for parsing out tags from result string, needs fixing. Based on tags demo in sphinx.
	private String parseCommand(String bestFinalResultNoFiller) {
		try {
			RuleGrammar g = b.loadJSGF(new URL("file:///Jarvis/src/main/jarvis.gram"), "jarvis.gram");
			System.out.println("\ngrammar" + g.toString());
			
			RuleParse p = g.parse(bestFinalResultNoFiller, null);
			
			ActionTagsParser parser = new ActionTagsParser();
			parser.parseTags(p);
			return p.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
