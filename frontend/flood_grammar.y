%{
/* import statements */
import java.lang.Math;
import java.io.*;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Iterator;
%}

/* YACC Declarations */
%token EMPTY            /* newline  */
%token <ival> INT	/* an integer */
%token <dval> FLT   	/* a float */
%token <sval> ID        /* a string */
%token WP               /* whitespace */
%token SEMICOLON        /* semicolon */
%token COMMA            /* comma separator */
%token COLON            /* colon separator */
%token OPEN_PARAN       /* { */ 
%token CLOSE_PARAN      /* } */
%token EQUAL            /* = */
%token NOTEQUAL         /* != */
%token LESSEQUAL        /* <= */
%token GREATEQUAL       /* >= */
%token ISEQUAL          /* == */
%token PLUS             /* + */
%token MINUS            /* - */
%token MUL              /* * */
%token DIV              /* / */
%token LESS             /* < */
%token GREAT            /* > */
%token OPEN             /* ( */
%token CLOSE            /* ) */
%token Quote            /* " */
%token STRING_CONST   	/* String literal constants */

/* Keywords */
%token Action		/* Action keyword */
%token bool		/* bool keyword */
%token Class		/* Class keyword */
%token Draft		/* Draft keyword */
%token else		/* else keyword */
%token false		/* false keyword */
%token <dval> flt	/* flt keyword */
%token for		/* for keyword */
%token get		/* get keyword */
%token if		/* if keyword */
%token in		/* in keyword */
%token <ival> int	/* int keyword */
%token is		/* is keyword */
%token League		/* League keyword */
%token list		/* list keyword */
%token new		/* new keyword */
%token Player		/* Player keyword */
%token private		/* private keyword */
%token public		/* public keyword */
%token return		/* return keyword */
%token set		/* set keyword */
%token setget		/* setget keyword */
%token str		/* str keyword */
%token true		/* true keyword */
%token User		/* User keyword */
%token void		/* void keyword */
%token while		/* while keyword */

/* Associativity and precedence */
%left MINUS PLUS
%left MUL DIV
%nonassoc EQUAL NOTEQUAL LESSEQUAL GREATEQUAL ISEQUAL LESS GREAT

%type <sval> definitions	/* definitions production */

%%

program: definitions usercode { };

definitions: crowd_definitions park_definition crowd_definitions { $$ = $1 + $2 + $3; } ;
crowd_definitions: crowd_definitions crowd_definition { $$ += $2; }
                 | error_production { $$ = $1; }
                 ;

usercode: start functions { $$ = $1 + $2; };

crowd_definition: Crowd crowd_name crowd_elements 
			{ scopeName = "Global"; addToHashtable($2, "Crowd"); globalObjects.add("Crowd " + $2);
			  $$ = "\n" + $2 + " = " + "new Crowd();\n" + generateSetAttribute($2, $3); 
			};
                
crowd_elements: SEMICOLON {}
              | crowd_attributes { $$ = $1; }
              ;
              
crowd_attributes: crowd_attributes crowd_attribute { $$ += $2;}
                | error_production	{ $$ = "";}
                ;
                
crowd_attribute: c1 { $$ = $1; }
               | c2 { $$ = $1; }
               | c3 { $$ = $1; }
               | c4 { $$ = $1; }
               ;

c1: Set SpendingCapacity NUMBER SEMICOLON { $$ = ":SpendingCapacity:" + $3;};
c2: Set Size NUMBER SEMICOLON 		 { $$ = ":Size:" + $3; };
c3: Set EnergyLevel NUMBER SEMICOLON	 { $$ = ":EnergyLevel:" + $3;};
c4: Set ThrillLevel NUMBER SEMICOLON   	 { $$ = ":ThrillLevel:" + $3;};

park_definition: Park park_name park_elements { scopeName = "Global"; addToHashtable($2, "Park"); 
                                                globalObjects.add("Park " + $2);};
		     land_definitions { $$ = "\n" + $2 + " = " + "new Park();\n" + generateSetAttribute($2, $3) + $5; };

park_elements: SEMICOLON {}
             | park_attributes { $$ = $1; }
             ;
             
park_attributes: park_attributes park_attribute { $$ += $2; }
               | error_production { $$ = ""; }
               ;
               
park_attribute: p1 { $$ = $1; }
              | p2 { $$ = $1; }
              | p3 { $$ = $1; }              
              ;
               
p1: Set Admission NUMBER SEMICOLON { $$ = ":Admission:" + $3;};
p2: Set Capacity NUMBER SEMICOLON  { $$ = ":Capacity:" + $3;};
p3: Set Cost NUMBER SEMICOLON      { $$ = ":Cost:" + $3;};

land_definitions: land_definitions land_definition { $$ += $2; }
                | error_production { $$ = ""; }
		    ;
land_definition: Land land_name { addToHashtable($2, "Land"); globalObjects.add("Land " + $2); } 
		         land_attributes 
		         land_elements 
		         {
			        $$ = "\n" + $2 + " = " + "new Land();\n" + generateSetAttribute($2, $4) + $5; 					   					 };
			     
land_attributes: Set Location NUMBER SEMICOLON { $$ = ":Location:" + $3; };
land_elements: land_elements land_element { $$ += $2; }
             | error_production { $$ = ""; }
		     ;
		     
land_element: attraction_definition { $$ = $1; }
            | restaurant_definition { $$ = $1; }
            | store_definition 	    { $$ = $1; }
            ;

