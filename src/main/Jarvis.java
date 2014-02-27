/*
 * Class for recognition and input from source
 */

package main;

import edu.cmu.sphinx.util.props.ConfigurationManager;

public class Jarvis {

	ConfigurationManager cm;
	
	//TODO: set inputed configuration manager or default from file
	public void setConfiguration(String s){
		if(s.isEmpty()){
			cm = new ConfigurationManager("Jarvis.config.xml");
		}
		else{
			cm = new ConfigurationManager(s);
		}
	}
	
}
