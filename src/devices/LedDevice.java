package devices;

public class LedDevice extends AbstractDevice {
	
	private Controller controller;

	public LedDevice(String name, Controller controller) {
		super(name);
		
		this.controller = controller;
		controller.assignPin("output", name);
	}

	@Override
	public boolean enable() {
		System.out.println(getName() + " turned on WOOHOOO");

		controller.pinSetHigh(1,2000);

		return true; //Might be unnecessary
	}

	@Override
	public boolean disable() {
		System.out.println(getName() + " turned off");

		

		return true; //Might be unnecessary
	}

	//Funkar inte att ha det på detta sättet, om t.ex. tänding misslyckas måste man först säga lamp disable innan
	//det kan gå att tända
	//	public void flipSwitch(int state) {
	//
	//	}

}
