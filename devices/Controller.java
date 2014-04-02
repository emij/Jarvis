package devices;

import java.io.IOException;

public class Controller {

	//Should it return a boolean indicating success?
	public static void radio(int id, int channel, int state) {

		String command = "pihat --repeats=20 --id=" + id + " --channel=" + channel +" --state=" + state;
		try {
			Process p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); //Fix exception
		}

	}
	
	

}
