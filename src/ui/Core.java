package ui;

import java.util.Observable;
import java.util.Observer;

import devices.DeviceInterface;
import devices.Lamp;
import voice.TestVoice;
import voice.VoiceCommand;

public class Core implements Observer  {
	private VoiceCommand voiceCommand;
	Lamp lamp = new Lamp();
	
	
	public Core(){
		voiceCommand = new VoiceCommand(); 
		voiceCommand.addObserver(this);
		
		// Setup device
		
		
	
		TestVoice tst = new TestVoice(voiceCommand);
		tst.start();
	}
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof VoiceCommand){
			
			VoiceCommand vCommand = (VoiceCommand)o;
			
			String[] strArray = vCommand.getCommand().split(" ");
			if(strArray[0].equalsIgnoreCase("lamp")){
				if(strArray[1].equalsIgnoreCase("enable")){
					lamp.on();
				} else if (strArray[1].equalsIgnoreCase("disable")){
					lamp.off();
				}
			}
			
		}
		
	}

}
