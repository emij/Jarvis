package devices;

public  class Device {
	boolean active = false;
	String name;
	
	public Device(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public boolean enable() {
		if(!isActive()){
			System.out.println(name + " turned on");
			flipSwitch();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean disable() {
		if(isActive()){
			System.out.println(name + " turned off");
			flipSwitch();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void flipSwitch() {
		active = !active;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
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