package main;

import java.util.Observable;
import java.util.Observer;

public class Core implements Observer  {
	private VoiceCommand voiceCommand;
	
	public Core(){
		voiceCommand = new VoiceCommand(); 
		voiceCommand.addObserver(this);
	
		TestVoice tst = new TestVoice(voiceCommand);
		tst.start();
	}
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("CORE");
		if (o instanceof VoiceCommand){
			VoiceCommand tmp = (VoiceCommand)o;
		}
		
	}

}
