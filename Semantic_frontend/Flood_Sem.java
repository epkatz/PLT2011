import java.util.HashMap;


public class Flood_Sem {
	
	public HashMap<String, String[]> functionTable = new HashMap<String, String[]>();
	public HashMap<String, String> varTable = new HashMap<String, String>();
		
	/***************************************************
	* HashTable functions
	****************************************************/

	public boolean inFunctionTable(String name)
	{
	    return functionTable.containsKey(name);
	}

	public boolean inVarTable(String name)
	{
	    return varTable.containsKey(name);
	}

	public boolean addToFunctionTable(String name, String[] args)
	{
	    if (!inFunctionTable(name))
	    {
	      functionTable.put(name, args);
	      return true;
	    }
	    else
	    {
	      return false;
	    }
	}

	public boolean addToVarTable(String name, String type)
	{
	    if (!inVarTable(name))
	    {
	      varTable.put(name, type);
	      return true;
	    }
	    else
	    {
	      return false;
	    }
	} 
	
}
