import java.util.HashMap;

public class Function {

	String functionName;
	String[] paramTypeList;
	HashMap<String, String> argsList = new HashMap<String, String>(); //name type
	String returnType;
	int lineNumber;
	static boolean debugging = false;
	
	/* Constructor sets a function's name, returnType and parameters in the instance variables of the function */
	public Function(String functionName, String returnType, String paramList, int lineNumber){
		this.functionName = functionName;
		if (debugging){System.out.println("**Initializing " + functionName + " function**");}
		this.returnType = returnType;
		if (debugging){System.out.println(functionName + "= returnType: " + returnType);}
		this.lineNumber = lineNumber;
		
		if (paramList.contains("\\w+")){
			String[] params = paramList.trim().split("\\s*,\\s*");
			paramTypeList = new String[params.length];
			for(int i=0; i<params.length; i++){
				//split
				String[] temp = params[i].split("\\s+");
				argsList.put(temp[1], temp[0]); //This is reversed in the argument list so reversing it back here
				paramTypeList[i]=temp[0];
				if (debugging) {System.out.println(functionName + "= argName: " + temp[1] + ", type: " + temp[0]);}
			}
		}
		else{
			paramTypeList = new String[0];
		}
	}
		
	public String getReturnType() {
		return returnType;
	}
}

