package util;

import client.Client;

/**
 * Command from network
 * @author Emil Johansson
 */

public class ClientCommand extends AbstractCommand{
	Client client;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7022538022453428284L;
	public ClientCommand(Client client){
		super();
		this.client = client;
	}
	public void generateCommand(){
		client.writeToServer(this);
		System.out.println(this.device+" "+action+" "+" "+pos+" "+param);
	}
}
