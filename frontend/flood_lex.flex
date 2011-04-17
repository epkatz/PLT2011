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

COMMENTS = "/*"(.*|\n*|\r*|\r\n*)*"*/"
ID    = [a-zA-Z"_"]([a-zA-Z"_"] | [0-9])*
NL    = \n | \r | \r\n
WP    = " "
INT   = [0-9]+
FLT   = [0-9]+ ("." [0-9]+)?
HT    = \t

%%

/* Comments (highest precedence) */
{COMMENTS}   { yyparser.yycolumn+= yytext().length();          }

/* Keywords */
defineLeague     { yyparser.yycolumn += yytext().length(); return Parser.defineLeague;  }
defineFunctions  { yyparser.yycolumn += yytext().length(); return Parser.defineFunctions;  }
leagueName        { yyparser.yycolumn += yytext().length(); return Parser.leagueName;     }
maxUser           { yyparser.yycolumn += yytext().length(); return Parser.maxUser;        }
minUser           { yyparser.yycolumn += yytext().length(); return Parser.minUser;        }
set               { yyparser.yycolumn += yytext().length(); return Parser.set;            }
add               { yyparser.yycolumn += yytext().length(); return Parser.add;            }
Action            { yyparser.yycolumn += yytext().length(); return Parser.Action;         }
User              { yyparser.yycolumn += yytext().length(); return Parser.User;           }
Void              { yyparser.yycolumn += yytext().length(); return Parser.Void;     }
Int               { yyparser.yycolumn += yytext().length(); return Parser.Int;      }
bool              { yyparser.yycolumn += yytext().length(); return Parser.bool;     }
str               { yyparser.yycolumn += yytext().length(); return Parser.str;      }
flt               { yyparser.yycolumn += yytext().length(); return Parser.flt;      }
Return            { yyparser.yycolumn += yytext().length(); return Parser.Return;      }
If                  { yyparser.yycolumn += yytext().length(); return Parser.If;             }
Else                { yyparser.yycolumn += yytext().length(); return Parser.Else;           }
While                { yyparser.yycolumn += yytext().length(); return Parser.While;           }

/* Identifier */
{ID}              {
                    yyparser.yycolumn += yytext().length();
                    yyparser.yylval = new ParserVal(yytext());
                    return Parser.ID;
                  }

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
                  

[\t]+            { yyparser.yycolumn += yytext().length(); }

"\""[^\"]*"\""    {
                    yyparser.yycolumn += yytext().length();
                    yyparser.yylval = new ParserVal(yytext());
                    return Parser.STRING_CONST;   
                  }

,                             { yyparser.yycolumn++; return Parser.COMMA;       }

"+"                           { yyparser.yycolumn++; return Parser.PLUS;        }

"{"                           { yyparser.yycolumn++; return Parser.OPEN_CURLY;  }

"}"                           { yyparser.yycolumn++; return Parser.CLOSE_CURLY; }

"("                           { yyparser.yycolumn++;return Parser.OPEN_PARAN;         }

")"                           { yyparser.yycolumn++;return Parser.CLOSE_PARAN;        }

"=="                { yyparser.yycolumn++; return Parser.ISEQUAL;        }

"="                 { yyparser.yycolumn++; return Parser.EQUAL;          }

"!="                { yyparser.yycolumn++; return Parser.NOTEQUAL;       }

"<="                { yyparser.yycolumn++; return Parser.LESSEQUAL;      }

">="                { yyparser.yycolumn++; return Parser.GREATEQUAL;     }

">"                 { yyparser.yycolumn++; return Parser.GREAT;          }

"<"                 { yyparser.yycolumn++;return Parser.LESS;            }

"+"                 { yyparser.yycolumn++; return Parser.PLUS;           }

"-"                 { yyparser.yycolumn++; return Parser.MINUS;          }

"*"                 { yyparser.yycolumn++; return Parser.MULT;            }

"/"                 { yyparser.yycolumn++; return Parser.DIV;            }

[;]+                { 
                        yyparser.yycolumn += yytext().length(); 
                        return Parser.SEMICOLON;      
                    }

/* Error Fallback */
[^]               {
                    System.err.println("Error: unexpected character '" + yytext() + "'");
                    return -1; 
                  }
