package devices;

import java.io.IOException;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public class Controller {
	
	//Singleton??
	GpioController gpio;
	GpioPinDigitalOutput pin;
	
	GpioPin pins[] = new GpioPin[8];
	
	public Controller() {
		gpio = GpioFactory.getInstance();
		//pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED");
	}

	//Should it return a boolean indicating success?
	public static void radio(int id, int channel, int state) {

		String command = "pihat --repeats=20 --id=" + id + " --channel=" + channel +" --state=" + state;
		try {
			Process p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); //Fix exception
		}
	}
	
	public int assignPin(String direction, String name) {
		if(direction.equals("output")); {
			pins[0] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, name);
		}
		
		return 0;
	}

	public void releasePin() {
		
	}
}
