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
%}

/* YACC Declarations */
%token PLUS                   /* + */
%token OPEN_CURLY             /* { */ 
%token CLOSE_CURLY            /* } */
%token OPEN_PARAN             /* ( */
%token CLOSE_PARAN            /* ) */
%token <dval> FLT             /* Float */
%token <ival> INT             /* Integer */
%token <sval> STRING_CONST    /* String literal onstants */
%token COMMA                  /* Comma */
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
%token SEMICOLON              /* Semicolon */

/* Keywords */
%token defineLeague
%token defineFunctions
%token leagueName
%token maxUser
%token minUser
%token set
%token add
%token Action
%token User
%token Void             
%token str              
%token bool            
%token flt              
%token Int
%token Return
%token If               /* If keyword */
%token Else             /* Else keyword */
%token While

/* Associativity and Precedence */
%left MINUS PLUS COMMA
%left MULT DIV
%nonassoc EQUAL NOTEQUAL LESSEQUAL GREATEQUAL ISEQUAL LESS GREAT

/* Types */
%type <sval> definitions
%type <sval> definitionlist
%type <sval> definitionproductions
%type <sval> functions
%type <sval> functionProductions
%type <sval> returnType
%type <sval> dataType
%type <sval> functionName
%type <sval> argumentList
%type <sval> empty;
%type <sval> returnProduction
%type <sval> blockBody
%type <sval> statement
%type <sval> statements
%type <sval> conditionals
%type <sval> loop
%type <sval> declarations
%type <sval> relationalExp
%type <sval> arithmeticExp
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
  System.out.println("Line 41");
  generateFloodProgram($1, $2);
  System.out.println("Total number of lines in the input: " + (yyline - 1));
};

definitions: defineLeague definitionlist { $$ = $2; };
           
definitionlist: definitionlist definitionproductions { $$ = $1 + $2; }
              | empty { $$ = $1; }
              ;

definitionproductions: set leagueName OPEN_PARAN STRING_CONST CLOSE_PARAN SEMICOLON { $$ = "myLeague = new League(" + $4 + ");\n"; }
                     | set maxUser OPEN_PARAN INT CLOSE_PARAN SEMICOLON { $$ = "myLeague.setMaxUser(" + $4 + ");\n"; }
                     | set minUser OPEN_PARAN INT CLOSE_PARAN SEMICOLON { $$ = "myLeague.setMinUser(" + $4 + ");\n"; }
                     | add User OPEN_PARAN STRING_CONST CLOSE_PARAN SEMICOLON { $$ = "myLeague.addUser(new User(" + $4 + "));\n"; }
                     | add Action OPEN_PARAN STRING_CONST COMMA FLT CLOSE_PARAN SEMICOLON { $$ = "myLeague.addAction(new Action(" + $4 + ", " + $6 + "));\n"; }
                     ;

functions: defineFunctions functionProductions { $$ = $2; };

functionProductions: functionProductions returnType functionName OPEN_PARAN argumentList CLOSE_PARAN blockBody
                     {
                      /*
                      * TODO: hastable for function scoping and function list
                      * eg: scopeName = $2; addToHashtable($2, "Function");
                      */
                      $$ = $1 + $2 + " " + $3 + "(" + $5 + ")\n" + $7;
                     }
                     | empty { $$ = $1; }
                     ;

returnType: Void { $$ = "void"; }
          | str { $$ = "String"; }
          | bool { $$ = "boolean"; }
          | Int { $$ = "int"; }
          | flt { $$ = "float"; }
          ;
          
functionName: ID { $$ = $1; };

argumentList: argumentList COMMA returnType ID { $$ = $1 + "," + $3 + " " + $4; }
            | returnType ID { $$ = $1 + " " + $2; }
            | empty { $$ = $1; }
            ;

/* Also check to see if there is a return statement for the return type mentioned */
blockBody: OPEN_CURLY blockBody statements returnProduction CLOSE_CURLY { $$ = "{\n" + $2 + $3 + "\n" + $4 + "\n}\n"; }
         | OPEN_CURLY statements returnProduction CLOSE_CURLY { $$ = "{\n" + $2 + "\n" + $3 + "\n}\n"; }
         ;

statements: statements statement SEMICOLON { $$ = $1 + $2; }
          | statement SEMICOLON { $$ = $1; }
          ;

statement: conditionals { $$ = $1; }
         | loop { $$ = $1; }
         | declarations { $$ = $1; }
         | relationalExp { $$ = $1; }
         | assignment { $$ = $1; }
         | functionCall { $$ = $1; }
         ;

returnProduction: Return ID { $$ = "return " + $2 + ";"; }
                | Return STRING_CONST { $$ = "return " + $2 + ";"; }
                | Return INT { $$ = "return " + $2 + ";"; }
                | Return FLT { $$ = "return " + $2 + ";"; }
                | empty { $$ = $1; }
                ;