attraction_definition: Attraction attraction_name { addToHashtable($2, "Attraction"); }
			         In land_name
			         attraction_attributes { $$ = createAttractionDefinition($5, $2) + generateSetAttribute($2, $6); };
attraction_attributes: SEMICOLON { $$ = ""; }
			         | attraction_attributes attraction_attribute { $$ += $2; }
                     | error_production { $$ = ""; }
                     ;
attraction_attribute: a1 { $$ = $1; }
                    | a2 { $$ = $1; }
                    | a3 { $$ = $1; }
                    | a4 { $$ = $1; }
                    | a5 { $$ = $1; }
                    ;                     
a1: Set Cost NUMBER SEMICOLON 	     { $$ = ":Cost:" + $3;};
a2: Set Capacity NUMBER SEMICOLON 	 { $$ = ":Capacity:" + $3;};
a3: Set Employees NUMBER SEMICOLON 	 { $$ = ":Employees:" + $3;};
a4: Set ThrillLevel NUMBER SEMICOLON { $$ = ":ThrillLevel:" + $3;};
a5: Set EnergyLost NUMBER SEMICOLON  { $$ = ":EnergyLost:" + $3;};

restaurant_definition: Restaurant restaurant_name { addToHashtable($2, "Restaurant"); }
			     In land_name
			     restaurant_attributes { $$ = createRestaurantDefinition($5, $2) + generateSetAttribute($2, $6); }
			     ;
restaurant_attributes: SEMICOLON { $$ = ""; }
			   | restaurant_attributes restaurant_attribute { $$ += $2; }
                     | error_production { $$ = ""; }
			   ;
restaurant_attribute: r1 { $$ = $1; }
                    | r2 { $$ = $1; }
                    | r3 { $$ = $1; }
                    | r4 { $$ = $1; }
                    | r5 { $$ = $1; }
                    ;
r1: Set Cost NUMBER SEMICOLON 		    { $$ = ":Cost:" + $3;};
r2: Set Capacity NUMBER SEMICOLON;		{ $$ = ":Capacity:" + $3;};
r3: Set Employees NUMBER SEMICOLON;		{ $$ = ":Employees:" + $3;};
r4: Set SpendLevel NUMBER SEMICOLON;	{ $$ = ":SpendLevel:" + $3;};
r5: Set EnergyIncrease NUMBER SEMICOLON;{ $$ = ":EnergyIncrease:" + $3;};

store_definition: Store store_name { addToHashtable($2, "Store"); }
			In land_name
			store_attributes { $$ = createStoreDefinition($5, $2) + generateSetAttribute($2, $6); }
			;
store_attributes: SEMICOLON { $$ = ""; }
		    | store_attributes store_attribute { $$ += $2; }
                | error_production { $$ = ""; }
		    ;
store_attribute: s1 { $$ = $1; }
               | s2 { $$ = $1; }
               | s3 { $$ = $1; }
               | s4 { $$ = $1; }
               ;
               
s1: Set Cost NUMBER SEMICOLON		 { $$ = ":Cost:" + $3; };
s2: Set Capacity NUMBER SEMICOLON;	 { $$ = ":Capacity:" + $3;};
s3: Set Employees NUMBER SEMICOLON;	 { $$ = ":Employees:" + $3;};
s4: Set SpendLevel NUMBER SEMICOLON; { $$ = ":SpendLevel:" + $3;}; 

start: Start { scopeName = "Start"; } COLON block { $$ = $4; };

functions: functions function { $$ = $1 + $2; }
	     | error_production { $$ = ""; }
	     ;

function: return_type function_name { scopeName = $2; addToHashtable($2, "Function"); } COLON actual_parameters block 
	  {
		$$ = "\n" + "public static " + generateFunction($1, $2, $5, $6);
		actualParams = 0;
	  }
        ;
return_type: Number { $$ = "double"; }
           | String { $$ = "String"; }
           | error_production {$$ = "void"; }
           ;
actual_parameters: actual_parameters COMMA data_type variable_name 
{ addToHashtable($4, $3); $$ = $1 + ", " + $3 + " " + $4; ++actualParams; }
		 | data_type variable_name { addToHashtable($2, $1); $$ = $1 + " " + $2; ++actualParams; }
         | error_production { $$ = ""; }
		 ;

block: start_block statements end_block { $$ = "{" + $2 + "\n}"; }
     ;
start_block: OPEN_PARAN;
end_block: CLOSE_PARAN;

statements: statements statement { $$ = $$ + "\n" +  $2; }
          | error_production { $$ = ""; }
	    ;
statement: add_attribute 	   { $$ = $1; }
	     | assignment 	       { $$ = $1; }
	     | condition 	 	   { $$ = $1; }
         | declaration 	       { $$ = $1; }
	     | function_call 	   { $$ = $1; }
         | initialization 	   { $$ = $1; }
	     | initialize_duration { $$ = $1; }
	     | loop 		       { $$ = $1; }
	     | return 		       { $$ = $1; }
	     | thrill_functions    { $$ = $1; }
         ;

