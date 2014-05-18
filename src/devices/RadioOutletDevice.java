package devices;

import controller.JarvisController;

public class RadioOutletDevice extends AbstractDevice {

	private JarvisController controller;
	private int channel;

	public RadioOutletDevice(String name, JarvisController controller, int channel) {
		super(name);
		this.controller = controller;
		this.channel = channel;
	}

	public RadioOutletDevice(String name, JarvisController controller, int channel, boolean smartSleep) {
		super(name);
		this.controller = controller;
		this.channel = channel;
		if (smartSleep) {
			this.controller.addSmartSleepDevice(this);
		}
	}

	@Override
	public void enable() {
		System.out.println(getName() + " turned on WOOHOOO");
		controller.radio(1,channel,1);
	}

	@Override
	public void disable() {
		System.out.println(getName() + " turned off");
		controller.radio(1,channel,0);
	}

	//TODO rename to SimpleDevice? Or let name stay and keep track of how it is controlled (e.g. radio, IR etc)
}
