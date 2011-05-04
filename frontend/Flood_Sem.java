import java.util.*;

public class Flood_Sem {
	
	HashMap<String, Function> functionTable = new HashMap<String, Function>();
	HashMap<String, String> varList = new HashMap<String, String>();
	static boolean debugging = true;
	boolean draftFunFlag = false;
	boolean draftPlayFlag = false;
	boolean tradeFlag = false;
	boolean dropPlayFlag = false;
	LinkedList<String> actionNames = new LinkedList<String>();
	LinkedList<String> playerNames = new LinkedList<String>();
	LinkedList<String> userNames = new LinkedList<String>();
	

	public Flood_Sem(){
		System.out.println("Starting Semantic Object Checker");
	}


	/* Function Debugging Test */
	public void functionTest(){
		Iterator it = functionTable.entrySet().iterator();
         	while (it.hasNext()) {
             		Map.Entry pairs = (Map.Entry)it.next();
             		System.out.println(pairs.getKey());
    		}
	}
	
	//RelationshalExpression Check; For instance if Int x = y then check that y is an int and is set
	
	//Validate Attribute Value ie: make sure that the assignment int x = 3; that 3 is actual an ant or in some cases use coercion
	
	//Check to make sure max user is greater than min user

	/*Adds an Action */
	public boolean addAction(String name){
		if (actionNames.contains(name)){
			if (debugging){System.out.println(name + " already exists as an Action");}
			return false;
		}
		actionNames.add(name);
		if (debugging){System.out.println(name + " was added as an Action");}
		return true;
	}

	/* Add a User */
	public boolean addUser(String name){
		if (userNames.contains(name)){
			if (debugging){System.out.println(name + " already exists as a User");}
			return false;
		}
		userNames.add(name);
		if (debugging){System.out.println(name + " was added as a User");}
		return true;
	}

	/* Add a Player */
	public boolean addPlayer(String name){
		if (playerNames.contains(name)){
			if (debugging){System.out.println(name + " already exists as a Player");}
			return false;
		}
		playerNames.add(name);
		if (debugging){System.out.println(name + " was added as a Player");}
		return true;
	}
	
	/* Check Divide by zero */
	public boolean checkDivideByZero(String var1, String var2){
		try{
			int x = Integer.parseInt(var1) / Integer.parseInt(var2);
			return true;
		} catch (Exception e){
			return false;
		}
	}

