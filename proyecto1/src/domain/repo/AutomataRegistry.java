package domain.repo;

import modelos.*;
import java.util.*;

public class AutomataRegistry {
    private Map<String, Automata> automatas;
    private List<String> tokens;
    private List<String> errores;
    
    public AutomataRegistry() {
        this.automatas = new HashMap<>();
        this.tokens = new ArrayList<>();
        this.errores = new ArrayList<>();
    }
    
    public void registrarAutomata(String nombre, Automata automata) {
        automatas.put(nombre, automata);
    }
    
    public Automata obtenerAutomata(String nombre) {
        return automatas.get(nombre);
    }
    
    public Set<String> obtenerNombresAutomatas() {
        return automatas.keySet();
    }
    
    public void agregarToken(String token) {
        tokens.add(token);
    }
    
    public void agregarError(String error) {
        errores.add(error);
    }
    
    public List<String> obtenerTokens() {
        return new ArrayList<>(tokens);
    }
    
    public List<String> obtenerErrores() {
        return new ArrayList<>(errores);
    }
    
    public void limpiar() {
        automatas.clear();
        tokens.clear();
        errores.clear();
    }
}