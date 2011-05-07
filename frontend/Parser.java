//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 9 "flood_grammar.y"
  import java.lang.Math;
  import java.io.*;
  import java.util.Hashtable;
  import java.util.ArrayList;
  import java.util.Iterator;
  import java.util.HashMap;
  import java.util.*;
//#line 25 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short PLUS=257;
public final static short OPEN_CURLY=258;
public final static short CLOSE_CURLY=259;
public final static short OPEN_PARAN=260;
public final static short CLOSE_PARAN=261;
public final static short OPEN_SQUARE=262;
public final static short CLOSE_SQUARE=263;
public final static short FLT=264;
public final static short INT=265;
public final static short STRING_CONST=266;
public final static short COMMA=267;
public final static short DOT=268;
public final static short ID=269;
public final static short EQUAL=270;
public final static short NOTEQUAL=271;
public final static short LESSEQUAL=272;
public final static short GREATEQUAL=273;
public final static short ISEQUAL=274;
public final static short LESS=275;
public final static short GREAT=276;
public final static short MINUS=277;
public final static short MULT=278;
public final static short DIV=279;
public final static short NOT=280;
public final static short AND=281;
public final static short OR=282;
public final static short MOD=283;
public final static short SEMICOLON=284;
public final static short DefineLeague=285;
public final static short DefineFunctions=286;
public final static short LeagueName=287;
public final static short MaxUser=288;
public final static short MinUser=289;
public final static short MaxTeamSize=290;
public final static short MinTeamSize=291;
public final static short Set=292;
public final static short Add=293;
public final static short Action=294;
public final static short User=295;
public final static short Player=296;
public final static short Void=297;
public final static short Str=298;
public final static short Bool=299;
public final static short Flt=300;
public final static short Int=301;
public final static short Return=302;
public final static short If=303;
public final static short Else=304;
public final static short While=305;
public final static short True=306;
public final static short False=307;
public final static short RemovePlayer=308;
public final static short AddPlayer=309;
public final static short ArrayLength=310;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    3,    3,    3,    3,    3,    3,
    3,    3,    4,    5,    5,    5,    5,    5,    5,    7,
    7,    7,    7,    6,    6,    6,    6,    6,    8,    9,
    9,    9,   10,   10,   10,   10,   10,   15,   15,   14,
   14,   14,   14,   14,   12,   12,   12,   12,   12,   12,
   12,   13,   13,   13,   13,   13,   13,   16,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   16,   17,
   17,   17,   17,   18,   18,   19,   19,   19,   19,   19,
   19,   19,   19,   19,   20,   20,   20,   20,   20,   20,
   20,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   23,   23,   23,   21,   21,   21,
   21,   21,   21,   21,   21,   21,   26,   24,   25,   25,
   25,   25,   25,   27,   27,   27,   27,   28,   28,   28,
   28,   28,   28,   28,   28,   11,
};
final static short yylen[] = {                            2,
    2,    2,    2,    1,    6,    6,    6,    6,    6,    6,
    8,    8,    2,   11,    9,   11,   11,    9,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    3,
    1,    1,    2,    4,    4,    2,    2,    3,    2,    1,
    1,    1,    1,    1,    3,    3,    3,    3,    3,    3,
    1,    3,    3,    3,    3,    3,    3,    7,   11,    7,
   11,   11,   11,    7,   11,    7,   11,   11,   11,    7,
    7,    7,    7,    3,    2,    2,    2,    2,    2,    4,
    4,    4,    4,    4,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    2,    1,    1,    1,    1,    1,    1,    3,    3,    3,
    3,    3,    3,    1,    1,    1,    3,    1,    1,    1,
    1,    1,    1,    4,    6,    6,    4,    3,    1,    1,
    1,    1,    4,    4,    1,    0,
};
final static short yydefred[] = {                         0,
  136,    0,    0,    0,    4,  136,    1,    0,    0,    3,
    0,   19,    0,    0,    0,    0,    0,    0,    0,    0,
   24,   25,   26,   28,   27,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   29,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   20,   21,   23,   22,    0,
    0,   31,   32,    5,    6,    7,    8,    9,    0,   10,
    0,    0,   36,    0,   37,   33,    0,    0,    0,    0,
    0,    0,    0,   30,   11,   12,   34,   35,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   15,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   40,   41,
   42,    0,   43,   44,   18,    0,    0,    0,   75,    0,
    0,    0,    0,   55,   54,   53,   52,   56,   57,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   39,    0,   51,    0,    0,    0,    0,
    0,   74,   84,   82,   83,   80,   81,   91,  105,  106,
  107,   87,   85,   86,   90,   88,   89,    0,    0,    0,
  103,  104,    0,    0,    0,    0,    0,    0,    0,  131,
  130,  132,    0,  135,    0,    0,    0,    0,    0,    0,
    0,   16,   38,    0,  115,  116,  121,    0,  122,  123,
    0,  117,  120,   17,   14,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  127,
    0,  124,    0,   48,   47,   46,   45,   49,   50,  114,
    0,    0,    0,    0,    0,    0,  100,    0,   94,   96,
   95,   97,    0,   98,   92,   99,   93,    0,    0,    0,
    0,    0,    0,  128,  113,    0,    0,    0,    0,  112,
    0,    0,    0,    0,    0,    0,    0,    0,  126,  125,
  133,  134,    0,    0,    0,    0,   71,   70,   73,   72,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   61,   63,   62,   59,
   67,   69,   68,   65,
};
final static short yydgoto[] = {                          2,
    3,    4,   10,    7,   11,   26,   60,  116,   61,   62,
  156,  157,   95,  117,  118,  119,  120,   96,   97,  121,
  211,  184,  172,  122,  212,  123,  124,  195,
};
final static short yysindex[] = {                      -273,
    0,    0, -269, -283,    0,    0,    0,   56,   71,    0,
   53,    0, -234, -224, -209, -187, -185, -146, -135, -129,
    0,    0,    0,    0,    0, -137, -112, -128, -103,  -97,
  -91, -100,  -88,  -80,    0,  -62,  -70,  -44,  -37,  -23,
  -11,  -24,    6,    8,   27,   -7,   -6,    2,    4,    9,
   -8,   15,   34, -203, -173,    0,    0,    0,    0,   45,
 -236,    0,    0,    0,    0,    0,    0,    0,   49,    0,
   54,   72,    0,  105,    0,    0,   76,   27,   85,   86,
  102,  103,   57,    0,    0,    0,    0,    0,  104,  106,
  107,  108, -237, -253,  115,  -64,   94,  109,  110,  111,
  112,   99,  100,  101,  113,  114,  116,    0, -247,   66,
  126,  127,  128,  129,  130,  131,  117,  -29,    0,    0,
    0,  122,    0,    0,    0,   91,  -29,  118,    0,  133,
 -150,  132,  138,    0,    0,    0,    0,    0,    0,   66,
  134,  -85,  -85,  -85,  -85,  -85,  -85, -239, -239,  125,
  135,  136,  -39,    0,   42,    0,  147,  123,   -5,  149,
  150,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -239,   66, -239,
    0,    0, -183,  -74,  -69,  -67,  143,  144,  151,    0,
    0,    0,  152,    0, -227,  137,  139,  140,  141,  142,
  146,    0,    0, -156,    0,    0,    0,    0,    0,    0,
   41,    0,    0,    0,    0,  -33,  -28, -191, -131,  155,
 -239, -239,  157, -239, -239,  158,  159,  153,  160,    0,
 -216,    0,  -39,    0,    0,    0,    0,    0,    0,    0,
 -102, -156, -156, -156, -156, -156,    0,  -18,    0,    0,
    0,    0,  -18,    0,    0,    0,    0,  -18,  -18,  166,
  167,  156,  168,    0,    0,   38,   38,  154,  154,    0,
  161, -245,  173, -222,  174, -198,  175, -175,    0,    0,
    0,    0,  148,  162,  163,  164,    0,    0,    0,    0,
  177,  178,  180,  181,  -18,  -18,  -18,  -18,  182, -167,
  183, -120,  184,  -99,  185,  -87,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  165,    0,    0,    0,    0,    0,    0,
  418,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -213,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -40,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -257,    0,  169,  170,  171,
  172,    0,    0,    0,    0,    0,    0,    0,    0, -252,
    0,    0,    0,    0,    0,    0,    0,  186,    0,    0,
    0,    0,    0,    0,    0,  186,  186,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -213,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   51,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -138,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -157,    0,    0,
  176,    0,    0,    0,    0,    0,    0,    0,  179,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -213,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  186,    0,    0,
    0,    0,  186,    0,    0,    0,    0,  186,  186,    0,
    0,    0,    0,    0,    0, -238, -160,    5,   52,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  187,  188,    2,   -7,    0,    0,    0,    0,
    0,    0,    0,    0,  186,  186,  186,  186,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,  420,    0,  369,
   -1,  145,    0, -107,  -95,    0,    0,    0,  352, -106,
  -98, -145,  217,    0,    0,    0,  290,  224,
};
final static int YYTABLESIZE=472;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          5,
  127,  136,  141,  186,   12,  108,  109,   29,    8,    9,
  158,    1,  109,  284,  109,  110,    6,  118,  108,  158,
  178,  140,  108,  110,   77,   27,  102,  103,  104,  179,
   78,  105,  217,  232,  219,   28,  286,  109,  108,  233,
  180,  183,  185,   63,  136,  108,  110,  136,  262,  111,
   29,  112,  263,  136,  113,  114,  115,  111,   72,  112,
  288,  109,  113,  114,  115,   73,  181,  182,  106,  107,
  110,  216,   30,  218,   31,  250,  252,  220,  255,  257,
  111,   94,  112,  290,  109,  113,  114,  115,   74,  221,
  222,  308,  109,  110,  126,   75,  109,  221,  222,  114,
  109,  110,   29,  204,  111,  241,  112,  205,  206,  113,
  114,  115,  240,   32,  249,  251,  109,  254,  256,  114,
  114,  114,  129,  109,   33,  114,  114,  111,  129,  112,
   34,   35,  113,  114,  115,  111,   38,  112,  310,  109,
  113,  114,  115,  266,  267,  268,  269,  270,  110,  224,
  225,  194,  272,   37,  242,  164,  165,  274,  265,  312,
  109,   39,  276,  278,  158,   42,  158,   40,  158,  110,
  158,  314,  109,   41,  243,  244,  245,   43,  169,  170,
  246,  110,  111,  171,  112,   44,  223,  113,  114,  115,
   46,  226,  158,  227,  158,  109,  158,   45,  158,  300,
  302,  304,  306,  111,  110,  112,  224,  225,  113,  114,
  115,  221,  222,  224,  225,  111,   47,  112,  136,  136,
  113,  114,  115,   48,  190,  191,  192,  168,  136,  193,
  109,  194,  247,   89,   90,   91,   92,   49,  111,  110,
  112,  109,   51,  113,  114,  115,  271,  221,  222,   50,
  110,  273,  224,  225,  204,   69,  275,  277,  205,  206,
  207,  110,  136,  208,  136,  110,   52,  136,  136,  136,
  160,  161,  155,  111,   53,  112,   64,   65,  113,  114,
  115,  110,  110,  110,  111,   66,  112,   67,  110,  113,
  114,  115,   68,  299,  301,  303,  305,  242,   70,   71,
  209,  210,  113,  114,  115,  196,  197,  198,  111,   79,
  199,  102,  111,   76,   80,  244,  245,  243,  244,  245,
  246,   54,   55,  246,   56,   57,   58,   59,  111,  111,
  111,  102,  102,   83,   81,  111,  142,  143,  144,  145,
  146,  147,   13,   14,   15,   16,   17,  200,  201,   21,
   22,   23,   24,   25,   89,   90,   91,   92,   93,  173,
  174,  175,  176,  177,   18,   19,   20,   82,   85,   86,
   87,   88,   98,  125,   99,  100,  101,  129,  130,  131,
  132,  133,  134,  135,  136,  148,  149,  150,  151,  152,
  153,  159,  155,  187,  168,  166,  137,  138,  163,  139,
  154,  162,  167,  188,  189,  202,  203,  214,  215,  228,
  229,  230,  248,  231,  253,  258,  259,   13,  281,  283,
  234,  260,  235,  236,  237,  238,  279,  280,  261,  239,
  282,  285,  287,  289,  295,  296,  246,  297,  298,  101,
  307,  309,  311,  313,  136,   36,   84,  128,  213,    0,
    2,  291,   79,   78,   76,   77,  264,    0,    0,  119,
    0,    0,    0,    0,    0,  292,  293,  294,    0,    0,
   60,   58,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
   96,  259,  109,  149,    6,  259,  260,  260,  292,  293,
  118,  285,  260,  259,  260,  269,  286,  270,  257,  127,
  260,  269,  261,  269,  261,  260,  264,  265,  266,  269,
  267,  269,  178,  261,  180,  260,  259,  260,  277,  267,
  280,  148,  149,   45,  302,  284,  269,  261,  265,  303,
  260,  305,  269,  267,  308,  309,  310,  303,  262,  305,
  259,  260,  308,  309,  310,  269,  306,  307,  306,  307,
  269,  178,  260,  180,  260,  221,  222,  261,  224,  225,
  303,   83,  305,  259,  260,  308,  309,  310,  262,  281,
  282,  259,  260,  269,   96,  269,  257,  281,  282,  257,
  261,  269,  260,  260,  303,  204,  305,  264,  265,  308,
  309,  310,  269,  260,  221,  222,  277,  224,  225,  277,
  278,  279,  261,  284,  260,  283,  284,  303,  267,  305,
  260,  269,  308,  309,  310,  303,  265,  305,  259,  260,
  308,  309,  310,  242,  243,  244,  245,  246,  269,  281,
  282,  153,  248,  266,  257,  306,  307,  253,  261,  259,
  260,  265,  258,  259,  272,  266,  274,  265,  276,  269,
  278,  259,  260,  265,  277,  278,  279,  266,  264,  265,
  283,  269,  303,  269,  305,  266,  261,  308,  309,  310,
  261,  261,  300,  261,  302,  260,  304,  260,  306,  295,
  296,  297,  298,  303,  269,  305,  281,  282,  308,  309,
  310,  281,  282,  281,  282,  303,  261,  305,  259,  260,
  308,  309,  310,  261,  264,  265,  266,  261,  269,  269,
  260,  233,  261,  298,  299,  300,  301,  261,  303,  269,
  305,  260,  267,  308,  309,  310,  248,  281,  282,  261,
  269,  253,  281,  282,  260,  264,  258,  259,  264,  265,
  266,  257,  303,  269,  305,  261,  261,  308,  309,  310,
  126,  127,  302,  303,  267,  305,  284,  284,  308,  309,
  310,  277,  278,  279,  303,  284,  305,  284,  284,  308,
  309,  310,  284,  295,  296,  297,  298,  257,  284,  266,
  306,  307,  308,  309,  310,  264,  265,  266,  257,  261,
  269,  261,  261,  269,  261,  278,  279,  277,  278,  279,
  283,  295,  296,  283,  298,  299,  300,  301,  277,  278,
  279,  281,  282,  258,  263,  284,  271,  272,  273,  274,
  275,  276,  287,  288,  289,  290,  291,  306,  307,  297,
  298,  299,  300,  301,  298,  299,  300,  301,  302,  143,
  144,  145,  146,  147,  294,  295,  296,  263,  284,  284,
  269,  269,  269,  259,  269,  269,  269,  284,  270,  270,
  270,  270,  284,  284,  284,  260,  260,  260,  260,  260,
  260,  270,  302,  269,  261,  264,  284,  284,  266,  284,
  284,  284,  265,  269,  269,  259,  284,  259,  259,  267,
  267,  261,  258,  262,  258,  258,  258,    0,  263,  259,
  284,  269,  284,  284,  284,  284,  261,  261,  269,  284,
  263,  259,  259,  259,  258,  258,  283,  258,  258,  261,
  259,  259,  259,  259,  259,   26,   78,   96,  159,   -1,
  286,  304,  284,  284,  284,  284,  233,   -1,   -1,  284,
   -1,   -1,   -1,   -1,   -1,  304,  304,  304,   -1,   -1,
  284,  284,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=310;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"PLUS","OPEN_CURLY","CLOSE_CURLY","OPEN_PARAN","CLOSE_PARAN",
