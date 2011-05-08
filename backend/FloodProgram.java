package backend;
public class FloodProgram {
	public static League myLeague;
	public static GUI run;

	public static void main(String[] args) {
		myLeague = new League("PLS-Pseudo League Soccer");
		myLeague.addUser(new User("Carlo Ancelotti"));
		myLeague.addUser(new User("Alex Ferguson"));
		myLeague.addUser(new User("Jose Mourinho"));
		myLeague.addUser(new User("Josep Guardiola"));
		myLeague.addUser(new User("Massimiliano Allegri"));
		myLeague.addAction(new Action("score goal", 2.0));
		myLeague.addAction(new Action("block shot", 0.5));
		myLeague.addAction(new Action("block shot on goal", 1.0));
		myLeague.addAction(new Action("tackle", 0.5));
		myLeague.addAction(new Action("shot on goal", 1.0));
		myLeague.addAction(new Action("shot", 0.5));
		myLeague.addAction(new Action("score penalty kick", 0.5));
		myLeague.addAction(new Action("score with a header", 1.5));
		myLeague.addAction(new Action("score from 30+ yards out", 1.5));
		myLeague.addAction(new Action("red card", -2.0));
		myLeague.addAction(new Action("yellow card", -1.0));
		myLeague.addPlayer(new Player("Petr Cech", "goal keeper"));
		myLeague.addPlayer(new Player("Branislav Ivanovic", "defender"));
		myLeague.addPlayer(new Player("Ashley Cole", "defender"));
		myLeague.addPlayer(new Player("Michael Essien", "midfielder"));
		myLeague.addPlayer(new Player("Ramires", "midfielder"));
		myLeague.addPlayer(new Player("Frank Lampard", "midfielder"));
		myLeague.addPlayer(new Player("Fernado Torres", "forward"));
		myLeague.addPlayer(new Player("Didier Drogba", "forward"));
		myLeague.addPlayer(new Player("Florent Malouda", "midfielder"));
		myLeague.addPlayer(new Player("Salomon Kalou", "forward"));
		myLeague.addPlayer(new Player("John Terry", "defender"));
		myLeague.addPlayer(new Player("Nicolas Anelka", "forward"));
		myLeague.addPlayer(new Player("Edwin Van der Sar", "goal keeper"));
		myLeague.addPlayer(new Player("Patrice Evra", "defender"));
		myLeague.addPlayer(new Player("Rio Ferdinand", "defender"));
		myLeague.addPlayer(new Player("Dimitriar Berbatov", "forward"));
		myLeague.addPlayer(new Player("Wayne Rooney", "forward"));
		myLeague.addPlayer(new Player("Ryan Giggs", "midfielder"));
		myLeague.addPlayer(new Player("Javier Hernandez", "forward"));
		myLeague.addPlayer(new Player("Namanja Vidic", "defender"));
		myLeague.addPlayer(new Player("Nani", "midfielder"));
		myLeague.addPlayer(new Player("Fabio", "defender"));
		myLeague.addPlayer(new Player("Darren Fletcher", "midfielder"));
		myLeague.addPlayer(new Player("Darron Gobson", "midfielder"));
		myLeague.addPlayer(new Player("Iker Casillas", "goal keeper"));
		myLeague.addPlayer(new Player("Ricardo Carvalho", "defender"));
		myLeague.addPlayer(new Player("Sergio Ramos", "defender"));
		myLeague.addPlayer(new Player("Emmanuel Adebayor", "forward"));
		myLeague.addPlayer(new Player("Cristiano Ronaldo", "forward"));
		myLeague.addPlayer(new Player("Karim Benzema", "forward"));
		myLeague.addPlayer(new Player("Raul Albiol", "defender"));
		myLeague.addPlayer(new Player("Mesut Ozil", "midfielder"));
		myLeague.addPlayer(new Player("Marcelo", "defender"));
		myLeague.addPlayer(new Player("Xabi Alonso", "midfielder"));
		myLeague.addPlayer(new Player("Ezequiel Garay", "defender"));
		myLeague.addPlayer(new Player("Pedro Leon", "midfielder"));
		myLeague.addPlayer(new Player("Victor Valdes", "goal keeper"));
		myLeague.addPlayer(new Player("Gerard Pique", "defender"));
		myLeague.addPlayer(new Player("Carles Puyol", "defender"));
		myLeague.addPlayer(new Player("Xavi Hernandez", "midfielder"));
		myLeague.addPlayer(new Player("David Villa", "forward"));
		myLeague.addPlayer(new Player("Andres Iniesta", "midfielder"));
		myLeague.addPlayer(new Player("Lionel Messi", "foward"));
		myLeague.addPlayer(new Player("Jeffren Suarez", "forward"));
		myLeague.addPlayer(new Player("Maxwell Andrade", "defender"));
		myLeague.addPlayer(new Player("Eric Abidal", "defender"));
		myLeague.addPlayer(new Player("Ibrahim Afellay", "midfielder"));
		myLeague.addPlayer(new Player("Javier Macherano", "midfielder"));
		myLeague.addPlayer(new Player("Marco Amelia", "goal keeper"));
		myLeague.addPlayer(new Player("Mark van Bommel", "midfielder"));
		myLeague.addPlayer(new Player("Gennaro Gattuso", "midfielder"));
		myLeague.addPlayer(new Player("Zlatan Ibrahimovic", "forward"));
		myLeague.addPlayer(new Player("Alessandro Nesta", "defender"));
		myLeague.addPlayer(new Player("Rodney Strasser", "midfielder"));
		myLeague.addPlayer(new Player("Massimo Oddo", "defender"));
		myLeague.addPlayer(new Player("Gianluca Zambrotta", "defender"));
		myLeague.addPlayer(new Player("Thiago Silva", "defender"));
		myLeague.addPlayer(new Player("Flavio Roma", "goal keeper"));
		myLeague.addPlayer(new Player("Antonio Cassano", "forward"));
		myLeague.addPlayer(new Player("Mario Yepes", "defender"));
		myLeague.addPlayer(new Player("Kevin Prince Boateng", "midfielder"));
		myLeague.addPlayer(new Player("Andreu Fontas", "defender"));
		myLeague.addPlayer(new Player("Jose Manuel Pinto", "goal keeper"));
		myLeague.addPlayer(new Player("Matrin Caceres", "defender"));
		myLeague.addPlayer(new Player("Sami Khedira", "midfielder"));
		myLeague.addPlayer(new Player("Henrique Hilario", "goal keeper"));
		myLeague.addPlayer(new Player("Oliver Norwood", "midfielder"));
		myLeague.addPlayer(new Player("Gabriel Obertan", "forward"));
		myLeague.addPlayer(new Player("Sergio Canales", "midfielder"));
		myLeague.addPlayer(new Player("Lassana Diarra", "midfielderforward"));
		run = new GUI(myLeague);
		run.drawBoard();
	}

