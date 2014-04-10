package util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class AudioRecorder {

	AudioFormat format = new AudioFormat(16000, 8, 1, true, true);
	AudioFileFormat.Type type = AudioFileFormat.Type.WAVE;
	File audio = new File("sound.wav");
	TargetDataLine line;
	
	public void record() throws LineUnavailableException, IOException{
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		
		line = AudioSystem.getTargetDataLine(format);
		line.open();
		line.start();
		
		AudioInputStream input = new AudioInputStream(line);
		
		byte[] b = null;
		while(line.available() > 0){
			line.read(b, 0, 8);
		}
		
		//TODO: process the sound to check for silence.
		
		AudioSystem.write(input, type, audio);
	}
}
