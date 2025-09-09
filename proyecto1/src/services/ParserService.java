/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import olc1.proyecto1.domain.repo.AutomataRegistry;
import analizadores.*;
import java.io.FileReader;
import java.io.StringReader;

public class ParserService {
    private AutomataRegistry registry;
    
    public ParserService(AutomataRegistry registry) {
        this.registry = registry;
    }
    
    public void analizarArchivo(String rutaArchivo) throws Exception {
        Lexer lexer = new Lexer(new FileReader(rutaArchivo));
        Parser parser = new Parser(lexer);
        
        System.out.println("Iniciando análisis léxico y sintáctico...");
        parser.parse();
        System.out.println("Análisis completado exitosamente.");
    }
    
    public void analizarTexto(String texto) throws Exception {
        Lexer lexer = new Lexer(new StringReader(texto));
        Parser parser = new Parser(lexer);
        
        parser.parse();
    }
}