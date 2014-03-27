package main;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.sphinx.frontend.util.AudioFileDataSource;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;


//Commented part are for audio file support.
public class TestGrammar {

	String[] sentences = {"Turn on lamp", "lamp", "disable the kitchen lights", "kitchen"};
	String[] expected = {"Turn on lamp", null, "disable the kitchen lights", null};
	ConfigurationManager cm;
	Recognizer recognizer;
	Microphone mic;
	URL audio;

	@Before
	public void beforeTest(){
		cm = new ConfigurationManager(Jarvis.class.getResource("jarvis.config.xml"));
		//audio = Jarvis.class.getResource("audio.wav");
		recognizer = (Recognizer) cm.lookup("recognizer");
		mic = (Microphone) cm.lookup("microphone");

		recognizer.allocate();

		//AudioFileDataSource dataSource = (AudioFileDataSource) cm.lookup("audioFileDataSource");
		//dataSource.setAudioFile(audio, null);
	}

	@Test
	public void test() {
		mic.startRecording();
		int i = 0;
		for(String e: expected){
			System.out.println("Please say: " + sentences[i]);
			Result result = recognizer.recognize();
			assertTrue(result.getBestFinalResultNoFiller().equalsIgnoreCase(e));
			i++;
		}
	}
	
	@After
	public void afterTest(){
		recognizer.deallocate();
		System.exit(0);
	}
}
