package devices;

public class RadioOutletDevice extends AbstractDevice {

	private Controller controller;

	public RadioOutletDevice(String name, Controller controller) {
		super(name);
		this.controller = controller;
	}

	public RadioOutletDevice(String name, Controller controller, boolean smartSleep) {
		super(name);
		this.controller = controller;
		if (smartSleep) {
			this.controller.addSmartSleepDevice(this);
		}
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

	//TODO rename to SimpleDevice? Or let name stay and keep track of how it is controlled (e.g. radio, IR etc)
}
