package backend;


public class Test {
	public static League myLeague;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		myLeague = new League("Happy League");
	    myLeague.setMaxUser(10);
	    myLeague.setMinUser(4);
	    myLeague.addUser(new User("Eli"));
	    myLeague.addUser(new User("Dillen"));
	    myLeague.addUser(new User("Anuj"));
	    myLeague.addUser(new User("Tam"));
	    myLeague.addUser(new User("Steph"));
	    myLeague.addPlayer(new Player("Lebron James","forawrd"));
	    myLeague.addPlayer(new Player("Chris Bosh","forward"));
	    myLeague.addPlayer(new Player("Dwyane Wade","guard"));
	    myLeague.addPlayer(new Player("Eddie House","guard"));
	    myLeague.addPlayer(new Player("Joel Anthony","center"));
	    myLeague.addPlayer(new Player("Ron Artest","forward"));
	    myLeague.addPlayer(new Player("Pau Gasol","forward-center"));
	    myLeague.addPlayer(new Player("Lamar Odom","forward"));
	    myLeague.addPlayer(new Player("Kobe Bryant","guard"));
	    myLeague.addPlayer(new Player("Derek Fisher","guard"));
	    myLeague.addPlayer(new Player("Ray Allen","guard"));
	    myLeague.addPlayer(new Player("Kevin Garnett","forward"));
	    myLeague.addPlayer(new Player("Paul Pierce","forward"));
	    myLeague.addPlayer(new Player("Shaquille O'Neal","center"));
	    myLeague.addPlayer(new Player("Glen Davis","forward-center"));
	    myLeague.addPlayer(new Player("Carmelo Anthony","forward"));
	    myLeague.addPlayer(new Player("Landry Fields","guard"));
	    myLeague.addPlayer(new Player("Derrick Brown","forward"));
	    myLeague.addPlayer(new Player("Amare Stoudemire","forward-center"));
	    myLeague.addPlayer(new Player("Bill Walker","guard-forward"));
	    myLeague.addPlayer(new Player("Josh Powell","forward"));
	    myLeague.addPlayer(new Player("Joe Johnson","guard"));
	    myLeague.addPlayer(new Player("Damien Wilkins","guard-forward"));
	    myLeague.addPlayer(new Player("Zaza Pachulia","center"));
	    myLeague.addPlayer(new Player("Marvin Williams","forward"));
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

