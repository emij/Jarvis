package devices;

//TODO might build a controller class instead, for sending RF-signals

public  abstract class AbstractDevice {
	private boolean active = false;
	private String name;

	public AbstractDevice(String name){
		this.name = name;
	}
	public AbstractDevice (String name, boolean active){
		this.name = name;
		this.active = active;
	}
	public String getName() {
		return name;
	}

	public abstract boolean enable();

	public abstract boolean disable();

	public boolean isActive() {
		return active;
	}
	
	//Temporary measure, might get removed with active flag
	public void activate() {
		active = true;
	}
	
	//Temporary measure, might get removed with active flag
	public void deactivate() {
		active = false;
	}

	//	public void flipSwitch() {
	//		active = !active;
	//	}

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
		if (active != other.active)
			return false;
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
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
}