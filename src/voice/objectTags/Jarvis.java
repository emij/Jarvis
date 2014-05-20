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

import util.ClientCommand;
import util.Command;

import com.sun.speech.engine.recognition.BaseRecognizer;
import com.sun.speech.engine.recognition.BaseRuleGrammar;

import controller.JarvisController;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.frontend.util.Utterance;
import edu.cmu.sphinx.jsgf.JSGFGrammar;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.tools.tags.ObjectTagsParser;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

public class Jarvis extends Thread{

	private ConfigurationManager cm;
	private BaseRecognizer baseRec;
	private Recognizer recognizer;
	private Microphone microphone;
	private RuleGrammar rules;
	private JSGFGrammar grammar;
	private ObjectTagsParser objParser;
	//private Command command;
	private ClientCommand clcommand;
	private int i=0;

	//private JarvisController controller = JarvisController.INSTANCE;

	public Jarvis(URL u, ClientCommand c){
		try {
			setConfiguration(u);
			clcommand = c;
			setup(c);
		} catch (EngineException | IOException | RuntimeException e) {
			System.out.println("Configuration and setup cannot be performed.");
			e.printStackTrace();
		}
	}

	public Jarvis(ClientCommand cmd){
		try{
			setConfiguration(null);
			clcommand = cmd;
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
	private void setup(ClientCommand cmd) throws RuntimeException, PropertyException, IOException, EngineException{
		baseRec = new BaseRecognizer(((JSGFGrammar) cm.lookup("jsgfGrammar")).getGrammarManager());
		recognizer = (Recognizer) cm.lookup("recognizer");
		microphone = (Microphone) cm.lookup("microphone");


		baseRec.allocate();
		recognizer.allocate();
	}


	private void setupParser(){
		grammar =  (JSGFGrammar) cm.lookup("jsgfGrammar");
		rules = new BaseRuleGrammar(baseRec, grammar.getRuleGrammar());
		objParser = new ObjectTagsParser();
		System.out.println(clcommand + "");
		objParser.put("appObj", clcommand);
	}

	public ClientCommand getCommand(){
		return clcommand;
	}
	@Override
	public void run(){
		while(true){
			//microphone.clear();
			if(!microphone.isRecording()){
				if(!microphone.startRecording()){
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
			else{
				setupParser();

				//controller.extinguishStatusLed("yellow");

				String bestResult = null;
				while(bestResult == null || bestResult.isEmpty()){
					//controller.lightStatusLed("green");
					System.out.println("Speak command please");
					Result r = recognizer.recognize();
					if(!microphone.isRecording()){
						System.out.println("Mic is not recording");
						return;
					}
					if(r!=null){
						bestResult = r.getBestFinalResultNoFiller();
						if(bestResult == null || bestResult.isEmpty()){
							System.out.println("Cannot hear command");
						}
					}
				}

				//controller.extinguishStatusLed("green");

				//String filename = "audio/audio" + i + ".wav";
				//saveAudio(filename, microphone.getUtterance());
				//microphone.stopRecording();
				System.out.println("Result: " + bestResult);
				parseCommand(bestResult);
			}
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i++;
	}
}
