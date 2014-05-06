package core;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import controller.JarvisController;

import devices.AbstractDevice;
import devices.ElectronicDevice;
import devices.InfoDevice;
import devices.MicrophoneDevice;
import devices.RadioOutletDevice;

/**
 * Mid-layer between interpretation and hardware controller
 * Singleton
 *  
 * @author Emil Johansson
 */

public class Core  {
	private String mic = "microphone";
	private Map<String, AbstractDevice> devices = new HashMap<String, AbstractDevice>(); 
	public final static Core INSTANCE = new Core();
	private JarvisController controller;
	private MicrophoneDevice microphone = new MicrophoneDevice(mic);

	// only one core should be instantiated. Controller for all hardware.
	private Core() {
		controller = JarvisController.INSTANCE;

		// Adding devices to hashmap
//		setUpDevices();
	}
	
	private void setUpDevices() {
		// TODO Will do this in a better way down the road. possible load everything from a settings file
		controller.setupStatusLeds();
		controller.assignPin("output", "radioTx", 7); //Allocate GPIO-pin 07 to the Radio Transmitter
		//addDevice(new MotionSensor("sensor", controller, 5)); //Allocate GPIO-pin 05 to the Motion Sensor
		controller.motionSensor();
		//addDevice(new LedDevice("radio", controller, 1));
		addDevice(new RadioOutletDevice("lamp", controller, true));		
		addDevice(new ElectronicDevice("tv", controller));
		addDevice(new InfoDevice("info"));
		addDevice(microphone);

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
	
	/*
	 * Method for controlling devices
	 * @param Command
	 */
	public synchronized void controlDevice(String device, String action, String pos, String param){
		AbstractDevice dev = devices.get(device); // get device from hashmap
		if(dev != null){
			if(microphone.isActive() || 
					dev.getName().equalsIgnoreCase(mic)){
				try {
					if(param == null) {
						Method method = dev.getClass().getDeclaredMethod(action, new Class [] {});
						method.invoke(dev, null);
					} else {
						Method method = dev.getClass().getDeclaredMethod(action, new Class [] {param.getClass()});
						method.invoke(dev, param);
					}
					// TODO create proper handling of exceptions
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
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
			System.out.println("I am sorry, but the device " + device + " is not installed");
		}
	}
}


