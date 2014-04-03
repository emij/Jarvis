package devices;

public class LampDevice extends AbstractDevice {

	public LampDevice(String name) {
		super(name);
	}

	@Override
	public boolean enable() {
		System.out.println(getName() + " turned on WOOHOOO");

		Controller.radio(1,0,1);

		return true; //Might be unnecessary
	}

	@Override
	public boolean disable() {
		System.out.println(getName() + " turned off");

		Controller.radio(1,0,0);

		return true; //Might be unnecessary
	}

	//Funkar inte att ha det på detta sättet, om t.ex. tänding misslyckas måste man först säga lamp disable innan
	//det kan gå att tända
	//	public void flipSwitch(int state) {
	//
	//	}

}
