package backend;

public class Player {
	private String name, position;
	
	public Player(String name, String position){
		this.name=name;
		this.position=position;
	}
	public String getName() {
		return name;
	}
	public String getPosition() {
		return position;
	}
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Player other = (Player) obj;
		if (name.equals(other.name) && position.equals(other.position))
			return true;
		return false;
	}
}
