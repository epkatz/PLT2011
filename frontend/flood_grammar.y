/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*
* FLOOD
* Syntactic/Semantic Analyzer
*
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%{
  import java.lang.Math;
  import java.io.*;
  import java.util.Hashtable;
  import java.util.ArrayList;
  import java.util.Iterator;
  import java.util.HashMap;
  import java.util.*;
%}

/* YACC Declarations */
%token PLUS                   /* + */
%token OPEN_CURLY             /* { */ 
%token CLOSE_CURLY            /* } */
%token OPEN_PARAN             /* ( */
%token CLOSE_PARAN            /* ) */
%token OPEN_SQUARE            /* [ */
%token CLOSE_SQUARE           /* ] */
%token <dval> FLT             /* Float */
%token <ival> INT             /* Integer */
%token <sval> STRING_CONST    /* String literal onstants */
%token COMMA                  /* , */
%token DOT                    /* . */
%token <sval> ID              /* Identifier */
%token EQUAL                  /* = */
%token NOTEQUAL               /* != */
%token LESSEQUAL              /* <= */
%token GREATEQUAL             /* >= */
%token ISEQUAL                /* == */
%token LESS                   /* < */
%token GREAT                  /* > */
%token PLUS                   /* + */
%token MINUS                  /* - */
%token MULT                   /* * */
%token DIV                    /* / */
%token NOT                    /* ! */
%token AND                    /* && */
%token OR                     /* || */
%token MOD                    /* % */
%token SEMICOLON              /* ; */

/* Keywords */
%token DefineLeague           /* Define the league */
%token DefineFunctions        /* Define functions */
%token LeagueName             /* Set league name */
%token MaxUser                /* Set the max user */
%token MinUser                /* Set the min user */
%token MaxTeamSize            /* Set the max team size */
%token MinTeamSize            /* Set the min team size */
%token Set                    /* Set keyword */
%token Add                    /* Add keyword */
%token Action                 /* Action keyword */
%token User                   /* User keyword */
%token Player                 /* Player keyword */
%token Void                   /* Void keyword */
%token Str                    /* String keyword */
%token Bool                   /* Boolean keyword */
%token Flt                    /* Float keyword */
%token Int                    /* Integer keyword */
%token Return                 /* Return keyword */
%token If                     /* If keyword */
%token Else                   /* Else keyword */
%token While                  /* While keyword */
%token True                   /* True keyword */
%token False                  /* False keyword */
%token RemovePlayer           /* Remove Player function */
%token AddPlayer              /* Add Player function */
%token ArrayLength            /* Length of an array */
%token GetUserName	      /* Gets the name of the user object*/
%token GetNumPlayers          /* Gets the number of players of the user*/
%token GetPlayerName          /* gets the name of the player object*/
%token GetPlayerPosition      /* Gets the position of the player*/
%token GetPlayerPoints        /* Gets the points of the player*/

/* Associativity and Precedence */
%left MINUS PLUS COMMA
%left MULT DIV
%right NOT NEG
%nonassoc EQUAL NOTEQUAL LESSEQUAL GREATEQUAL ISEQUAL LESS GREAT AND OR MOD DOT

/* Types */
%type <sval> definitions
%type <sval> definitionlist
%type <sval> definitionproductions
%type <sval> functions
%type <sval> functionProductions
%type <sval> returnType
%type <sval> dataType
%type <sval> functionName
%type <sval> argumentLists
%type <sval> argumentList
%type <sval> empty;
%type <sval> returnProduction
%type <sval> returnProd
%type <sval> statement
%type <sval> statements
%type <sval> conditionals
%type <sval> loop
%type <sval> declarations
%type <sval> declaration
%type <sval> relationalExp
%type <sval> arithmeticExp
%type <sval> booleanExp
%type <sval> constOrVar
%type <sval> leftSide
%type <sval> rightSide
%type <sval> assignment
%type <sval> functionCall
%type <sval> parameterList

%%

/***************************************************
* program
****************************************************/
program: definitions functions
{
	if(semantics.validProgram)
	{
		generateFloodProgram($1, $2);
		System.out.println("Total number of lines in the input: " + (yyline - 1));
	}
	else
	{
		System.out.println(semantics.printErrors());
	}
};

