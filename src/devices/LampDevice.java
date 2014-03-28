package devices;

import java.io.IOException;

public class LampDevice extends AbstractDevice {

	public LampDevice(String name) {
		super(name);
	}

	public LampDevice(String name, boolean active) {
		super(name, active);
	}

	@Override
	public boolean enable() {
		if(!isActive()){
			System.out.println(getName() + " turned on WOOHOOO");
			flipSwitch(1);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean disable() {
		if(isActive()){
			System.out.println(getName() + " turned off");
			flipSwitch(0);
			return true;
		} else {
		return false;
		}
	}
	
	//Funkar inte att ha det på detta sättet, om t.ex. tänding misslyckas måste man först säga lamp disable innan
	//det kan gå att tända
	public void flipSwitch(int state) {
		String command = "pihat --repeats=50 --id=1 --channel=0 --state="+state;
		try {
			Process p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); //Fixa exception
		}
		super.flipSwitch();
	}

}
