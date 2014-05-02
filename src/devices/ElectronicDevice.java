package devices;

import controller.JarvisController;

public class ElectronicDevice extends AbstractDevice {
	private JarvisController controller;

	public ElectronicDevice(String name, JarvisController controller) {
		super(name);
		this.controller = controller;
	}
	
	public ElectronicDevice(String name, JarvisController controller, boolean smartSleep) {
		super(name);
		this.controller = controller;
		if (smartSleep) {
			this.controller.addSmartSleepDevice(this);
		}
	}
	
	@Override
	public void enable() {
		System.out.println(getName() + " turned on WOOHOOO");
	}

	@Override
	public void disable() {
		System.out.println(getName() + " turned off");
	}
	
	@Override
	public void upVolume(String inc) {
	}
	
	@Override
	public void downVolume(String dec) {
	}
	
	@Override
	public void channel(String param) {
	}
	
	@Override
	public void mute() {
	}
	
	@Override
	public void unmute() {
	}
}
