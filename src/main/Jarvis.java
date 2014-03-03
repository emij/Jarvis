/*
 * Class for recognition and input from source
 */

package main;

import java.io.IOException;
import java.net.URL;

import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

public class Jarvis {

	ConfigurationManager cm;
	
	public void setConfiguration(URL s){
		if(s == null){
			try {
				cm = new ConfigurationManager(new URL("config.xml"));
			} catch (IOException | PropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			try {
				cm = new ConfigurationManager(s);
			} catch (IOException | PropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
