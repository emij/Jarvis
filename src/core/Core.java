package core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import util.Command;
import devices.AbstractDevice;
import devices.Controller;
import devices.LampDevice;
import devices.LedDevice;
import devices.MicrophoneDevice;
import devices.MotionSensor;
import devices.RadioDevice;
//import voice.objectTags.Jarvis;
import voice.noTags.Jarvis;
//import voice.getTags.Jarvis;

public class Core implements Observer  {
	private Command voiceCommand;
	private Controller controller;
	private String mic = "microphone";
	private Map<String, AbstractDevice> devices = new HashMap<String, AbstractDevice>(); 

	public Core(){
		voiceCommand = new Command(); 
		voiceCommand.addObserver(this);
		
		controller = new Controller();
		
		// Adding devices to hashmap
		setUpDevices();
		
		// Test
		controller.printPinStatus();

		//Jarvis tst = new Jarvis(voiceCommand);
		Jarvis tst = new Jarvis();
		tst.start();
	}
	private void setUpDevices() {
		// TODO Will do this in a better way down the road. possible load everything from a settings file
		controller.assignPin("output", "radioTx", 7); //Allocate GPIO-pin 07 to the Radio Transmitter
		addDevice(new MotionSensor("sensor", controller, 5)); //Allocate GPIO-pin 05 to the Motion Sensor
		addDevice(new LedDevice("tv", controller, 1));
		addDevice(new LampDevice("lamp", controller));		
		addDevice(new RadioDevice("radio"));
		addDevice(new MicrophoneDevice("microphone", true));

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
			if(devices.get(mic).isActive() || 
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
			}
		} else {
			System.out.println("I am sorry, but the device " + command.getDevice() + " is not installed");
		}
		command.resetCommand();
	}
	
	@Override
	public void update(Observable o, Object arg) { // maybe should have synchronized here instead
		if (o instanceof Command){
			Command voiceCommand = (Command)o;
		controlDevice(voiceCommand);
		}
	}
}


