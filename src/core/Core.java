package core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import util.Command;
import devices.AbstractDevice;
import devices.Controller;
import devices.ElectronicDevice;
import devices.InfoDevice;
import devices.LampDevice;
import devices.MicrophoneDevice;
import devices.MotionSensor;

public class Core  {
	private String mic = "microphone";
	private Map<String, AbstractDevice> devices = new HashMap<String, AbstractDevice>(); 
	public final static Core INSTANCE = new Core();
	private Controller controller;
	private MicrophoneDevice microphone = new MicrophoneDevice(mic);

	// only one core should be instantiated. Controller for all hardware.
	private Core() {
		controller = Controller.getInstance();

		// Adding devices to hashmap
		setUpDevices();
	}
	
	private void setUpDevices() {
		// TODO Will do this in a better way down the road. possible load everything from a settings file
		controller.assignPin("output", "radioTx", 7); //Allocate GPIO-pin 07 to the Radio Transmitter
		addDevice(new MotionSensor("sensor", controller, 5)); //Allocate GPIO-pin 05 to the Motion Sensor
		//addDevice(new LedDevice("radio", controller, 1));
		addDevice(new LampDevice("lamp", controller));		
		addDevice(new ElectronicDevice("tv"));
		addDevice(new InfoDevice("info"));

		addDevice(microphone);

		controller.setupStatusLeds();

		// Test
		controller.printPinStatus();
	}
	public void addDevice(AbstractDevice dev){
		devices.put(dev.getName(), dev);
	}
	public boolean removeDevice(AbstractDevice dev){ 
		return (devices.remove(dev.getName()) != null); //return true if found and removed
	}
	// strArray should be on the form Device | method | param
	// not sure about synchronized here, but may need it
	public synchronized void controlDevice(Command command){
		AbstractDevice dev = devices.get(command.getDevice()); // get device from hashmap
		if(dev != null){
			if(microphone.isActive() || 
					dev.getName().equalsIgnoreCase(mic)){
				try {
					Method method = dev.getClass().getDeclaredMethod(command.getAction(), new Class[] {});
					method.invoke(dev, null);
					// TODO create proper handling of exceptions
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("Microphone is muted");
			}
		} else {
			System.out.println("I am sorry, but the device " + command.getDevice() + " is not installed");
		}
		command.resetCommand();
	}
}