definitions: DefineLeague definitionlist { $$ = $2; };
           
definitionlist: definitionlist definitionproductions { $$ = $1 + $2; }
              | empty { $$ = $1; }
              ;

definitionproductions: Set LeagueName OPEN_PARAN STRING_CONST CLOSE_PARAN SEMICOLON { $$ = "myLeague = new League(" + $4 + ");\n"; }
                     | Set MaxUser OPEN_PARAN INT CLOSE_PARAN SEMICOLON { $$ = "myLeague.setMaxUser(" + $4 + ");\n"; }
                     | Set MinUser OPEN_PARAN INT CLOSE_PARAN SEMICOLON { $$ = "myLeague.setMinUser(" + $4 + ");\n"; }
                     | Set MaxTeamSize OPEN_PARAN INT CLOSE_PARAN SEMICOLON { $$ = "myLeague.setMaxTeamSize(" + $4 + ");\n"; }
                     | Set MinTeamSize OPEN_PARAN INT CLOSE_PARAN SEMICOLON { $$ = "myLeague.setMinTeamSize(" + $4 + ");\n"; }
                     | Add User OPEN_PARAN STRING_CONST CLOSE_PARAN SEMICOLON { $$ = "myLeague.addUser(new User(" + $4 + "));\n"; semantics.addUser($4, yyline); }
                     | Add Action OPEN_PARAN STRING_CONST COMMA FLT CLOSE_PARAN SEMICOLON { $$ = "myLeague.addAction(new Action(" + $4 + ", " + $6 + "));\n"; semantics.addAction($4, yyline); }
		     | Add Action OPEN_PARAN STRING_CONST COMMA MINUS FLT CLOSE_PARAN SEMICOLON %prec NEG { $$ = "myLeague.addAction(new Action(" + $4 + ", -" + $7 + "));\n"; semantics.addAction($4, yyline); }
                     | Add Player OPEN_PARAN STRING_CONST COMMA STRING_CONST CLOSE_PARAN SEMICOLON { $$ = "myLeague.addPlayer(new Player(" + $4 + ", " + $6 + "));\n"; semantics.addPlayer($4, yyline); }
                     ;

functions: DefineFunctions functionProductions { $$ = $2 + semantics.setFlags(); };

functionProductions: functionProductions returnType functionName OPEN_PARAN argumentLists CLOSE_PARAN OPEN_CURLY  declarations statements returnProduction CLOSE_CURLY { $$ = $1 +"public static "+ $2 + " " + $3 + "(" + $5 + ")\n{\n" + $8 + $9 + $10 + "\n}\n"; this.scope = $3; semantics.addToFunctionTable($3, $2, $5, yyline); semantics.checkReturnTypeMatch($2, yyline); }
                     | functionProductions returnType functionName OPEN_PARAN argumentLists CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY {$$ = $1 +"public static "+ $2 + " " + $3 + "(" + $5 + ")\n{\n}\n"; this.scope = $3; semantics.addToFunctionTable($3, $2, $5, yyline); semantics.checkReturnTypeMatch($2, yyline); }
                     | functionProductions returnType functionName OPEN_PARAN argumentLists CLOSE_PARAN OPEN_CURLY  empty statements returnProduction CLOSE_CURLY {$$ = $1 +"public static "+ $2 + " " + $3 + "(" + $5 + ")\n{\n" + $8 + $9 + $10 + "\n}\n"; this.scope = $3; semantics.addToFunctionTable($3, $2, $5, yyline); semantics.checkReturnTypeMatch($2, yyline); }
                     | functionProductions returnType functionName OPEN_PARAN argumentLists CLOSE_PARAN OPEN_CURLY  declarations empty returnProduction CLOSE_CURLY {$$ = $1 +"public static "+ $2 + " " + $3 + "(" + $5 + ")\n{\n" + $8 + $9 + $10 + "\n}\n"; this.scope = $3; semantics.addToFunctionTable($3, $2, $5, yyline); semantics.checkReturnTypeMatch($2, yyline); }
                     | functionProductions returnType functionName OPEN_PARAN argumentLists CLOSE_PARAN OPEN_CURLY returnProd CLOSE_CURLY {$$ = $1 +"public static "+ $2 + " " + $3 + "(" + $5 + ")\n{\n" + $8 + "\n}\n"; this.scope = $3; semantics.addToFunctionTable($3, $2, $5, yyline); semantics.checkReturnTypeMatch($2, yyline); }
                     | empty { $$ = $1; }
                     ;

