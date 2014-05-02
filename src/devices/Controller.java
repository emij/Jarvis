package devices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import util.WakeFromSleep;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Controller {
	private static Controller instance = null;	
	private GpioController gpio;
	private GpioPin pins[] = new GpioPin[8];
	private Pin pinNames[] = new Pin[8];
	private long lastMovement;
	private Map<String, Integer> statusLeds = new HashMap<String, Integer>(); //TODO Look over the status leds, who "owns" them? 
	private boolean sleep = false;
	private List<AbstractDevice> smartSleepDevices = new ArrayList<AbstractDevice>();
	private int sleepTimeout = 20*1000; //set sleep mode after 20 s //TODO decide suitable time before sleeping
	private int motionSensorPin;
	private WakeFromSleep wake;

	public static Controller getInstance()	{
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	protected Controller() {
		gpio = GpioFactory.getInstance();

		// TODO redo this part
		pinNames[0] = RaspiPin.GPIO_00;
		pinNames[1] = RaspiPin.GPIO_01;
		pinNames[2] = RaspiPin.GPIO_02;
		pinNames[3] = RaspiPin.GPIO_03;
		pinNames[4] = RaspiPin.GPIO_04;
		pinNames[5] = RaspiPin.GPIO_05;
		pinNames[6] = RaspiPin.GPIO_06;
		pinNames[7] = RaspiPin.GPIO_07;
	}
	
	//Sets the specified pin high for the provided duration (in ms)
	public void pinPulse(int pin, long duration) {
		if (pins[pin].getMode() == PinMode.DIGITAL_OUTPUT) {
			((GpioPinDigitalOutput) pins[pin]).pulse(duration);
		} // TODO Do something if pin is not output? maybe return false
	}

	//Sets the specified pin high
	public void pinSetHigh(int pin) {
		if (pins[pin].getMode() == PinMode.DIGITAL_OUTPUT) {
			((GpioPinDigitalOutput) pins[pin]).high();
		} // TODO Do something if pin is not output? maybe return false
	}

	//Sets the specified pin low
	public void pinSetLow(int pin) {
		if (pins[pin].getMode() == PinMode.DIGITAL_OUTPUT) {
			((GpioPinDigitalOutput) pins[pin]).low();
		} // TODO Do something if pin is not output? maybe return false
	}

	//Checks if the wanted pin is available and if so grabs it and provisions it as in/out
	//Returns true on success, false otherwise
	public synchronized boolean assignPin(String direction, String name, int pin) {
		if(pin < 0 || pin > 7 || pins[pin] != null) {
			return false; //The specified pin was not available
		}

		if(direction.equalsIgnoreCase("output")) {
			pins[pin] = gpio.provisionDigitalOutputPin(pinNames[pin], name);
			pins[pin].setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF, PinMode.DIGITAL_OUTPUT);
		} else if(direction.equalsIgnoreCase("input")) {
			pins[pin] = gpio.provisionDigitalInputPin(pinNames[pin], name);
		}
		return true;
	}

	//Finds an available pin, if one is found it is grabbed and provisioned as in/out.
	//Returns the assigned pin, -1 if no free pin is found
	public synchronized int assignPin(String direction, String name) {
		int pin = -1; //Returns -1 if no free pin is found
		for (int i=0; i<7; i++) {
			if(pins[i] == null) {
				if (assignPin(direction, name, i)); {
					pin = i;
					break;
				}
			}
		}
		return pin;
	}
	
	//Release the specified pin so it later can be assigned to something else
	public void releasePin(int pin) {
		//TODO Check if pin is actually assigned, not strictly necessary, however there should exist some form of check to prevent whomever from releasing any pin
		pins[pin].clearProperties();
		pins[pin] = null;
	}

	//Controls a radio outlet
	//id is the message will claim to be sent from
	//channel corresponds to the numbers on the physical remote
	//state indicates whether to send an on (1) or off (0) signal (other won't have any effect)
	public void radio(int id, int channel, int state) {
		String command = "pihat --repeats=20 --id=" + id + " --channel=" + channel +" --state=" + state;
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//TODO should this be part of the MotionSensor class?
	//Sets up the controller to be able to respond to movements detected by an attached motion sensor
	public void motionSensor() {
		motionSensorPin = assignPin("input", "sensor");
		if (motionSensorPin < 0) {
			System.out.println("Motion sensor could not be initiated, no available pins");
			//TODO add flag to indicate whether a motion sensor is active, for sleep mode purposes?
		} else {
			((GpioPinDigitalInput) pins[motionSensorPin]).addListener(new GpioPinListenerDigital() {
				@Override
				public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
					PinState state = event.getState();
					if(state == PinState.HIGH) {
						Controller controller = Controller.getInstance();
						controller.lastMovement = System.currentTimeMillis();
						sleep = false;
						System.out.println("Movement detected");
						controller.pulseStatusLed("red", 2000);
					}
				}
			});
			wake = new WakeFromSleep(pins[motionSensorPin], Thread.currentThread());
		}
	}

	//Determines if enough time has passed since last trigger of the motion sensor to go to sleep
	//Returns true if the prototype should sleep, false otherwise
	public boolean isAsleep() {
		if(!sleep) {
			if ((System.currentTimeMillis() - lastMovement) > sleepTimeout) {
				sleep = true;
			}
		}
		return sleep;
	}

	//Add a device that can be deactivated after period of inactivity
	public void addSmartSleepDevice(AbstractDevice dev) {
		smartSleepDevices.add(dev);
	}

	//Disables all devices that are "smart sleep" enabled
	private void sleepDevices() {
		Iterator<AbstractDevice> it = smartSleepDevices.iterator();
		while (it.hasNext()) {
			it.next().disable();
		}
		System.out.println("All devices put to sleep");
	}

	//Shows all assigned pins, the name of the attached device and in-/output status
	public void printPinStatus() {
		for (int i=0; i<8; i++) {
			System.out.print("Pin: " + i);
			if (pins[i] != null) {
				System.out.println(" assigned to: " + pins[i].getName() + ", mode: " + pins[i].getMode());
			} else {
				System.out.println(" not assigned");
			}
		}
	}

	//Sets up pins to be used for the status LEDs, one each of the colors red, yellow, green
	public void setupStatusLeds() {
		int redLed = assignPin("output", "red");
		statusLeds.put("red", redLed);

		int yellowLed = assignPin("output", "yellow");
		statusLeds.put("yellow", yellowLed);

		int greenLed = assignPin("output", "green");
		statusLeds.put("green", greenLed);

		lightStatusLed("yellow"); //Setup is in progress
	}

	//Lights the status led of the provided color for the specified duration (in ms)
	public void pulseStatusLed(String color, long duration) {
		pinPulse(statusLeds.get(color.toLowerCase()), duration);
	}
	
	//Lights the status led of the provided color
	public void lightStatusLed(String color) {
		pinSetHigh(statusLeds.get(color.toLowerCase()));
	}
	
	//Extinguishes the status led of the provided color
	public void extinguishStatusLed(String color) {
		pinSetLow(statusLeds.get(color.toLowerCase()));
	}

	//Puts the prototype to sleep by suspending the executing thread until movement is registered
	//by an attached motion sensor
	public void goToSleep() {
		sleepDevices();
		System.out.println("Going to sleep");
		wake.goToSleep();
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}
}
