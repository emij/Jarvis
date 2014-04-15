package util;


public class ClientCommand extends AbstractCommand{
	
	public ClientCommand(){
		super();
	}
	public void generateCommand(){
		System.out.println(this.device+" "+action+" "+" "+pos+" "+param);
	}
}
