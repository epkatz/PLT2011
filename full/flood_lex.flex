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
NL    = \n | \r | \r\n
WP    = " "
INT   = [0-9]+ /*| "-"[0-9]+ */
FLT   = [0-9]+ ("." [0-9]+)? /*| "-"[0-9]+ ("." [0-9]+)?*/
HT    = \t
COMMENTS = "/*" [^*] ~"*/" | "/*" "*" + "/"

%%

/* Keywords */
DefineLeague      { yyparser.yycolumn += yytext().length(); return Parser.DefineLeague;     }
DefineFunctions   { yyparser.yycolumn += yytext().length(); return Parser.DefineFunctions;  }
LeagueName        { yyparser.yycolumn += yytext().length(); return Parser.LeagueName;       }
MaxUser           { yyparser.yycolumn += yytext().length(); return Parser.MaxUser;          }
MinUser           { yyparser.yycolumn += yytext().length(); return Parser.MinUser;          }
MaxTeamSize       { yyparser.yycolumn += yytext().length(); return Parser.MaxTeamSize;      }
MinTeamSize       { yyparser.yycolumn += yytext().length(); return Parser.MinTeamSize;      }
Set               { yyparser.yycolumn += yytext().length(); return Parser.Set;              }
Add               { yyparser.yycolumn += yytext().length(); return Parser.Add;              }
Action            { yyparser.yycolumn += yytext().length(); return Parser.Action;           }
User              { yyparser.yycolumn += yytext().length(); return Parser.User;             }
Void              { yyparser.yycolumn += yytext().length(); return Parser.Void;             }
Int               { yyparser.yycolumn += yytext().length(); return Parser.Int;              }
Bool              { yyparser.yycolumn += yytext().length(); return Parser.Bool;             }
Str               { yyparser.yycolumn += yytext().length(); return Parser.Str;              }
Flt               { yyparser.yycolumn += yytext().length(); return Parser.Flt;              }
Return            { yyparser.yycolumn += yytext().length(); return Parser.Return;           }
If                { yyparser.yycolumn += yytext().length(); return Parser.If;               }
Else              { yyparser.yycolumn += yytext().length(); return Parser.Else;             }
While             { yyparser.yycolumn += yytext().length(); return Parser.While;            }
Player            { yyparser.yycolumn += yytext().length(); return Parser.Player;           }
True              { yyparser.yycolumn += yytext().length(); return Parser.True;             }
False             { yyparser.yycolumn += yytext().length(); return Parser.False;            }
RemovePlayer      { yyparser.yycolumn += yytext().length(); return Parser.RemovePlayer;     }
AddPlayer         { yyparser.yycolumn += yytext().length(); return Parser.AddPlayer;        }
ArrayLength       { yyparser.yycolumn += yytext().length(); return Parser.ArrayLength;      }
Alert		  { yyparser.yycolumn += yytext().length(); return Parser.Alert;            }
Error		  { yyparser.yycolumn += yytext().length(); return Parser.Error;            }
GetUserName       { yyparser.yycolumn += yytext().length(); return Parser.GetUserName;      }
GetNumPlayers     { yyparser.yycolumn += yytext().length(); return Parser.GetNumPlayers;    }
GetPlayerName     { yyparser.yycolumn += yytext().length(); return Parser.GetPlayerName;    }
GetPlayerPosition { yyparser.yycolumn += yytext().length(); return Parser.GetPlayerPosition;}
GetPlayerPoints   { yyparser.yycolumn += yytext().length(); return Parser.GetPlayerPoints;  }



/* Comments */
{COMMENTS}        { /* ignore */ }

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


[\t]+             { yyparser.yycolumn += yytext().length(); }

"\""[^\"]*"\""    {
                    yyparser.yycolumn += yytext().length();
                    yyparser.yylval = new ParserVal(yytext());
                    return Parser.STRING_CONST;   
                  }

,                 { yyparser.yycolumn++; return Parser.COMMA;          }

"."               { yyparser.yycolumn++; return Parser.DOT;            }

"+"               { yyparser.yycolumn++; return Parser.PLUS;           }

"{"               { yyparser.yycolumn++; return Parser.OPEN_CURLY;     }

"}"               { yyparser.yycolumn++; return Parser.CLOSE_CURLY;    }

"("               { yyparser.yycolumn++;return Parser.OPEN_PARAN;      }

")"               { yyparser.yycolumn++;return Parser.CLOSE_PARAN;     }

"=="              { yyparser.yycolumn++; return Parser.ISEQUAL;        }

"="               { yyparser.yycolumn++; return Parser.EQUAL;          }

"!="              { yyparser.yycolumn++; return Parser.NOTEQUAL;       }

"<="              { yyparser.yycolumn++; return Parser.LESSEQUAL;      }

">="              { yyparser.yycolumn++; return Parser.GREATEQUAL;     }

">"               { yyparser.yycolumn++; return Parser.GREAT;          }

"<"               { yyparser.yycolumn++;return Parser.LESS;            }

"+"               { yyparser.yycolumn++; return Parser.PLUS;           }

"-"               { yyparser.yycolumn++; return Parser.MINUS;          }

"*"               { yyparser.yycolumn++; return Parser.MULT;           }

"/"               { yyparser.yycolumn++; return Parser.DIV;            }

"!"               { yyparser.yycolumn++; return Parser.NOT;            }

"&&"              { yyparser.yycolumn++; return Parser.AND;            }

"||"              { yyparser.yycolumn++; return Parser.OR;             }

"%"               { yyparser.yycolumn++; return Parser.MOD;            }

[;]+              {
                    yyparser.yycolumn += yytext().length();
                    return Parser.SEMICOLON;      
                  }
                    
"["               { yyparser.yycolumn++; return Parser.OPEN_SQUARE;    }

"]"               { yyparser.yycolumn++; return Parser.CLOSE_SQUARE;    }

/* Error Fallback */
[^]               {
                    System.err.println("Error: unexpected character '" + yytext() + "'");
                    return -1; 
                  }
