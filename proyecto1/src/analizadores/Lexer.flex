package analizadores;
import java_cup.runtime.Symbol;

%%

%class Lexer
%public
%line
%column
%cup

%{
  private Symbol sym(int id, Object val) { return new Symbol(id, yyline+1, yycolumn+1, val); }
  private Symbol sym(int id)             { return new Symbol(id, yyline+1, yycolumn+1); }
%}

%state IN_AFD, IN_AP

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]
Letter = [a-zA-Z]
Digit = [0-9]
Identifier = {Letter}({Letter}|{Digit}|"_")*
String = \"([^\\\"]|\\.)*\"

/* Comentarios */
SingleLineComment = "//" [^\r\n]*
MultiLineComment = "/*" ~"*/"

%%

/* Espacios en blanco */
{WhiteSpace}                    { /* ignorar */ }

/* Comentarios */
{SingleLineComment}             { /* comentario una línea */ }
{MultiLineComment}              { /* comentario multilínea */ }

/* Palabras clave */
"verAutomatas"                  { return sym(sym.VER_AUTOMATAS, yytext()); }
"desc"                          { return sym(sym.DESC, yytext()); }

/* Símbolos */
"("                             { return sym(sym.LPAREN); }
")"                             { return sym(sym.RPAREN); }
","                             { return sym(sym.COMMA); }
";"                             { return sym(sym.SEMI); }

/* Estados contextuales para AFD y AP */
"<AFD"                          { yybegin(IN_AFD); }
"<AP"                           { yybegin(IN_AP); }

<IN_AFD> {
  "</AFD>"                      { yybegin(YYINITIAL); }
  [^<\r\n]+                     { /* ignorar contenido AFD */ }
}

<IN_AP> {
  "</AP>"                       { yybegin(YYINITIAL); }
  [^<\r\n]+                     { /* ignorar contenido AP */ }
}

/* Identificadores y strings */
{Identifier}                    { return sym(sym.ID, yytext()); }
{String}                        { return sym(sym.STRING, yytext().substring(1, yytext().length()-1)); }

/* Cualquier otro caracter */
.                               { System.err.println("Error léxico: '"+yytext()+"' en línea "+(yyline+1)+", col "+(yycolumn+1)); }