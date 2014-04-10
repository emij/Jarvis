package core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import devices.AbstractDevice;
import devices.LampDevice;
import devices.MicrophoneDevice;
import devices.RadioDevice;
import util.Command;
import voice.objectTags.Jarvis;
//import voice.noTags.Jarvis;
//import voice.getTags.Jarvis;

public class Core  {
	private String mic = "microphone";
	private Map<String, AbstractDevice> devices = new HashMap<String, AbstractDevice>(); 
	public final static Core INSTANCE = new Core();
	// only one core should be instantiated. Controller for all hardware.
	private Core(){
		// Adding devices to hashmap
		setUpDevices();
	}
	private void setUpDevices() {
		// TODO Will do this in a better way down the road. possible load everything from a settings file

		addDevice(new LampDevice("lamp"));
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
}


