package devices;

public class MicrophoneDevice extends AbstractDevice {

	public MicrophoneDevice(String name) {
		super(name);
	}

	public MicrophoneDevice(String name, boolean active) {
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
