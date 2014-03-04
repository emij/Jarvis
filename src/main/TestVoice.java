package main;


import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;


public class TestVoice extends Thread {
	private ConfigurationManager cm;
	private Recognizer recognizer;
	private Result result;
	VoiceCommand voiceCommand;
	public TestVoice(VoiceCommand voiceCommand) {
		cm = new ConfigurationManager(TestVoice.class.getResource("testVoice.config.xml"));

		recognizer = (Recognizer) cm.lookup("recognizer");
		recognizer.allocate();
		
		this.voiceCommand = voiceCommand;
		// start the microphone or exit if the program if this is not possible
		Microphone microphone = (Microphone) cm.lookup("microphone");
		if (!microphone.startRecording()) {
			System.out.println("Cannot start microphone.");
			recognizer.deallocate();
			System.exit(1);
		}
    }
	

    // loop the recognition until the program exits.
	public void run() {
		System.out.println("( Enable | Disable ) lamp  or  ( Enable | Disable ) radio ");
		while (true) {
			System.out.println("Start speaking. Press Ctrl-C to quit.\n");

			result = recognizer.recognize();

			if (result != null) {
				String resultText = result.getBestFinalResultNoFiller();
				if(resultText.length() != 0){
					System.out.println("You said: " + resultText + '\n');
					voiceCommand.newCommand(resultText);
				} else {
					System.out.println("I can't hear what you said. \n");
				}
			} else {
				System.out.println("I can't hear what you said.\n");
			}
		}
	}
}


	