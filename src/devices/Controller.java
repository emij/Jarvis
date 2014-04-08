package devices;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Controller {
	
	//Singleton??
	private GpioController gpio;	
	private GpioPin pins[] = new GpioPin[8];
	private Queue<Integer> availablePins = new LinkedList<Integer>();
	
	public Controller() {
		gpio = GpioFactory.getInstance();
		
		for(int i = 0; i<8; i++) {
			availablePins.add(i);
		}
	}

	//Should it return a boolean indicating success?
	public void radio(int id, int channel, int state) {

		String command = "pihat --repeats=20 --id=" + id + " --channel=" + channel +" --state=" + state;
		try {
			Process p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); //Fix exception
		}
	}
	
	public void pinSetHigh(int pin, long duration) {
		((GpioPinDigitalOutput) pins[pin]).pulse(duration);
	}
	
	//Still needs assignment logic
	public int assignPin(String direction, String name) {
		Integer pinNr = availablePins.poll();
		if(pinNr == null) {
			return -1; //No more gpio pins available (Maybe to much C here)
		}
		
		if(direction.toLowerCase().equals("output")); {
			pins[pinNr] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, name);
			pins[pinNr].setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF, PinMode.DIGITAL_OUTPUT);
		}
		
		return pinNr;
	}

	public void releasePin(int pinNr) {
		pins[pinNr] = null;
		availablePins.add(pinNr);
		
	}
}
