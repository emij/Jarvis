package util;


public class ClientCommand extends AbstractCommand{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7022538022453428284L;
	public ClientCommand(){
		super();
	}
	public void generateCommand(){
		System.out.println(this.device+" "+action+" "+" "+pos+" "+param);
	}
}
