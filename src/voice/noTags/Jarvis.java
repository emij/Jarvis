/**
 *  Class for recognition and input from source
 *
 * @author Marika Hansson
 * @author Markus Otterberg
 */

package voice.noTags;

import java.io.IOException;
import java.net.URL;

import javax.speech.EngineException;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

public class Jarvis extends Thread{

	private ConfigurationManager cm;
	private Recognizer recognizer;
	private Microphone microphone;
	
	public Jarvis(URL u){
			try {
				setConfiguration(u);
				setup();
			} catch (IOException | EngineException | RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public Jarvis(){
		try {
			setConfiguration(null);
			setup();
		} catch (IOException | EngineException | RuntimeException e) {
			// TODO Auto-generated catch block
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
	private void setup() throws RuntimeException, PropertyException, IOException, EngineException{
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
				String bestResult = r.getBestFinalResultNoFiller();
				System.out.println(r.getTimedBestResult(false, true));
				
				if(r != null && bestResult.length() > 0){
					System.out.println(bestResult);
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
