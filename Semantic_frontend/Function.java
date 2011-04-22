import java.util.HashMap;


public class Function {

	private String functionName;
	private HashMap<String, String> argsList = new HashMap<String, String>();
	private String returnType;
	private HashMap<String, String> varList = new HashMap<String, String>();
	
	public Function(String functionName, String returnType, String paramList){
		//Parse and set
	}
	
	//Check whether the return type is the same as the returnProduction
	
	//Check whether a variable used exists and is the same as its declared type
	
	//Setters and Getters for VarList

	
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
