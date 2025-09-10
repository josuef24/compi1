/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public interface Automata {
  String getNombre();
  String getTipo();
  boolean validar(String cadena, StringBuilder pasos); // llena pasos para los reportes
  String descripcion(); // Estados, alfabeto, etc.
}
