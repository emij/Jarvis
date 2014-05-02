package util;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class WakeFromSleep extends Thread implements GpioPinListenerDigital {
	private GpioPinDigitalInput pin; //Pin number that should be listened to
	private Thread wakeThread;
	private boolean isAsleep; //Indicates whether the prototype is currently sleeping or not

	public WakeFromSleep(GpioPin pin, Thread thread) {
		this.pin = ((GpioPinDigitalInput) pin);
		this.pin.addListener(this);
		wakeThread = thread;
	}

	@Override
	//When the device is asleep and a high edge is detected on the listening pin this method
	//will wake the prototype
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		PinState state = event.getState();
		if(isAsleep && state == PinState.HIGH) {
			isAsleep = false;
			wakeThread.interrupt();
		}
	}
	
	//Called when the prototype is going to sleep
	public void goToSleep() {
		isAsleep = true;		
	}

	public void run() {
	}
}
