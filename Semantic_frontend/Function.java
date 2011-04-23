import java.util.HashMap;
import java.lang.Integer;


public class Function {

	String functionName;
	HashMap<String, String> argsList = new HashMap<String, String>(); //name type
	String returnType;
	static boolean debugging = true;
	
	/* Constructor sets a function's name, returnType and parameters in the instance variables of the function */
	public Function(String functionName, String returnType, String paramList){
		this.functionName = functionName;
		if (debugging){System.out.println("**Initializing " + functionName + " function**");}
		this.returnType = returnType;
		if (debugging){System.out.println(functionName + "= returnType: " + returnType);}
		String[] params = paramList.split(",");
		String[] temp;
		for(int i=0; i<params.length; i++){
			//remove spaces at beginning
			if(params[i].charAt(0)==' ')
				params[i] = params[i].substring(1);
			//split
			temp = params[i].split(" ");
			argsList.put(temp[1], temp[0]); //This is reversed in the argument list so reversing it back here
			if (debugging) {System.out.println(functionName + "= argName: " + temp[1] + ", type: " + temp[0]);}
		}
	}
	
	/* Check whether the return type is the same as the returnProduction */
	public boolean checkReturnType(String returnProduction){
		return returnType.equals(returnProduction);
	}
	
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	
	
}