dataType: Str { $$ = "String"; }
        | Bool { $$ = "boolean"; }
        | Int { $$ = "int"; }
        | Flt { $$ = "float"; }
        ;

returnType: Void { $$ = "void"; }
          | Str { $$ = "String"; }
          | Bool { $$ = "boolean"; }
          | Int { $$ = "int"; }
          | Flt { $$ = "float"; }
          ;
          
functionName: ID { $$ = $1; };

argumentLists: argumentLists COMMA argumentList { $$ = $1 + ", " + $3; }
              | argumentList { $$ = $1;}
              | empty { $$ = $1; }
              ;

argumentList: dataType ID { $$ = $1 + " " + $2; semantics.addVar($2,$1,yyline); }
            | User OPEN_SQUARE CLOSE_SQUARE ID { $$ = "User[] " + $4; semantics.addVar($4,"User[]",yyline); }
            | Player OPEN_SQUARE CLOSE_SQUARE ID { $$ = "Player[] " + $4; semantics.addVar($4,"Player[]",yyline); }
            | User ID { $$ = "User " + $2; semantics.addVar($2,"User",yyline); }
            | Player ID { $$ = "Player " + $2; semantics.addVar($2,"Player",yyline); }
            ;

statements: statements statement SEMICOLON { $$ = $1 + $2; }
          | statement SEMICOLON { $$ = $1; }
          ;

statement: conditionals { $$ = $1; }
         | loop { $$ = $1; }
         | relationalExp { $$ = $1; }
         | assignment { $$ = $1; }
         | functionCall { $$ = $1 + ";\n"; }
         ;

returnProduction: Return ID SEMICOLON { $$ = "return " + $2 + ";"; semantics.setReturnProdType(semantics.getType($2)); }
                | Return STRING_CONST SEMICOLON { $$ = "return " + $2 + ";"; semantics.setReturnProdType("String"); }
                | Return INT SEMICOLON { $$ = "return " + $2 + ";"; semantics.setReturnProdType("int"); }
                | Return FLT SEMICOLON { $$ = "return " + $2 + ";"; semantics.setReturnProdType("float"); }
                | Return True SEMICOLON { $$ = "return true;"; semantics.setReturnProdType("boolean"); }
                | Return False SEMICOLON { $$ = "return false;"; semantics.setReturnProdType("boolean"); }
                | empty { $$ = $1; semantics.setReturnProdType("void"); }
                ;

returnProd: Return ID SEMICOLON { $$ = "return " + $2 + ";"; semantics.setReturnProdType(semantics.getType($2)); }
                | Return STRING_CONST SEMICOLON { $$ = "return " + $2 + ";"; semantics.setReturnProdType("String"); }
                | Return INT SEMICOLON { $$ = "return " + $2 + ";"; semantics.setReturnProdType("int"); }
                | Return FLT SEMICOLON { $$ = "return " + $2 + ";"; semantics.setReturnProdType("float"); }
                | Return True SEMICOLON { $$ = "return true;"; semantics.setReturnProdType("boolean"); }
                | Return False SEMICOLON { $$ = "return false;"; semantics.setReturnProdType("boolean"); }
    ;

