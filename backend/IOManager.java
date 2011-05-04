package backend;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * Stats Parser
 */
public class IOManager {
	
	public static String[][] getStats(String file){
		ArrayList<String[]> statsAL = new ArrayList<String[]>();		
		try {
			// Open the file that is the first 
			FileInputStream fstream = new FileInputStream(file);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String str;
			//Read File Line By Line
			while((str = br.readLine()) != null){
				statsAL.add(str.split(","));
			}
			in.close();
			String[][] stats = new String[statsAL.size()][3];
			for(int i=0; i<stats.length; i++){
				for(int j=0; j<3; j++){
					stats[i][j] = statsAL.get(i)[j];
				}
			}
			return stats;
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		} catch (IndexOutOfBoundsException e){
			return null;
		}
	}
	public static void writeState(League myLeague,int turn){
		try {
			FileWriter fstream = new FileWriter("statdmp.txt");	//Create the file
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(turn+","+myLeague.getMaxTeamSize()+","+myLeague.getMinTeamSize()+","+myLeague.getMaxUser()+","+myLeague.getMinUser()+"\n");
			out.write("ACTIONS:\n");
			Action[] actions=myLeague.getActions();
			for(int i=0;i<actions.length;i++){
				out.write(actions[i].getAction()+","+actions[i].getPoints()+"\n");
			}
			out.write("PLAYERS:\n");
			Player[] players=myLeague.getAllPlayers();
			for(int i=0;i<players.length;i++){
				out.write(players[i].getName()+","+players[i].getPosition()+","+players[i].getPoints()+"\n");
			}
			out.write("TEAMS:\n");
			User[] teams=myLeague.getRankedUsers();
			for(int i=0;i<teams.length;i++){
				Player[] teamPlayers=teams[i].getPlayers();
				out.write(","+teams[i].getName()+","+teams[i].getPoints());
				for(int j=0;j<teamPlayers.length;j++){
					out.write(teamPlayers[j].getName()+"\n");
				}
			}
			out.write("FREE AGENT:\n");
			User free=League.freeAgent;
			Player[] freePlayers=free.getPlayers();
			for(int i=0;i<freePlayers.length;i++){
				out.write(freePlayers[i].getName()+"\n");
			}
		} catch (IOException e) {
			GUI.alert("Stat Dump Error!", "Error creating the dump file! Please try again.");
		}
	}
	public static int importState(String filePath,League myLeague){
		try {
			myLeague.clear();
			// Open the file that is the first 
			FileInputStream fstream = new FileInputStream(filePath);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String str="",team="";
			int turn=-1;
			str=br.readLine();
			String[] data=str.split(",");
			turn=Integer.parseInt(data[0]);
			myLeague.setMaxTeamSize(Integer.parseInt(data[1]));
			myLeague.setMinTeamSize(Integer.parseInt(data[2]));
			myLeague.setMaxUser(Integer.parseInt(data[3]));
			myLeague.setMinUser(Integer.parseInt(data[4]));
			//Read File Line By Line
			boolean teamFlag=false,playerFlag=false,actionFlag=false,freeFlag=false;
			while((str = br.readLine()) != null){
				if(str.equalsIgnoreCase("ACTIONS:")){
					actionFlag=true;
					playerFlag=false;
					teamFlag=false;
					freeFlag=false;
				}
				else if(str.equalsIgnoreCase("PLAYERS:")){
					playerFlag=true;
					actionFlag=false;
					teamFlag=false;
					freeFlag=false;
				}
				else if(str.equalsIgnoreCase("TEAMS:")){
					teamFlag=true;
					actionFlag=false;
					playerFlag=false;
					freeFlag=false;
				}
				else if(str.equals("FREE AGENT:")){
					teamFlag=false;
					actionFlag=false;
					playerFlag=false;
					freeFlag=true;
				}
				if(actionFlag){
					String[] parts=str.split(",");
					myLeague.addAction(new Action(parts[0].trim(),Double.parseDouble(parts[1].trim())));
				}
				else if(playerFlag){
					String[] parts=str.split(",");
					myLeague.addPlayer(new Player(parts[0].trim(),parts[1].trim(),Double.parseDouble(parts[2].trim())));
				}
				else if(teamFlag){
					String[] parts=str.split(",");
					if(str.charAt(0)==','){
						team=parts[0].trim();
						myLeague.addUser(new User(team,Double.parseDouble(parts[1].trim())));
					}
					else
						myLeague.getTeam(team).addPlayer(myLeague.getPlayer(str.trim()));
				}
				else if(freeFlag){
					myLeague.getFreeAgent().addPlayer(myLeague.getPlayer(str.trim()));
				}
			}
			in.close();
			return turn;
		} catch (FileNotFoundException e) {
			return -1;
		} catch (IOException e) {
			return -1;
		} catch (IndexOutOfBoundsException e){
			return -1;
		}
	}
}
