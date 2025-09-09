/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import olc1.proyecto1.domain.repo.AutomataRegistry;

public class CommandService {
    private AutomataRegistry registry;
    private ExecutorService executor;
    private ReportService reports;
    
    public CommandService(AutomataRegistry registry, ExecutorService executor, ReportService reports) {
        this.registry = registry;
        this.executor = executor;
        this.reports = reports;
    }
    
    public void ejecutarComando(String comando) {
        try {
            if (comando.trim().equals("verAutomatas()")) {
                executor.listarAutomatas();
            } else if (comando.startsWith("desc(") && comando.endsWith(")")) {
                String nombre = comando.substring(5, comando.length() - 1);
                executor.describir(nombre);
            } else if (comando.contains("(\"") && comando.endsWith("\")")) {
                // Comando de validación: AutomataName("cadena")
                int inicio = comando.indexOf('(');
                int fin = comando.lastIndexOf(')');
                String nombreAutomata = comando.substring(0, inicio);
                String cadena = comando.substring(inicio + 2, fin - 1); // quitar ("...")
                executor.validarCadena(nombreAutomata, cadena);
            }
        } catch (Exception e) {
            System.err.println("Error ejecutando comando: " + comando);
            registry.agregarError("Error en comando: " + comando);
        }
    }
}