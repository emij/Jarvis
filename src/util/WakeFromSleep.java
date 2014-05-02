package util;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class WakeFromSleep extends Thread implements GpioPinListenerDigital {
	private GpioPinDigitalInput pin;
	private Thread wakeThread;
	private boolean isAsleep;

	public WakeFromSleep(GpioPin pin, Thread thread) {
		this.pin = ((GpioPinDigitalInput) pin);
		this.pin.addListener(this);
		wakeThread = thread;
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		PinState state = event.getState();
		if(isAsleep && state == PinState.HIGH) {
			isAsleep = false;
			wakeThread.interrupt();
		}
	}
	
	public void goToSleep() {
		isAsleep = true;
		
	}

	public void run() {
	}
}
