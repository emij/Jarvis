package devices;

public class ElectronicDevice extends AbstractDevice {

	public ElectronicDevice(String name) {
		super(name);
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
	public void upVol(String inc) {
	}
	
	@Override
	public void downVol(String dec) {
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