conditionals: If OPEN_PARAN relationalExp CLOSE_PARAN blockBody { $$ = "if(" + $3 + ")" + $5; }
            | If OPEN_PARAN relationalExp CLOSE_PARAN blockBody Else blockBody { $$ = "if(" + $3 + ")" + $5 + "\nelse" + $7; }
            | If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY  { $$ = "if(" + $3 + ")\n{\n}"; }
            | If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY Else OPEN_CURLY empty CLOSE_CURLY { $$ = "if(" + $3 + ")\n{\n}\nelse\n{\n}"; }
            | If OPEN_PARAN relationalExp CLOSE_PARAN blockBody Else OPEN_CURLY empty CLOSE_CURLY { $$ = "if(" + $3 + ")" + $5 + "\nelse\n{\n}"; }
            | If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY Else blockBody { $$ = "if(" + $3 + ")\n{\n}" + "\nelse" + $9; }
            ;

loop: While OPEN_PARAN relationalExp CLOSE_PARAN blockBody { $$ = "while(" + $3 + ")" + $5; }
    | While OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY { $$ = "while(" + $3 + ")\n{\n}"; }
    ;

declarations: dataType ID { $$ = $1 + " " + $2 + ";\n"; if (!addToVarTable($2, $1)) {System.out.println("variable already exists");}}
            | dataType ID EQUAL FLT { $$ = $1 + " " + $2 + " = " + $4 + ";\n"; }
            | dataType ID EQUAL INT { $$ = $1 + " " + $2 + " = " + $4 + ";\n"; }
            | dataType ID EQUAL STRING_CONST { $$ = $1 + " " + $2 + " = " + $4 + ";\n"; }
            ;

relationalExp: ID LESSEQUAL constOrVar { $$ = $1 + " <= " + $3; }
             | ID GREATEQUAL constOrVar { $$ = $1 + " >= " + $3; }
             | ID NOTEQUAL constOrVar { $$ = $1 + " != " + $3; }
             | ID LESS constOrVar { $$ = $1 + " < " + $3; }
             | ID GREAT constOrVar { $$ = $1 + " > " + $3; }
             | ID ISEQUAL constOrVar { $$ = $1 + " == " + $3; }
             | ID {$$ = $1; /* Check whether its a boolean value*/}
             ;

constOrVar: FLT { $$ = "" + $1; }
          | INT { $$ = "" + $1; }
          | ID { $$ = "" + $1; }
          ;

arithmeticExp: arithmeticExp PLUS arithmeticExp { $$ = $1 + " + " + $3; }
             | arithmeticExp MINUS arithmeticExp { $$ = $1 + " - " + $3; }
             | arithmeticExp MULT arithmeticExp { $$ = $1 + " * " + $3; }
             | arithmeticExp DIV arithmeticExp { $$ = $1 + " / " + $3; }
             | OPEN_PARAN arithmeticExp CLOSE_PARAN { $$ = "(" + $2 + ")"; }
             | ID
               {
                //TODO: semantic check for mismatch operands
                $$ = $1;
               }
             | FLT { $$ = "" + $1; }
             | INT { $$ = "" + $1; }
             ;

assignment: leftSide EQUAL rightSide { $$ = $1 + " = " + $3;}

leftSide: ID { $$ = $1; }

rightSide: arithmeticExp { $$ = $1 + ";"; }
         | functionCall { $$ = $1; }
         ;

functionCall: functionName OPEN_PARAN parameterList CLOSE_PARAN { $$ = $1 + "(" + $3 + ");"; }

dataType: str { $$ = "String"; }
        | bool { $$ = "boolean"; }
        | Int { $$ = "int"; }
        | flt { $$ = "float"; }
        ;

parameterList: parameterList COMMA parameterList { $$ = $1 + ", " + $3; }
             | ID { $$ = $1; }
             | INT { $$ = "" + $1; }
             | FLT { $$ = "" + $1; }
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

/***************************************************
* generateFloodProgram()
****************************************************/
public void generateFloodProgram(String definitions, String functions)
{
  System.out.println("Line 59");
  
  String classStart = "public class FloodProgram\n{\n";
  String classEnd = "\n}";

  String main_start = "public static void main(String[] args)\n{\n";
  String main_end = "\n}";

  try
  {
    System.out.println("Line 67");
    FileWriter writer = new FileWriter(new File("C:/PLT/FloodProgram.java"));
    String buffer = classStart + main_start + definitions  + main_end + functions + classEnd;
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
  System.out.println("Line 102");

  lexer = new Yylex(r, this);
  this.createPositionFile = createFile;
}

/***************************************************
* yyerror()
****************************************************/
public void yyerror(String error)
{
}

/***************************************************
* main()
****************************************************/
public static void main(String args[]) throws IOException
{
  System.out.println("Line 114");

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