add_attribute: Set Capacity value In variable_name SEMICOLON		    { $$ = generateAttribute($5, "Capacity", $3); }
		     | Set Cost value In variable_name SEMICOLON			    { $$ = generateAttribute($5, "Cost", $3); }
		     | Set Employees value In variable_name SEMICOLON		    { $$ = generateAttribute($5, "Employees", $3); }
		     | Set EnergyIncrease value In variable_name SEMICOLON	    { $$ = generateAttribute($5, "EnergyIncrease", $3); }
	         | Set EnergyLevel value In variable_name SEMICOLON		    { $$ = generateAttribute($5, "EnergyLevel", $3); }
             | Set EnergyLost value In variable_name SEMICOLON		    { $$ = generateAttribute($5, "EnergyLost", $3); }
		     | Set Size value In variable_name SEMICOLON			    { $$ = generateAttribute($5, "Size", $3); }
             | Set SpendingCapacity value In variable_name SEMICOLON	{ $$ = generateAttribute($5, "SpendingCapacity", $3); }
		     | Set SpendLevel value In variable_name SEMICOLON		    { $$ = generateAttribute($5, "SpendLevel", $3); }
             | Set ThrillLevel value In variable_name SEMICOLON	        { $$ = generateAttribute($5, "ThrillLevel", $3); }
    		 ;

assignment: left_side EQUAL right_side { $$ = $1 + " = " + $3;};

left_side: variable_name 
{ 
    boolean exists = checkHashtable($1); 
    if(exists) { $$ = $1; } 
    else{ ThrillException.ObjectNotFoundException("Error on line(" + yyline + ") and column(" + yycolumn + "): ", $1); } 
};

right_side: variable_name SEMICOLON 
{ 
    boolean exists = checkHashtable($1); 
    if(exists) { $$ = $1 + ";"; } 
    else{ ThrillException.ObjectNotFoundException("Error on line(" + yyline + ") and column(" + yycolumn + "): ", $1); } 
}
         | arithmetic_expression SEMICOLON { $$ = $1 + ";"; }
 	     | function_call { $$ = $1; }
	     | calculate_revenue { $$ = $1; }
	     ;
	     
arithmetic_expression: arithmetic_expression PLUS arithmetic_expression  { $$ = $1 + "+" + $3; }
			         | arithmetic_expression MINUS arithmetic_expression { $$ = $1 + "-" + $3; }
			         | arithmetic_expression MUL arithmetic_expression   { $$ = $1 + "*" + $3; }
			         | arithmetic_expression DIV arithmetic_expression   { checkDivideByZero($1, $3); $$ = $1 + "/" + $3; }
                     | OPEN arithmetic_expression CLOSE 			     { $$ = "(" + $2 + ")"; }
                     | variable_name 	
                     { 
                        boolean exists = checkHashtable($1); 
                        if(exists){ $$ = checkSemanticValue($1); } 
                        else{ ThrillException.ObjectNotFoundException("Error on line(" + yyline + ") and column(" + yycolumn + "): ", $1); }
                     }
                     | constant { $$ = $1; }
                     ;

condition: If OPEN relational_expression CLOSE block 		    { $$ = "if(" + $3 + ")" + $5; }
         | If OPEN relational_expression CLOSE block Else block { $$ = "if(" + $3 + ")" + $5 + "\nelse" + $7; }
	     ;
relational_expression: variable_name LESSEQUAL constant_or_variable  { $$ = generateRelationalExpression($1, " <= ", $3); }
			         | variable_name GREATEQUAL constant_or_variable { $$ = generateRelationalExpression($1, " >= ", $3); }
			         | variable_name NOTEQUAL constant_or_variable   { $$ = generateRelationalExpression($1, " != ", $3); }
			         | variable_name LESS constant_or_variable 	     { $$ = generateRelationalExpression($1, " < ", $3);  }
			         | variable_name GREAT constant_or_variable 	 { $$ = generateRelationalExpression($1, " > ", $3);  }
                     | variable_name ISEQUAL constant_or_variable 	 { $$ = generateRelationalExpression($1, " == ", $3); }
		             ;

declaration: primitive_type declaration_list SEMICOLON { addDeclVariables($1, $2); $$ = $1 + " " + $2 + ";"; };
declaration_list: declaration_list COMMA variable_name { $$ = $1 + ", " + $3; }
                | variable_name { $$ = $1; }
		    ;

function_call: function_name COLON formal_parameters SEMICOLON { 
                                                                 $$ = $1 + "(" + $3 + ");" ;
                                                                 addFunctionToHashtable($1, $3);
                                                               }
		                                                       ;

formal_parameters: formal_parameters COMMA variable_name { $$ = $$ + "," + $3; ++formalParams; }
                 | variable_name { $$ = $1; ++formalParams; }
		         | error_production { $$ = ""; }
		         ;

initialization: primitive_type initialization_list SEMICOLON 
                { 
                    addInitVariables($1, $2); $$ = $1 + " " + $2 + ";"; 
                }
		        ;

initialization_list: initialization_list COMMA variable_name EQUAL constant 
                     { $$ = $1 + ", " + $3 + " = " + $5; }
		           | variable_name EQUAL constant 
			         { 
			            $$ = $1 + " = " + $3; 
			         }
		           ;

initialize_duration: duration_type variable_name EQUAL NUMBER SEMICOLON { addToHashtable($2, $1); $$ = initializeDuration($1, $2, new Double($4).toString() ); }

loop: Iterate block Until OPEN relational_expression CLOSE SEMICOLON 
      {$$ = "do" + $2 + "while (" + $5 + ");" ; }
    ;

return: Return constant_or_variable SEMICOLON { $$ = "return " + $2 + ";"; }
	| Return SEMICOLON { $$ = "return ;"; }
	;

thrill_functions: calculate_revenue { $$ = $1; }
                  | output { $$ = $1; } 
                  | simulate { $$ = $1; }
			;

calculate_revenue: CalculateRevenue COLON crowd_name COMMA duration_name SEMICOLON
			 {$$ = generateRevenue($3, $5) ; }
		     	;

