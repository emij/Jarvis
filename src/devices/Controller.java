package devices;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

	//TODO Singleton??
	private static Controller instance = null;
	
	private GpioController gpio;
	private GpioPin pins[] = new GpioPin[8];
	private Pin pinNames[] = new Pin[8];
	private int usedPins = 0;
	private static long lastMovement; //TODO initiate?
	private Map<String, Integer> statusLeds = new HashMap<String, Integer>(); //TODO enum instead of string for colors? TODO Look over the status leds, who "owns" them? 
	private boolean sleep = false;
	private List<AbstractDevice> smartSleepDevices = new ArrayList<AbstractDevice>();
	
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

	// TODO Synchronized??
	public synchronized void pinPulse(int pin, long duration) {
		if (pins[pin].getMode() == PinMode.DIGITAL_OUTPUT) {
			((GpioPinDigitalOutput) pins[pin]).pulse(duration);
		} // TODO Do something if pin is not output? maybe return false
	}

	// TODO Synchronized??
	public synchronized void pinSetHigh(int pin) {
		if (pins[pin].getMode() == PinMode.DIGITAL_OUTPUT) {
			((GpioPinDigitalOutput) pins[pin]).high();
		} // TODO Do something if pin is not output? maybe return false
	}

	// TODO Synchronized??
	public synchronized void pinSetLow(int pin) {
		if (pins[pin].getMode() == PinMode.DIGITAL_OUTPUT) {
			((GpioPinDigitalOutput) pins[pin]).low();
		} // TODO Do something if pin is not output? maybe return false
	}

	//Checks if the wanted pin is available and if so grabs it and provisions it as in/out
	//TODO atomic?
	public boolean assignPin(String direction, String name, int pin) {
		if(pin < 0 || pin > 7 || pins[pin] != null) {
			return false; //The specified pin was not available
		}

		if(direction.equalsIgnoreCase("output")) {
			pins[pin] = gpio.provisionDigitalOutputPin(pinNames[pin], name);
			pins[pin].setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF, PinMode.DIGITAL_OUTPUT);
		} else if(direction.equalsIgnoreCase("input")) {
			pins[pin] = gpio.provisionDigitalInputPin(pinNames[pin], name);
		}
		usedPins++;

		return true;
	}

	//TODO implement method for assign available pin, maybe add this logic to the existing method
	//Finds an available pin, if one is found it is grabbed and provisioned as in/out.
	//Returns the assigned pin, -1 if no free pin is found
	public int assignPin(String direction, String name) {
		// Necessary? Saves some time when all pins are used, but isn't needed for correct functionality
		if (usedPins >= 8) {
			return -1; // No more available pins, very 'C'
		}

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

	public void releasePin(int pin) {
		//TODO Check if pin is actually assigned, not strictly necessary
		pins[pin].clearProperties();
		pins[pin] = null;
		usedPins--;
	}

	public void radio(int id, int channel, int state) {

		String command = "pihat --repeats=20 --id=" + id + " --channel=" + channel +" --state=" + state;
		try {
			/*Process p = */ Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); //Fix exception
		}
	}

	public void motionSensor(int pin) {
		// Let controller assign the pin?
		((GpioPinDigitalInput) pins[pin]).addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				//System.out.println("Motion detected from pin " + event.getPin() + " (state " + event.getState() + ")");
				PinState state = event.getState();
				if(state == PinState.HIGH) {
					Controller.lastMovement = System.currentTimeMillis(); // TODO revisit this implementation of tracking movement
					sleep = false;
					System.out.println("Movement detected");
				}
			}
		});
	}

	public boolean isAsleep() {
		if(!sleep) {
			if ((System.currentTimeMillis() - lastMovement) > 20*1000) { //20 s to timeout, test value TODO remove magic variable, decide suitable time before sleeping
				sleep = true;
				sleepDevices();
				System.out.println("Going to sleep");
			}
		}
		return sleep;
	}
	
	public void addSmartSleepDevice(AbstractDevice dev) { //TODO rename sleep?
		smartSleepDevices.add(dev);
	}
	
	private void sleepDevices() { //TODO rename?
		Iterator<AbstractDevice> it = smartSleepDevices.iterator();
		while (it.hasNext()) {
			it.next().disable();
		}
		System.out.println("All devices put to sleep");
	}

	// Test method
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

	public void setupStatusLeds() {
		int redLed = assignPin("output", "red");
		statusLeds.put("red", redLed);

		int yellowLed = assignPin("output", "yellow");
		statusLeds.put("yellow", yellowLed);

		int greenLed = assignPin("output", "green");
		statusLeds.put("green", greenLed);
		
		lightStatusLed("red"); //Power is on
		lightStatusLed("yellow"); //Setup is in progress

	}

	public void lightStatusLed(String color) { //TODO add duration?
		pinSetHigh(statusLeds.get(color.toLowerCase())); //TODO toLowerCase() necessary?
	}
	
	public void extinguishStatusLed(String color) { //TODO add duration?
		pinSetLow(statusLeds.get(color.toLowerCase())); //TODO toLowerCase() necessary?
	}
}
