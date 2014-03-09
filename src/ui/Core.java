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
	
	private Map<String, Device> devices = new HashMap<String, Device>(); // Maybe should put deviceInterface instead of object
		
	public Core(){
		// Adding devices to hashmap
		setUpDevices();
		
		voiceCommand = new VoiceCommand(); 
		voiceCommand.addObserver(this);
		
		// Setup device
		
		
	
		TestVoice tst = new TestVoice(voiceCommand);
		tst.start();
	}
	private void setUpDevices() {
		// Will do this in a better way down the road. possible load everything from a settings file
		Device lamp = new Device("lamp");
		Device kitchen = new Device("kitchen");
		Device coffee = new Device("coffee");
		Device bathroom = new Device("bathroom");
		Device radio = new Device("radio");
		
		devices.put("lamp", lamp);
		devices.put("kitchen", kitchen);
		devices.put("coffee", coffee);
		devices.put("bathroom", bathroom);
		devices.put("radio", radio);
		
	}
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof VoiceCommand){
			VoiceCommand vCommand = (VoiceCommand)o;
			
			String[] strArray = vCommand.getCommand().split(" ");
			Device dev = devices.get(strArray[0]);
			if(dev != null){
				try {
					Method method = dev.getClass().getDeclaredMethod(strArray[1], new Class[] {});
					method.invoke(dev, null);
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
			
		}
		
	}

}
