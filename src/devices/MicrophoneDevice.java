package devices;

public class MicrophoneDevice extends AbstractDevice {
	private boolean active = true;
	public MicrophoneDevice(String name) {
		super(name);
	}
	
	@Override
	public boolean enable() {

		System.out.println(getName() + " turned on");
		active = !active;
		return true; //Might be unnecessary

	}

	@Override
	public boolean disable() {
		System.out.println(getName() + " turned off");
		active = !active;
		return false; //Might be unnecessary
	}
	public boolean isActive(){
		return active;
	}
}
