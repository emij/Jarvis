package main;

public class Lamp implements DeviceInterface{

	public boolean on() {
		System.out.println("Lamp turned on");
		return true;
	}

	public boolean off() {
		System.out.println("Lamp turned off");
		return true;
	}
	
	

}
