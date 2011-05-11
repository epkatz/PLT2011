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
	
	/**Write the current state of the league to a text file so that the program
	 * may be exited and resumes from the place it left off.
	 * 
	 * @param League myLeague
	 * @param String filePath
	 * @param int turn
	 */
	public static void writeState(League myLeague,String filePath,int turn){
		try {
			FileWriter fstream = new FileWriter(filePath);	//Create the file
			BufferedWriter out = new BufferedWriter(fstream);	//Initialize the output stream
			out.write(turn+","+myLeague.getMaxTeamSize()+","+myLeague.getMinTeamSize()+","+myLeague.getMaxUser()+","+myLeague.getMinUser()+"\n");	//Write the first line of the file
			//Write the actions
			out.write("ACTIONS:\n");
			Action[] actions=myLeague.getActions();	//Get the actions
			for(int i=0;i<actions.length;i++){	//Iterate through the actions
				out.write(actions[i].getAction()+","+actions[i].getPoints()+"\n");	//Write each action
			}
			//Write the players
			out.write("PLAYERS:\n");
			Player[] players=myLeague.getPlayers();	//Get the players
			for(int i=0;i<players.length;i++){	//Iterate through the players
				out.write(players[i].getName()+","+players[i].getPosition()+","+players[i].getPoints()+"\n");	//Write each players
			}
			//Write the teams
			out.write("TEAMS:\n");
			User[] teams=myLeague.getUsers();	//Get the teams
			for(int i=0;i<teams.length;i++){	//Iterate through the teams
				Player[] teamPlayers=teams[i].getPlayers();	//Get the players of each team
				out.write(","+teams[i].getName()+","+teams[i].getPoints()+"\n");	//Write each team
				for(int j=0;j<teamPlayers.length;j++){	//Iterate through the players
					out.write(teamPlayers[j].getName()+"\n");	//Write the player's name as a reference to the above players
				}
			}
			//Write the free agent
			out.write("FREE AGENT:\n");
			User free=League.freeAgent;	//Get the free agen
			Player[] freePlayers=free.getPlayers();	//Get the players of the free agent
			for(int i=0;i<freePlayers.length;i++){	//Iterate through the players
				out.write(freePlayers[i].getName()+"\n");	//Write the player's name as a reference to the above players
			}
			out.close();	//Close the output stream
		} catch (IOException e) {
			GUI.alert("Stat Dump Error!", "Error creating the dump file! Please try again.");
		}
	}
	
	/**Read a file representing the state of a program and restore that state
	 * to the current program.
	 * 
	 * @param League myLeague
	 * @param String filePath
	 * @return int turn
	 */
	public static int importState(League myLeague,String filePath){
		try {
			myLeague.clear();	//Clear the current league
			FileInputStream fstream = new FileInputStream(filePath);	//Open the file
			DataInputStream in = new DataInputStream(fstream);//Get the object of DataInputStream
			BufferedReader br = new BufferedReader(new InputStreamReader(in));	//Initialize the buffered reader
			//Initialize variables
			String str="",team="";
			int turn=-1;
			str=br.readLine();	//Read the first line
			String[] data=str.split(",\\s*");	//Split on commas
			if(data.length!=5){
				in.close();
				myLeague.clear();
				GUI.alert("Dump Import Error!", "Invalid dump file!");
				return -1;
			}
			//Store the data
			turn=Integer.parseInt(data[0]);
			myLeague.setMaxTeamSize(Integer.parseInt(data[1]));
			myLeague.setMinTeamSize(Integer.parseInt(data[2]));
			myLeague.setMaxUser(Integer.parseInt(data[3]));
			myLeague.setMinUser(Integer.parseInt(data[4]));
			
			boolean teamFlag=false,playerFlag=false,actionFlag=false,freeFlag=false;
			while((str = br.readLine()) != null){	//Read File Line By Line
				if(str.equalsIgnoreCase("ACTIONS:")){	//Actions reached
					actionFlag=true;
					playerFlag=false;
					teamFlag=false;
					freeFlag=false;
				}
				else if(str.equalsIgnoreCase("PLAYERS:")){	//Players reached
					playerFlag=true;
					actionFlag=false;
					teamFlag=false;
					freeFlag=false;
				}
				else if(str.equalsIgnoreCase("TEAMS:")){	//Teams reached
					teamFlag=true;
					actionFlag=false;
					playerFlag=false;
					freeFlag=false;
				}
				else if(str.equals("FREE AGENT:")){	//Free agent reached
					teamFlag=false;
					actionFlag=false;
					playerFlag=false;
					freeFlag=true;
				}
				else{	//Data
					if(actionFlag){	//If currently looking at actions
						String[] parts=str.split(",\\s*");	//Split on commas
						if(parts.length!=2){	//Validate that it's an action
							in.close();
							myLeague.clear();
							GUI.alert("Dump Import Error!", "Invalid dump file!");
							return -1;
						}
						myLeague.addAction(new Action(parts[0].trim(),Float.parseFloat(parts[1].trim())));	//Add action to league
					}
					else if(playerFlag){	//If currently looking at players
						String[] parts=str.split(",\\s*");	//Split on commas
						if(parts.length!=3){	//Validate that it's a player
							in.close();
							myLeague.clear();
							GUI.alert("Dump Import Error!", "Invalid dump file!");
							return -1;
						}
						myLeague.addPlayer(new Player(parts[0].trim(),parts[1].trim(),Float.parseFloat(parts[2].trim())));	//Add player to league
					}
					else if(teamFlag){	//If currently looking at teams
						String[] parts=str.split(",\\s*");	//Split on commas
						if(str.charAt(0)==','){	//If it's a team name
							if(parts.length!=3){	//Validate that it's a team name
								in.close();
								myLeague.clear();
								GUI.alert("Dump Import Error!", "Invalid dump file!");
								return -1;
							}
							team=parts[1].trim();	//Trim white space
							myLeague.addUser(new User(team,Float.parseFloat(parts[2].trim())));	//Add team to league
						}
						else{	//If it's a team player
							if(parts.length!=1){	//Validate that it's a player name
								in.close();
								myLeague.clear();
								GUI.alert("Dump Import Error!", "Invalid dump file!");
								return -1;
							}
							myLeague.getTeam(team).addPlayer(myLeague.getPlayer(parts[0].trim()));	//Get reference to player and add to team
						}
					}
					else if(freeFlag){	//If currently looking at free agent
						String[] parts=str.split(",\\s*");	//Split on commas
						if(parts.length!=1){	//Validate that it's a player name
							in.close();
							myLeague.clear();
							GUI.alert("Dump Import Error!", "Invalid dump file!");
							return -1;
						}
						myLeague.getFreeAgent().addPlayer(myLeague.getPlayer(parts[0].trim()));	//Get reference to player and add player to free agent
					}
				}
			}
			in.close();	//Close input stream
			if(!freeFlag){	//If the free agent was never reached
				in.close();
				myLeague.clear();
				GUI.alert("Dump Import Error!", "Invalid dump file!");
				return -1;
			}
			return turn;	//Return current turn
		} catch (FileNotFoundException e) {
			myLeague.clear();
			GUI.alert("Dump Import Error!", "File not found!");
			return -1;
		} catch (IOException e) {
			myLeague.clear();
			GUI.alert("Dump Import Error!", "Error writing file!");
			return -1;
		} catch (IndexOutOfBoundsException e){
			myLeague.clear();
			GUI.alert("Dump Import Error!", "Invalid dump file!");
			return -1;
		}
	}
	
	/**Upload the statistics from a file.
	 * 
	 * @param League myLeague
	 * @param String fileName
	 */
	public static void uploadStats(League myLeague,String filePath){
		try {
			ArrayList<String[]> statsAL = new ArrayList<String[]>();
			FileInputStream fstream = new FileInputStream(filePath);	//Open the file
			DataInputStream in = new DataInputStream(fstream);	//Get the object of DataInputStream
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String str;
			String[] stats;
			boolean valid=true;
			while((str = br.readLine()) != null){	//Read File Line By Line
				stats=str.split(",\\s*");
				valid=valid && myLeague.getPlayer(stats[0])!=null;	//Check if the athlete exists
				if(!valid){	//If the athlete doesn't exist
					in.close();
					GUI.error("Athelete doesn't exist! ",stats[0]+" is not a valid athlete.");
					return;
				}
				valid=valid && myLeague.getAction(stats[1])!=null;	//Check if the action exists
				if(!valid){	//If the athlete doesn't exist
					in.close();
					GUI.error("Action doesn't exist! ",stats[1]+" is not a valid action.");
					return;
				}
				valid=valid && Integer.parseInt(stats[2])>0;	//Check if the quantity is greater than zero
				if(!valid){	//If the quantity is less than or equal to zero
					in.close();
					GUI.error("Quantity must be positive! ",stats[2]+" is not a positive number greater than zero.");
					return;
				}
				statsAL.add(stats);	//Split on commas
			}
			in.close();	//Close
			for(int i=0;i<statsAL.size();i++){	//Iterate through the stats
				float pts=myLeague.getAction(statsAL.get(i)[1]).getPoints() * Integer.parseInt(statsAL.get(i)[2]);	//Compute the points
				Player temp=myLeague.getPlayer(statsAL.get(i)[0]);	//Get the player
				temp.addPoints(pts);	//Add the points to the player and thereby the team they're one
			}
		} catch (FileNotFoundException e) {
			GUI.alert("Stat Parsing Error!","File not found!");
		} catch (IOException e) {
			GUI.alert("Stat Parsing Error!","Error opening the file!");
		} catch (IndexOutOfBoundsException e){
			GUI.alert("Stat Parsing Error!","Invalid stat file!");
		}
	}
}