"OPEN_SQUARE","CLOSE_SQUARE","FLT","INT","STRING_CONST","COMMA","DOT","ID",
"EQUAL","NOTEQUAL","LESSEQUAL","GREATEQUAL","ISEQUAL","LESS","GREAT","MINUS",
"MULT","DIV","NOT","AND","OR","MOD","SEMICOLON","DefineLeague",
"DefineFunctions","LeagueName","MaxUser","MinUser","MaxTeamSize","MinTeamSize",
"Set","Add","Action","User","Player","Void","Str","Bool","Flt","Int","Return",
"If","Else","While","True","False","RemovePlayer","AddPlayer","ArrayLength",
};
final static String yyrule[] = {
"$accept : program",
"program : definitions functions",
"definitions : DefineLeague definitionlist",
"definitionlist : definitionlist definitionproductions",
"definitionlist : empty",
"definitionproductions : Set LeagueName OPEN_PARAN STRING_CONST CLOSE_PARAN SEMICOLON",
"definitionproductions : Set MaxUser OPEN_PARAN INT CLOSE_PARAN SEMICOLON",
"definitionproductions : Set MinUser OPEN_PARAN INT CLOSE_PARAN SEMICOLON",
"definitionproductions : Set MaxTeamSize OPEN_PARAN INT CLOSE_PARAN SEMICOLON",
"definitionproductions : Set MinTeamSize OPEN_PARAN INT CLOSE_PARAN SEMICOLON",
"definitionproductions : Add User OPEN_PARAN STRING_CONST CLOSE_PARAN SEMICOLON",
"definitionproductions : Add Action OPEN_PARAN STRING_CONST COMMA FLT CLOSE_PARAN SEMICOLON",
"definitionproductions : Add Player OPEN_PARAN STRING_CONST COMMA STRING_CONST CLOSE_PARAN SEMICOLON",
"functions : DefineFunctions functionProductions",
"functionProductions : functionProductions returnType functionName OPEN_PARAN argumentLists CLOSE_PARAN OPEN_CURLY declarations statements returnProduction CLOSE_CURLY",
"functionProductions : functionProductions returnType functionName OPEN_PARAN argumentLists CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY",
"functionProductions : functionProductions returnType functionName OPEN_PARAN argumentLists CLOSE_PARAN OPEN_CURLY empty statements returnProduction CLOSE_CURLY",
"functionProductions : functionProductions returnType functionName OPEN_PARAN argumentLists CLOSE_PARAN OPEN_CURLY declarations empty returnProduction CLOSE_CURLY",
"functionProductions : functionProductions returnType functionName OPEN_PARAN argumentLists CLOSE_PARAN OPEN_CURLY returnProd CLOSE_CURLY",
"functionProductions : empty",
"dataType : Str",
"dataType : Bool",
"dataType : Int",
"dataType : Flt",
"returnType : Void",
"returnType : Str",
"returnType : Bool",
"returnType : Int",
"returnType : Flt",
"functionName : ID",
"argumentLists : argumentLists COMMA argumentList",
"argumentLists : argumentList",
"argumentLists : empty",
"argumentList : dataType ID",
"argumentList : User OPEN_SQUARE CLOSE_SQUARE ID",
"argumentList : Player OPEN_SQUARE CLOSE_SQUARE ID",
"argumentList : User ID",
"argumentList : Player ID",
"statements : statements statement SEMICOLON",
"statements : statement SEMICOLON",
"statement : conditionals",
"statement : loop",
"statement : relationalExp",
"statement : assignment",
"statement : functionCall",
"returnProduction : Return ID SEMICOLON",
"returnProduction : Return STRING_CONST SEMICOLON",
"returnProduction : Return INT SEMICOLON",
"returnProduction : Return FLT SEMICOLON",
"returnProduction : Return True SEMICOLON",
"returnProduction : Return False SEMICOLON",
"returnProduction : empty",
"returnProd : Return ID SEMICOLON",
"returnProd : Return STRING_CONST SEMICOLON",
"returnProd : Return INT SEMICOLON",
"returnProd : Return FLT SEMICOLON",
"returnProd : Return True SEMICOLON",
"returnProd : Return False SEMICOLON",
"conditionals : If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY",
"conditionals : If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY Else OPEN_CURLY statements CLOSE_CURLY",
"conditionals : If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY",
"conditionals : If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY Else OPEN_CURLY empty CLOSE_CURLY",
"conditionals : If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY Else OPEN_CURLY empty CLOSE_CURLY",
"conditionals : If OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY Else OPEN_CURLY statements CLOSE_CURLY",
"conditionals : If OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY",
"conditionals : If OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY Else OPEN_CURLY statements CLOSE_CURLY",
"conditionals : If OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY",
"conditionals : If OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY Else OPEN_CURLY empty CLOSE_CURLY",
"conditionals : If OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY Else OPEN_CURLY empty CLOSE_CURLY",
"conditionals : If OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY Else OPEN_CURLY statements CLOSE_CURLY",
"loop : While OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY",
"loop : While OPEN_PARAN relationalExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY",
"loop : While OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY statements CLOSE_CURLY",
"loop : While OPEN_PARAN booleanExp CLOSE_PARAN OPEN_CURLY empty CLOSE_CURLY",
"declarations : declarations declaration SEMICOLON",
"declarations : declaration SEMICOLON",
"declaration : Flt ID",
"declaration : Int ID",
"declaration : Bool ID",
"declaration : Str ID",
"declaration : Flt ID EQUAL FLT",
"declaration : Int ID EQUAL INT",
"declaration : Bool ID EQUAL True",
"declaration : Bool ID EQUAL False",
"declaration : Str ID EQUAL STRING_CONST",
"relationalExp : ID LESSEQUAL constOrVar",
"relationalExp : ID GREATEQUAL constOrVar",
"relationalExp : ID NOTEQUAL constOrVar",
"relationalExp : ID LESS constOrVar",
"relationalExp : ID GREAT constOrVar",
"relationalExp : ID ISEQUAL constOrVar",
"relationalExp : OPEN_PARAN relationalExp CLOSE_PARAN",
"booleanExp : booleanExp AND booleanExp",
"booleanExp : booleanExp OR booleanExp",
"booleanExp : relationalExp AND relationalExp",
"booleanExp : relationalExp OR relationalExp",
"booleanExp : relationalExp AND booleanExp",
"booleanExp : relationalExp OR booleanExp",
"booleanExp : booleanExp AND relationalExp",
"booleanExp : booleanExp OR relationalExp",
"booleanExp : OPEN_PARAN booleanExp CLOSE_PARAN",
"booleanExp : NOT booleanExp",
"booleanExp : ID",
"booleanExp : True",
"booleanExp : False",
"constOrVar : FLT",
"constOrVar : INT",
"constOrVar : ID",
"arithmeticExp : arithmeticExp PLUS arithmeticExp",
"arithmeticExp : arithmeticExp MINUS arithmeticExp",
"arithmeticExp : arithmeticExp MULT arithmeticExp",
"arithmeticExp : arithmeticExp DIV arithmeticExp",
"arithmeticExp : arithmeticExp MOD arithmeticExp",
"arithmeticExp : OPEN_PARAN arithmeticExp CLOSE_PARAN",
"arithmeticExp : ID",
"arithmeticExp : FLT",
"arithmeticExp : INT",
"assignment : leftSide EQUAL rightSide",
"leftSide : ID",
"rightSide : arithmeticExp",
"rightSide : functionCall",
"rightSide : STRING_CONST",
"rightSide : True",
"rightSide : False",
"functionCall : functionName OPEN_PARAN parameterList CLOSE_PARAN",
"functionCall : AddPlayer OPEN_PARAN ID COMMA ID CLOSE_PARAN",
"functionCall : RemovePlayer OPEN_PARAN ID COMMA ID CLOSE_PARAN",
"functionCall : ArrayLength OPEN_PARAN ID CLOSE_PARAN",
"parameterList : parameterList COMMA parameterList",
"parameterList : ID",
"parameterList : INT",
"parameterList : FLT",
"parameterList : STRING_CONST",
"parameterList : ID OPEN_SQUARE INT CLOSE_SQUARE",
"parameterList : ID OPEN_SQUARE ID CLOSE_SQUARE",
"parameterList : empty",
"empty :",
};

