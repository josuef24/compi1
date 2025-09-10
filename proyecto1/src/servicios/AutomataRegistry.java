package servicios;
import java.util.*; import modelo.*;

public class AutomataRegistry {
  private static final Map<String, Automata> map = new HashMap<>();
  public static void add(Automata a){ map.put(a.getNombre(), a); }
  public static void listar(){
    map.values().forEach(a -> System.out.println(a.getNombre()+"\t"+a.getTipo()));
  }
  public static void desc(String nombre){
    var a = map.get(nombre);
    System.out.println(a==null? ("No existe "+nombre) : a.descripcion());
  }
  public static void validar(String nombre, String cadena){
    var a = map.get(nombre);
    if(a==null){ System.out.println("No existe "+nombre); return; }
    StringBuilder sb = new StringBuilder();
    boolean ok = a.validar(cadena, sb);
    System.out.println(nombre+"\t"+cadena+"\t"+(ok? "Cadena Válida":"Cadena Inválida"));
    System.out.println("Pasos:\n"+sb.toString());
  }
}
