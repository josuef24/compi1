/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import olc1.proyecto1.services.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ProyectoController {
    private ParserService parser;
    private CommandService commandService;
    private List<String> comandosEncontrados;
    
    public ProyectoController(ParserService parser, CommandService commandService) {
        this.parser = parser;
        this.commandService = commandService;
        this.comandosEncontrados = new ArrayList<>();
    }
    
    public void cargarYCompilar(String archivo) throws Exception {
        // Fase 1: Analizar archivo con JFlex/CUP
        parser.analizarArchivo(archivo);
        
        // Fase 2: Extraer comandos del archivo
        extraerComandos(archivo);
    }
    
    public void ejecutarScriptComandos() {
        System.out.println("Ejecutando comandos encontrados:");
        for (String comando : comandosEncontrados) {
            System.out.println("Ejecutando: " + comando);
            commandService.ejecutarComando(comando);
        }
    }
    
    private void extraerComandos(String archivo) throws Exception {
        comandosEncontrados.clear();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                
                // Detectar comandos
                if (linea.equals("verAutomatas();")) {
                    comandosEncontrados.add("verAutomatas()");
                } else if (linea.matches("desc\\(.+\\);")) {
                    comandosEncontrados.add(linea.substring(0, linea.length()-1));
                } else if (linea.matches("\\w+\\(\".+\"\\);")) {
                    comandosEncontrados.add(linea.substring(0, linea.length()-1));
                }
            }
        }
        
        System.out.println("Comandos encontrados: " + comandosEncontrados.size());
    }
}