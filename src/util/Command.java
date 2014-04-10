package util;

import java.util.Observable;

import core.Core;

public class Command extends Observable{
	private String action;
	private String device;
	private String pos;
	private String param;
	private Core core;
	public Command(){
		core = Core.INSTANCE;
	}
	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
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

	public void generateCommand(){
		System.out.println(device+" "+action+" "+" "+pos+" "+param);
		core.controlDevice(this);
	}
	public void resetCommand(){
		action = null;
		device = null;
		pos = null;
		param = null;
	}
}