conditionals: If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY { $$ = "if(" + $3 + ")\n{\n" + $6 + "}\n"; }
            | If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY Else OPEN_CURLY statements CLOSE_CURLY { $$ = "if(" + $3 + ")\n{\n" + $6 + "}\nelse\n{\n" + $10 + "}\n"; }
            | If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY { $$ = "if(" + $3 + ")\n{\n}\n"; }
            | If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY Else OPEN_CURLY empty CLOSE_CURLY { $$ = "if(" + $3 + ")\n{\n}\nelse\n{\n}\n"; }
            | If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY Else OPEN_CURLY empty CLOSE_CURLY { $$ = "if(" + $3 + ")\n{\n" + $6 + "}\nelse\n{\n}\n"; }
            | If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY Else OPEN_CURLY statements CLOSE_CURLY { $$ = "if(" + $3 + ")\n{\n}" + "\nelse\n{\n" + $10 + "}\n"; }
            | If OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY { $$ = "if(" + $3 + ")\n{\n" + $6 + "}\n"; }
            | If OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY Else OPEN_CURLY statements CLOSE_CURLY { $$ = "if(" + $3 + ")\n{\n" + $6 + "}\nelse\n{\n" + $10 + "}\n"; }
            | If OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY { $$ = "if(" + $3 + ")\n{\n}\n"; }
            | If OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY Else OPEN_CURLY empty CLOSE_CURLY { $$ = "if(" + $3 + ")\n{\n}\nelse\n{\n}\n"; }
            | If OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY Else OPEN_CURLY empty CLOSE_CURLY { $$ = "if(" + $3 + ")\n{\n" + $6 + "}\nelse\n{\n}\n"; }
            | If OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY Else OPEN_CURLY statements CLOSE_CURLY { $$ = "if(" + $3 + ")\n{\n}" + "\nelse\n{\n" + $10 + "}\n"; }
            ;

loop: While OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY { $$ = "while(" + $3 + ")\n{\n" + $6 + "}\n"; }
    | While OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY { $$ = "while(" + $3 + ")\n{\n}\n"; }
    | While OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY { $$ = "while(" + $3 + ")\n{\n" + $6 + "}\n"; }
    | While OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY { $$ = "while(" + $3 + ")\n{\n}\n"; }
    ;

declarations: declarations declaration SEMICOLON{ $$ = $1 + $2; }
            | declaration SEMICOLON{ $$ = $1; }
            ;

/* NEED SEMANCTIC ACTION: Declaration needs type checking - !!!PLEASE REMOVE THIS COMMENT WHEN DONE!!! */
declaration: Flt ID  { $$ = "float " + $2 + ";\n";  semantics.addVar($2, "float", yyline); }
            | Int ID  { $$ = "int " + $2 + ";\n";  semantics.addVar($2, "int", yyline); }
            | Bool ID  { $$ = "boolean " + $2 + ";\n";  semantics.addVar($2, "boolean", yyline); }
            | Str ID  { $$ = "String " + $2 + " = new String();\n";  semantics.addVar($2, "String", yyline); }
            | Flt ID EQUAL FLT { $$ = "float " + $2 + " = " + $4 + ";\n"; semantics.addVar($2, "float", yyline); }
            | Int ID EQUAL INT { $$ = "int " + $2 + " = " + $4 + ";\n"; semantics.addVar($2, "int", yyline); }
            | Bool ID EQUAL True { $$ = "boolean " + $2 + " = true;\n"; semantics.addVar($2, "boolean", yyline); }
            | Bool ID EQUAL False { $$ = "boolean " + $2 + " = false;\n"; semantics.addVar($2, "boolean", yyline); }
            | Str ID EQUAL STRING_CONST { $$ = "String " + $2 + " = " + $4 + ";\n"; semantics.addVar($2, "String", yyline); }
            ;

relationalExp: ID LESSEQUAL constOrVar { $$ = $1 + " <= " + $3; semantics.checkRelExp($1, $3, yyline); semantics.checkRelationForInvalidType($1, yyline); }
             | ID GREATEQUAL constOrVar { $$ = $1 + " >= " + $3; semantics.checkRelExp($1, $3, yyline); semantics.checkRelationForInvalidType($1, yyline); }
             | ID NOTEQUAL constOrVar { $$ = $1 + " != " + $3; semantics.checkRelExp($1, $3, yyline); }
             | ID LESS constOrVar { $$ = $1 + " < " + $3; semantics.checkRelExp($1, $3, yyline); semantics.checkRelationForInvalidType($1, yyline); }
             | ID GREAT constOrVar { $$ = $1 + " > " + $3; semantics.checkRelExp($1, $3, yyline); semantics.checkRelationForInvalidType($1, yyline); }
             | ID ISEQUAL constOrVar { $$ = $1 + " == " + $3; semantics.checkRelExp($1, $3, yyline); }
             | OPEN_PARAN relationalExp CLOSE_PARAN { $$ = "(" + $2 + ")"; }
             //FLOODException Here
             ;