	/* Adds a function to the function table. TODO Checks if it exists first */
	public boolean addToFunctionTable(String functionName, String returnType, String paramList, int lineNumber){
		try{
			functionTable.put(functionName, new Function(functionName, returnType, paramList, lineNumber));
			this.varList = new HashMap<String, String>();
			if (debugging){System.out.println("Reinitializing variable list");}
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	/* Checks whether a given value is boolean true or false (note: case specific) */
	public boolean isBooleanValue(String bool){
		if (bool.equals("true") || bool.equals("false")){
			return true;
		}
		if (debugging){System.out.println(bool + " is not a boolean value");}
		return false;
	}
	
	/* Adds a variable to the function's variable list. Checks if it exists first */
	public boolean addVar(String varName, String varType){
		try{
			if (varExists(varName)){
				if (debugging){System.out.println(varName + " already exists");}
				return false;
			}
			addVarToTable(varName, varType);
			return true;
		} catch(Exception e){
			if (debugging){System.out.println(varName + " has bad data");}
			return false;
		}	
	}

	/* Check whether a variable used exists and is the same as its declared type */
	public boolean varExists(String varName){
		if(varList.containsKey(varName)){
			return true;
		}
		else
			return false;
	}
	
	/* AddtoCurrentVarList */
	public boolean addVarToTable(String varName, String varType){
		varList.put(varName, varType);
		if (debugging){System.out.println("Added varName: " + varName + ", type: " + varType);}
		return true;
	}

	/* Checking both sides of an assignment */
	public boolean assignmentCheck(String left, String right){
		System.out.println("Checking if " + left + " = " + right);
		if (varExists(left)){
			String leftType = varList.get(left);
			if (varExists(right)){
				if (varList.get(right).equals(leftType)){
					if (debugging){System.out.println("Both are of type " + leftType);}
					return true;
				}
			}
			else if (right.contains("(")){
				String[] params = right.split("\\("); //Opening bracket is special character to be escaped
				String functionName = params[0];
				if (functionTable.containsKey(functionName)){
					if (functionTable.get(functionName).getReturnType().equals(leftType)){
						if (debugging){System.out.println("Both are of type " + leftType);}
						return true;
					}
				}
			}
		}
		else{
			if (debugging){System.out.println(left + " doesn't exist");}		
		}
		return false;
	}
	
	//set flags for required functions
	public void setFlags() throws FLOODException{
		//flag for draftFunction
		Function fun;
		if(functionTable.containsKey("draftFunction")){
			fun = functionTable.get("draftFunction");
			if(fun.returnType.equals("int"))
				draftFunFlag = true;
			else
				throw FLOODException.InvalidFunctionReturnType(fun.lineNumber, fun.functionName);
		}
		if(functionTable.containsKey("draftPlayer")){
			fun = functionTable.get("draftPlayer");
			if(fun.returnType.equals("boolean"))
				draftPlayFlag = true;
			else
				throw FLOODException.InvalidFunctionReturnType(fun.lineNumber, fun.functionName);
		}
		if(functionTable.containsKey("trade")){
			fun = functionTable.get("trade");
			if(fun.returnType.equals("boolean"))
				draftPlayFlag = true;
			else
				throw FLOODException.InvalidFunctionReturnType(fun.lineNumber, fun.functionName);
		}
		if(functionTable.containsKey("dropPlayer")){
			fun = functionTable.get("dropPlayer");
			if(fun.returnType.equals("boolean"))
				draftPlayFlag = true;
			else
				throw FLOODException.InvalidFunctionReturnType(fun.lineNumber, fun.functionName);
		}
	}
	
	//write default functions if needed
	public String[] writeDefaultFuns(){
		String[] functions = {"", "", "", ""};
		if(!draftFunFlag){
			//write draft function
			functions[0] = "public static int draftFunction(int turn){return turn%myLeague.getCurrentNumUsers();}";
		}
		if(!draftPlayFlag){
			//write draft player
			functions[1] = "public static boolean draftPlayer(User u, Player p){u.addPlayer(p);return true;}";
		}
		if(!tradeFlag){
			//write trade
			functions[2] = "public static boolean trade(User u1, Player[] p1, User u2, Player[] p2){boolean flag2=true;for(int i=0;i<p1.length;i++){flag2=dropPlayer(u1,p1[i]);if(!flag2){for(int j=i;j>=0;j--){draftPlayer(u1,p1[j]);}return false;}}for(int i=0;i<p2.length;i++){flag2=dropPlayer(u2,p2[i]);if(!flag2){for(int j=i;j>=0;j--){draftPlayer(u2,p2[j]);}for(int j=0;j<p1.length;j++){draftPlayer(u1,p1[j]);}return false;}}for(int i=0;i<p1.length;i++){flag2=draftPlayer(u2,p1[i]);if(!flag2){for(int j=i;j>=0;j--){dropPlayer(u2,p1[j]);}for(int j=0;j<p1.length;j++){draftPlayer(u1,p1[j]);}for(int j=0;j<p2.length;j++){draftPlayer(u2,p2[j]);}return false;}}for(int i=0;i<p2.length;i++){flag2=draftPlayer(u1,p2[i]);if(!flag2){for(int j=i;j>=0;j--){dropPlayer(u1,p2[j]);}for(int j=0;j<p1.length;j++){dropPlayer(u2,p1[j]);}for(int j=0;j<p1.length;j++){draftPlayer(u1,p1[j]);}for(int j=0;j<p2.length;j++){draftPlayer(u2,p2[j]);}return false;}}return true;}";
		}
		if(!dropPlayFlag){
			//write drop player
			functions[3] = "public static boolean dropPlayer(User u, Player p){u.removePlayer(p);return true;}";
		}
		return functions;
	}
}
