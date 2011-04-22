import java.util.HashMap;


public class Function {

	private String functionName;
	private HashMap<String, String> argsList = new HashMap<String, String>(); //name type
	private String returnType;
	private HashMap<String, String> varList = new HashMap<String, String>(); //name, type
	
	//Parse and set
	public Function(String functionName, String returnType, String paramList){
		this.functionName = functionName;
		this.returnType = returnType;
		String[] params = paramList.split(",");
		String[] temp;
		for(int i=0; i<params.length; i++){
			//remove spaces at beginning
			if(params[i].charAt(0)==' ')
				params[i] = params[i].substring(1);
			//split
			temp = params[i].split(" ");
			argsList.put(temp[0], temp[1]);
		}
	}
	
	//Check Divide by zero
	public boolean checkDivideByZero(String var1, String var2){
		for(;;)
			return true;
	}
	
	//Check whether the return type is the same as the returnProduction
	public boolean checkReturnType(String returnProduction){
		return returnType.equals(returnProduction);
	}
	
	//Check whether a variable used exists and is the same as its declared type
	public boolean varExists(String varName, String varType){
		if(varList.containsKey(varName)){
			return varType.equals(varList.get(varName));
		}
		else
			return false;
	}
	
	//Setters and Getters for VarList
	public boolean addVar(String varName, String varType){
		varList.put(varName, varType);
		return true;
	}
	public boolean setVarType(String varName, String varType){
		varList.remove(varName);
		varList.put(varName, varType);
		return true;
	}
	public String getVar(String varName){
		return varList.get(varName);
	}
	
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public HashMap<String, String> getArgsList() {
		return argsList;
	}
	public void setArgsList(HashMap<String, String> argsList) {
		this.argsList = argsList;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	
	
}