booleanExp: booleanExp AND booleanExp { $$ = $1 + " && " + $3; }
          | booleanExp OR booleanExp { $$ = $1 + " || " + $3; }
          | relationalExp AND relationalExp { $$ = $1 + " && " + $3; }
          | relationalExp OR relationalExp { $$ = $1 + " || " + $3; }
          | relationalExp AND booleanExp { $$ = $1 + " && " + $3; }
          | relationalExp OR booleanExp { $$ = $1 + " || " + $3; }
          | booleanExp AND relationalExp { $$ = $1 + " && " + $3; }
          | booleanExp OR relationalExp { $$ = $1 + " || " + $3; }
          | OPEN_PARAN booleanExp CLOSE_PARAN { $$ = "(" +  $2 + ")"; }
          | NOT booleanExp { $$ = "!" + $2; }
          | ID { $$ = $1; }
          | True { $$ = "true"; }
          | False { $$ = "false"; }
          ;

constOrVar: FLT { $$ = "" + $1; }
          | INT { $$ = "" + $1; }
          | ID { $$ = "" + $1; }
          ;

arithmeticExp: arithmeticExp PLUS arithmeticExp { $$ = $1 + " + " + $3 ; semantics.checkForBadAdditionType(yyline);}
             | arithmeticExp MINUS arithmeticExp { $$ = $1 + " - " + $3; semantics.checkForBadArithmeticType(yyline);}
             | arithmeticExp MULT arithmeticExp { $$ = $1 + " * " + $3; semantics.checkForBadArithmeticType(yyline);}
             | arithmeticExp DIV arithmeticExp { $$ = $1 + " / " + $3; semantics.checkForBadArithmeticType(yyline);}
             | arithmeticExp MOD arithmeticExp { $$ = $1 + " % " + $3; semantics.checkForBadArithmeticType(yyline);}
             | OPEN_PARAN arithmeticExp CLOSE_PARAN { $$ = "(" + $2 + ")"; }
             | ID  { $$ = $1; semantics.assignmentCheckVar($1, yyline); }
             | FLT { $$ = "" + $1; semantics.assignmentCheckLeftIsOfType("float", yyline); }
             | INT { $$ = "" + $1; semantics.assignmentCheckLeftIsOfType("int", yyline); }
             ;

assignment: leftSide EQUAL rightSide { $$ = $1 + " = " + $3; semantics.funcReturnFlag=false;}

leftSide: ID { $$ = $1; semantics.assignmentCheckLeft($1, yyline); semantics.funcReturnFlag=true;}

rightSide: arithmeticExp { $$ = $1 + ";\n"; }
         | functionCall { $$ = $1 + ";\n"; }
         | STRING_CONST { $$ = $1 + ";\n"; semantics.assignmentCheckLeftIsOfType("String", yyline); }
         | True { $$ = "true" + ";\n"; semantics.assignmentCheckLeftIsOfType("boolean", yyline); }
         | False { $$ = "false" + ";\n"; semantics.assignmentCheckLeftIsOfType("boolean", yyline); }
         ;

functionCall: functionName OPEN_PARAN parameterList CLOSE_PARAN { $$ = $1 + "(" + $3 + ")";  if(semantics.funcReturnFlag)semantics.assignmentCheckFunction($1, yyline);}
            | AddPlayer OPEN_PARAN ID COMMA ID CLOSE_PARAN { $$ = $3 + ".addPlayer(" + $5 + ")" ; semantics.checkIDagainstType($3,"User", yyline);semantics.checkIDagainstType($5,"Player", yyline);}
            | RemovePlayer OPEN_PARAN ID COMMA ID CLOSE_PARAN { $$ = $3 + ".removePlayer(" + $5 + ")" ; semantics.checkIDagainstType($3,"User", yyline);semantics.checkIDagainstType($5,"Player", yyline);}
            | ArrayLength OPEN_PARAN ID CLOSE_PARAN { $$ = $3 + ".length"; semantics.checkArrayType($3,yyline); }
	    | GetUserName OPEN_PARAN ID CLOSE_PARAN { $$ = $3 + ".getName()";} //Semantic check to be added
	    | GetNumPlayers OPEN_PARAN ID CLOSE_PARAN { $$ = $3 +".getNumPlayers()";} //Semantic check needed
	    | GetPlayerName OPEN_PARAN ID CLOSE_PARAN { $$ = $3 + ".getName()";} //Semantic check needed
	    | GetPlayerPosition OPEN_PARAN ID CLOSE_PARAN { $$= $3 + ".getPosition()";}//Semantic check needed
	    | GetPlayerPoints OPEN_PARAN ID CLOSE_PARAN { $$ =$3 + ".getPoints()";}//Semantic check needed
            ;

