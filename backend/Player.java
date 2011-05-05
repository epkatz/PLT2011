package backend;

public class Player implements Comparable<Player>{
	private String name, position;
	private double totalPoints;
	
	/**Constructor with just the name and position.
     * 
     * @param String name
     * @param String position
     */
	public Player(String name, String position){
		this.name=name;
		this.position=position;
		totalPoints=0;
	}
	
	/**Constructor with the name and points. Used for importing
     * dump files.
     * 
     * @param String name
     * @param String position
     * @param double totalPoints
     */
	public Player(String name, String position,double totalPoints){
		this.name=name;
		this.position=position;
		this.totalPoints=totalPoints;
	}
	
	/**Get the player's name.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**Return a string representation of the player.
	 * 
	 * @return String player
	 */
	public String toString() {
		return "Player [name=" + name + ", position=" + position
				+ ", totalPoints=" + totalPoints + "]";
	}
	
	/**Get the player's position.
	 * 
	 * @return String position
	 */
	public String getPosition() {
		return position;
	}
	
	/**Add points to the player and the team they are on.
	 * 
	 * @param double pts
	 */
	public void addPoints(double pts){
		totalPoints+=pts;	//Add pts to players points
		User temp=League.playerToTeam.get(this);	//Get team they are on
		if(temp!=null)	//Free agent
			temp.addPoints(pts);	//Add the points to the team
	}
	
	/**Get the player's total points.
	 * 
	 * @return double totalPoints
	 */
	public double getPoints(){
		return totalPoints;
	}
	
	/**Determine if two players are equal.
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
		final Player other = (Player) obj;	//Cast other object
		if (name.equals(other.name) && position.equals(other.position))	//Same name and position
			return true;
		return false;
	}
	
	/**Compare two players based on their total points scored.
	 * 
	 * @return int compared
	 */
	public int compareTo(Player o) {
		if(totalPoints>o.getPoints())
			return 1;
		else if(totalPoints==o.getPoints())
			return 0;
		return -1;
	}
	
}
