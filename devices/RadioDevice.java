package devices;

public class RadioDevice extends AbstractDevice {

	public RadioDevice(String name) {
		super(name);
	}
	@Override
	public boolean enable() {

		System.out.println(getName() + " turned on WOOHOOO");
		//			flipSwitch();
		super.activate();
		return true; //Might be unnecessary

	}

	@Override
	public boolean disable() {
		System.out.println(getName() + " turned off");
		//			flipSwitch();
		super.deactivate();
		return false; //Might be unnecessary
	}
}
