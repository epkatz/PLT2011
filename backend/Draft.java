package backend;

public class Draft {
	private League game;
	public Draft(League game){
		this.game=game;
	}
	public int draftFunction(int turn){
		return turn%game.getMaxSize();
	}
	public boolean pickPlayer(User u, Player p){
		u.addPlayer(p);
		return true;
	}
	public boolean trade(User u1, Player p1, User u2, Player p2){
		u1.removePlayer(p1);
		u2.removePlayer(p2);
		u1.addPlayer(p2);
		u2.addPlayer(p1);
		return true;
	}
	public void dropPlayer(User u, Player p){
		u.removePlayer(p);
	}
}

