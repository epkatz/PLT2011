import java.io.*;
import java.util.ArrayList;

/*
 * Stats Parser
 */
public class StatParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[][] stats = getStats(args[0]);
		print(stats);
	}
	
	public static String[][] getStats(String file){
		ArrayList<String> strLine = new ArrayList<String>();		
		try{
			// Open the file that is the first 
			FileInputStream fstream = new FileInputStream(file);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String str;
			//Read File Line By Line
			while((str = br.readLine()) != null){
				strLine.add(str);
			}
			in.close();
			
		}
		catch (Exception e){
			//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		ArrayList<ArrayList<String>>statsAL = new ArrayList<ArrayList<String>>();
		for(int i=0; i<strLine.size(); i++){
			String[] temp = new String[3];
			temp = strLine.get(i).split(",");
			ArrayList<String> tempAL = new ArrayList<String>();
			for(int j=0; j<temp.length; j++){
				tempAL.add(temp[j]);
			}
			statsAL.add(tempAL);
		}
		String[][] stats = new String[statsAL.size()][statsAL.get(0).size()];
		for(int i=0; i<stats.length; i++){
			for(int j=0; j<stats[i].length; j++)
				stats[i][j] = statsAL.get(i).get(j);
		}
		return stats;
	}
	
	public static void print(String[][] array){
		for(int i=0; i<array.length; i++){
			for(int j=0; j<array[i].length; j++){
				System.out.print(array[i][j]+", ");
			}
			System.out.println();
		}
	}
}
