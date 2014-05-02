package devices;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InfoDevice extends AbstractDevice {

	public InfoDevice(String name) {
		super(name);
	}
	
	@Override
	public void enable() {
		System.out.println(getName() + " turned on WOOHOOO");
	}

	@Override
	public void disable() {
		System.out.println(getName() + " turned off");
	}
		
	@Override
	public void date() {
		String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		System.out.println("Today's date is " + date);
	}
	
	@Override
	public void time() {
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		System.out.println("The current time is " + time);
	}	
	
	@Override
	public void week() {
		System.out.println("Week " + Calendar.WEEK_OF_YEAR);
	}
	
	@Override
	public void studyWeek() {
		System.out.println("Waaay to high");
	}
	
	@Override
	public void temp() {
		System.out.println("Why, are you cold?");
	}
	
	@Override
	public void morning() {
		System.out.println("Good morning!");
	}
	
	@Override
	public void night() {
		System.out.println("Good night!");
	}
	
	@Override
	public void bye() {
		System.out.println("Good bye!");
	}
	
	@Override
	public void hello() {
		System.out.println("Hello!");
	}
}
