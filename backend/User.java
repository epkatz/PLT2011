package backend;
import java.util.ArrayList;


public class User {
    private double points;
    private String name;
    private ArrayList<Player> teamAthletes;
    
    public User(String name){
    	this.name=name;
    	points=0;
    	teamAthletes=new ArrayList<Player>();
    }
    public void addPlayer(Player athelete){
    		teamAthletes.add(athelete);
    }
    public void removePlayer(Player athlete){
    	teamAthletes.remove(athlete);
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
}
