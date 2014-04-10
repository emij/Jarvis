package client;


public class ClientCommand{
	private String action;
	private String device;
	private String pos;
	private String param;
	
	public ClientCommand(){
		
	}
	public ClientCommand(String device){
		this.device = device;
	}
	
	public String getParam() {
		return param;
	}

	public String getAction() {
		return action;
	}

	public String getDevice() {
		return device;
	}

	public String getPos() {
		return pos;
	}
	public void addDevice(String device){
		this.device = device;
	}
	public void addAction(String action){
		this.action = action;
	}
		
	public void addPosition(String pos){
		this.pos = pos;
	}
	
	public void addParam(String param){
		this.param = param;
	}

	public void resetCommand(){
		action = null;
		pos = null;
		param = null;
	}
}
