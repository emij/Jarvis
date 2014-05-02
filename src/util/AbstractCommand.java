package util;

import java.io.Serializable;

/**
 * Abstract representation of a Command object
 * Represents a command from either speech recognition or from a client application
 *  
 * @author Emil Johansson
 */
public abstract class AbstractCommand implements Serializable{
	
	private static final long serialVersionUID = 9174174993585262346L;
	protected String action;
	protected String device;
	protected String pos;
	protected String param;
	
	
	public AbstractCommand(){
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
	public abstract void generateCommand();
	
	public void resetCommand(){
		action = null;
		pos = null;
		param = null;
		device = null;
	}
}