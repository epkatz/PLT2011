# makefile to generate the FLOOD compiler frontend

JFLEX      = jflex 
JAVAC      = javac
JAR        = jar -cf
DELETE = rm -rf
BYACCJ = byaccj -J

# targets:

all: flood_frontend.jar

run: flood_frontend.jar
	java Parser

build: clean flood_frontend.jar

clean:
	$(DELETE) *.*~ *.class
	$(DELETE) ParserVal.java
	$(DELETE) Parser.java
	$(DELETE) Yylex.java
	$(DELETE) flood_front.jar
	$(DELETE) FloodProgram.java

flood_frontend.jar: Parser.class
	$(JAR) flood_front.jar *.class

Parser.class: Yylex.java Parser.java
	$(JAVAC) Parser.java

Yylex.java: flood_lex.flex
	$(JFLEX) flood_lex.flex

Parser.java: flood_grammar.y
	$(BYACCJ) flood_grammar.y
