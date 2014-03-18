package main;

public class CommandObject {
	
	String action;
	String device;
	
	public CommandObject() {
	}
	
	public void addAction(String action){
		this.action = action;
	}
	
	public void addDevice(String device){
		this.device = device;
	}
	
	public void addPosition(String position){
		
	}
	
	public void addParam(String param){
		
	}
	
	public void generateCommand(){
		//Command c = new Command();
		//c.newCommand(action + " " + device);
		System.out.println(action + " " + device);
	}
}