	public static boolean tooSmall(int players) {
		boolean flag = false;
		if (players < 11) {
			flag = true;
		}
		return flag;
	}

	public static boolean tooBig(int players) {
		boolean flag = false;
		if (players > 13) {
			flag = true;
		}
		return flag;
	}

	public static boolean draftPlayer(User u, Player p) {
		boolean tooBig;
		boolean value = false;
		int i;
		i = u.getNumPlayers();
		i = i + 1;
		tooBig = tooBig(i);
		if (!tooBig) {
			u.addPlayer(p);
			value = true;
		}
		return value;
	}

	public static boolean dropPlayer(User u, Player p) {
		boolean tooSmall;
		boolean value = false;
		int i;
		i = u.getNumPlayers();
		i = i + 1;
		tooSmall = tooSmall(i);
		if (!tooSmall) {
			u.removePlayer(p);
			value = true;
		}
		return value;
	}

	public static boolean trade(User u1, Player[] p1, User u2, Player[] p2) {
		int i = 0;
		int j;
		int len1;
		int len2;
		boolean flag = true;
		boolean value = true;
		len1 = p1.length;
		len2 = p2.length;
		while (i < len1) {
			flag = dropPlayer(u1, p1[i]);
			if (!flag) {
				j = i;
				while (j >= 0) {
					draftPlayer(u1, p1[j]);
					j = j - 1;
				}
				value = false;
			}
			i = i + 1;
		}
		i = 0;
		while (value && i < len2) {
			flag = dropPlayer(u2, p2[i]);
			if (!flag) {
				j = i;
				while (j >= 0) {
					draftPlayer(u2, p2[j]);
					j = j - 1;
				}
				j = 0;
				while (j < len1) {
					draftPlayer(u1, p1[j]);
					j = j + 1;
				}
				value = false;
			}
			i = i + 1;
		}
		i = 0;
		while (value && i < len1) {
			flag = draftPlayer(u2, p1[i]);
			if (!flag) {
				j = i;
				while (j >= 0) {
					dropPlayer(u2, p1[j]);
					j = j - 1;
				}
				j = 0;
				while (j < len1) {
					draftPlayer(u1, p1[j]);
					j = j + 1;
				}
				j = 0;
				while (j < len2) {
					draftPlayer(u2, p2[j]);
					j = j + 1;
				}
				value = false;
			}
			i = i + 1;
		}
		i = 0;
		while (value && i < len2) {
			flag = draftPlayer(u1, p2[i]);
			if (!flag) {
				j = i;
				while (j >= 0) {
					dropPlayer(u1, p2[j]);
					j = j - 1;
				}
				j = 0;
				while (j < len1) {
					dropPlayer(u2, p1[j]);
					j = j + 1;
				}
				j = 0;
				while (j < len1) {
					draftPlayer(u1, p1[j]);
					j = j + 1;
				}
				j = 0;
				while (j < len2) {
					draftPlayer(u2, p2[j]);
					j = j + 1;
				}
				value = false;
			}
			i = i + 1;
		}
		return value;
	}

	public static int draftFunction(int turn) {
		return turn % myLeague.getCurrentNumUsers();
	}
}