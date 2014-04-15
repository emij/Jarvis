package devices;

public class MicrophoneDevice extends AbstractDevice {
	private boolean active = true;
	public MicrophoneDevice(String name) {
		super(name);
	}
	
	@Override
	public void enable() {
		System.out.println(getName() + " turned on");
		active = !active;
	}

	@Override
	public void disable() {
		System.out.println(getName() + " turned off");
		active = !active;
	}
	
	public boolean isActive(){
		return active;
	}
	
	@Override
	public void mute() {
		disable();
	}
	
	@Override
	public void unmute() {
		enable();
	}
}
