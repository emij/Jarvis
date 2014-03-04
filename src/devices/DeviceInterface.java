package devices;

public interface DeviceInterface {
	
	boolean on();
	boolean off();
	boolean isActive();
	void flipSwitch();
}
