import java.util.*;

public class Flood_Sem {
	
	HashMap<String, Function> functionTable = new HashMap<String, Function>();
	HashMap<String, String> varList = new HashMap<String, String>();
	ArrayList<String> errorList=new ArrayList<String>();
	String returnProductionType;
	static boolean debugging = false,validProgram=true;
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
		if(debugging)System.out.print("Starting Semantic Object Checker");
	}
	
	public String printErrors(){
		String errors="";
		for(String s:errorList){
			errors+=s+"\n";
		}
		return errors;
	}
	
	/*Adds an Action */
	public void addAction(String name, int line){
		if (actionNames.contains(name)){
			if (debugging){System.out.println(name + " already exists as an Action");}
			validProgram=false;
			errorList.add("Error at Line " + line + ": " +name+" has already been defined.");
			return;
		}
		actionNames.add(name);
		if (debugging){System.out.println(name + " was added as an Action");}
		return;
	}

	/* Add a User */
	public void addUser(String name, int line){
		if (userNames.contains(name)){
			if (debugging){System.out.println(name + " already exists as a User");}
			validProgram=false;
			errorList.add("Error at Line " + line + ": " +name+" has already been defined.");
			return;
		}
		userNames.add(name);
		if (debugging){System.out.println(name + " was added as a User");}
	}

	/* Add a Player */
	public void addPlayer(String name, int line){
		if (playerNames.contains(name)){
			if (debugging){System.out.println(name + " already exists as a Player");}
			validProgram=false;
			errorList.add("Error at Line " + line + ": " +name+" has already been defined.");
			return;
		}
		playerNames.add(name);
		if (debugging){System.out.println(name + " was added as a Player");}
	}
	
	/* Adds a function to the function table. */
	public void addToFunctionTable(String functionName, String returnType, String paramList, int line){
		if (functionTable.containsKey(functionName)){
			if (debugging){System.out.println(functionName + "already exists");}
			validProgram=false;
			errorList.add("Error at Line " + line + ": " +functionName+" has already been defined.");
			return;
		}
		functionTable.put(functionName, new Function(functionName, returnType, paramList, line));
		this.varList = new HashMap<String, String>();
		if (debugging){System.out.println("Reinitializing variable list");}
	}

	/* Checks whether a given value is boolean true or false (note: case specific) */
	public void isBooleanValue(String bool, int line){
		if (bool.equals("true") || bool.equals("false")){
			return;
		}
		if (debugging){System.out.println(bool + " is not a boolean value");}
		validProgram=false;
		errorList.add("Error at Line " + line + ": " +bool+" is not of type Bool.");
	}
	
	/* Adds a variable to the function's variable list. Checks if it exists first */
	public void addVar(String varName, String varType, int line){
		if (varExists(varName)){
			if (debugging){System.out.println(varName + " already exists");}
			validProgram=false;
			errorList.add("Error at Line " + line + ": " +varName+" has already been defined.");
			return;
		}
		addVarToTable(varName, varType);
	}

	/* Check whether a variable used exists and is the same as its declared type */
	public boolean varExists(String varName){
		if(varList.containsKey(varName)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/* AddtoCurrentVarList */
	public void addVarToTable(String varName, String varType){
		varList.put(varName, varType);
		if (debugging){System.out.println("Added varName: " + varName + ", type: " + varType);}
	}

	/* Checks the type of a variable against the left side of an expression */
	public void assignmentCheckVar(String right, int line){
		if (varExists(right)){
			if (varList.get(right).equals(leftSide)){
				if (debugging){System.out.println("Both are of type " + leftSide);}
				return;
			}
			if (debugging){System.out.println(right + " isn't of type " + leftSide);}
			validProgram=false;
			errorList.add("Error at Line " + line + ": " +right+" is not of type "+leftSide+".");
			return;
		}
		if (debugging){System.out.println(right + " doesn't exist");}
		validProgram=false;
			errorList.add("Error at Line " + line + ": " +right+" has not been defined.");
			return;
	}

	/* Preserves the type of the left side of an expression */
	public void assignmentCheckLeft(String left, int line){
		if (varExists(left)){
			leftSide = varList.get(left);
			if (debugging){System.out.println("Added " + left);}
			return;
		}
		validProgram=false;
		errorList.add("Error at Line " + line + ": " +left+" has not been defined.");
	}
	
	/* Check whether an arithmetic expression can be used with the kind of ID */
	public void checkForBadAdditionType(int line){
		if (leftSide.equals("float") || leftSide.equals("int") || leftSide.equals("String")){
			if (debugging){System.out.println(leftSide + " can be used with add");}
			return;
		}
		if (debugging){System.out.println(leftSide + " cannot be used in addition");}
		validProgram=false;
		errorList.add("Error at Line " + line + ": " +leftSide+" cannot be used in addition.");
		return;
	}
	
	/* Check whether an arithmetic expression can be used with the kind of ID */
	public void checkForBadArithmeticType(int line){
		if (leftSide.equals("float") || leftSide.equals("int")){
			if (debugging){System.out.println(leftSide + " can be used with add/sub/mul/div/mod");}
			return;
		}
		if (debugging){System.out.println(leftSide + " cannot be used in an arithmetic expression");}
		validProgram=false;
		errorList.add("Error at Line " + line + ": " +leftSide+" cannot be used with arithmetic expressions.");
		return;
	}

	/* Checks whether the left side of the expression is a TYPE */
	public void assignmentCheckLeftIsOfType(String type, int line){
		if (leftSide.equals(type)){
			if (debugging){System.out.println(leftSide + " IS of type " + type);}
			return;
		}
		if (debugging){System.out.println(leftSide + " is not of type " + type);}
		validProgram=false;
			errorList.add("Error at Line " + line + ": " +leftSide + " is not of type " + type + ".");
			return;
	}
	
	/* Checks the Return Type of the Function Against the Left Side of an expression */
	public void assignmentCheckFunction(String right, int line){
		String[] params = right.split("\\("); //Opening bracket is special character to be escaped
		String functionName = params[0];
		if (functionTable.containsKey(functionName)){
			if (functionTable.get(functionName).getReturnType().equals(leftSide)){
				if (debugging){System.out.println("Both are of type " + leftSide);}
				return;
			}
			if (debugging){System.out.println(right + " doesn't return type " + leftSide);}
			validProgram=false;
			errorList.add("Error at Line " + line + ": " +right + " does not return a value of type " + leftSide + ".");
			return;
		}
		if (debugging){System.out.println(right + " has not been defined.");}
		validProgram=false;
		errorList.add("Error at Line " + line + ": " +right + " doesn't return a value of type " + leftSide + ".");
		return;
	}

	/* Check Divide by zero */
	public void checkDivideByZero(String var, int line){
		if (Double.parseDouble(var) == 0){
			return;
		}
		validProgram=false;
		errorList.add("Error at Line " + line + ": Cannot divide by zero.");
		return;
	}

	/* Checks against an int */
	public void checkRelationalExp(String left, int right, int line){
		checkRelationalExpAgainstType(left, "int", line);
	}

	/* Checks against a float */
	public void checkRelationalExp(String left, float right, int line){
		checkRelationalExpAgainstType(left, "float", line);
	}

	/* Checks against a declared variable */
	public void checkRelationalExp(String left, String right, int line){
		if (varExists(right)){
			checkRelationalExpAgainstType(left, varList.get(right), line);
			return;
		}
		if (debugging){System.out.println(right + " doesn't exist");}
		validProgram=false;
		errorList.add("Error at Line " + line + ": " +right+" has not been defined.");
		return;
	}

	/* Checks to make sure that relational expression don't compare an invalid type */
	public void checkRelationForInvalidType(String left, int line){
		if (varExists(left)){
			String leftType = varList.get(left);
			if (!leftType.equals("int") || !leftType.equals("float")){
				if (debugging){System.out.println(left + " cannot be used because it is of type " + leftType);}
				validProgram=false;
				errorList.add("Error at Line " + line + ": " +left + " cannot be used because it is of type " + leftType + ".");
				return;
			}
		}
		else{
			validProgram=false;
			errorList.add("Error at Line " + line + ": " +left+" has not been defined.");
			return;
		}
	}
	
	/* Private method for checking a variable against a type */
	private void checkRelationalExpAgainstType(String left, String rightType, int line){
		if (varExists(left)){
			String leftType = varList.get(left);
			if (leftType.equals(rightType)){
				if (debugging){System.out.println("Both are of type " + rightType);}
				return;
			}
			if (debugging){System.out.println(rightType + " is not " + leftType);}
			validProgram=false;
			errorList.add("Error at Line " + line + ": " +rightType + " is not " + leftType + ".");
			return;
		}
		else{
			if (debugging){System.out.println(left + " doesn't exist");}
			validProgram=false;
			errorList.add("Error at Line " + line + ": " +left + " has not been defined.");
			return;
		}
	}
	
	//check that array index is an int
	public void checkIndex(String arrayIndex, int line){ 
		//check if ID is a variable of type int
		arrayIndex = arrayIndex.replaceAll(" ", "");
		if(varList.containsKey(arrayIndex) && varList.get(arrayIndex).equals("int")){
			if(debugging){System.out.println(arrayIndex +"\n"+ varList.get(arrayIndex));}
			return;
		}
		else{
			if (debugging){System.out.println("Fail, invalid type");}
			validProgram=false;
			errorList.add("Error at Line " + line + ": " +arrayIndex+" is an invalid type for an array index.");
			return;
		}
	}
	
	public String getType(String id){
		if(varExists(id))
			return varList.get(id);
		if(debugging){System.out.println(id+" is a valid variable!");}
		return null;
	}
	public void setReturnProdType(String type){
		returnProductionType=type;
	}
	public void checkReturnTypeMatch(String returnType, int line){
		if(returnProductionType!=null){
			if(returnProductionType.equals(returnType)){
				if (debugging){System.out.println(returnProductionType + " return type matches "+returnType);}
				return;
			}
			if (debugging){System.out.println(returnProductionType + " return type doesn't match "+returnType);}
			validProgram=false;
			errorList.add("Error at Line " + line + ": " +returnProductionType + " return type does not match "+returnType+".");
			return;
		}
		if (debugging){System.out.println(returnProductionType + " is not a valid type");}
		validProgram=false;
		errorList.add("Error at Line " + line + ": " +returnProductionType + " is not a valid type.");
		return;
	}
	
	/* Check that an ID is of a certain type */
	public void checkIDagainstType(String id, String type, int line){
		if (varExists(id)){
			String idType = varList.get(id);
			if (idType.equals(type)){
				if (debugging){System.out.println(id + " IS of type " + type);}
				return;
			}
			if (debugging){System.out.println(id + " is not " + type);}
			validProgram=false;
			errorList.add("Error at Line " + line + ": " +id + " is not of type " + type + ".");
			return;
		}
		if (debugging){System.out.println(id + " doesn't exist");}
		validProgram=false;
		errorList.add("Error at Line " + line + ": " +id + " has not been defined.");
		return;
	}
	
	//set flags for required functions
	public String setFlags(){
		//flag for draftFunction
		Function fun;
		if(functionTable.containsKey("draftFunction")){
			fun = functionTable.get("draftFunction");
			if(fun.returnType.equals("int")){
				if(fun.paramTypeList.length == 1 && fun.paramTypeList[0].equals("int")){
					if(debugging)System.out.print("Draft function found");
					draftFunFlag = true;
				}
			}
		}
		if(functionTable.containsKey("draftPlayer")){
			fun = functionTable.get("draftPlayer");
			if(fun.returnType.equals("boolean")){
				if(fun.paramTypeList.length==2 && fun.paramTypeList[0].equals("User")  && fun.paramTypeList[1].equals("Player")){
					if(debugging)System.out.print("Draft player found");
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
					if(debugging)System.out.print("Trade found");
					tradeFlag = true;
				}
			}
		}
		if(functionTable.containsKey("dropPlayer")){
			fun = functionTable.get("dropPlayer");
			if(fun.returnType.equals("boolean")){
				if(fun.paramTypeList.length==2 && fun.paramTypeList[0].equals("User")
						&& fun.paramTypeList[1].equals("Player")){
					if(debugging)System.out.print("Drop player found");
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