output: Print constant_variable_chain SEMICOLON { $$ = "System.out.println(" + $2 + ");" ; };

simulate: Simulate COLON crowd_name SEMICOLON {$$ = generateSimulate($3); };

constant_variable_chain: constant_variable_chain COMMA constant_or_variable { $$ = $1 + "+" + $3;}
                       | constant_or_variable { $$ = $1; }
			           ;

constant_or_variable: constant { $$ = $1; }
                    | variable_name { boolean exists = checkHashtable($1); if(exists){ $$ = $1; } else{ ThrillException.ObjectNotFoundException("Error on line(" + yyline + ") and column(" + yycolumn + "): ", $1); } }
			  ;

data_type: Crowd { $$ = "Crowd"; }
         | primitive_type { $$ = $1; }
	   ;

primitive_type: Number 		{ $$ = "double"; }
              | String 		{ $$ = "String"; }
		  ;

duration_type: Days   { $$ = "Days"; }
             | Weeks  { $$ = "Weeks"; }
             | Months { $$ = "Months"; }
             | Years  { $$ = "Years"; }
		 ;

constant: NUMBER { $$ = new Double($1).toString(); }
        | STRING_CONST { $$ = $1; }
	    ;

value: NUMBER 	   { $$ = new Double($1).toString(); }
     | variable_name 
     { 
        boolean exists = checkHashtable($1); if(exists){ $$ = $1; } 
        else{ ThrillException.ObjectNotFoundException("Error on line(" + yyline + ") and column(" + yycolumn + "): ", $1); } 
     }
     ;

attraction_name: variable_name { $$ = $1; } ;
crowd_name: variable_name 	   { $$ = $1; } ;
duration_name: variable_name   { $$ = $1; } ;
function_name: variable_name   { $$ = $1; } ;
land_name: variable_name 	   { $$ = $1; } ;
park_name: variable_name 	   { $$ = $1; } ;
restaurant_name: variable_name { $$ = $1; } ;
store_name: variable_name 	   { $$ = $1; } ;
variable_name: ID 		       { $$ = $1; } ;

error_production: empty { $$ = $1; }
                ;
                
empty: ; { $$ = ""; }                

