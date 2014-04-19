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
	
	public LedDevice(String name, Controller controller, int pin, boolean smartSleep) {
		super(name);
		this.controller = controller;
		pinNr = pin;
		if (smartSleep) {
			this.controller.addSmartSleepDevice(this);
		}
	}

	@Override
	public void enable() {
		System.out.println(getName() + " turned on WOOHOOO");
		controller.pinPulse(pinNr,2000);
	}

	@Override
	public void disable() {
		System.out.println(getName() + " turned off");
	}
}
