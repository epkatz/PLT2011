package backend;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    League mybl = new League("Happy League");
	    mybl.setMaxUser(10);
	    mybl.setMinUser(4);
	    mybl.addUser(new User("Eli"));
	    mybl.addUser(new User("Dillen"));
	    mybl.addUser(new User("Anuj"));
	    mybl.addUser(new User("Tam"));
	    mybl.addUser(new User("Steph"));
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
		Draft blDraft=new Draft(mybl);
		
//		        FSys.launchGui(blDraft, mybl);


	}

}

