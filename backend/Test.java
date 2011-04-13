package backend;


public class Test {
	public static League myLeague;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		myLeague = new League("Happy League");
		myLeague.setMaxTeamSize(10);
		myLeague.setMinTeamSize(5);
	    myLeague.setMaxUser(10);
	    myLeague.setMinUser(4);
	    myLeague.addUser(new User("Eli"));
	    myLeague.addUser(new User("Dillen"));
	    myLeague.addUser(new User("Anuj"));
	    myLeague.addUser(new User("Tam"));
	    myLeague.addUser(new User("Steph"));
	    myLeague.addPlayer(new Player("Jesus","Everything"));
	    myLeague.addPlayer(new Player("Marry Poppins","Kitchen"));
	    myLeague.addPlayer(new Player("Pippy Longstocking","At sea"));
	    myLeague.addPlayer(new Player("Glenda the Good","Doing jack shit"));
	    myLeague.addPlayer(new Player("The Wicked Witch of the East","Under Dorothy's House"));
	    myLeague.addPlayer(new Player("The Grinch","Thief"));
	    myLeague.addPlayer(new Player("Mike Reed","Anal"));
	    myLeague.addPlayer(new Player("Dillen Roggensinger","Missionary"));
	    myLeague.addAction(new Action("Field Goal Attempt",-0.45));
		myLeague.addAction(new Action("Field Goal Made",1.0));
		myLeague.addAction(new Action("Free Throw Attempt",-0.75));
		myLeague.addAction(new Action("Free Throw Made",1.0));
		myLeague.addAction(new Action("3-Point Shot Made",3.0));
		myLeague.addAction(new Action("Point Scored",0.5));
		myLeague.addAction(new Action("Rebound",1.5));
		myLeague.addAction(new Action("Assist",2.0));
		myLeague.addAction(new Action("Steal",3.0));
		myLeague.addAction(new Action("Turnover",-2.0));
		myLeague.addAction(new Action("Blocked Shot",3.0));
		
		
		GUI run = new GUI(myLeague);
		run.drawBoard();

	}
	public static int draftFunction(int turn){
		return turn%myLeague.getCurrentNumUsers();
	}
	public static boolean draftPlayer(User u, Player p){
		u.addPlayer(p);
		return true;
	}
	public static boolean trade(User u1, Player p1, User u2, Player p2){
		u1.removePlayer(p1);
		u2.removePlayer(p2);
		u1.addPlayer(p2);
		u2.addPlayer(p1);
		return true;
	}
	public static void dropPlayer(User u, Player p){
		u.removePlayer(p);
	}
}

