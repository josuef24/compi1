/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.*;

public class AFD extends Automata {
    private Map<String, Map<String, String>> transiciones;
    
    public AFD(String nombre, Set<String> estados, Set<String> alfabeto, 
               String estadoInicial, Set<String> estadosAceptacion) {
        super(nombre, estados, alfabeto, estadoInicial, estadosAceptacion);
        this.transiciones = new HashMap<>();
        inicializarTransiciones();
    }
    
    // Constructor por defecto para crear AFDs vacíos
    public AFD() {
        super("", new HashSet<>(), new HashSet<>(), "", new HashSet<>());
        this.transiciones = new HashMap<>();
    }
    
    private void inicializarTransiciones() {
        for (String estado : estados) {
            transiciones.put(estado, new HashMap<>());
        }
    }
    
    public void agregarTransicion(String origen, String simbolo, String destino) {
        if (!estados.contains(origen) || !estados.contains(destino)) {
            throw new IllegalArgumentException("Estados no válidos en transición");
        }
        if (!alfabeto.contains(simbolo)) {
            throw new IllegalArgumentException("Símbolo no válido en alfabeto");
        }
        
        transiciones.get(origen).put(simbolo, destino);
    }
    
    @Override
    public String getTipo() {
        return "Autómata Finito Determinista";
    }
    
    @Override
    public boolean validar(String cadena) {
        if (estadoInicial == null || estadoInicial.isEmpty()) {
            return false;
        }
        
        String estadoActual = estadoInicial;
        
        for (char c : cadena.toCharArray()) {
            String simbolo = String.valueOf(c);
            
            if (!alfabeto.contains(simbolo)) {
                return false; // Símbolo no en alfabeto
            }
            
            Map<String, String> transicionesEstado = transiciones.get(estadoActual);
            if (transicionesEstado == null || !transicionesEstado.containsKey(simbolo)) {
                return false; // No hay transición
            }
            
            estadoActual = transicionesEstado.get(simbolo);
        }
        
        return estadosAceptacion.contains(estadoActual);
    }
    
    public Map<String, Map<String, String>> getTransiciones() {
        return transiciones;
    }
}