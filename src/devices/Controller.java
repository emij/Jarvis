package devices;

import java.io.IOException;

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
	
	public Controller() {
		gpio = GpioFactory.getInstance();
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
	
	public int assignPin(String direction, String name) {
		if(direction.toLowerCase().equals("output")); {
			pins[1] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, name);
			pins[1].setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF, PinMode.DIGITAL_OUTPUT);
		}
		
		return 0;
	}

	public void releasePin(int pin) {
		pins[pin] = null;
		
	}
}
