/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import analizadores.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class ReporteErrores {
    private List<ErrorInfo> errores;
    
    public ReporteErrores() {
        this.errores = new ArrayList<>();
    }
    
    public void agregarError(String tipo, String descripcion, int linea, int columna) {
        errores.add(new ErrorInfo(tipo, descripcion, linea, columna));
    }
    
    public void agregarErrorLexico(String caracter, int linea, int columna) {
        String descripcion = "El carácter '" + caracter + "' no pertenece al lenguaje";
        agregarError("Léxico", descripcion, linea, columna);
    }
    
    public void agregarErrorSintactico(String descripcion, int linea, int columna) {
        agregarError("Sintáctico", descripcion, linea, columna);
    }
    
    public void limpiar() {
        errores.clear();
    }
    
    public void generarReporteHTML() {
        try {
            FileWriter writer = new FileWriter("reporte_errores.html");
            
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n<head>\n");
            writer.write("<title>Reporte de Errores</title>\n");
            writer.write("<style>\n");
            writer.write("table { border-collapse: collapse; width: 100%; }\n");
            writer.write("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n");
            writer.write("th { background-color: #ffebee; }\n");
            writer.write(".error-lexico { background-color: #ffcdd2; }\n");
            writer.write(".error-sintactico { background-color: #f8bbd9; }\n");
            writer.write("</style>\n");
            writer.write("</head>\n<body>\n");
            
            writer.write("<h1>Reporte de Errores - AutómataLab</h1>\n");
            
            if (errores.isEmpty()) {
                writer.write("<p>No se encontraron errores en el análisis.</p>\n");
            } else {
                writer.write("<table>\n");
                writer.write("<tr><th>#</th><th>Tipo</th><th>Descripción</th><th>Línea</th><th>Columna</th></tr>\n");
                
                for (int i = 0; i < errores.size(); i++) {
                    ErrorInfo error = errores.get(i);
                    String clase = error.tipo.equals("Léxico") ? "error-lexico" : "error-sintactico";
                    writer.write("<tr class=\"" + clase + "\">");
                    writer.write("<td>" + (i + 1) + "</td>");
                    writer.write("<td>" + error.tipo + "</td>");
                    writer.write("<td>" + error.descripcion + "</td>");
                    writer.write("<td>" + error.linea + "</td>");
                    writer.write("<td>" + error.columna + "</td>");
                    writer.write("</tr>\n");
                }
                
                writer.write("</table>\n");
            }
            
            writer.write("</body>\n</html>");
            writer.close();
            
            System.out.println("Reporte de errores generado: reporte_errores.html");
        } catch (IOException e) {
            System.err.println("Error generando reporte de errores: " + e.getMessage());
        }
    }
    
    public void mostrarErrores() {
        if (errores.isEmpty()) {
            System.out.println("No hay errores registrados.");
        } else {
            System.out.println("=== ERRORES ENCONTRADOS ===");
            for (int i = 0; i < errores.size(); i++) {
                ErrorInfo error = errores.get(i);
                System.out.printf("%d. [%s] %s (Línea %d, Columna %d)%n", 
                    i + 1, error.tipo, error.descripcion, error.linea, error.columna);
            }
        }
    }
    
    public List<ErrorInfo> getErrores() {
        return new ArrayList<>(errores);
    }
    
    public boolean tieneErrores() {
        return !errores.isEmpty();
    }
    
    public static class ErrorInfo {
        public final String tipo;
        public final String descripcion;
        public final int linea;
        public final int columna;
        
        public ErrorInfo(String tipo, String descripcion, int linea, int columna) {
            this.tipo = tipo;
            this.descripcion = descripcion;
            this.linea = linea;
            this.columna = columna;
        }
        
        @Override
        public String toString() {
            return "[" + tipo + "] " + descripcion + " (" + linea + "," + columna + ")";
        }
    }
}