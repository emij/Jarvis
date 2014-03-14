package devices;

public class RadioDevice extends Device {

	public RadioDevice(String name) {
		super(name);
		// TODO Auto-generated constructor stub
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
}
