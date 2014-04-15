/**
 *  Class for recognition and input from source
 *
 * @author Marika Hansson
 * @author Markus Otterberg
 */

package voice.objectTags;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.recognition.GrammarException;
import javax.speech.recognition.RuleGrammar;
import javax.speech.recognition.RuleParse;

//import sun.net.www.content.audio.wav;
import util.Command;

import com.sun.speech.engine.recognition.BaseRecognizer;
import com.sun.speech.engine.recognition.BaseRuleGrammar;

import devices.Controller;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.frontend.util.Utterance;
import edu.cmu.sphinx.jsgf.JSGFGrammar;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.tools.tags.ObjectTagsParser;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

public class Jarvis{

	private ConfigurationManager cm;
	private BaseRecognizer baseRec;
	private Recognizer recognizer;
	private Microphone microphone;
	private RuleGrammar rules;
	private JSGFGrammar grammar;
	private ObjectTagsParser objParser;
	private Command command;
	private int i=0;

	//PoC for status indication
	private Controller controller = Controller.getInstance();

	public Jarvis(URL u, Command c){
		try {
			setConfiguration(u);
			setup(c);
		} catch (EngineException | IOException | RuntimeException e) {
			System.out.println("Configuration and setup cannot be performed.");
			e.printStackTrace();
		}
	}

	public Jarvis(Command cmd){
		try{
			setConfiguration(null);
			setup(cmd);
		}
		catch(EngineException | IOException | RuntimeException e){
			System.out.println("Configuration and setup cannot be performed.");
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
		command = cmd;

		baseRec.allocate();
		recognizer.allocate();
	}

	private void setupParser(){
		grammar =  (JSGFGrammar) cm.lookup("jsgfGrammar");
		rules = new BaseRuleGrammar(baseRec, grammar.getRuleGrammar());
		objParser = new ObjectTagsParser();
		objParser.put("appObj", command);
	}

	public Command getCommand(){
		return command;
	}

	public void record(){
		boolean recording = true;
		if(!microphone.isRecording()){
			recording = microphone.startRecording();
		}
		if(!recording){
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
		else{
			setupParser();

			controller.extinguishStatusLed("yellow");

			String bestResult = null;
			while(bestResult == null || bestResult.isEmpty()){
				controller.lightStatusLed("green");
				System.out.println("Speak command please");
				Result r = recognizer.recognize();
				bestResult = r.getBestFinalResultNoFiller();
				if(bestResult == null || bestResult.isEmpty()){
					System.out.println("Cannot hear command");
				}
			}
			
			controller.extinguishStatusLed("green");

			String filename = "audio/audio" + i + ".wav";
			saveAudio(filename, microphone.getUtterance());

			System.out.println("Result: " + bestResult);
			parseCommand(bestResult);
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
		objParser.parseTags(parse);
	}

	
	private void saveAudio(String filename, Utterance audio){
		try {
			audio.save(filename, AudioFileFormat.Type.WAVE);
			i++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
