package backend;
import java.util.HashMap;


public class User implements Comparable<User>{
    private double points;
    private String name;
    private HashMap<String,Player> teamAthletes;
    
    public User(String name){
    	this.name=name;
    	points=0;
    	teamAthletes=new HashMap<String,Player>();
    }
    public void addPlayer(Player athlete){
    	teamAthletes.put(athlete.getName(),athlete);
    }
    public void removePlayer(Player athlete){
    	teamAthletes.remove(athlete.getName());
    }
	public double getPoints() {
		return points;
	}
	public String getName() {
		return name;
	}
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final User other = (User) obj;
		if (name.equals(other.name))
			return true;
		return false;
	}
	public int compareTo(User o) {
		if(points>o.getPoints())
			return 1;
		else if(points==o.getPoints())
			return 0;
		return -1;
	}
}
