package devices;

public class LedDevice extends AbstractDevice {

	private Controller controller;
	private int pinNr;

	public LedDevice(String name, Controller controller, int pin) {
		super(name);

		this.controller = controller;
		pinNr = pin;
		controller.assignPin("output", name, pinNr); //TODO handle error if pin is occupied		
	}

	@Override
	public boolean enable() {
		System.out.println(getName() + " turned on WOOHOOO");
		controller.pinSetHigh(pinNr,2000);
		return true; //Might be unnecessary
	}

	@Override
	public boolean disable() {
		System.out.println(getName() + " turned off");
		return true; //Might be unnecessary
	}
}
