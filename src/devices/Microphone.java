package devices;

public class Microphone extends Device {
	public Microphone(String name){
		super(name);
		this.active = true;
	}

	@Override
	public boolean enable() {
		System.out.println("Mic on");
		return super.enable();
	}

	@Override
	public boolean disable() {
		System.out.println("Mic off");
		return super.disable();
	}
	

}
