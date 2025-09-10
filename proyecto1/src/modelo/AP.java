/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.*;

public class AP implements Automata {
  public static class Tr {
    public final String q, a, pop, qn, push;
    public Tr(String q,String a,String pop,String qn,String push){
      this.q=q;this.a=a;this.pop=pop;this.qn=qn;this.push=push;
    }
    public String toString() {
      return q + " (" + a + ") -> (" + pop + "), " + qn + " : (" + push + ")";
    }
  }
  
  private final String nombre, q0; 
  private final Set<String> Q,Sigma,Gamma,F; 
  private final List<Tr> trans;
  
  public AP(String nombre, Set<String> Q, Set<String> Sigma, Set<String> Gamma, String q0, Set<String> F, List<Tr> trans){
    this.nombre=nombre; this.Q=Q; this.Sigma=Sigma; this.Gamma=Gamma; this.q0=q0; this.F=F; this.trans=trans;
  }
  
  public String getNombre(){return nombre;}
  public String getTipo(){return "Autómata de Pila";}
  
  public String descripcion(){ 
    StringBuilder sb = new StringBuilder();
    sb.append("Nombre: ").append(nombre).append("\n");
    sb.append("Tipo: ").append(getTipo()).append("\n");
    sb.append("Estados: ").append(String.join(", ", Q)).append("\n");
    sb.append("Alfabeto: ").append(String.join(", ", Sigma)).append("\n");
    sb.append("Símbolos de Pila: ").append(String.join(", ", Gamma)).append("\n");
    sb.append("Estado Inicial: ").append(q0).append("\n");
    sb.append("Estados de aceptación: ").append(String.join(", ", F)).append("\n");
    sb.append("Transiciones: ");
    for(int i = 0; i < trans.size(); i++) {
      if(i > 0) sb.append(" | ");
      sb.append(trans.get(i).toString());
    }
    return sb.toString();
  }
  
  public boolean validar(String s, StringBuilder pasos){
    // Implementación básica de simulación de autómata de pila
    Stack<String> pila = new Stack<>();
    String estadoActual = q0;
    
    pasos.append("Estado inicial: ").append(estadoActual).append(", Pila: [vacía]\n");
    
    for(int i = 0; i < s.length(); i++) {
      char simbolo = s.charAt(i);
      String a = String.valueOf(simbolo);
      
      // Buscar transición aplicable
      Tr transicionUsada = null;
      for(Tr t : trans) {
        if(t.q.equals(estadoActual) && t.a.equals(a)) {
          // Verificar si se puede hacer pop
          if(t.pop.equals("$") || (!pila.isEmpty() && pila.peek().equals(t.pop))) {
            transicionUsada = t;
            break;
          }
        }
      }
      
      if(transicionUsada == null) {
        pasos.append("Paso ").append(i+1).append(": No hay transición válida para '").append(a).append("' desde ").append(estadoActual).append("\n");
        pasos.append("RESULTADO: RECHAZADA");
        return false;
      }
      
      // Ejecutar transición
      pasos.append("Paso ").append(i+1).append(": ").append(transicionUsada.toString()).append("\n");
      
      // Pop de la pila
      if(!transicionUsada.pop.equals("$") && !pila.isEmpty()) {
        pila.pop();
      }
      
      // Push a la pila
      if(!transicionUsada.push.equals("$")) {
        pila.push(transicionUsada.push);
      }
      
      estadoActual = transicionUsada.qn;
      pasos.append("  Estado: ").append(estadoActual).append(", Pila: ").append(pila).append("\n");
    }
    
    boolean acepta = F.contains(estadoActual);
    pasos.append("Estado final: ").append(estadoActual).append("\n");
    pasos.append("¿Es estado de aceptación? ").append(acepta ? "SÍ" : "NO").append("\n");
    pasos.append("RESULTADO: ").append(acepta ? "ACEPTADA" : "RECHAZADA");
    
    return acepta;
  }
}