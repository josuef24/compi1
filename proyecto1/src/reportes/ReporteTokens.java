/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class ReporteTokens {
    private List<TokenInfo> tokens;
    
    public ReporteTokens() {
        this.tokens = new ArrayList<>();
    }
    
    public void agregarToken(String lexema, String tipo, int linea, int columna) {
        tokens.add(new TokenInfo(lexema, tipo, linea, columna));
    }
    
    public void limpiar() {
        tokens.clear();
    }
    
    public void generarReporteHTML() {
        try {
            FileWriter writer = new FileWriter("reporte_tokens.html");
            
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n<head>\n");
            writer.write("<title>Reporte de Tokens</title>\n");
            writer.write("<style>\n");
            writer.write("table { border-collapse: collapse; width: 100%; }\n");
            writer.write("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n");
            writer.write("th { background-color: #f2f2f2; }\n");
            writer.write("</style>\n");
            writer.write("</head>\n<body>\n");
            
            writer.write("<h1>Reporte de Tokens - AutómataLab</h1>\n");
            writer.write("<table>\n");
            writer.write("<tr><th>#</th><th>Lexema</th><th>Tipo</th><th>Línea</th><th>Columna</th></tr>\n");
            
            for (int i = 0; i < tokens.size(); i++) {
                TokenInfo token = tokens.get(i);
                writer.write("<tr>");
                writer.write("<td>" + (i + 1) + "</td>");
                writer.write("<td>" + token.lexema + "</td>");
                writer.write("<td>" + token.tipo + "</td>");
                writer.write("<td>" + token.linea + "</td>");
                writer.write("<td>" + token.columna + "</td>");
                writer.write("</tr>\n");
            }
            
            writer.write("</table>\n");
            writer.write("</body>\n</html>");
            writer.close();
            
            System.out.println("Reporte de tokens generado: reporte_tokens.html");
        } catch (IOException e) {
            System.err.println("Error generando reporte de tokens: " + e.getMessage());
        }
    }
    
    public List<TokenInfo> getTokens() {
        return new ArrayList<>(tokens);
    }
    
    public static class TokenInfo {
        public final String lexema;
        public final String tipo;
        public final int linea;
        public final int columna;
        
        public TokenInfo(String lexema, String tipo, int linea, int columna) {
            this.lexema = lexema;
            this.tipo = tipo;
            this.linea = linea;
            this.columna = columna;
        }
        
        @Override
        public String toString() {
            return lexema + " [" + tipo + "] (" + linea + "," + columna + ")";
        }
    }
}