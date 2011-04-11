package backend;


public class Test {
	public static League mybl;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		mybl = new League("Happy League");
	    mybl.setMaxUser(10);
	    mybl.setMinUser(4);
	    mybl.addUser(new User("Eli"));
	    mybl.addUser(new User("Dillen"));
	    mybl.addUser(new User("Anuj"));
	    mybl.addUser(new User("Tam"));
	    mybl.addUser(new User("Steph"));
	    mybl.addPlayer(new Player("Jesus","Everything"));
	    mybl.addPlayer(new Player("Marry Poppins","Kitchen"));
	    mybl.addPlayer(new Player("Pippy Longstocking","At sea"));
	    mybl.addPlayer(new Player("Glenda the Good","Doing jack shit"));
	    mybl.addPlayer(new Player("The Wicked Witch of the East","Under Dorothy's House"));
	    mybl.addPlayer(new Player("The Grinch","Thief"));
	    mybl.addPlayer(new Player("Mike Reed","Anal"));
	    mybl.addPlayer(new Player("Dillen Roggensinger","Missionary"));
	    mybl.addAction(new Action("Field Goal Attempt",-0.45));
		mybl.addAction(new Action("Field Goal Made",1.0));
		mybl.addAction(new Action("Free Throw Attempt",-0.75));
		mybl.addAction(new Action("Free Throw Made",1.0));
		mybl.addAction(new Action("3-Point Shot Made",3.0));
		mybl.addAction(new Action("Point Scored",0.5));
		mybl.addAction(new Action("Rebound",1.5));
		mybl.addAction(new Action("Assist",2.0));
		mybl.addAction(new Action("Steal",3.0));
		mybl.addAction(new Action("Turnover",-2.0));
		mybl.addAction(new Action("Blocked Shot",3.0));
		
		
		GUI run = new GUI(mybl);
		run.drawBoard();

	}
	public int draftFunction(int turn){
		return turn%mybl.getCurrentNumUsers();
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

