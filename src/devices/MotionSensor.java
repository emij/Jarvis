package devices;

public class MotionSensor extends Thread {

	private Controller controller;
	private int pinNr;

	public MotionSensor(String name, Controller controller, int pin) {
		super(name);

		this.controller = controller;
		pinNr = pin;
		if (controller.assignPin("input", name, pin)) { //ToDo, handle error if pin is occupied
			System.out.println("Motion sensor " + name + " assigned pin " + pin); //temptest
		}
	}

//	@Override
//	public boolean enable() {
//				System.out.println(getName() + " turned on WOOHOOO");
//				controller.pinSetHigh(1,2000);
//				return true; //Might be unnecessary
//	}

//	@Override
//	public boolean disable() {
//				System.out.println(getName() + " turned off");
//				return true; //Might be unnecessary
//	}
	
	@Override
	public void run() {
		while(true) {
			try {
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
