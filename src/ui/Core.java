package ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import devices.DeviceInterface;
import devices.Lamp;
import voice.TestVoice;
import voice.VoiceCommand;

public class Core implements Observer  {
	private VoiceCommand voiceCommand;
	
	private Map<String, DeviceInterface> devices = new HashMap<String, DeviceInterface>(); // Maybe should put deviceInterface instead of object
		
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
		Lamp lamp = new Lamp("lamp");
		devices.put("lamp", lamp);
		
	}
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof VoiceCommand){
			VoiceCommand vCommand = (VoiceCommand)o;
			
			String[] strArray = vCommand.getCommand().split(" ");
			DeviceInterface dev = devices.get(strArray[0]);
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
