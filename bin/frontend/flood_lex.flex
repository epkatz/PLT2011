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

ID      = [a-zA-Z"_"]([a-zA-Z"_"] | [0-9])*
INT     = [0-9]+
FLT     = [0-9]+ ("." [0-9]+)?
NL      = \n | \r | \r\n
HT      = \t

%%

/* Keywords */
Action    { yyparser.yycolumn += yytext().length(); return Parser.Action; }
Class     { yyparser.yycolumn += yytext().length(); return Parser.Class;  }
get       { yyparser.yycolumn += yytext().length(); return Parser.get;      }
else                { yyparser.yycolumn += yytext().length(); return Parser.else;           }
if                  { yyparser.yycolumn += yytext().length(); return Parser.if;             }
in                  { yyparser.yycolumn += yytext().length(); return Parser.in;             }
new       { yyparser.yycolumn += yytext().length(); return Parser.new;            }
public        { yyparser.yycolumn += yytext().length(); return Parser.public;         }
private       { yyparser.yycolumn += yytext().length(); return Parser.private;        }
for               { yyparser.yycolumn += yytext().length(); return Parser.for;            }
void        { yyparser.yycolumn += yytext().length(); return Parser.void;           }
set       { yyparser.yycolumn += yytext().length(); return Parser.set;            }
setget        { yyparser.yycolumn += yytext().length(); return Parser.setget;         }
while       { yyparser.yycolumn += yytext().length(); return Parser.while;          }
int       { yyparser.yycolumn += yytext().length(); return Parser.int;            }
str             { yyparser.yycolumn += yytext().length(); return Parser.str;            }
flt       { yyparser.yycolumn += yytext().length(); return Parser.flt;            }
bool        { yyparser.yycolumn += yytext().length(); return Parser.bool;           }
is        { yyparser.yycolumn += yytext().length(); return Parser.is;             }
return        { yyparser.yycolumn += yytext().length(); return Parser.return;         }
list        { yyparser.yycolumn += yytext().length(); return Parser.list;           }
true        { yyparser.yycolumn += yytext().length(); return Parser.true;           }
false       { yyparser.yycolumn += yytext().length(); return Parser.false;          }
Player        { yyparser.yycolumn += yytext().length(); return Parser.Player;         }
User              { yyparser.yycolumn += yytext().length(); return Parser.User;           }
League        { yyparser.yycolumn += yytext().length(); return Parser.League;         }
Draft       { yyparser.yycolumn += yytext().length(); return Parser.Draft;          }
bool        { yyparser.yycolumn += yytext().length(); return Parser.bool;           }

/* newline */
{NL}                { 
                        yyparser.yycolumn = 0; 
                        yyparser.yyline++; 
                    }

/* Identifier */
{ID}                { 
                        yyparser.yycolumn += yytext().length();                
                        yyparser.yylval = new ParserVal(yytext()); 
                        return Parser.ID; 
                    }

/* float */
{FLT}            {
                        yyparser.yycolumn += yytext().length();
                  yyparser.yylval = new ParserVal(Double.parseDouble(yytext()));
                        return Parser.FLT;                                        
              }
/*integer*/
{INT}   {
      yyparser.yycolumn += yytext().length();
          yyparser.yylval = new ParseVal(Integer.parseInt(yytext()));
        return Parser.INT;
        }

\b                  { System.err.println("Sorry, backspace doesn't work"); }

[ \t]+              { yyparser.yycolumn += yytext().length();            }

[;]+                { 
                        yyparser.yycolumn += yytext().length(); 
                        return Parser.SEMICOLON;      
                    }

,                   { yyparser.yycolumn++; return Parser.COMMA;          }

:                   { yyparser.yycolumn++; return Parser.COLON;          }

"\""[^\"]*"\""      { 
                        yyparser.yycolumn += yytext().length();                
                        yyparser.yylval = new ParserVal(yytext()); 
                        return Parser.STRING_CONST;   
                    }

"\\\*[.]*\*\\"      { yyparser.yycolumn+= yytext().length();             }

"{"                 { yyparser.yycolumn++; return Parser.OPEN_PARAN;     }

"}"                 { yyparser.yycolumn++; return Parser.CLOSE_PARAN;    }

"+"                 { yyparser.yycolumn++; return Parser.PLUS;           }

"-"                 { yyparser.yycolumn++; return Parser.MINUS;          }

"*"                 { yyparser.yycolumn++; return Parser.MUL;            }

"/"                 { yyparser.yycolumn++; return Parser.DIV;            }

"%"                 { yyparser.yycolumn++; return Parser.MODULUS;        }

"=="                { yyparser.yycolumn++; return Parser.ISEQUAL;        }

"="                 { yyparser.yycolumn++; return Parser.EQUAL;          }

"!="                { yyparser.yycolumn++; return Parser.NOTEQUAL;       }

"<="                { yyparser.yycolumn++; return Parser.LESSEQUAL;      }

">="                { yyparser.yycolumn++; return Parser.GREATEQUAL;     }

">"                 { yyparser.yycolumn++; return Parser.GREAT;          }

"<"                 { yyparser.yycolumn++;return Parser.LESS;            }

"("                 { yyparser.yycolumn++;return Parser.OPEN;            }

")"                 { yyparser.yycolumn++;return Parser.CLOSE;           }



/* error fallback */
[^]                 { 
                        System.err.println("Error: unexpected character '" + yytext() + "'"); 
                        return -1; 
                    }
