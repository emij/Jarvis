package util;


import core.Core;


public class Command extends AbstractCommand{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2436436072460623364L;
	public void generateCommand(){
		System.out.println(device+" "+action+" "+" "+pos+" "+param);
		if(pos != null){
			device = pos + device;
		}
		Core.INSTANCE.controlDevice(this);
		resetCommand();
	}
	public void copyCommand(ClientCommand tmp){
		this.action = tmp.action;
		this.device = tmp.device;
		this.param = tmp.param;
		this.pos = tmp.pos;
	}
}
