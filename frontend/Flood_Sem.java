import java.util.*;

public class Flood_Sem {
	
	HashMap<String, Function> functionTable = new HashMap<String, Function>();
	HashMap<String, String> varList = new HashMap<String, String>();
	static boolean debugging = true;
	boolean draftFunFlag = false;
	boolean draftPlayFlag = false;
	boolean tradeFlag = false;
	boolean dropPlayFlag = false;

	//Add Variables
	LinkedList<String> actionNames = new LinkedList<String>();
	LinkedList<String> playerNames = new LinkedList<String>();
	LinkedList<String> userNames = new LinkedList<String>();
	
	//Assignment Variables
	String leftSide = "";

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

	//Check whether a user and action have already been added

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
	
	/* Adds a function to the function table. */
	public boolean addToFunctionTable(String functionName, String returnType, String paramList, int lineNumber){
		try{
			if (functionTable.containsKey(functionName)){
				if (debugging){System.out.println(functionName + "already exists");}
				return false;
			}
			functionTable.put(functionName, new Function(functionName, returnType, paramList, lineNumber));
			this.varList = new HashMap<String, String>();
			if (debugging){System.out.println("Reinitializing variable list");}
			return true;
		} catch(Exception e) {
			System.out.println(e);
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

	/* Checks the type of a variable against the left side of an expression */
	public boolean assignmentCheckVar(String right){
		if (varExists(right)){
			if (varList.get(right).equals(leftSide)){
				if (debugging){System.out.println("Both are of type " + leftSide);}
				return true;
			}
			if (debugging){System.out.println(right + " isn't of type " + leftSide);}
			return false;
		}
		if (debugging){System.out.println(right + " doesn't exist");}
		return false;
	}

	/* Preserves the type of the left side of an expression */
	public boolean assignmentCheckLeft(String left){
		if (varExists(left)){
			leftSide = varList.get(left);
			if (debugging){System.out.println("Added " + left);}	
		}
		return false;
	}

	/* Checks whether the left side of the expression is a TYPE */
	public boolean assignmentCheckLeftIsOfType(String type){
		if (leftSide.equals(type)){
			if (debugging){System.out.println(leftSide + " IS of type " + type);}
			return true;
		}
		if (debugging){System.out.println(leftSide + " is not of type " + type);}
		return false;
	}
	
	/* Checks the Return Type of the Function Against the Left Side of an expression */
	public boolean assignmentCheckFunction(String right){
		String[] params = right.split("\\("); //Opening bracket is special character to be escaped
		String functionName = params[0];
		if (functionTable.containsKey(functionName)){
			if (functionTable.get(functionName).getReturnType().equals(leftSide)){
				if (debugging){System.out.println("Both are of type " + leftSide);}
				return true;
			}
			if (debugging){System.out.println(right + " doesn't return type " + leftSide);}
			return false;
		}
		if (debugging){System.out.println(right + " doesn't exist");}
		return false;
	}

	/* Check Divide by zero */
	public boolean checkDivideByZero(String var){
		try{
			if (Double.parseDouble(var) == 0){
				return true;
			}
			return false;
		} catch (Exception e){
			return false;
		}
	}

	/* Checks against an int */
	public boolean checkRelationalExp(String left, int right){
		return checkRelationalExpAgainstType(left, "int");
	}

	/* Checks against a float */
	public boolean checkRelationalExp(String left, float right){
		return checkRelationalExpAgainstType(left, "float");
	}

	/* Checks against a declared variable */
	public boolean checkRelationalExp(String left, String right){
		if (varExists(right)){
			return checkRelationalExpAgainstType(left, varList.get(right));
		}
		if (debugging){System.out.println(right + " doesn't exist");}
		return false;
	}

	/* Checks to make sure that relational expression don't compare an invalid type */
	public boolean checkRelationForInvalidType(String left){
		if (varExists(left)){
			String leftType = varList.get(left);
			if (!leftType.equals("int") || !leftType.equals("float")){
				if (debugging){System.out.println(left + " cannot be used because it is of type " + leftType);}
				return false;
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	/* Private method for checking a variable against a type */
	private boolean checkRelationalExpAgainstType(String left, String rightType){
		if (varExists(left)){
			String leftType = varList.get(left);
			if (leftType.equals(rightType)){
				if (debugging){System.out.println("Both are of type " + rightType);}
				return true;
			}
			if (debugging){System.out.println(rightType + " is not " + leftType);}
			return false;
		}
		else{
			if (debugging){System.out.println(left + " doesn't exist");}
			return false;
		}
	}
	
	//check that array index is an int
	public boolean checkIndex(String arrayIndex){ 
		//check if ID is a variable of type int
		arrayIndex = arrayIndex.replaceAll(" ", "");
		if(varList.containsKey(arrayIndex) && varList.get(arrayIndex).equals("int")){
			System.out.println(arrayIndex +"\n"+ varList.get(arrayIndex));
			return true;
		}
		else{
			System.out.println("Fail, invalid type");
			return false;
		}
	} 
	
	//set flags for required functions
	public String setFlags(){
		//flag for draftFunction
		Function fun;
		if(functionTable.containsKey("draftFunction")){
			fun = functionTable.get("draftFunction");
			if(fun.returnType.equals("int")){
				if(fun.paramTypeList.length == 1 && fun.paramTypeList[0].equals("int")){
					System.out.println("Draft function found");
					draftFunFlag = true;
				}
			}
		}
		if(functionTable.containsKey("draftPlayer")){
			fun = functionTable.get("draftPlayer");
			if(fun.returnType.equals("boolean")){
				if(fun.paramTypeList.length==2 && fun.paramTypeList[0].equals("User")  && fun.paramTypeList[1].equals("Player")){
					System.out.println("Draft player found");
					draftPlayFlag = true;
				}
			}
		}
		if(functionTable.containsKey("trade")){
			fun = functionTable.get("trade");
			if(fun.returnType.equals("boolean")){
				if(fun.paramTypeList.length==4 && fun.paramTypeList[0].equals("User")
						&& fun.paramTypeList[1].equals("Player[]") && fun.paramTypeList[2].equals("User")
						&& fun.paramTypeList[3].equals("Player[]")){
					System.out.println("Trade found");
					tradeFlag = true;
				}
			}
		}
		if(functionTable.containsKey("dropPlayer")){
			fun = functionTable.get("dropPlayer");
			if(fun.returnType.equals("boolean")){
				if(fun.paramTypeList.length==2 && fun.paramTypeList[0].equals("User")
						&& fun.paramTypeList[1].equals("Player")){
					System.out.println("Drop player found");
					dropPlayFlag = true;
				}
			}
		}
		return writeDefaultFuns();
	}
	
	//write default functions if needed
	public String writeDefaultFuns(){
		String functions = "";
		if(!draftFunFlag){
			//write draft function
			functions += "public static int draftFunction(int turn){\nreturn turn%myLeague.getCurrentNumUsers();\n}\n";
		}
		if(!draftPlayFlag){
			//write draft player
			functions += "public static boolean draftPlayer(User u, Player p){\nu.addPlayer(p);\nreturn true;\n}\n";
		}
		if(!tradeFlag){
			//write trade
			functions += "public static boolean trade(User u1, Player[] p1, User u2, Player[] p2){\n"+
						"int i,j;\n"+
						"boolean flag2=true;\n"+
						"i=0;\n"+
						"while(i<p1.length){\n"+
							"flag2=dropPlayer(u1,p1[i]);\n"+
							"if(!flag2){    //If the drop was unsuccessful\n"+
								"j=i;\n"+
								"while(j>=0){    //Add p1 back to u1\n"+
								    "draftPlayer(u1,p1[j]);\n"+
								    "j--;\n"+
								"}\n"+
								"return false;\n"+
							"}\n"+
							"i++;\n"+
						"}\n"+
						"i=0;\n"+
						"while(i<p2.length){\n"+
							"flag2=dropPlayer(u2,p2[i]);\n"+
							"if(!flag2){    //If the drop was unsuccessful\n"+
								"j=i;\n"+
								"while(j>=0){    //Add p2 back to u2\n"+
								    "draftPlayer(u2,p2[j]);\n"+
								    "j--;\n"+
								"}\n"+
								"j=0;\n"+
								"while(j<p1.length){    //Add p1 to u1\n"+
								    "draftPlayer(u1,p1[j]);\n"+
								    "j++;\n"+
								"}\n"+
								"return false;\n"+
							"}\n"+
							"i++;\n"+
						"}\n"+
						"i=0;\n"+
						"while(i<p1.length){\n"+
							"flag2=draftPlayer(u2,p1[i]);\n"+
							"if(!flag2){    //If draft was unsuccessful\n"+
								"j=i;\n"+
								"while(j>=0){    //Remove p1 from u2\n"+
								    "dropPlayer(u2,p1[j]);\n"+
								    "j--;\n"+
								"}\n"+
								"j=0;\n"+
								"while(j<p1.length){    //Add p1 to u1\n"+
								    "draftPlayer(u1,p1[j]);\n"+
								    "j++;\n"+
								"}\n"+
								"j=0;\n"+
								"while(j<p2.length){    //Add p2 to u2\n"+
								    "draftPlayer(u2,p2[j]);\n"+
								    "j++;\n"+
								"}\n"+
								"return false;\n"+
							"}\n"+
							"i++;\n"+
						"}\n"+
						"i=0;\n"+
						"while(i<p2.length){\n"+
							"flag2=draftPlayer(u1,p2[i]);\n"+
							"if(!flag2){    //If the drop was unsuccessful\n"+
								"j=i;\n"+
								"while(j>=0){    //Remove p2 from u1\n"+
								    "dropPlayer(u1,p2[j]);\n"+
								    "j--;\n"+
								"}\n"+
								"j=0;\n"+
								"while(j<p1.length){    //Remove p1 from u2\n"+
								    "dropPlayer(u2,p1[j]);\n"+
								    "j++;\n"+
								"}\n"+
								"j=0;\n"+
								"while(j<p1.length){    //Add p1 to u1\n"+
								    "draftPlayer(u1,p1[j]);\n"+
								    "j++;\n"+
								"}\n"+
								"j=0;\n"+
								"while(j<p2.length){    //Add p2 to u2\n"+
								    "draftPlayer(u2,p2[j]);\n"+
								    "j++;\n"+
								"}\n"+
								"return false;\n"+
							"}\n"+
							"i++;\n"+
						"}\n"+
						"return true;\n"+
					"}\n";
		}
		if(!dropPlayFlag){
			//write drop player
			functions += "public static boolean dropPlayer(User u, Player p){\nu.removePlayer(p);\nreturn true;\n}\n";
		}
		return functions;
	}
}
