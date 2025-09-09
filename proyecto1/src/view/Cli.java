/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import olc1.proyecto1.controller.ProyectoController;
import olc1.proyecto1.services.*;
import olc1.proyecto1.domain.repo.*;

public class Cli {
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("             AUTOMATALAB - OLC1 PROYECTO 1");
        System.out.println("=".repeat(60));
        
        try {
            // Inicializar componentes
            var registry = new AutomataRegistry();
            var exec = new ExecutorService(registry);
            var reports = new ReportService(registry);
            var cmd = new CommandService(registry, exec, reports);
            var parser = new ParserService(registry);
            var ctrl = new ProyectoController(parser, cmd);

            // Determinar archivo de entrada
            String archivo = (args.length > 0) ? args[0] : "entrada.atm";
            System.out.println("Archivo de entrada: " + archivo);

            // Fase 1: Análisis léxico y sintáctico
            System.out.println("\n--- FASE 1: ANÁLISIS LÉXICO Y SINTÁCTICO ---");
            ctrl.cargarYCompilar(archivo);

            // Fase 2: Ejecución de comandos
            System.out.println("\n--- FASE 2: EJECUCIÓN DE COMANDOS ---");
            ctrl.ejecutarScriptComandos();

            System.out.println("\n" + "=".repeat(60));
            System.out.println("             EJECUCIÓN COMPLETADA");
            System.out.println("=".repeat(60));
            
        } catch (Exception e) {
            System.err.println("Error fatal en la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}