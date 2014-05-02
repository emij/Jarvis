package controller;

import devices.AbstractDevice;

public interface Controller {
	//Sets the specified pin high for the provided duration (in ms)
	public void pinPulse(int pin, long duration);

	//Sets the specified pin high
	public void pinSetHigh(int pin);

	//Sets the specified pin low
	public void pinSetLow(int pin);

	//Checks if the wanted pin is available and if so grabs it and provisions it as in/out
	//Returns true on success, false otherwise
	public boolean assignPin(String direction, String name, int pin);

	//Finds an available pin, if one is found it is grabbed and provisioned as in/out.
	//Returns the assigned pin, -1 if no free pin is found
	public int assignPin(String direction, String name);

	//Release the specified pin so it later can be assigned to something else
	public void releasePin(int pin);

	//Controls a (NEXA) radio outlet
	//id is the message will claim to be sent from
	//channel corresponds to the numbers on the physical remote
	//state indicates whether to send an on (1) or off (0) signal (other won't have any effect)
	public void radio(int id, int channel, int state);

	//TODO should this be part of the MotionSensor class?
	//Sets up the controller to be able to respond to movements detected by an attached motion sensor
	public void motionSensor();

	//Determines if enough time has passed since last trigger of the motion sensor to go to sleep
	//Returns true if the prototype should sleep, false otherwise
	public boolean isAsleep();

	//Add a device that can be deactivated after period of inactivity
	public void addSmartSleepDevice(AbstractDevice dev);

	//	//Disables all devices that are "smart sleep" enabled
	//	private void sleepDevices() {
	//		Iterator<AbstractDevice> it = smartSleepDevices.iterator();
	//		while (it.hasNext()) {
	//			it.next().disable();
	//		}
	//		System.out.println("All devices put to sleep");
	//	}

	//	//Shows all assigned pins, the name of the attached device and in-/output status
	//	public void printPinStatus() {
	//		for (int i=0; i<8; i++) {
	//			System.out.print("Pin: " + i);
	//			if (pins[i] != null) {
	//				System.out.println(" assigned to: " + pins[i].getName() + ", mode: " + pins[i].getMode());
	//			} else {
	//				System.out.println(" not assigned");
	//			}
	//		}
	//	}

	//Sets up pins to be used for the status LEDs, one each of the colors red, yellow, green
	public void setupStatusLeds();

	//	//Lights the status led of the provided color for the specified duration (in ms)
	//	public void pulseStatusLed(String color, long duration) {
	//		pinPulse(statusLeds.get(color.toLowerCase()), duration);
	//	}

	//	//Lights the status led of the provided color
	//	public void lightStatusLed(String color) {
	//		pinSetHigh(statusLeds.get(color.toLowerCase()));
	//	}

	//	//Extinguishes the status led of the provided color
	//	public void extinguishStatusLed(String color) {
	//		pinSetLow(statusLeds.get(color.toLowerCase()));
	//	}

	//Puts the prototype to sleep until movement is registered by an attached motion sensor
	public void goToSleep();
}
