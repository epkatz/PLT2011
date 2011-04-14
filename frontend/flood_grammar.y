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
%}

/* YACC Declarations */
%token PLUS                   /* + */
%token OPEN_PARAN             /* { */ 
%token CLOSE_PARAN            /* } */
%token OPEN                   /* ( */
%token CLOSE                  /* ) */
%token <dval> FLT             /* Float */
%token <ival> INT             /* Integer */
%token <sval> STRING_CONST    /* String literal onstants */
%token COMMA                  /* Comma */
%token <sval> ID              /* a string */

/* Keywords */
%token define_league
%token define_functions
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

/* Associativity and Precedence */
%left PLUS

/* Types */
%type <sval> definitions
%type <sval> definitionlist
%type <sval> definitionproductions
%type <sval> functions
%type <sval> functionproductions
%type <sval> returnType
%type <sval> functionName
%type <sval> argumentList
%type <sval> functionBody
%type <sval> empty;
%type <sval> returnproduction;

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

definitions: define_league definitionlist { $$ = $2; };
           
definitionlist: definitionlist definitionproductions {$$ = $1 + $2;}
              | empty { $$ = $1; }
              ;

definitionproductions: set leagueName OPEN STRING_CONST CLOSE { $$ = "myLeague = new League(" + $4 + ");\n"; }
                     | set maxUser OPEN INT CLOSE { $$ = "myLeague.setMaxUser(" + $4 + ");\n"; }
                     | set minUser OPEN INT CLOSE { $$ = "myLeague.setMinUser(" + $4 + ");\n"; }
                     | add User OPEN STRING_CONST CLOSE { $$ = "myLeague.addUser(new User(" + $4 + "));\n"; }
                     | add Action OPEN STRING_CONST COMMA FLT CLOSE { $$ = "myLeague.addAction(new Action(" + $4 + ", " + $6 + "));\n"; }
                     ;

functions: define_functions functionproductions { $$ = $2; };

functionproductions: functionproductions returnType functionName OPEN argumentList CLOSE OPEN_PARAN functionBody CLOSE_PARAN { $$ = $1 + $2 + " " + $3 + "(" + $5 + ")\n{\n" + $8 + "\n}"; }
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

functionBody: returnproduction { $$ = $1; };

/* TODO: semicolon after return */
returnproduction: Return ID { $$ = "return " + $2; }
                | Return STRING_CONST { $$ = "return " + $2; }
                | Return INT { $$ = "return " + $2; }
                | Return FLT { $$ = "return " + $2; }
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
boolean createPositionFile = false;

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
    String buffer = classStart + main_start + definitions + functions + main_end + classEnd;
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