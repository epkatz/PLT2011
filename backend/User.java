package backend;
import java.util.Arrays;
import java.util.HashMap;


public class User implements Comparable<User>{
    private double totalPoints;
    private String name;
    private HashMap<String,Player> teamAthletes;
    
    public User(String name){
    	this.name=name;
    	totalPoints=0;
    	teamAthletes=new HashMap<String,Player>();
    }
    public void addPlayer(Player athlete){
    	teamAthletes.put(athlete.getName(),athlete);	//Add player to this team
    	if(!name.equals("Free Agent")){	//If it's not the free agent team
    		League.playerToTeam.put(athlete,this);	//Add the association of player to team mapping
    		League.freeAgent.removePlayer(athlete);	//Remove this player from the free agent
    	}
    }
    public void removePlayer(Player athlete){
    	teamAthletes.remove(athlete.getName());	//Remove athlete from this team
    	if(!name.equals("Free Agent")){	//If it's not the free agent
    		League.playerToTeam.remove(athlete);	//Remove the association of player to team
    		League.freeAgent.addPlayer(athlete);	//Add the player to the free agent
    	}
    }
    public void addPoints(double points){
    	totalPoints+=points;
    }
	public double getPoints() {
		return totalPoints;
	}
	public String getName() {
		return name;
	}
	public int getNumPlayers(){
		return teamAthletes.size();
	}
	public Player[] getPlayers(){
		Player[] ranked=new Player[teamAthletes.size()];
		teamAthletes.values().toArray(ranked);
		Arrays.sort(ranked);
		return ranked;
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
		if(totalPoints>o.getPoints())
			return 1;
		else if(totalPoints==o.getPoints())
			return 0;
		return -1;
	}
	public String toString() {
		return "User [name=" + name + ", points=" + totalPoints + "]";
	}
}
