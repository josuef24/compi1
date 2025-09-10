package analizadores;
import java_cup.runtime.Symbol;

%%
// ----- Config -----
%class Lexer
%public
%unicode
%cup
%line
%column

// ----- Macros -----
BLANKS   = [ \t\r\n]+
ID       = [A-Za-z_][A-Za-z_0-9]*
NUM      = [0-9]+
SYM      = [A-Za-z0-9#]        // símbolos de alfabeto/pila (0,1,a,b,#)
STRING   = \"([^\"\\]|\\.)*\"

// ----- Acciones por defecto -----
%{
  private Symbol s(int id){ return new Symbol(id, yyline+1, yycolumn+1); }
  private Symbol s(int id, Object val){ return new Symbol(id, yyline+1, yycolumn+1, val); }
%}

%%
// --- Ignorar comentarios y espacios ---
"//".*                         { /* ignore */ }
"/*"([^*]|(\*+[^*/]))*\*+\/    { /* ignore */ }
{BLANKS}                       { /* ignore */ }

// --- Palabras reservadas/secciones ---
"Transiciones:"                { return s(sym.TRANSICIONES); }
"Nombre"                       { return s(sym.NOMBRE); }
"AFD"                          { return s(sym.KAFD); }
"AP"                           { return s(sym.KAP); }
"N"                            { return s(sym.N); }
"T"                            { return s(sym.T); }
"I"                            { return s(sym.I); }
"A"                            { return s(sym.A); }
"P"                            { return s(sym.P); }

// --- Funciones del lenguaje ---
"verAutomatas"                 { return s(sym.VER); }
"desc"                         { return s(sym.DESC); }

// --- Símbolos léxicos ---
"("                            { return s(sym.LPAR); }
")"                            { return s(sym.RPAR); }
"{"                            { return s(sym.LBRACE); }
"}"                            { return s(sym.RBRACE); }
","                            { return s(sym.COMMA); }
":"                            { return s(sym.COLON); }
";"                            { return s(sym.SEMI); }
"|"                            { return s(sym.OR); }
"->"                           { return s(sym.ARROW); }
"="                            { return s(sym.EQ); }
"</"                           { return s(sym.LT_SLASH); }
"<"                            { return s(sym.LT); }
">"                            { return s(sym.GT); }
"$"                            { return s(sym.LAMBDA); }   // λ -> $
"#"                            { return s(sym.HASH); }

// --- Identificadores y literales ---
{STRING}                       { return s(sym.STRING, yytext()); }
{ID}                           { return s(sym.ID, yytext()); }
{SYM}                          { return s(sym.SYM, yytext()); }

// --- Cualquier otro char -> error léxico ---
.                              { return s(sym.ERROR, "Carácter no válido: "+yytext()); }
<<EOF>>                        { return s(sym.EOF); }