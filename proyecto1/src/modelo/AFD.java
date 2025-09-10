/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.*;

public class AFD implements Automata {
    
  public static class Tr { 
    public final String q, a, qn; 
    public Tr(String q,String a,String qn){this.q=q;this.a=a;this.qn=qn;} 
    public String toString() { return q + " -> " + a + ", " + qn; }
  }
  
  private final String nombre, q0; 
  private final Set<String> Q, Sigma, F;
  private final Map<String, Map<String,String>> delta = new HashMap<>();
  private final List<Tr> transiciones;
  
  public AFD(String nombre, Set<String> Q, Set<String> Sigma, String q0, Set<String> F, java.util.List<Tr> trans){
    this.nombre=nombre; this.Q=Q; this.Sigma=Sigma; this.q0=q0; this.F=F;
    this.transiciones = trans;
    
    for(Tr t: trans){
      delta.computeIfAbsent(t.q, k->new HashMap<>());
      if(delta.get(t.q).containsKey(t.a)) 
        throw new RuntimeException("AFD inválido: transición duplicada "+t.q+" con "+t.a);
      delta.get(t.q).put(t.a, t.qn);
    }
  }
  
  public String getNombre(){return nombre;}
  public String getTipo(){return "Autómata Finito Determinista";}
  
  public String descripcion(){ 
    StringBuilder sb = new StringBuilder();
    sb.append("Nombre: ").append(nombre).append("\n");
    sb.append("Tipo: ").append(getTipo()).append("\n");
    sb.append("Estados: ").append(String.join(", ", Q)).append("\n");
    sb.append("Alfabeto: ").append(String.join(", ", Sigma)).append("\n");
    sb.append("Estado Inicial: ").append(q0).append("\n");
    sb.append("Estados de aceptación: ").append(String.join(", ", F)).append("\n");
    sb.append("Transiciones: ");
    for(int i = 0; i < transiciones.size(); i++) {
      if(i > 0) sb.append(" | ");
      sb.append(transiciones.get(i).toString());
    }
    return sb.toString();
  }
  
  public boolean validar(String s, StringBuilder pasos){
    String q = q0; 
    pasos.append("Estado inicial: ").append(q).append("\n");
    
    for(int i = 0; i < s.length(); i++){
      char c = s.charAt(i);
      String a = String.valueOf(c);
      String qn = delta.getOrDefault(q, Map.of()).get(a);
      
      pasos.append("Paso ").append(i+1).append(": ");
      pasos.append(q).append(" --").append(a).append("--> ");
      
      if(qn == null){ 
        pasos.append("∅ (No hay transición)\n");
        pasos.append("RESULTADO: RECHAZADA");
        return false; 
      }
      
      pasos.append(qn).append("\n");
      q = qn;
    }
    
    boolean acepta = F.contains(q);
    pasos.append("Estado final: ").append(q).append("\n");
    pasos.append("¿Es estado de aceptación? ").append(acepta ? "SÍ" : "NO").append("\n");
    pasos.append("RESULTADO: ").append(acepta ? "ACEPTADA" : "RECHAZADA");
    
    return acepta;
  }
}