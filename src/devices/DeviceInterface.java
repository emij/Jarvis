package devices;

public interface DeviceInterface {
	
	boolean enable();
	boolean disable();
	boolean isActive();
	void flipSwitch();
	String toString();
	int hashCode();
	boolean equals(Object obj);
	String getName();
}
