import java.util.HashMap;
import java.lang.Integer;


public class Function {

	String functionName;
	String[] paramTypeList;
	HashMap<String, String> argsList = new HashMap<String, String>(); //name type
	String returnType;
	int lineNumber;
	static boolean debugging = true;
	
	/* Constructor sets a function's name, returnType and parameters in the instance variables of the function */
	public Function(String functionName, String returnType, String paramList, int lineNumber){
		this.functionName = functionName;
		if (debugging){System.out.println("**Initializing " + functionName + " function**");}
		this.returnType = returnType;
		if (debugging){System.out.println(functionName + "= returnType: " + returnType);}
		this.lineNumber = lineNumber;
		
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
	
	/* Check whether the return type is the same as the returnProduction */
	public boolean checkReturnType(String returnProduction){
		return returnType.equals(returnProduction);
	}
	
	/* Check whether the return type is the same as the actual return */
	public boolean checkActualReturn(String returnProduction){
		//get return value
		String returnVal = returnProduction.replaceAll("return ", "");
		returnVal = returnVal.replaceAll(";", "");
		
		//find in argument list
		if(argsList.containsKey(returnVal)){
			return returnType.equals(argsList.get(returnVal));
		}
		//if not in argument list the check constant type
		else{
			//if starts and ends with "
			if(returnVal.startsWith("\"") && returnVal.endsWith("\""))
				return  returnType.equals("str");
			//if starts and ends with '
			if(returnVal.startsWith("\'") && returnVal.endsWith("\'"))
				return  returnType.equals("str");
			//check if float
			if(!returnVal.contains(".")){
				try{
					Integer.parseInt(returnVal);
					return returnType.equals("Int");
				}
				catch(Exception e){
					return false;
				}
			}
			else{
				try{
					Float.parseFloat(returnVal);
					return returnType.equals("flt");
				}
				catch(Exception e){
					return false;
				}
			}
		}
	}
	
	//check that function returns
	public boolean functionReturns(String returnProduction){
		//get return value
		String returnVal = returnProduction.replaceAll("return ", "");
		returnVal = returnVal.replaceAll(";", "");
		return (returnVal.length()>0);
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
