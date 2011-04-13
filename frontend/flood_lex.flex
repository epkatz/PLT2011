/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*
* FLOOD
* Lexical Analyzer
*
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%%

%byaccj

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser)
  {
    this(r);
    this.yyparser = yyparser;
  }
%}

ID    = [a-zA-Z"_"]([a-zA-Z"_"] | [0-9])*
INT   = [0-9]+
FLT   = [0-9]+ ("." [0-9]+)?
NL    = \n | \r | \r\n
HT    = \t
WP    = " "

%%

/* Keywords */
leagueName        { yyparser.yycolumn += yytext().length(); return Parser.leagueName;   }
maxUser           { yyparser.yycolumn += yytext().length(); return Parser.maxUser;   }
minUser           { yyparser.yycolumn += yytext().length(); return Parser.minUser;   }
set               { yyparser.yycolumn += yytext().length(); return Parser.set;          }
define_league     { yyparser.yycolumn += yytext().length(); return Parser.define_league;   }
add               { yyparser.yycolumn += yytext().length(); return Parser.add;          }
User              { yyparser.yycolumn += yytext().length(); return Parser.User;   }
Action              { yyparser.yycolumn += yytext().length(); return Parser.Action;   }

/* Newline */
{NL}              {
                    yyparser.yycolumn = 0;
                    yyparser.yyline++;
                  }

{WP}              {
                    yyparser.yycolumn++;
                  }

/* Integer */
{INT}             {
                    yyparser.yycolumn += yytext().length();
                    yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
                    return Parser.INT;
                  }

/* Float */
{FLT}             {
                    yyparser.yycolumn += yytext().length();
                    yyparser.yylval = new ParserVal(Double.parseDouble(yytext()));
                    return Parser.FLT;
                  }

"\""[^\"]*"\""    {
                    yyparser.yycolumn += yytext().length();
                    yyparser.yylval = new ParserVal(yytext());
                    return Parser.STRING_CONST;   
                  }

"/*"(.*|\n*|\r*|\r\n*)*"*/"     { yyparser.yycolumn+= yytext().length();          }

,                 { yyparser.yycolumn++; return Parser.COMMA; }

"+"               { yyparser.yycolumn++; return Parser.PLUS;        }

"{"               { yyparser.yycolumn++; return Parser.OPEN_PARAN;  }

"}"               { yyparser.yycolumn++; return Parser.CLOSE_PARAN; }

"("               { yyparser.yycolumn++;return Parser.OPEN;         }

")"               { yyparser.yycolumn++;return Parser.CLOSE;        }

/* Error Fallback */
[^]               {
                    System.err.println("Error: unexpected character '" + yytext() + "'");
                    return -1; 
                  }
