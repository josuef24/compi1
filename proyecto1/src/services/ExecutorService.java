/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import olc1.proyecto1.domain.repo.AutomataRegistry;
import modelos.Automata;

public class ExecutorService {
    private AutomataRegistry registry;
    
    public ExecutorService(AutomataRegistry registry) {
        this.registry = registry;
    }
    
    public void listarAutomatas() {
        System.out.println("=== LISTA DE AUTÓMATAS ===");
        var nombres = registry.obtenerNombresAutomatas();
        if (nombres.isEmpty()) {
            System.out.println("No hay autómatas definidos.");
        } else {
            for (String nombre : nombres) {
                Automata automata = registry.obtenerAutomata(nombre);
                System.out.println(nombre + " - " + automata.getTipo());
            }
        }
    }
    
    public void describir(String nombre) {
        Automata automata = registry.obtenerAutomata(nombre);
        if (automata == null) {
            System.err.println("Autómata '" + nombre + "' no encontrado.");
            return;
        }
        
        System.out.println("=== DESCRIPCIÓN DEL AUTÓMATA ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Tipo: " + automata.getTipo());
        System.out.println("Estados: " + automata.getEstados());
        System.out.println("Alfabeto: " + automata.getAlfabeto());
        System.out.println("Estado inicial: " + automata.getEstadoInicial());
        System.out.println("Estados de aceptación: " + automata.getEstadosAceptacion());
    }
    
    public void validarCadena(String nombreAutomata, String cadena) {
        Automata automata = registry.obtenerAutomata(nombreAutomata);
        if (automata == null) {
            System.err.println("Autómata '" + nombreAutomata + "' no encontrado.");
            return;
        }
        
        boolean esValida = automata.validar(cadena);
        String resultado = esValida ? "Cadena Válida" : "Cadena Inválida";
        System.out.println(nombreAutomata + " " + cadena + " " + resultado);
    }
}