/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.*;

public class AP extends Automata {
    private Set<String> alfabetoPila;
    private List<TransicionAP> transiciones;
    
    public AP(String nombre, Set<String> estados, Set<String> alfabeto, 
              Set<String> alfabetoPila, String estadoInicial, Set<String> estadosAceptacion) {
        super(nombre, estados, alfabeto, estadoInicial, estadosAceptacion);
        this.alfabetoPila = alfabetoPila;
        this.transiciones = new ArrayList<>();
    }
    
    // Constructor por defecto
    public AP() {
        super("", new HashSet<>(), new HashSet<>(), "", new HashSet<>());
        this.alfabetoPila = new HashSet<>();
        this.transiciones = new ArrayList<>();
    }
    
    public void agregarTransicion(String origen, String simboloEntrada, String simboloPila,
                                String destino, String simboloApilar) {
        transiciones.add(new TransicionAP(origen, simboloEntrada, simboloPila, destino, simboloApilar));
    }
    
    @Override
    public String getTipo() {
        return "Autómata de Pila";
    }
    
    @Override
    public boolean validar(String cadena) {
        // Implementación simplificada del autómata de pila
        if (estadoInicial == null || estadoInicial.isEmpty()) {
            return false;
        }
        
        String estadoActual = estadoInicial;
        Stack<String> pila = new Stack<>();
        pila.push("#"); // Símbolo inicial de pila
        
        int i = 0;
        while (i <= cadena.length()) {
            String simboloEntrada = (i < cadena.length()) ? String.valueOf(cadena.charAt(i)) : "$";
            String topePila = pila.isEmpty() ? "$" : pila.peek();
            
            TransicionAP transicionValida = null;
            for (TransicionAP t : transiciones) {
                if (t.origen.equals(estadoActual) && 
                    t.simboloEntrada.equals(simboloEntrada) && 
                    t.simboloPila.equals(topePila)) {
                    transicionValida = t;
                    break;
                }
            }
            
            if (transicionValida == null) {
                // Buscar transiciones lambda (vacías)
                for (TransicionAP t : transiciones) {
                    if (t.origen.equals(estadoActual) && 
                        t.simboloEntrada.equals("$") && 
                        t.simboloPila.equals(topePila)) {
                        transicionValida = t;
                        break;
                    }
                }
            }
            
            if (transicionValida == null) {
                break;
            }
            
            // Aplicar transición
            estadoActual = transicionValida.destino;
            if (!transicionValida.simboloPila.equals("$")) {
                pila.pop();
            }
            if (!transicionValida.simboloApilar.equals("$")) {
                pila.push(transicionValida.simboloApilar);
            }
            
            if (!transicionValida.simboloEntrada.equals("$")) {
                i++;
            }
        }
        
        return estadosAceptacion.contains(estadoActual);
    }
    
    public Set<String> getAlfabetoPila() {
        return alfabetoPila;
    }
    
    public List<TransicionAP> getTransiciones() {
        return transiciones;
    }
    
    // Clase interna para transiciones del AP
    public static class TransicionAP {
        public final String origen;
        public final String simboloEntrada;
        public final String simboloPila;
        public final String destino;
        public final String simboloApilar;
        
        public TransicionAP(String origen, String simboloEntrada, String simboloPila,
                           String destino, String simboloApilar) {
            this.origen = origen;
            this.simboloEntrada = simboloEntrada;
            this.simboloPila = simboloPila;
            this.destino = destino;
            this.simboloApilar = simboloApilar;
        }
        
        @Override
        public String toString() {
            return origen + " (" + simboloEntrada + ") -> (" + simboloPila + "), " 
                   + destino + " : (" + simboloApilar + ")";
        }
    }
}