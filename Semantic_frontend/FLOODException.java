import java.util.Hashtable;

public class FLOODException extends Exception {
	private String message = null;
	private String lineInfo = null;
	
	public FLOODException(int lineInfo, String message){
		this.lineInfo = lineInfo + "";
		this.message = message;
	}

	public static FLOODException VariableAlreadyExists(int lineInfo, String element) throws FLOODException{
		FLOODException e = new FLOODException(lineInfo, "At line: " + lineInfo + ", " + element + " variable Already Exists");
		return e;		
	}

	public String getError(){
		return message;
	}
}
