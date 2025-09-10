/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import java.nio.file.*; import java.io.*; 
import analizadores.*; import java_cup.runtime.*;

public class Cli {
  public static void main(String[] args) throws Exception {
    String path = (args.length>0)? args[0] : "entrada.atm";
    String input = Files.readString(Path.of(path));
    Lexer lx = new Lexer(new java.io.StringReader(input));
    Parser p = new Parser(lx);
    p.parse(); // define AFD/AP y ejecuta sentencias
  }
}
