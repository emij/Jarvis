package util;

import java.io.Serializable;

import client.Client;

/**
 * Command from network
 * @author Emil Johansson
 */

public class ClientCommand extends AbstractCommand{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3906846443658081682L;
	transient Client client;
	/**
	 * 
	 */
	public ClientCommand(Client client){
		super();
		this.client = client;
	}
	public void generateCommand(){
		if(pos != null){
			device = pos + device;
		}
		client.writeToServer(this);
		System.out.println(this.device+" "+action+" "+" "+pos+" "+param);
		resetCommand();
	}
	public String toString(){
		return "hej";
	}
}
