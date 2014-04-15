package devices;

//TODO might build a controller class instead, for sending RF-signals

public abstract class AbstractDevice {
	private String name;

	public AbstractDevice(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public abstract void enable();
	public abstract void disable();
	
	public void mute() {}
	public void unmute() {}
	
	public void upVolume(String inc) {}
	public void downVolume(String dec){}
	public void channel(String param){}
	
	public void date(){}
	public void time(){}
	public void week(){}
	public void studyWeek(){}
	public void temp(){}
	public void morning(){}
	public void night(){}
	public void bye(){}
	public void hello(){}
	
	// TODO Not sure if I should implement equals and hashcode in abstract class or in subclasses
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractDevice other = (AbstractDevice) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString(){
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
}