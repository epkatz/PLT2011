package backend;
import java.util.ArrayList;


public class League {
	String name;
	private int maxSize,maxUser,minUser;
	private ArrayList<User> teams;
	private ArrayList<Player> athletes;
	private ArrayList<Action> ptsDist;
	public League(String name){
		this.name=name;
		teams=new ArrayList<User>();
		athletes=new ArrayList<Player>();
		ptsDist=new ArrayList<Action>();
	}
	public void addAction(Action a){
		ptsDist.add(a);
	}
	public void addUser(User u){
		teams.add(u);
	}
	public void addPlayer(Player p){
		athletes.add(p);
	}
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
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
	public User getTeam(int index){
		return teams.get(index);
	}
	public Player getPlayer(int index){
		return athletes.get(index);
	}
	public Action getAction(int index){
		return ptsDist.get(index);
	}
}