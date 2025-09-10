/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import analizadores.Lexer;
import java.nio.file.*; 
import java.io.*; 
import analizadores.*; 
import java_cup.runtime.*;
import javax.swing.text.html.parser.Parser;

public class Cli {
  public static void main(String[] args) throws Exception {
    String path = (args.length>0)? args[0] : "entrada.atm";
    String input = Files.readString(Path.of(path));
    
    System.out.println("=== CONTENIDO DEL ARCHIVO ===");
    System.out.println(input);
    System.out.println("=== FIN CONTENIDO ===\n");
    
    System.out.println("=== ANÁLISIS LÉXICO ===");
    Lexer debugLexer = new Lexer(new java.io.StringReader(input));
    Symbol token;
    do {
      token = debugLexer.next_token();
      System.out.println("Token: " + token.sym + " (" + sym.terminalNames[token.sym] + ") = " + token.value);
    } while(token.sym != sym.EOF);
    System.out.println("=== FIN ANÁLISIS LÉXICO ===\n");
    
    System.out.println("=== ANÁLISIS SINTÁCTICO ===");
    Lexer lx = new Lexer(new java.io.StringReader(input));
    Parser p = new Parser(lx);
    p.parse(); // define AFD/AP y ejecuta sentencias
  }
}