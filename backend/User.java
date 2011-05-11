import java.util.Arrays;
import java.util.HashMap;


public class User implements Comparable<User>{
    private float totalPoints;
    private String name;
    private HashMap<String,Player> teamAthletes;
    
    /**Constructor with just the name.
     * 
     * @param String name
     */
    public User(String name){
    	this.name=name;
    	totalPoints=0;
    	teamAthletes=new HashMap<String,Player>();
    }
    
    /**Constructor with the name and points. Used for importing
     * dump files.
     * 
     * @param String name
     * @param float totalPoints
     */
    public User(String name,float totalPoints){
    	this.name=name;
    	this.totalPoints=totalPoints;
    	teamAthletes=new HashMap<String,Player>();
    }
    
    /**Add a player to the team and if the team is not the free agent,
     * remove the player from the free agent.
     * 
     * @param Player athlete
     */
    public void addPlayer(Player athlete){
    	teamAthletes.put(athlete.getName(),athlete);	//Add player to this team
    	if(!name.equals("Free Agent")){	//If it's not the free agent team
    		League.playerToTeam.put(athlete,this);	//Add the association of player to team mapping
    		League.freeAgent.removePlayer(athlete);	//Remove this player from the free agent
    	}
    }
    
    /**Remove a player from a team and if the team is not the free agent,
     * add the player to the free agent.
     * 
     * @param Player athlete
     */
    public void removePlayer(Player athlete){
    	teamAthletes.remove(athlete.getName());	//Remove athlete from this team
    	if(!name.equals("Free Agent")){	//If it's not the free agent
    		League.playerToTeam.remove(athlete);	//Remove the association of player to team
    		League.freeAgent.addPlayer(athlete);	//Add the player to the free agent
    	}
    }
    
    /**Add points to the team.
     * 
     * @param float points
     */
    public void addPoints(float points){
    	totalPoints+=points;
    }
    
    /**Get the user's points.
     * 
     * @return float totalPoints
     */
	public float getPoints() {
		return totalPoints;
	}
	
	/**Get the name of the user.
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}
	
	/**Get the number of players on the user's team.
	 * 
	 * @return int numPlayers
	 */
	public int getNumPlayers(){
		return teamAthletes.size();
	}
	
	/**Get the player on the user's team in reverse ranked
	 * order.
	 * 
	 * @return Player[] rankedPlayers
	 */
	public Player[] getPlayers(){
		Player[] ranked=teamAthletes.values().toArray(new Player[teamAthletes.size()]);
		Arrays.sort(ranked);
		return ranked;
	}
	
	/**Determine if two users are the same.
	 * 
	 * @return boolean areEqual
	 */
	public boolean equals(Object obj){
		if (this == obj)	//Same reference
			return true;
		if (obj == null)	//Other is null
			return false;
		if (getClass() != obj.getClass())	//Not the same class
			return false;
		final User other = (User) obj;	//Cast object
		if (name.equals(other.name))	//If the names are equal
			return true;
		return false;
	}
	
	/**Compare one user to another based on their number of points.
	 * 
	 * @return int compared
	 */
	public int compareTo(User o) {
		if(totalPoints>o.getPoints())
			return 1;
		else if(totalPoints==o.getPoints())
			return 0;
		return -1;
	}
	
	/**Return a string representation of the user.
	 * 
	 * @return String user
	 */
	public String toString() {
		return "User [name=" + name + ", points=" + totalPoints + "]";
	}
	
	/**Clear the data structures.
	 * 
	 */
	public void clear(){
		teamAthletes.clear();
	}
}
