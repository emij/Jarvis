/* Class for recognition and input from source */

package main;

import java.io.IOException;
import java.net.URL;

import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.recognition.GrammarException;
import javax.speech.recognition.RuleGrammar;
import javax.speech.recognition.RuleParse;

import com.sun.speech.engine.recognition.BaseRecognizer;
import com.sun.speech.engine.recognition.BaseRuleGrammar;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.jsgf.JSGFGrammar;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
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
	
	// TODO: deal with exceptions.
	/**
	 * setup for recognizer, microphone for recognition.
	 * setup for baseRecognizer that is used to parse tags.
	 * 
	 * @throws InstantiationException
	 * @throws PropertyException
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws EngineException
	 * @throws EngineStateError
	 */
	private void setup() throws RuntimeException, PropertyException, IOException, EngineException{
		b = new BaseRecognizer(((JSGFGrammar) cm.lookup("jsgfGrammar")).getGrammarManager());
		recognizer = (Recognizer) cm.lookup("recognizer");
		microphone = (Microphone) cm.lookup("microphone");
		
		b.allocate();
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
					
					String[] parsed = parseCommand(r.getBestFinalResultNoFiller());
					if(parsed != null){
						System.out.print("\nParsed: ");
						for(String item: parsed){
							System.out.print(item + " ");
						}
						System.out.println("");
					}
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

	/**
	 * Parses a string command to corresponding tags based on the rule grammar.
	 * 
	 * @param spokenString: result string from spoken command to be parsed.
	 * @return array of tags corresponding to the inputed command.
	 */
	private String[] parseCommand(String spokenString) {
		JSGFGrammar g =  (JSGFGrammar) cm.lookup("jsgfGrammar");
		RuleGrammar r = new BaseRuleGrammar(b, g.getRuleGrammar());
		
		RuleParse p = null;
		try {
			p = r.parse(spokenString, null);
		} catch (GrammarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p.getTags();
	}
}
