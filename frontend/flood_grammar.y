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
%token EMPTY            /* newline */
%token <ival> INT       /* an integer */
%token <dval> FLT       /* a float */
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
%token MODULUS          /* % */
%token LESS             /* < */
%token GREAT            /* > */
%token OPEN             /* ( */
%token CLOSE            /* ) */
%token Quote            /* " */
%token STRING_CONST     /* String literal constants */

/* Keywords */
%token Action           /* Action */
%token Class            /* Class */
%token Draft            /* Draft */
%token League           /* League */
%token Player           /* Player */
%token User             /* User */
%token if               /* if */
%token else             /* else */
%token for              /* for */
%token in               /* in */
%token while            /* while */
%token is               /* is */
%token private          /* private */
%token public           /* public */
%token set              /* set */
%token get              /* get */
%token setget           /* setget */
%token return           /* return */
%token void             /* void */
%token new              /* new */
%token true             /* true */
%token false            /* false */
%token str              /* str */
%token bool             /* bool */
%token <dval> flt       /* flt */
%token <ival> int       /* int */
%token list             /* list */

/* Associativity and Precedence */
%left MINUS PLUS
%left MUL DIV
%nonassoc EQUAL NOTEQUAL LESSEQUAL GREATEQUAL ISEQUAL LESS GREAT

/* Types */
%type <sval> definitions
%type <sval> usercode

%%

program: definitions usercode
{
  generateFloodProgram();
  System.out.println("Total number of lines in the input: " + (yyline-1));
};

definitions: PLUS{} ;
usercode: PLUS{};

%%

private Yylex lexer;
public int yyline = 1;
public int yycolumn = 0;

public void generateFloodProgram()
{
  String classStart = "public class FloodProgram {\n\n";
  String classEnd = "\n}";

  String main = "public static void main(String[] args){\n";

  FileWriter writer = new FileWriter(new File("FloodProgram.java"));
  String buffer = classStart + main + classEnd;
  writer.write(buffer);
  writer.close();
}

private int yylex ()
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

public Parser(Reader r, boolean createFile)
{
  lexer = new Yylex(r, this);
  this.createPositionFile = createFile;
}

public static void main(String args[]) throws IOException
{
  Parser yyparser;
  boolean createFile = false;

  if (args.length < 1)
  {
    System.out.println("Usage: java Parser <thrill_program.txt>");
    return;
  }
  else if (args.length == 2)
  {
    createFile = Boolean.parseBoolean(args[1]);
  }

  // parse a file
  yyparser = new Parser(new FileReader(args[0]), createFile);

  System.out.println("\nCompiling ...\n");

  try
  {
    yyparser.yyparse();
    
    if (yyparser.checkParseErrors())
    {
      System.out.println("\nCompilation failed!!\n");
    }
    else
    {
      System.out.println("\nThrillProgram.java generated successfully.\n");;
    }

  }
  catch (ThrillException ex)
  {
    System.out.println(ex.getMessage() + "\n");     
  }
}