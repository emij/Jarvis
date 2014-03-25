package devices;

public class LampDevice extends Device {

	public LampDevice(String name) {
		super(name);
	}

	public LampDevice(String name, boolean active) {
		super(name, active);
	}

	@Override
	public boolean enable() {
		if(!isActive()){
			System.out.println(getName() + " turned on WOOHOOO");
			flipSwitch();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean disable() {
		if(isActive()){
			System.out.println(getName() + " turned off");
			flipSwitch();
			return true;
		} else {
		return false;
		}
	}

}