//#line 319 "flood_grammar.y"

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
//#line 687 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 119 "flood_grammar.y"
{
	if(semantics.validProgram)
	{
		generateFloodProgram(val_peek(1).sval, val_peek(0).sval);
		System.out.println("Total number of lines in the input: " + (yyline - 1));
	}
	else
	{
		System.out.println(semantics.printErrors());
	}
}
break;
case 2:
//#line 131 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 3:
//#line 133 "flood_grammar.y"
{ yyval.sval = val_peek(1).sval + val_peek(0).sval; }
break;
case 4:
//#line 134 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 5:
//#line 137 "flood_grammar.y"
{ yyval.sval = "myLeague = new League(" + val_peek(2).sval + ");\n"; }
break;
case 6:
//#line 138 "flood_grammar.y"
{ yyval.sval = "myLeague.setMaxUser(" + val_peek(2).ival + ");\n"; }
break;
case 7:
//#line 139 "flood_grammar.y"
{ yyval.sval = "myLeague.setMinUser(" + val_peek(2).ival + ");\n"; }
break;
case 8:
//#line 140 "flood_grammar.y"
{ yyval.sval = "myLeague.setMaxTeamSize(" + val_peek(2).ival + ");\n"; }
break;
case 9:
//#line 141 "flood_grammar.y"
{ yyval.sval = "myLeague.setMinTeamSize(" + val_peek(2).ival + ");\n"; }
break;
case 10:
//#line 142 "flood_grammar.y"
{ yyval.sval = "myLeague.addUser(new User(" + val_peek(2).sval + "));\n"; semantics.addUser(val_peek(2).sval, yyline); }
break;
case 11:
//#line 143 "flood_grammar.y"
{ yyval.sval = "myLeague.addAction(new Action(" + val_peek(4).sval + ", " + val_peek(2).dval + "));\n"; semantics.addAction(val_peek(4).sval, yyline); }
break;
case 12:
//#line 144 "flood_grammar.y"
{ yyval.sval = "myLeague.addPlayer(new Player(" + val_peek(4).sval + ", " + val_peek(2).sval + "));\n"; semantics.addPlayer(val_peek(4).sval, yyline); }
break;
case 13:
//#line 147 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval + semantics.setFlags(); }
break;
case 14:
//#line 149 "flood_grammar.y"
{ yyval.sval = "public static "+val_peek(10).sval + val_peek(9).sval + " " + val_peek(8).sval + "(" + val_peek(6).sval + ")\n{\n" + val_peek(3).sval + val_peek(2).sval + val_peek(1).sval + "\n}\n"; this.scope = val_peek(8).sval; semantics.addToFunctionTable(val_peek(8).sval, val_peek(9).sval, val_peek(6).sval, yyline); semantics.checkReturnTypeMatch(val_peek(9).sval, yyline); }
break;
case 15:
//#line 150 "flood_grammar.y"
{yyval.sval = "public static "+val_peek(8).sval + val_peek(7).sval + " " + val_peek(6).sval + "(" + val_peek(4).sval + ")\n{\n}\n"; this.scope = val_peek(6).sval; semantics.addToFunctionTable(val_peek(6).sval, val_peek(7).sval, val_peek(4).sval, yyline); semantics.checkReturnTypeMatch(val_peek(7).sval, yyline); }
break;
case 16:
//#line 151 "flood_grammar.y"
{yyval.sval = "public static "+val_peek(10).sval + val_peek(9).sval + " " + val_peek(8).sval + "(" + val_peek(6).sval + ")\n{\n" + val_peek(3).sval + val_peek(2).sval + val_peek(1).sval + "\n}\n"; this.scope = val_peek(8).sval; semantics.addToFunctionTable(val_peek(8).sval, val_peek(9).sval, val_peek(6).sval, yyline); semantics.checkReturnTypeMatch(val_peek(9).sval, yyline); }
break;
case 17:
//#line 152 "flood_grammar.y"
{yyval.sval = "public static "+val_peek(10).sval + val_peek(9).sval + " " + val_peek(8).sval + "(" + val_peek(6).sval + ")\n{\n" + val_peek(3).sval + val_peek(2).sval + val_peek(1).sval + "\n}\n"; this.scope = val_peek(8).sval; semantics.addToFunctionTable(val_peek(8).sval, val_peek(9).sval, val_peek(6).sval, yyline); semantics.checkReturnTypeMatch(val_peek(9).sval, yyline); }
break;
case 18:
//#line 153 "flood_grammar.y"
{yyval.sval = "public static "+val_peek(8).sval + val_peek(7).sval + " " + val_peek(6).sval + "(" + val_peek(4).sval + ")\n{\n" + val_peek(1).sval + "\n}\n"; this.scope = val_peek(6).sval; semantics.addToFunctionTable(val_peek(6).sval, val_peek(7).sval, val_peek(4).sval, yyline); semantics.checkReturnTypeMatch(val_peek(7).sval, yyline); }
break;
case 19:
//#line 154 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 20:
//#line 157 "flood_grammar.y"
{ yyval.sval = "String"; }
break;
case 21:
//#line 158 "flood_grammar.y"
{ yyval.sval = "boolean"; }
break;
case 22:
//#line 159 "flood_grammar.y"
{ yyval.sval = "int"; }
break;
case 23:
//#line 160 "flood_grammar.y"
{ yyval.sval = "float"; }
break;
case 24:
//#line 163 "flood_grammar.y"
{ yyval.sval = "void"; }
break;
case 25:
//#line 164 "flood_grammar.y"
{ yyval.sval = "String"; }
break;
case 26:
//#line 165 "flood_grammar.y"
{ yyval.sval = "boolean"; }
break;
case 27:
//#line 166 "flood_grammar.y"
{ yyval.sval = "int"; }
break;
case 28:
//#line 167 "flood_grammar.y"
{ yyval.sval = "float"; }
break;
case 29:
//#line 170 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 30:
//#line 172 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + ", " + val_peek(0).sval; }
break;
case 31:
//#line 173 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 32:
//#line 174 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 33:
//#line 177 "flood_grammar.y"
{ yyval.sval = val_peek(1).sval + " " + val_peek(0).sval; }
break;
case 34:
//#line 178 "flood_grammar.y"
{ yyval.sval = "User[] " + val_peek(0).sval; }
break;
case 35:
//#line 179 "flood_grammar.y"
{ yyval.sval = "Player[] " + val_peek(0).sval; }
break;
case 36:
//#line 180 "flood_grammar.y"
{ yyval.sval = "User " + val_peek(0).sval; }
break;
case 37:
//#line 181 "flood_grammar.y"
{ yyval.sval = "Player " + val_peek(0).sval; }
break;
case 38:
//#line 184 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + val_peek(1).sval; }
break;
case 39:
//#line 185 "flood_grammar.y"
{ yyval.sval = val_peek(1).sval; }
break;
case 40:
//#line 188 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 41:
//#line 189 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 42:
//#line 190 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 43:
//#line 191 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 44:
//#line 192 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval + ";\n"; }
break;
case 45:
//#line 195 "flood_grammar.y"
{ yyval.sval = "return " + val_peek(1).sval + ";"; semantics.setReturnProdType(semantics.getType(val_peek(1).sval)); }
break;
case 46:
//#line 196 "flood_grammar.y"
{ yyval.sval = "return " + val_peek(1).sval + ";"; semantics.setReturnProdType("String"); }
break;
case 47:
//#line 197 "flood_grammar.y"
{ yyval.sval = "return " + val_peek(1).ival + ";"; semantics.setReturnProdType("int"); }
break;
case 48:
//#line 198 "flood_grammar.y"
{ yyval.sval = "return " + val_peek(1).dval + ";"; semantics.setReturnProdType("float"); }
break;
case 49:
//#line 199 "flood_grammar.y"
{ yyval.sval = "return true;"; semantics.setReturnProdType("boolean"); }
break;
case 50:
//#line 200 "flood_grammar.y"
{ yyval.sval = "return false;"; semantics.setReturnProdType("boolean"); }
break;
case 51:
//#line 201 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; semantics.setReturnProdType("void"); }
break;
case 52:
//#line 204 "flood_grammar.y"
{ yyval.sval = "return " + val_peek(1).sval + ";"; semantics.setReturnProdType(semantics.getType(val_peek(1).sval)); }
break;
case 53:
//#line 205 "flood_grammar.y"
{ yyval.sval = "return " + val_peek(1).sval + ";"; semantics.setReturnProdType("String"); }
break;
case 54:
//#line 206 "flood_grammar.y"
{ yyval.sval = "return " + val_peek(1).ival + ";"; semantics.setReturnProdType("int"); }
break;
case 55:
//#line 207 "flood_grammar.y"
{ yyval.sval = "return " + val_peek(1).dval + ";"; semantics.setReturnProdType("float"); }
break;
case 56:
//#line 208 "flood_grammar.y"
{ yyval.sval = "return true;"; semantics.setReturnProdType("boolean"); }
break;
case 57:
//#line 209 "flood_grammar.y"
{ yyval.sval = "return false;"; semantics.setReturnProdType("boolean"); }
break;
case 58:
//#line 212 "flood_grammar.y"
{ yyval.sval = "if(" + val_peek(4).sval + ")\n{\n" + val_peek(1).sval + "}\n"; }
break;
case 59:
//#line 213 "flood_grammar.y"
{ yyval.sval = "if(" + val_peek(8).sval + ")\n{\n" + val_peek(5).sval + "}\nelse\n{\n" + val_peek(1).sval + "}\n"; }
break;
case 60:
//#line 214 "flood_grammar.y"
{ yyval.sval = "if(" + val_peek(4).sval + ")\n{\n}\n"; }
break;
case 61:
//#line 215 "flood_grammar.y"
{ yyval.sval = "if(" + val_peek(8).sval + ")\n{\n}\nelse\n{\n}\n"; }
break;
case 62:
//#line 216 "flood_grammar.y"
{ yyval.sval = "if(" + val_peek(8).sval + ")\n{\n" + val_peek(5).sval + "}\nelse\n{\n}\n"; }
break;
case 63:
//#line 217 "flood_grammar.y"
{ yyval.sval = "if(" + val_peek(8).sval + ")\n{\n}" + "\nelse\n{\n" + val_peek(1).sval + "}\n"; }
break;
case 64:
//#line 218 "flood_grammar.y"
{ yyval.sval = "if(" + val_peek(4).sval + ")\n{\n" + val_peek(1).sval + "}\n"; }
break;
case 65:
//#line 219 "flood_grammar.y"
{ yyval.sval = "if(" + val_peek(8).sval + ")\n{\n" + val_peek(5).sval + "}\nelse\n{\n" + val_peek(1).sval + "}\n"; }
break;
case 66:
//#line 220 "flood_grammar.y"
{ yyval.sval = "if(" + val_peek(4).sval + ")\n{\n}\n"; }
break;
case 67:
//#line 221 "flood_grammar.y"
{ yyval.sval = "if(" + val_peek(8).sval + ")\n{\n}\nelse\n{\n}\n"; }
break;
case 68:
//#line 222 "flood_grammar.y"
{ yyval.sval = "if(" + val_peek(8).sval + ")\n{\n" + val_peek(5).sval + "}\nelse\n{\n}\n"; }
break;
case 69:
//#line 223 "flood_grammar.y"
{ yyval.sval = "if(" + val_peek(8).sval + ")\n{\n}" + "\nelse\n{\n" + val_peek(1).sval + "}\n"; }
break;
case 70:
//#line 226 "flood_grammar.y"
{ yyval.sval = "while(" + val_peek(4).sval + ")\n{\n" + val_peek(1).sval + "}\n"; }
break;
case 71:
//#line 227 "flood_grammar.y"
{ yyval.sval = "while(" + val_peek(4).sval + ")\n{\n}\n"; }
break;
case 72:
//#line 228 "flood_grammar.y"
{ yyval.sval = "while(" + val_peek(4).sval + ")\n{\n" + val_peek(1).sval + "}\n"; }
break;
case 73:
//#line 229 "flood_grammar.y"
{ yyval.sval = "while(" + val_peek(4).sval + ")\n{\n}\n"; }
break;
case 74:
//#line 232 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + val_peek(1).sval; }
break;
case 75:
//#line 233 "flood_grammar.y"
{ yyval.sval = val_peek(1).sval; }
break;
case 76:
//#line 237 "flood_grammar.y"
{ yyval.sval = "float " + val_peek(0).sval + ";\n";  semantics.addVar(val_peek(0).sval, "float", yyline); }
break;
case 77:
//#line 238 "flood_grammar.y"
{ yyval.sval = "int " + val_peek(0).sval + ";\n";  semantics.addVar(val_peek(0).sval, "int", yyline); }
break;
case 78:
//#line 239 "flood_grammar.y"
{ yyval.sval = "boolean " + val_peek(0).sval + ";\n";  semantics.addVar(val_peek(0).sval, "boolean", yyline); }
break;
case 79:
//#line 240 "flood_grammar.y"
{ yyval.sval = "String " + val_peek(0).sval + " = new String();\n";  semantics.addVar(val_peek(0).sval, "String", yyline); }
break;
case 80:
//#line 241 "flood_grammar.y"
{ yyval.sval = "float " + val_peek(2).sval + " = " + val_peek(0).dval + ";\n"; semantics.addVar(val_peek(2).sval, "float", yyline); }
break;
case 81:
//#line 242 "flood_grammar.y"
{ yyval.sval = "int " + val_peek(2).sval + " = " + val_peek(0).ival + ";\n"; semantics.addVar(val_peek(2).sval, "int", yyline); }
break;
case 82:
//#line 243 "flood_grammar.y"
{ yyval.sval = "boolean " + val_peek(2).sval + " = true;\n"; semantics.addVar(val_peek(2).sval, "boolean", yyline); }
break;
case 83:
//#line 244 "flood_grammar.y"
{ yyval.sval = "boolean " + val_peek(2).sval + " = false;\n"; semantics.addVar(val_peek(2).sval, "boolean", yyline); }
break;
case 84:
//#line 245 "flood_grammar.y"
{ yyval.sval = "String " + val_peek(2).sval + " = " + val_peek(0).sval + ";\n"; semantics.addVar(val_peek(2).sval, "String", yyline); }
break;
case 85:
//#line 248 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " <= " + val_peek(0).sval; semantics.checkRelationalExp(val_peek(2).sval, val_peek(0).sval, yyline); semantics.checkRelationForInvalidType(val_peek(2).sval, yyline); }
break;
case 86:
//#line 249 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " >= " + val_peek(0).sval; semantics.checkRelationalExp(val_peek(2).sval, val_peek(0).sval, yyline); semantics.checkRelationForInvalidType(val_peek(2).sval, yyline); }
break;
case 87:
//#line 250 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " != " + val_peek(0).sval; semantics.checkRelationalExp(val_peek(2).sval, val_peek(0).sval, yyline); }
break;
case 88:
//#line 251 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " < " + val_peek(0).sval; semantics.checkRelationalExp(val_peek(2).sval, val_peek(0).sval, yyline); semantics.checkRelationForInvalidType(val_peek(2).sval, yyline); }
break;
case 89:
//#line 252 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " > " + val_peek(0).sval; semantics.checkRelationalExp(val_peek(2).sval, val_peek(0).sval, yyline); semantics.checkRelationForInvalidType(val_peek(2).sval, yyline); }
break;
case 90:
//#line 253 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " == " + val_peek(0).sval; semantics.checkRelationalExp(val_peek(2).sval, val_peek(0).sval, yyline); }
break;
case 91:
//#line 254 "flood_grammar.y"
{ yyval.sval = "(" + val_peek(1).sval + ")"; }
break;
case 92:
//#line 258 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval; }
break;
case 93:
//#line 259 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval; }
break;
case 94:
//#line 260 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval; }
break;
case 95:
//#line 261 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval; }
break;
case 96:
//#line 262 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval; }
break;
case 97:
//#line 263 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval; }
break;
case 98:
//#line 264 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval; }
break;
case 99:
//#line 265 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval; }
break;
case 100:
//#line 266 "flood_grammar.y"
{ yyval.sval = "(" +  val_peek(1).sval + ")"; }
break;
case 101:
//#line 267 "flood_grammar.y"
{ yyval.sval = "!" + val_peek(0).sval; }
break;
case 102:
//#line 268 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 103:
//#line 269 "flood_grammar.y"
{ yyval.sval = "true"; }
break;
case 104:
//#line 270 "flood_grammar.y"
{ yyval.sval = "false"; }
break;
case 105:
//#line 273 "flood_grammar.y"
{ yyval.sval = "" + val_peek(0).dval; }
break;
case 106:
//#line 274 "flood_grammar.y"
{ yyval.sval = "" + val_peek(0).ival; }
break;
case 107:
//#line 275 "flood_grammar.y"
{ yyval.sval = "" + val_peek(0).sval; }
break;
case 108:
//#line 278 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval ; semantics.checkForBadAdditionType(yyline);}
break;
case 109:
//#line 279 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval; semantics.checkForBadArithmeticType(yyline);}
break;
case 110:
//#line 280 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval; semantics.checkForBadArithmeticType(yyline);}
break;
case 111:
//#line 281 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval; semantics.checkForBadArithmeticType(yyline);}
break;
case 112:
//#line 282 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval; semantics.checkForBadArithmeticType(yyline);}
break;
case 113:
//#line 283 "flood_grammar.y"
{ yyval.sval = "(" + val_peek(1).sval + ")"; }
break;
case 114:
//#line 284 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; semantics.assignmentCheckVar(val_peek(0).sval, yyline); }
break;
case 115:
//#line 285 "flood_grammar.y"
{ yyval.sval = "" + val_peek(0).dval; semantics.assignmentCheckLeftIsOfType("float", yyline); }
break;
case 116:
//#line 286 "flood_grammar.y"
{ yyval.sval = "" + val_peek(0).ival; semantics.assignmentCheckLeftIsOfType("int", yyline); }
break;
case 117:
//#line 289 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + " = " + val_peek(0).sval; }
break;
case 118:
//#line 291 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; semantics.assignmentCheckLeft(val_peek(0).sval, yyline); }
break;
case 119:
//#line 293 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval + ";\n"; }
break;
case 120:
//#line 294 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval + ";\n"; semantics.assignmentCheckFunction(val_peek(0).sval, yyline); }
break;
case 121:
//#line 295 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval + ";\n"; semantics.assignmentCheckLeftIsOfType("String", yyline); }
break;
case 122:
//#line 296 "flood_grammar.y"
{ yyval.sval = "true" + ";\n"; semantics.assignmentCheckLeftIsOfType("boolean", yyline); }
break;
case 123:
//#line 297 "flood_grammar.y"
{ yyval.sval = "false" + ";\n"; semantics.assignmentCheckLeftIsOfType("boolean", yyline); }
break;
case 124:
//#line 300 "flood_grammar.y"
{ yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")"; }
break;
case 125:
//#line 301 "flood_grammar.y"
{ yyval.sval = val_peek(3).sval + ".addPlayer(" + val_peek(1).sval + ")" ; semantics.checkIDagainstType(val_peek(3).sval,"User", yyline);semantics.checkIDagainstType(val_peek(1).sval,"Player", yyline);}
break;
case 126:
//#line 302 "flood_grammar.y"
{ yyval.sval = val_peek(3).sval + ".removePlayer(" + val_peek(1).sval + ")" ; semantics.checkIDagainstType(val_peek(3).sval,"User", yyline);semantics.checkIDagainstType(val_peek(1).sval,"Player", yyline);}
break;
case 127:
//#line 303 "flood_grammar.y"
{ yyval.sval = val_peek(1).sval + ".length"; semantics.checkIDagainstType(val_peek(1).sval,"User[]", yyline); semantics.checkIDagainstType(val_peek(1).sval,"Player[]", yyline);}
break;
case 128:
//#line 306 "flood_grammar.y"
{ yyval.sval = val_peek(2).sval + ", " + val_peek(0).sval; }
break;
case 129:
//#line 307 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 130:
//#line 308 "flood_grammar.y"
{ yyval.sval = "" + val_peek(0).ival; }
break;
case 131:
//#line 309 "flood_grammar.y"
{ yyval.sval = "" + val_peek(0).dval; }
break;
case 132:
//#line 310 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 133:
//#line 311 "flood_grammar.y"
{ yyval.sval = val_peek(3).sval + "[" + val_peek(1).ival + "]"; }
break;
case 134:
//#line 312 "flood_grammar.y"
{ yyval.sval = val_peek(3).sval + "[" + val_peek(1).sval + "]"; semantics.checkIndex(val_peek(1).sval, yyline);}
break;
case 135:
//#line 313 "flood_grammar.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 136:
//#line 316 "flood_grammar.y"
{ yyval.sval = ""; }
break;
//#line 1390 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
