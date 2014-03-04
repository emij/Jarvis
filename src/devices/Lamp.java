package devices;

public class Lamp implements DeviceInterface{

	boolean active = false;

	public boolean on() {
		if(!isActive()){
			System.out.println("Lamp turned on");
			flipSwitch();
			return true;
		} else {
			return false;
		}
	}

	public boolean off() {
		if(isActive()){
			System.out.println("Lamp turned off");
			flipSwitch();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void flipSwitch() {
		active = !active;
	}
	
	



}
