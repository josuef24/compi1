/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.*;

public class AFD implements Automata {
    
  public static class Tr { public final String q, a, qn; public Tr(String q,String a,String qn){this.q=q;this.a=a;this.qn=qn;} }
  private final String nombre, q0; private final Set<String> Q, Sigma, F;
  private final Map<String, Map<String,String>> delta = new HashMap<>();
  public AFD(String nombre, Set<String> Q, Set<String> Sigma, String q0, Set<String> F, java.util.List<Tr> trans){
    this.nombre=nombre; this.Q=Q; this.Sigma=Sigma; this.q0=q0; this.F=F;
    for(Tr t: trans){
      delta.computeIfAbsent(t.q, k->new HashMap<>());
      if(delta.get(t.q).containsKey(t.a)) throw new RuntimeException("AFD inválido: transición duplicada "+t.q+" con "+t.a);
      delta.get(t.q).put(t.a, t.qn);
    }
    
  }
  
  public String getNombre(){return nombre;}
  public String getTipo(){return "Autómata Finito Determinista";}
  public String descripcion(){ return "Nombre: "+nombre+"\nEstados: "+Q+"\nAlfabeto: "+Sigma+"\nInicial: "+q0+"\nAceptación: "+F+"\nTransiciones: "+delta; }
  
  public boolean validar(String s, StringBuilder pasos){
    String q= q0; pasos.append("q0=").append(q).append("\n");
    for(char c: s.toCharArray()){
      String a= String.valueOf(c);
      String qn = delta.getOrDefault(q, Map.of()).get(a);
      pasos.append(q).append(" --").append(a).append("--> ");
      if(qn==null){ pasos.append("∅\n"); return false; }
      pasos.append(qn).append("\n");
      q = qn;
    }
    boolean ok = F.contains(q);
    pasos.append("qf=").append(q).append("  ").append(ok? "ACCEPT":"REJECT");
    return ok;
  }
}
