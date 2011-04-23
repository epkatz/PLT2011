import java.util.HashMap;
import java.util.*;


public class Flood_Sem {
	
	HashMap<String, Function> functionTable = new HashMap<String, Function>();
	HashMap<String, String> varList = new HashMap<String, String>();
	static boolean debugging = true;

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

	//Check SemanticValue
	
	//RelationshalExpression Check; For instance if Int x = y then check that y is an int and is set
	
	//Validate Attribute Value ie: make sure that the assignment int x = 3; that 3 is actual an ant or in some cases use coercion
	
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
	public boolean addToFunctionTable(String functionName, String returnType, String paramList){
		try{
			functionTable.put(functionName, new Function(functionName, returnType, paramList));
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
	
}
