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
import voice.Jarvis;

public class Core implements Observer  {
	private Command voiceCommand;
	private String mic = "microphone";
	private Map<String, AbstractDevice> devices = new HashMap<String, AbstractDevice>(); 

	public Core(){
		// Adding devices to hashmap
		setUpDevices();

		voiceCommand = new Command(); 
		voiceCommand.addObserver(this);

		Jarvis tst = new Jarvis(voiceCommand);
		
		tst.start();
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
	public synchronized void controlDevice(String[] strArray){
		AbstractDevice dev = devices.get(strArray[0]); // get device from hashmap
		if(dev != null){
			if(devices.get(mic).isActive() || 
					dev.getName().equalsIgnoreCase(mic)){
				try {
					Method method = dev.getClass().getDeclaredMethod(strArray[1], new Class[] {});
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
			System.out.println("I am sorry, but the device " + strArray[0] + " is not installed");
		}
	}
	
	@Override
	public void update(Observable o, Object arg) { // maybe should have synchronized here instead
		if (o instanceof Command){
			Command voiceCommand = (Command)o;
			// Divide voicecommand into a stringarray
			if(voiceCommand.getCommand().length() != 0){
				String[] strArray = voiceCommand.getCommand().split(" ");
				// Actual control of devices in another method, for GUI-reasons
				controlDevice(strArray);
			}
		}
	}
}


