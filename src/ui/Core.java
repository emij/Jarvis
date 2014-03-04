package ui;

import java.util.Observable;
import java.util.Observer;

import voice.TestVoice;
import voice.VoiceCommand;

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
		if (o instanceof VoiceCommand){
			VoiceCommand tmp = (VoiceCommand)o;
			System.out.println(tmp.getCommand());
		}
		
	}

}
