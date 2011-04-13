package backend;

public class Player implements Comparable<Player>{
	private String name, position;
	private double totalPoints;
	
	public Player(String name, String position){
		this.name=name;
		this.position=position;
		totalPoints=0;
	}
	public String getName() {
		return name;
	}
	public String getPosition() {
		return position;
	}
	public void addPoints(double pts){
		totalPoints+=pts;
	}
	public double getPoints(){
		return totalPoints;
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
	public int compareTo(Player o) {
		if(totalPoints>o.getPoints())
			return 1;
		else if(totalPoints==o.getPoints())
			return 0;
		return -1;
	}
	
}
