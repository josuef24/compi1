/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.*;

public class AP implements Automata {
  public static class Tr {
    public final String q, a, pop, qn, push;
    public Tr(String q,String a,String pop,String qn,String push){this.q=q;this.a=a;this.pop=pop;this.qn=qn;this.push=push;}
  }
  private final String nombre; private final Set<String> Q,Sigma,Gamma,F; private final String q0;
  private final List<Tr> trans;
  public AP(String nombre, Set<String> Q, Set<String> Sigma, Set<String> Gamma, String q0, Set<String> F, List<Tr> trans){
    this.nombre=nombre; this.Q=Q; this.Sigma=Sigma; this.Gamma=Gamma; this.q0=q0; this.F=F; this.trans=trans;
  }
  public String getNombre(){return nombre;}
  public String getTipo(){return "Autómata de Pila";}
  public String descripcion(){ return "Nombre: "+nombre+"\nEstados: "+Q+"\nAlfabeto: "+Sigma+"\nPila: "+Gamma+"\nInicial: "+q0+"\nAceptación: "+F+"\nTransiciones: "+trans.size(); }
  public boolean validar(String s, StringBuilder pasos){
    // Implementación de simulación con pila (pendiente de completar pasos detallados)
    return false;
  }
}
