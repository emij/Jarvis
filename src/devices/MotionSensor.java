package devices;

public class MotionSensor extends AbstractDevice {

	private Controller controller;
	private int pinNr;

	public MotionSensor(String name, Controller controller, int pin) {
		super(name);

		this.controller = controller;
		pinNr = pin;
		if (this.controller.assignPin("input", name, pin)) {
//			this.controller.motionSensor(pinNr);
		} else {
			System.out.println("Motion sensor could not be initiated, pin " + pin + "is unavailable");
		}
	}

	@Override
	public void enable() {
		System.out.println(getName() + " turned on WOOHOOO (but nothing happens");
	}

	@Override
	public void disable() {
		System.out.println(getName() + " turned off (but nothing happens");
	}

	//TODO Is this class necessary, currently redundant. All intended logic resides in Controller.
}