%%
	private Yylex lexer;
	public int yyline = 1;
	public int yycolumn = 0;
	private ArrayList<String> globalObjects = new ArrayList<String>();
	private Hashtable<String, String> thrillObjects = new Hashtable<String, String>();
	private ArrayList<ThrillUserFunction> userFunctions = new ArrayList<ThrillUserFunction>();
	private Hashtable<String, String[]> definedFunctions = new Hashtable<String, String[]>();
	private ArrayList<ThrillException> errorList = new ArrayList<ThrillException>();
	int noOfParks = 0, noOfLands = 0;
	boolean[] locationValues = new boolean[6];
	String parkName = null;
	String scopeName = null;
	private int actualParams = 0;
	private int formalParams = 0;
	final int MAX_LIMIT_PARK = 1;
	final int MAX_LIMIT_LANDS = 6;
	boolean createPositionFile = false;

	private int yylex () {
		int yyl_return = -1;
		try {
			yylval = new ParserVal(0);
			yyl_return = lexer.yylex();
		}
		catch (IOException e) {
			System.err.println("IO error: " + e.getMessage());
		}
		return yyl_return;
	}

	public String getErrorLocationInfo(boolean onlyLineInfo){
		if(onlyLineInfo)
			return "Error on line(" + yyline + "): ";
		else
			return "Error on line(" + yyline + ") and column(" + yycolumn + "): ";		
	}

	public void yyerror(String error) {
		try{			
			if(stateptr > 0) {
				System.out.print("Syntax " + getErrorLocationInfo(true));
				System.out.println(": Illegal token '" + lexer.yytext() + "'");
			}
		}
		catch(Exception ex){			
		}
	}

	public Parser(Reader r, boolean createFile) {
		lexer = new Yylex(r, this);
		this.createPositionFile = createFile;
	}

	static boolean interactive;

	public String createAttractionDefinition(String landName, String attractionName) throws ThrillException{ 
		String result = "\n" + attractionName + " = new Attraction();\n";
		String key = "Global." + landName;        

		if(!thrillObjects.containsKey(key)){
			errorList.add(ThrillException.ObjectNotFoundException(getErrorLocationInfo(false), landName));
		}
		
		globalObjects.add("Attraction " + attractionName);

		String setName = attractionName + ".setAttractionName(\"" + attractionName + "\");\n";
		String setLand = attractionName + ".setLand(" + landName + ");\n";
		String addAttraction = landName + ".addAttraction(" + attractionName + ");\n";
		result += setName + setLand + addAttraction;

		return result;
	}

	public String createRestaurantDefinition(String landName, String restaurantName) throws ThrillException { 
		String result = "\n" + restaurantName + " = new Restaurant();\n";
		String key = "Global." + landName;

		if(!thrillObjects.containsKey(key)){
			errorList.add(ThrillException.ObjectNotFoundException(getErrorLocationInfo(false), landName));
		}
		
		globalObjects.add("Restaurant " + restaurantName);

		String setName = restaurantName + ".setRestaurantName(\"" + restaurantName + "\");\n";
		String setLand = restaurantName + ".setLand(" + landName + ");\n";
		String addRestaurant = landName + ".addRestaurant(" + restaurantName + ");\n";
		result += setName + setLand + addRestaurant;

		return result;
	}

	public String createStoreDefinition(String landName, String storeName) throws ThrillException{ 
		String result = "\n" + storeName + " = new Store();\n";
		String key = "Global." + landName;

		if(!thrillObjects.containsKey(key)){
			errorList.add(ThrillException.ObjectNotFoundException(getErrorLocationInfo(false), landName));
		}

		globalObjects.add("Store " + storeName);
		
		String setName = storeName + ".setStoreName(\"" + storeName + "\");\n";
		String setLand = storeName + ".setLand(" + landName + ");\n";
		String addStore = landName + ".addStore(" + storeName + ");\n";
		result += setName + setLand + addStore;

		return result;
	}

	public void addFunctionToHashtable(String functionName, String parameters) throws ThrillException{
		String[] formalParameters = null;

		if(parameters != null) {
			formalParameters = parameters.split(",");
		}

		ThrillUserFunction userFunction = new ThrillUserFunction(functionName);
		userFunction.setLine(yyline);
		userFunction.setFormalParameters(formalParameters);
		userFunction.setParameters(formalParams);
		userFunction.setScopeName(scopeName);

		// adding the function and the parameters to the list of user functions		
		userFunctions.add(userFunction);				
		formalParams = 0;
	}

	public void addToHashtable(String identifier, String type) throws ThrillException{
		String key = identifier;
		if(scopeName != null){
			key = scopeName + "." + identifier;
		}

		if(thrillObjects.containsKey(key)){
			errorList.add(ThrillException.RedefinitionException(getErrorLocationInfo(false), identifier));
		}

		if(type == "Park"){
			if(noOfParks > 1){
				errorList.add(ThrillException.ExceededObjectLimitException(getErrorLocationInfo(false), "Park", MAX_LIMIT_PARK));
			}
			else{
				parkName = identifier;
			}

		}
		else if(type == "Land"){
			if(noOfLands > 6)
				errorList.add(ThrillException.ExceededObjectLimitException(getErrorLocationInfo(false), "Land", MAX_LIMIT_LANDS));
		}
		else if(type == "double"){
			type = "Number";
		}

		thrillObjects.put(key, type);
	}

	public boolean checkHashtable(String identifier) {
		boolean result = false;
		String key = scopeName + "." + identifier;

		if(thrillObjects.containsKey(key)){
			result = true;
		}
		return result;
	}

	public void addDeclVariables(String type, String allVariables) throws ThrillException{
		String[] variables = allVariables.split(",");

		for(int i = 0; i < variables.length; ++i){
			addToHashtable(variables[i].trim(), type);
		}
	}

	// we have a small problem here. An initialization might be of the form
	// Number a = 10; 
	// OR
	// Number a = 10, b = 10;
	// So we need to check for both equal to and comma before splitting
	// Not a good way, but there is no choice
	public void addInitVariables(String type, String allVariables) throws ThrillException{
		String[] variables = null;

		if(allVariables.contains(",")){
			String[] temp = allVariables.split(",");
			for(int i = 0; i < temp.length; ++i){
				variables = temp[i].split("=");
				addToHashtable(variables[0].trim(), type);
			}
		}
		else{
			variables = allVariables.split("=");
			addToHashtable(variables[0].trim(), type);
		}
	}

	public String generateSetAttribute(String identifier, String allAttributes) throws ThrillException{
		String result = "";
		String key = scopeName + "." + identifier;
		String obj = thrillObjects.get(key);
		if(obj == null){
			ThrillException.ObjectNotFoundException(getErrorLocationInfo(false), identifier);
		}

		if(obj.equalsIgnoreCase("Attraction")){
			result += generateAttractionAttribute(identifier, allAttributes);
		}		
		else if(obj.equalsIgnoreCase("Crowd")){
			result += generateCrowdAttribute(identifier, allAttributes);
		}
		else if(obj.equalsIgnoreCase("Land")){
			result += generateLandAttribute(identifier, allAttributes);
		}
		else if(obj.equalsIgnoreCase("Park")){
			result += generateParkAttribute(identifier, allAttributes);
		}	
		else if(obj.equalsIgnoreCase("Restaurant")){
			result += generateRestaurantAttribute(identifier, allAttributes);
		}
		else if(obj.equalsIgnoreCase("Store")){
			result += generateStoreAttribute(identifier, allAttributes);
		}

		return result;
	}

	public String generateAttractionAttribute(String a, String allAttributes) throws ThrillException{
		String result = "";
		String regex = ":";		
		String[] attributes = allAttributes.split(regex);

		for(int i = 1; i < attributes.length; i+=2){
			String value = validateAttributeValue(attributes[i], attributes[i+1]);
			result += a + ".set" + attributes[i] + "(" + value + ");\n";
		}

		return result;
	}

	public String generateCrowdAttribute(String c, String allAttributes) throws ThrillException{		
		String regex = ":";
		String result = c + ".setCrowdName(\"" + c + "\");\n";
		String[] attributes = allAttributes.split(regex);

		for(int i = 1; i < attributes.length; i+=2){
			String value = validateAttributeValue(attributes[i], attributes[i+1]);
			result += c + ".set" + attributes[i] + "(" + value + ");\n";
		}

		return result;
	}

	public String generateLandAttribute(String l, String allAttributes)throws ThrillException{
		String regex = ":";
		String[] attributes = allAttributes.split(regex);
		String result = l + ".setLandName(\"" + l + "\");\n";
		String setPark = l + ".setPark(" + parkName + ");\n";
		String addLand = parkName + ".addLand(" + l + ");\n";
		result += setPark + addLand;

		for(int i = 1; i < attributes.length; i+=2){
			String value = validateAttributeValue(attributes[i], attributes[i+1]);
			int location = Integer.parseInt(value);
			if(locationValues[location - 1]) {
				errorList.add(ThrillException.AlreadyDefinedLocationException(getErrorLocationInfo(true), location));
			}
			else{
				locationValues[location - 1] = true; 
			}
			result += l + ".set" + attributes[i] + "(" + value + ");\n";
		}			
		return result;
	}

	public String generateParkAttribute(String p, String allAttributes) throws ThrillException{
		String regex = ":";
		String[] attributes = allAttributes.split(regex);
		String result = p + ".setParkName(\"" + p + "\");\n"; 

		for(int i = 1; i < attributes.length; i+=2){
			String value = validateAttributeValue(attributes[i], attributes[i+1]);
			result += p + ".set" + attributes[i] + "(" + value + ");\n";
		}

		if(createPositionFile){
			String createFileString = p + ".setCreateFile(true);\n";
			result += createFileString;
		}

		return result;
	}

	public String generateRestaurantAttribute(String r, String allAttributes) throws ThrillException{
		String regex = ":";
		String[] attributes = allAttributes.split(regex);
		String result = "";

		for(int i = 1; i < attributes.length; i+=2){
			String value = validateAttributeValue(attributes[i], attributes[i+1]);
			result += r + ".set" + attributes[i] + "(" + value + ");\n";
		}		

		return result;
	}

	public String generateStoreAttribute(String s, String allAttributes) throws ThrillException{
		String regex = ":";
		String[] attributes = allAttributes.split(regex);
		String result = "";

		for(int i = 1; i < attributes.length; i+=2){
			String value = validateAttributeValue(attributes[i], attributes[i+1]);
			result += s + ".set" + attributes[i] + "(" + value + ");\n";
		}		

		return result;
	}

	public Hashtable<String, String> getThrillObjects() {
		return thrillObjects;
	}

	public String generateAttribute(String variable, String function, String value) throws ThrillException{
		String result = "";;
		String key = "Global." + variable;
		value = validateAttributeValue(function, value);

		String obj = thrillObjects.get(key);

		if(obj == null){
			errorList.add(ThrillException.ObjectNotFoundException(getErrorLocationInfo(false), variable));
		}
		else{
			if(function.equalsIgnoreCase("Capacity")){
				if(obj.equalsIgnoreCase("Crowd")){
					errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), variable, "Crowd"));
				}
				result = variable.concat(".set" + function + "((int)" + value + ");");
			}
			else if(function.equalsIgnoreCase("Cost")){
				if(obj.equalsIgnoreCase("Crowd")){
					errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), variable, "Crowd"));
				}
				result = variable.concat(".set" + function + "(" + value + ");");
			}
			else if(function.equalsIgnoreCase("Employees")){
				if(obj.equalsIgnoreCase("Crowd")){
					errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), variable, "Crowd"));
				}
				result = variable.concat(".set" + function + "((int)" + value + ");");
			}
			else if(function.equalsIgnoreCase("EnergyIncrease")){
				if(!(obj.equalsIgnoreCase("Restaurant"))){
					errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), "Restaurant", variable));
				}
				result = variable.concat(".set" + function + "(" + value + ");");

			}
			else if(function.equalsIgnoreCase("EnergyLevel")){
				if(!(obj.equalsIgnoreCase("Crowd"))){
					errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), "Crowd", variable));
				}
				result = variable.concat(".set" + function + "((int)" + value + ");");				
			}
			else if(function.equalsIgnoreCase("EnergyLost")){
				if(!(obj.equalsIgnoreCase("Attraction"))){
					errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), "Attraction", variable));
				}
				result = variable.concat(".set" + function + "((int)" + value + ");");
			}
			else if(function.equalsIgnoreCase("Size")){
				if(!(obj.equalsIgnoreCase("Crowd"))){
					errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), "Crowd", variable));
				}
				result = variable.concat(".set" + function + "((int)" + value + ");");				
			}
			else if(function.equalsIgnoreCase("SpendingCapacity")){
				if(!(obj.equalsIgnoreCase("Crowd"))){
					errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), "Crowd", variable));
				}
				result = variable.concat(".set" + function + "((int)" + value + ");");				
			}
			else if(function.equalsIgnoreCase("SpendLevel")){
				if(!(obj.equalsIgnoreCase("Restaurant") || obj.equalsIgnoreCase("Store"))){
					errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), "Restaurant/Store", variable));
				}
				result = variable.concat(".set" + function + "((int)" + value + ");");				
			}
			else if(function.equalsIgnoreCase("ThrillLevel")){
				if(!(obj.equalsIgnoreCase("Attraction") || obj.equalsIgnoreCase("Crowd"))){
					errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), "Attraction/Crowd", variable));
				}
				result = variable.concat(".set" + function + "((int)" + value + ");");				
			}
			else{
				// error condition
				errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), "Attraction/Crowd/Restaurant/Store", variable));
			}
		}

		return result;
	}

	public String generateRelationalExpression(String value1, String relOp, String value2) throws ThrillException{    
		String result = "";
		boolean val1 = checkHashtable(value1);
		boolean val2 = checkHashtable(value2);
		if(val1 == true && val2 == true) 
		{ 
			if(checkSemanticTypes(value1, value2)){
				result = value1 + relOp + value2;
			}
			else{
				String type1 = thrillObjects.get(scopeName + "." + value1);
				String type2 = thrillObjects.get(scopeName + "." + value2);
				errorList.add(ThrillException.TypesMismatchException(getErrorLocationInfo(false), type1, type2));
			}
		}
		else 
		{ 
			if(val1 == false)
				errorList.add(ThrillException.ObjectNotFoundException(getErrorLocationInfo(false), value1));      
			if(val2 == false)
				errorList.add(ThrillException.ObjectNotFoundException(getErrorLocationInfo(false), value2));	
		}

		return result;
	}

	public String checkSemanticValue(String value) throws ThrillException{
		String key = scopeName + "." + value;
		String type = thrillObjects.get(key);
		if(type == null){
			errorList.add(ThrillException.ObjectNotFoundException(getErrorLocationInfo(false), value));
			return null;
		}
		else if(!type.equalsIgnoreCase("Number")){			
			errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), "Number", type));
		}
		return value;
	}

	public boolean checkSemanticTypes(String value1, String value2){
		String key1 = scopeName + "." + value1;
		String key2 = scopeName + "." + value2;

		if(thrillObjects.get(key1).equalsIgnoreCase(thrillObjects.get(key2))){
			return true;
		}
		else{
			return false;
		}
	}

	public boolean checkDivideByZero(String value1, String value2){
		return true;
	}

	public String generateFunction(String returnType, String functionName, String parameters, String block) throws ThrillException{
		String result = null;
		boolean checkReturn = false;
		String returnStmt = null;
		int beginIndex = 0;
		int endIndex = 0;

		if(block.contains("return")){
			beginIndex = block.indexOf("return");
			endIndex = block.indexOf(";", beginIndex);
			returnStmt = block.substring(beginIndex, endIndex + 1);	
			checkReturn = true;
		}

		if(checkReturn && !checkReturnType(returnType, returnStmt) || 
				!returnType.equalsIgnoreCase("void") && returnStmt == null){
			errorList.add(ThrillException.MissingReturnStatementException(getErrorLocationInfo(false), "Invalid/Missing return statement"));
		}

		if(!definedFunctions.containsKey(functionName)){
			definedFunctions.put(functionName, parameters.trim().split(","));
		}

		result = returnType + " " + functionName + "(" + parameters + ")\n" + block;

		return result;
	}

	boolean validParametersType(String functionName, String[] formalParameters, String[] actualParameters, int line) throws ThrillException{
		String[] paramTypes = null;

		if(formalParameters.length != actualParameters.length){
			errorList.add(ThrillException.InsufficientParamsException("Error on line(" + line +"):", functionName));
		}
		else{
			paramTypes = new String[formalParameters.length];
			for(int i = 0; i < formalParameters.length; ++i){
				String type = actualParameters[i].trim().split(" ")[0];
				paramTypes[i] = (type.equalsIgnoreCase("double")) ? "Number" : "String"; 

				String identifier = "Start.".concat(formalParameters[i]);
				if(thrillObjects.containsKey(identifier)){
					type = thrillObjects.get(identifier);
					if(!type.equalsIgnoreCase(paramTypes[i])){
						return false;
					}
				}
			}
		}

		return true;
	}

	boolean checkReturnType(String returnType, String returnStmt) throws ThrillException{
		boolean result = false;
		String temp = returnType.equalsIgnoreCase("double") ? "Number" : returnType; 
		String retVal = returnStmt.substring(7, returnStmt.length() - 1);		

		if(!returnType.equalsIgnoreCase("void")){
			String key = scopeName + "." + retVal;
			String type = thrillObjects.get(key);
			if(retVal.length() > 0){

				if(type == null){
					errorList.add(ThrillException.ObjectNotFoundException(getErrorLocationInfo(false), retVal));
				}

				if(Character.isDigit(retVal.charAt(0)) && type.equalsIgnoreCase("Number")){
					result = true;
				}
				else if(type.equalsIgnoreCase(temp)){
					result = true;
				}
				else{
					errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), returnType, type));
				}				
			}
			else{
				errorList.add(ThrillException.UnexpectedTypeException(getErrorLocationInfo(false), returnType, "void"));
			}
		}
		else{			
			if(retVal.length() == 0)
				return true;
			else
				return false;
		}

		return result;
	}

	public String[] generateParamTypes(String[] params) {
		String[] types = new String[params.length];
		for(int i = 0; i < params.length; ++i){
			types[i] = thrillObjects.get("Start.".concat(params[i]));
		}
		return types;
	}

	public String validateAttributeValue(String attribute, String value) throws ThrillException{
		String result = "";;
		double d = 0;

		if(Character.isDigit(value.charAt(0))) {
			d = Double.parseDouble(value);

			if(attribute.equalsIgnoreCase("Admission") ||
					attribute.equalsIgnoreCase("Cost")){	
				if(d < 0)
					errorList.add(ThrillException.InvalidArgumentException(getErrorLocationInfo(false), attribute + " cannot be less than zero"));
				result = value;
			}
			else{
				int i = (int)d;
				if(attribute.equalsIgnoreCase("Location")){
					if(i < 1 || i > 6)
						errorList.add(ThrillException.InvalidArgumentException(getErrorLocationInfo(false), attribute + " should be a value between 1 and 6"));			
				}
				else if(attribute.equalsIgnoreCase("Capacity")  || 
						attribute.equalsIgnoreCase("Employees") ||
						attribute.equalsIgnoreCase("Size")){
					if(i < 0)
						errorList.add(ThrillException.InvalidArgumentException(getErrorLocationInfo(false), attribute + " cannot be less than zero"));

				}
				else {
					if(i < 0 || i > 20)
						errorList.add(ThrillException.InvalidArgumentException(getErrorLocationInfo(false), attribute + " cannot be less than zero or greater than 20"));
				}
				result = new Integer(i).toString();
			}
		}
		else{
			result = value;
		}
		return result;
	}

	// have to check the second argument as well
	public String generateRevenue(String crowdName, String duration) throws ThrillException {		
		String result = null;
		String c = thrillObjects.get("Global." + crowdName);
		if(c == null){
			errorList.add(ThrillException.ObjectNotFoundException(getErrorLocationInfo(false), crowdName));
		}		

		String d = thrillObjects.get(scopeName + "." + duration);
		if(d == null){
			errorList.add(ThrillException.ObjectNotFoundException(getErrorLocationInfo(false), duration));	
		}

		result = parkName + ".calculateRevenue(" + crowdName + ", " + duration + ");";
		return result;
	}

	public String generateSimulate(String crowdName) throws ThrillException{
		String result = null;
		String c = thrillObjects.get("Global." + crowdName);
		if(c == null){
			errorList.add(ThrillException.ObjectNotFoundException(getErrorLocationInfo(false), crowdName));
		}		
		result = parkName + ".simulate(" + crowdName + ");";
		return result;
	}

	public void generateThrillProgram(String definitions, String usercode) throws ThrillException{
		String classStart = "public class ThrillProgram {\n\n";
		String classEnd = "\n}";
		String staticLine = "public static ";
		String globalObjectDefns = "";
		String main = "public static void main(String[] args){\n";		
		usercode = usercode.substring(1);

		validateAllUserFunctions();

		if(errorList.size() != 0){
			listParserErrors();
			throw new ThrillException("Compilation Failed");
		}
		
		for(int i = 0; i < globalObjects.size(); ++i){
			globalObjectDefns += staticLine + globalObjects.get(i) + ";\n";
		}

		globalObjectDefns += "\n";
		
		System.out.println();
		
		try{
			FileWriter writer = new FileWriter(new File("ThrillProgram.java"));
			String buffer = classStart + globalObjectDefns + main + definitions +  usercode + classEnd;
			writer.write(buffer);
			writer.close();
		}catch(IOException io){			
		}		
	}

	public void validateAllUserFunctions() throws ThrillException{

		// Need to check all the function definitions before generating the intermediate code.
		Iterator<ThrillUserFunction> userFunctionsList = userFunctions.iterator();

		while(userFunctionsList.hasNext()){
			ThrillUserFunction userFunction = userFunctionsList.next();

			// Get all the relevant info
			String[] formalParameters = userFunction.getFormalParameters();
			String functionName = userFunction.getFunctionName();

			if(definedFunctions.containsKey(functionName)){
				// We need to compare the actual and formal parameters
				// We need to compare the parameters of these functions				
				String[] actualParameters = definedFunctions.get(functionName);
				actualParameters = (actualParameters[0].equalsIgnoreCase("") ? null : actualParameters);

				if(formalParameters == null && actualParameters == null){
					// found a match
					continue;
				}
				else if(formalParameters != null && actualParameters != null) {
					boolean valid = validParametersType(functionName, formalParameters, actualParameters, userFunction.getLine());
					if(valid){
						continue;
					}
					else{
						String[] types = generateParamTypes(formalParameters);
						String lineInfo = "Error on line(" + userFunction.getLine() + "): ";
						errorList.add(ThrillException.UndefinedFunctionException(lineInfo, functionName, types));
					}
				}
				else{
					// function has not been defined.
					String[] types = generateParamTypes(formalParameters);
					String lineInfo = "Error on line(" + userFunction.getLine() + "): ";
					errorList.add(ThrillException.UndefinedFunctionException(lineInfo, functionName, types));
				}					
			}
			else{
				// function has not been defined.
				String[] types = generateParamTypes(formalParameters);						
				String lineInfo = "Error on line(" + userFunction.getLine() + "): ";
				errorList.add(ThrillException.UndefinedFunctionException(lineInfo, functionName, types));
			}
		}		
	}

	public String initializeDuration(String durationType, String durationName, String value) throws ThrillException{
		String result = null;
		if(!checkHashtable(durationName)){
			errorList.add(ThrillException.ObjectNotFoundException(getErrorLocationInfo(false), durationName));
		}

		double temp = Double.parseDouble(value);
		int days = (int)temp;
		result = durationType + " " + durationName + " = new " + durationType + "(" + days + ");"; 
		return result;
	}

	public void listParserErrors(){
		for(int i = 0; i < errorList.size(); ++i){
			System.out.println(errorList.get(i).getMessage());
		}
	}

	public boolean checkParseErrors(){
		boolean flag = false;
		if(yynerrs > 0) {
			flag = true;
		}
		return flag;
	}

	public static void main(String args[]) throws IOException {

		Parser yyparser;
		boolean createFile = false;

		if(args.length < 1){
			System.out.println("Usage: java Parser <thrill_program.txt>");
			return;
		}
		else if(args.length == 2){
			createFile = Boolean.parseBoolean(args[1]);
		}

		// parse a file
		yyparser = new Parser(new FileReader(args[0]), createFile);

		System.out.println("\nCompiling ...\n");

		try{
			yyparser.yyparse();
			if(yyparser.checkParseErrors()){
				System.out.println("\nCompilation failed!!\n");
			}
			else{
				System.out.println("\nThrillProgram.java generated successfully.\n");;
			}

		}catch(ThrillException ex){
			System.out.println(ex.getMessage() + "\n");			
		}
	}
