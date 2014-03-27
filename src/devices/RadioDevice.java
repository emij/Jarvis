package devices;

public class RadioDevice extends AbstractDevice {

	public RadioDevice(String name) {
		super(name);
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
