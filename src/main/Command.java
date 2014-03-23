package main;

import java.util.Observable;

public class Command extends Observable{
	private String command;
	private String action = "null";
	private String device = "null";
	private String pos = "null";
	private String param = "null";
	
	public Command(){
	}

	public void addAction(String action){
		this.action = action;
	}
	
	public void addDevice(String device){
		this.device = device;
	}
	
	public void addPosition(String pos){
		this.pos = pos;
	}
	
	public void addParam(String param){
		this.param = param;
	}
	
	public String getCommand(){
		return command;
	}

	public void generateCommand(){
		command = action + " " + device + " " + pos + " " + param;
		System.out.println(command);
		this.setChanged();
		notifyObservers(command);
	}
}
