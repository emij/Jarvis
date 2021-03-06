/**
 *  Class for recognition and input from source
 *
 * @author Marika Hansson
 * @author Markus Otterberg
 */

package voice.getTags;

import java.io.IOException;
import java.net.URL;

import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.recognition.GrammarException;
import javax.speech.recognition.RuleGrammar;
import javax.speech.recognition.RuleParse;

import util.Command;


import com.sun.speech.engine.recognition.BaseRecognizer;
import com.sun.speech.engine.recognition.BaseRuleGrammar;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.jsgf.JSGFGrammar;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

public class Jarvis extends Thread{

	private ConfigurationManager cm;
	private BaseRecognizer baseRec;
	private Recognizer recognizer;
	private Microphone microphone;
	private RuleGrammar rules;
	private JSGFGrammar grammar;

	public Jarvis(URL u, Command c){
		try {
			setConfiguration(u);
			setup(c);
		} catch (EngineException | IOException | RuntimeException e) {
			System.out.println("Configuration and setup cannot be performed. Check if inputed ConfigurationManager url is valid.");
			e.printStackTrace();
		}
	}

	public Jarvis(Command cmd){
		try{
			setConfiguration(null);
			setup(cmd);
		}
		catch(EngineException | IOException | RuntimeException e){
			System.out.println("Configuration and setup cannot be performed. Check if a valid default configurationManager exsits or input an url to a valid ConfigurationManager.");
			e.printStackTrace();
		}
	}

	public void setConfiguration(URL u) throws IOException, PropertyException{
		if(u == null){
			cm = new ConfigurationManager(Jarvis.class.getResource("../jarvis.config.xml"));
		}
		else{
			cm = new ConfigurationManager(u);
		}
	}

	/**
	 * setup for recognizer, microphone for recognition.
	 * setup for baseRecognizer that is used to parse tags.
	 * 
	 * @throws RumtimeException
	 * @throws PropertyException
	 * @throws IOException
	 * @throws EngineException
	 */
	private void setup(Command cmd) throws RuntimeException, PropertyException, IOException, EngineException{
		baseRec = new BaseRecognizer(((JSGFGrammar) cm.lookup("jsgfGrammar")).getGrammarManager());
		recognizer = (Recognizer) cm.lookup("recognizer");
		microphone = (Microphone) cm.lookup("microphone");

		baseRec.allocate();
		recognizer.allocate();
	}

	private void setupParser(){
		grammar =  (JSGFGrammar) cm.lookup("jsgfGrammar");
		rules = new BaseRuleGrammar(baseRec, grammar.getRuleGrammar());
		rules.setEnabled(true); //TODO: check if needed?
	}

	@Override
	public void run(){
		setupParser();
		if(microphone.startRecording()){
			while(true){
				System.out.println("Speak command please");

				Result r = recognizer.recognize();
				String bestResult = r.getBestFinalResultNoFiller();
				System.out.println(r.getTimedBestResult(false, true));

				if(r != null && bestResult.length() > 0){
					System.out.println(bestResult);

					parseCommand(bestResult);
				}
				else{
					System.out.println("Cannot hear command, please try again");
				}
			}
		}
		else{
			System.out.println("Cannot start microphone");
			recognizer.deallocate();
			try {
				baseRec.deallocate();
			} catch (EngineException | EngineStateError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(1); //Error occurred
		}
	}

	/**
	 * Parses a string command to corresponding tags based on the rule grammar.
	 * 
	 * @param spokenString: result string from spoken command to be parsed.
	 * @return array of tags corresponding to the inputed command.
	 */
	private void parseCommand(String spokenString) {
		RuleParse parse = null;
		try {
			parse = rules.parse(spokenString, null);
		} catch (GrammarException e) {
			e.printStackTrace();
		}
		String[] result = parse.getTags();

		for(String r:result){
			System.out.println(r);
		}
	}
}