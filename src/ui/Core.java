package ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import devices.Device;
import voice.TestVoice;
import voice.VoiceCommand;

public class Core implements Observer  {
	private VoiceCommand voiceCommand;
	
	private Map<String, Device> devices = new HashMap<String, Device>(); 
		
	public Core(){
		// Adding devices to hashmap
		setUpDevices();

		voiceCommand = new VoiceCommand(); 
		voiceCommand.addObserver(this);
		
		TestVoice tst = new TestVoice(voiceCommand);
		tst.start();
	}
	private void setUpDevices() {
		// TODO Will do this in a better way down the road. possible load everything from a settings file
		Device lamp = new Device("lamp");
		Device kitchen = new Device("kitchen");
		Device coffee = new Device("coffee");
		Device bathroom = new Device("bathroom");
		Device radio = new Device("radio");
		
		devices.put(lamp.getName(), lamp);
		devices.put(kitchen.getName(), kitchen);
		devices.put(coffee.getName(), coffee);
		devices.put(bathroom.getName(), bathroom);
		devices.put(radio.getName(), radio);
		
	}
	public void addDevice(Device dev){
		devices.put(dev.getName(), dev);
	}
	public boolean removeDevice(Device dev){ 
		return (devices.remove(dev.getName()) != null); //return true if found and removed
	}
	// TODO needs to be rewritten
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof VoiceCommand){
			VoiceCommand vCommand = (VoiceCommand)o;
			
			// Divide voicecommand into a stringarray
			String[] strArray = vCommand.getCommand().split(" ");
			Device dev = devices.get(strArray[0]); // get device from hashmap
			if(dev != null){
				try {
					Method method = dev.getClass().getDeclaredMethod(strArray[1], new Class[] {});
					method.invoke(dev, null);
					// TODO create proper exceptionhandling
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
				System.out.println("I am sorry, but the device " + strArray[0] + " is not installed");
			}
			
		}
		
	}

}
