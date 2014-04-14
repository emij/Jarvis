package devices;

public class MotionSensor extends AbstractDevice {

	private Controller controller;
	private int pinNr;

	public MotionSensor(String name, Controller controller, int pin) {
		super(name);

		this.controller = controller;
		pinNr = pin;
		if (this.controller.assignPin("input", name, pin)) { //TODO, handle error if pin is occupied
			this.controller.motionSensor(pinNr); //TODO this feels wrong
		} else {
			System.out.println("Motion sensor could not be initiated, pin " + pin + "is unavailable");
		}
		
	}

	@Override
	public boolean enable() {
				System.out.println(getName() + " turned on WOOHOOO (but nothing happens"); //TODO implement enable
				return true; //Might be unnecessary
	}

	@Override
	public boolean disable() {
				System.out.println(getName() + " turned off (but nothing happens"); //TODO implement disable
				return true; //Might be unnecessary
	}
	
	//TODO Is this class necessary?
}