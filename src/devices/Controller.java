package devices;

import java.io.IOException;

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

	//Singleton??
	private GpioController gpio;
	private GpioPin pins[] = new GpioPin[8];
	private Pin pinNames[] = new Pin[8];
	private int usedPins = 0;
	static long lastMovement; //TODO initiate?

//	private Set<Integer> availablePins = new HashSet<Integer>();
//	private Map<Integer,RaspiPin> pinMap = new HashMap<Integer, RaspiPin>();

	public Controller() {
		gpio = GpioFactory.getInstance();

		//		for(int i = 0; i<8; i++) {
		//			availablePins.add(i);
		//			pinMap.add(i,RaspiPin.);
		//		}

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

	// Synchronized??
	public synchronized void pinSetHigh(int pin, long duration) {
		if (pins[pin].getMode() == PinMode.DIGITAL_OUTPUT) {
			((GpioPinDigitalOutput) pins[pin]).pulse(duration);
		} // TODO Do something if pin is not output? maybe return false
	}

	//Checks if the wanted pin is available and if so grabs it and provisions it as in/out
	public boolean assignPin(String direction, String name, int pin) {
		if(pins[pin] != null) {
			return false; //The specified pin was not available
		}

		if(direction.toLowerCase().equals("output")) {
			pins[pin] = gpio.provisionDigitalOutputPin(pinNames[pin], name);
		} else if(direction.toLowerCase().equals("input")) {
			pins[pin] = gpio.provisionDigitalInputPin(pinNames[pin], name);
		}
		pins[pin].setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF, PinMode.DIGITAL_OUTPUT);
		usedPins++;

		return true;
	}

	//TODO implement method for assign available pin, maybe add this logic to the existing method
	//Finds an available pin, if one is found it is grabbed and provisioned as in/out
	public int assignPin(String direction, String name) {
		// Necessary? Saves some time when all pins are used, but isn't needed for correct functionality
		if (usedPins >= 8) {
			return -1; // No more available pins, very 'C'
		}

		int pin = -1;
		for (int i=0; i<7; i++) {
			if(pins[i] != null) {
				pin = i;
				assignPin(direction, name, pin);
			}
		}

		return pin;
	}

	public void releasePin(int pin) {
		//TODO Check if pin is actually assigned, not strictly necessary
		pins[pin].clearProperties();
		pins[pin] = null;
		usedPins--;
		//availablePins.add(pin);
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
					}
				}
			});
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
}
