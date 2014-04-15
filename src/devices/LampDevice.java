package devices;

public class LampDevice extends AbstractDevice {
	
	private Controller controller;

	public LampDevice(String name, Controller controller) {
		super(name);
		this.controller = controller;
	}

	@Override
	public boolean enable() {
		System.out.println(getName() + " turned on WOOHOOO");
		controller.radio(1,0,1);
		return true; //Might be unnecessary
	}

	@Override
	public boolean disable() {
		System.out.println(getName() + " turned off");
		controller.radio(1,0,0);
		return true; //Might be unnecessary
	}
}
