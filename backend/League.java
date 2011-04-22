package backend;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class League {
	String name;
	private int maxTeamSize,minTeamSize,maxUser,minUser;
	public static HashMap<String,User> teams;
	public static HashMap<Player,User> playerToTeam;
	public static HashMap<String,Player> athletes;
	public static HashMap<String,Action> ptsDist;
	public static ArrayList<User> indexedTeams;
	public static User freeAgent;
	public League(String name){
		this.name=name;
		teams=new HashMap<String,User>();
		indexedTeams=new ArrayList<User>();
		athletes=new HashMap<String,Player>();
		ptsDist=new HashMap<String,Action>();
		playerToTeam= new HashMap<Player,User>();
		freeAgent=new User("Free Agent");
	}
	public void addAction(Action a){
		ptsDist.put(a.getAction(),a);
	}
	public void addUser(User u){
		teams.put(u.getName(),u);
		indexedTeams.add(u);
	}
	public void addPlayer(Player p){
		athletes.put(p.getName(),p);
		freeAgent.addPlayer(p);
	}
	public User getUser(int index){
		return indexedTeams.get(index);
	}
	public int getMaxTeamSize() {
		return maxTeamSize;
	}
	public void setMaxTeamSize(int maxTeamSize) {
		this.maxTeamSize = maxTeamSize;
	}
	public int getMinTeamSize() {
		return minTeamSize;
	}
	public void setMinTeamSize(int minTeamSize) {
		this.minTeamSize = minTeamSize;
	}
	public int getMaxUser() {
		return maxUser;
	}
	public void setMaxUser(int maxUser) {
		this.maxUser = maxUser;
	}
	public int getMinUser() {
		return minUser;
	}
	public void setMinUser(int minUser) {
		this.minUser = minUser;
	}
	public String getName() {
		return name;
	}
	public int getCurrentNumUsers(){
		return teams.size();
	}
	public int getCurrentNumPlayers(){
		return teams.size();
	}
	public int getCurrentNumActions(){
		return athletes.size();
	}
	public User getTeam(String name){
		return teams.get(name);
	}
	public Player getPlayer(String name){
		return athletes.get(name);
	}
	public Action getAction(String action){
		return ptsDist.get(action);
	}
	public User[] getRankedUsers(){
		User[] ranked=new User[teams.size()];
		teams.values().toArray(ranked);
		Arrays.sort(ranked);
		return ranked;
	}
	public Player[] getRankedAvailablePlayers(){
		return freeAgent.getPlayers();
	}
	public boolean uploadStatFile(File file){
		return true;
	}
}