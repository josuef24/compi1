/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.List;
import java.util.Set;

public abstract class Automata {
    protected String nombre;
    protected Set<String> estados;
    protected Set<String> alfabeto;
    protected String estadoInicial;
    protected Set<String> estadosAceptacion;
    
    public Automata(String nombre, Set<String> estados, Set<String> alfabeto, 
                   String estadoInicial, Set<String> estadosAceptacion) {
        this.nombre = nombre;
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.estadoInicial = estadoInicial;
        this.estadosAceptacion = estadosAceptacion;
    }
    
    public abstract String getTipo();
    public abstract boolean validar(String cadena);
    
    // Getters
    public String getNombre() { return nombre; }
    public Set<String> getEstados() { return estados; }
    public Set<String> getAlfabeto() { return alfabeto; }
    public String getEstadoInicial() { return estadoInicial; }
    public Set<String> getEstadosAceptacion() { return estadosAceptacion; }
}