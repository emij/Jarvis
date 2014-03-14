package main;

import java.util.Observable;

public class Command extends Observable{
	private String command = "";
	
	public Command(){
	}
	
	public String getCommand(){
		return command;
	}

	public void newCommand(String str){
		command = str;
		this.setChanged();
		notifyObservers();
	}
}
