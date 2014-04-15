package devices;

public class LampDevice extends AbstractDevice {
	
	private Controller controller;

	public LampDevice(String name, Controller controller) {
		super(name);
		this.controller = controller;
	}

	@Override
	public void enable() {
		System.out.println(getName() + " turned on WOOHOOO");
		controller.radio(1,0,1);
	}

	@Override
	public void disable() {
		System.out.println(getName() + " turned off");
		controller.radio(1,0,0);
	}
	
	//TODO rename to SimpleDevice? Or let lamp device stay and keep track of how it is controlled (e.g. radio, IR etc)
}