parameterList: parameterList COMMA parameterList { $$ = $1 + ", " + $3; }
             | ID { $$ = $1; }
             | INT { $$ = "" + $1; }
             | FLT { $$ = "" + $1; }
             | STRING_CONST { $$ = $1; }
             | ID OPEN_SQUARE INT CLOSE_SQUARE { $$ = $1 + "[" + $3 + "]"; }
             | ID OPEN_SQUARE ID CLOSE_SQUARE { $$ = $1 + "[" + $3 + "]"; semantics.checkIndex($3, yyline);}
             | empty { $$ = $1; }
             ;

empty: ; { $$ = ""; }

%%

/***************************************************
* Variables
****************************************************/
private Yylex lexer;
public int yyline = 1;
public int yycolumn = 0;
public boolean createPositionFile = false;
//Semantic Object
Flood_Sem semantics = new Flood_Sem();
String scope = "main";

/***************************************************
* generateFloodProgram()
****************************************************/
public void generateFloodProgram(String definitions, String functions)
{
  String classStart = "public class FloodProgram\n{\n";
  String staticDeclarations = "public static League myLeague;\npublic static GUI run;\n";
  String classEnd = "}\n";

  String main_start = "public static void main(String[] args)\n{\n";
  String main_preEndAutogenerate = "run = new GUI(myLeague);\nrun.drawBoard();\n";
  String main_end = "}\n";

  try
  {
    FileWriter writer = new FileWriter(new File("FloodProgram.java"));
    String buffer = classStart + staticDeclarations + main_start + definitions + main_preEndAutogenerate + main_end + functions + classEnd;
    writer.write(buffer);
    writer.close();
  }
  catch (IOException e)
  {
  }
}

/***************************************************
* yylex()
****************************************************/
private int yylex()
{  
  int yyl_return = -1;
  
  try
  {
    yylval = new ParserVal(0);
    yyl_return = lexer.yylex();
  }
  catch (IOException e)
  {
    System.err.println("IO error: " + e.getMessage());
  }
  
  return yyl_return;
}

/***************************************************
* Parser()
****************************************************/
public Parser(Reader r, boolean createFile)
{
  lexer = new Yylex(r, this);
  this.createPositionFile = createFile;
}

/***************************************************
* getErrorLocationInfo()
****************************************************/
public String getErrorLocationInfo(boolean justLine)
{
  if(justLine)
    return "Error on line(" + yyline + "): ";
  else
    return "Error on line(" + yyline + ") and column(" + yycolumn + "): ";    
}

/***************************************************
* yyerror()
****************************************************/
public void yyerror(String error)
{
    try{      
      if(stateptr > 0) {
        System.out.print("Syntax " + getErrorLocationInfo(true));
        System.out.println(": Illegal token '" + lexer.yytext() + "'");
      }
    }
    catch(Exception ex){      
    }
}

/***************************************************
* main()
****************************************************/
public static void main(String args[]) throws IOException
{

  Parser yyparser;
  boolean createFile = false;

  if (args.length < 1)
  {
    System.out.println("Usage: java Parser <flood_progam.txt>");
    return;
  }
  else if (args.length == 2)
  {
    createFile = Boolean.parseBoolean(args[1]);
  }

  // parse a file
  yyparser = new Parser(new FileReader(args[0]), createFile);

  System.out.println("\nCompiling ...\n");
  yyparser.yyparse();
}
