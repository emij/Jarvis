package ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import devices.Device;
import edu.cmu.sphinx.frontend.util.Microphone;
import voice.TestVoice;
import voice.VoiceCommand;

public class Core implements Observer  {
	private VoiceCommand voiceCommand;
	private String mic = "microphone";
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

		addDevice(new Device("lamp"));
		addDevice(new Device("kitchen"));
		addDevice(new Device("coffee"));
		addDevice(new Device("bathroom"));
		addDevice(new Device("radio"));
		addDevice(new Device("microphone", true));

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
			if(vCommand.getCommand().length() != 0){
				String[] strArray = vCommand.getCommand().split(" ");
				Device dev = devices.get(strArray[0]); // get device from hashmap
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

		}

	}
}

