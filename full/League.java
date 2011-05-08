import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class League {
	private String name;
	private int maxTeamSize,minTeamSize,maxUsers,minUsers;
	public static HashMap<String,User> teams;
	public static HashMap<Player,User> playerToTeam;
	public static HashMap<String,Player> athletes;
	public static HashMap<String,Action> ptsDist;
	public static ArrayList<User> indexedTeams;
	public static User freeAgent;
	
	/**Constructor for League.
	 * 
	 * @param String name
	 */
	public League(String name){
		this.name=name;
		//Initialize data structures
		teams=new HashMap<String,User>();
		indexedTeams=new ArrayList<User>();
		athletes=new HashMap<String,Player>();
		ptsDist=new HashMap<String,Action>();
		playerToTeam= new HashMap<Player,User>();
		freeAgent=new User("Free Agent");
	}
	
	/**Add an action to the league.
	 * 
	 * @param Action a
	 */
	public void addAction(Action a){
		ptsDist.put(a.getAction(),a);
	}
	
	/**Add a user to the league.
	 * 
	 * @param User u
	 */
	public void addUser(User u){
		teams.put(u.getName(),u);
		indexedTeams.add(u);	//Store the indexed user
	}
	
	/**Add a player to the league.
	 * 
	 * @param p
	 */
	public void addPlayer(Player p){
		athletes.put(p.getName(),p);	//Add to the list of players
		freeAgent.addPlayer(p);	//Add to the free agent
	}
	
	/**Get a user based on an index.
	 * 
	 * @param int index
	 * @return User u
	 */
	public User getUser(int index){
		return indexedTeams.get(index);
	}
	
	/**Get the max team size.
	 * 
	 * @return int maxTeamSize
	 */
	public int getMaxTeamSize() {
		return maxTeamSize;
	}
	
	/**Set the max team size.
	 * 
	 * @param int maxTeamSize
	 */
	public void setMaxTeamSize(int maxTeamSize) {
		this.maxTeamSize = maxTeamSize;
	}
	
	/**Get the min team size.
	 * 
	 * @return int minTeamSize
	 */
	public int getMinTeamSize() {
		return minTeamSize;
	}
	
	/**Set the min team size.
	 * 
	 * @param int minTeamSize
	 */
	public void setMinTeamSize(int minTeamSize) {
		this.minTeamSize = minTeamSize;
	}
	
	/**Get the max number of users
	 * 
	 * @return int maxUsers
	 */
	public int getMaxUser() {
		return maxUsers;
	}
	
	/**Set the max number of users
	 * 
	 * @param int maxUsers
	 */
	public void setMaxUser(int maxUsers) {
		this.maxUsers = maxUsers;
	}
	
	/**Get the min number of users
	 * 
	 * @return int minUsers
	 */
	public int getMinUser() {
		return minUsers;
	}
	
	/**Set the min number of users.
	 * 
	 * @param int minUsers
	 */
	public void setMinUser(int minUsers) {
		this.minUsers = minUsers;
	}
	
	/**Get the name of the league.
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}
	
	/**Get the current number of users in the league.
	 * 
	 * @return int numUsers
	 */
	public int getCurrentNumUsers(){
		return teams.size();
	}
	
	/**Get the current number of players in the league.
	 * 
	 * @return int numPlayers
	 */
	public int getCurrentNumPlayers(){
		return teams.size();
	}
	
	/**Get the current number of actions in the league.
	 * 
	 * @return int numActions
	 */
	public int getCurrentNumActions(){
		return athletes.size();
	}
	
	/**Get a user from their name.
	 * 
	 * @param String name
	 * @return User u
	 */
	public User getTeam(String name){
		return teams.get(name);
	}
	
	/**Get a player from their name.
	 * 
	 * @param String name
	 * @return Player p
	 */
	public Player getPlayer(String name){
		return athletes.get(name);
	}
	
	/**Get an action from the rule.
	 * 
	 * @param String action
	 * @return Action a
	 */
	public Action getAction(String action){
		return ptsDist.get(action);
	}
	
	/**Get the free agent.
	 * 
	 * @return User freeAgent
	 */
	public User getFreeAgent(){
		return freeAgent;
	}
	
	/**Get the users in reverse ranked order based on
	 * the number of points they have.
	 * 
	 * @return Users[] rankedTeams
	 */
	public User[] getRankedUsers(){
		User[] ranked=teams.values().toArray(new User[teams.size()]);
		Arrays.sort(ranked);
		return ranked;
	}
	
	/**Get the players that are still in the draft in reverse
	 * ranked order.
	 * 
	 * @return Player[] availablePlayers
	 */
	public Player[] getRankedAvailablePlayers(){
		return freeAgent.getPlayers();
	}
	
	/**Get all the players in the league.
	 * 
	 * @return Player[] allPlayers
	 */
	public Player[] getPlayers(){
		return athletes.values().toArray(new Player[athletes.size()]);
	}
	
	/**Get all the actions in the league.
	 * 
	 * @return Action[] allActions
	 */
	public Action[] getActions(){
		return ptsDist.values().toArray(new Action[ptsDist.size()]);
	}
	
	/**Get all the users in the correct indexed order.
	 * 
	 * @return User[] users
	 */
	public User[] getUsers(){
		return indexedTeams.toArray(new User[indexedTeams.size()]);
	}
	/**Clear all the data structures.
	 * 
	 */
	public void clear(){
		teams.clear();
		playerToTeam.clear();
		athletes.clear();
		ptsDist.clear();
		indexedTeams.clear();
		freeAgent.clear();
	}
	
	/**Get a string representation of the league's statistics.
	 * 
	 * @return String league
	 */
	public String toString() {
		return "League [name=" + name + ", maxTeamSize=" + maxTeamSize
				+ ", minTeamSize=" + minTeamSize + ", maxUser=" + maxUsers
				+ ", minUser=" + minUsers + "]";
	}
	
}