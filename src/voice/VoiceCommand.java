package voice;

import java.util.Observable;

public class VoiceCommand extends Observable{
	private String interpretedCommand = "";
	
	public VoiceCommand(){
	}
	
	public String getCommand(){
		return interpretedCommand;
	}

	public void newCommand(String str){
		interpretedCommand = str;
		this.setChanged();
		notifyObservers();
	}
}
