/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import olc1.proyecto1.domain.repo.AutomataRegistry;
import reportes.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportService {
    private AutomataRegistry registry;
    private ReporteTokens reporteTokens;
    private ReporteErrores reporteErrores;
    
    public ReportService(AutomataRegistry registry) {
        this.registry = registry;
        this.reporteTokens = new ReporteTokens();
        this.reporteErrores = new ReporteErrores();
    }
    
    public void generarReporteTokens() {
        try {
            File file = new File("reporte_tokens.html");
            FileWriter writer = new FileWriter(file);
            
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html><head><title>Reporte de Tokens</title></head><body>\n");
            writer.write("<h1>Reporte de Tokens</h1>\n");
            writer.write("<table border='1'>\n");
            writer.write("<tr><th>#</th><th>Lexema</th><th>Tipo</th><th>Línea</th><th>Columna</th></tr>\n");
            
            var tokens = registry.obtenerTokens();
            for (int i = 0; i < tokens.size(); i++) {
                writer.write("<tr><td>" + (i+1) + "</td><td>" + tokens.get(i) + "</td><td>TOKEN</td><td>1</td><td>1</td></tr>\n");
            }
            
            writer.write("</table></body></html>");
            writer.close();
            
            // Abrir en navegador
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
            
            System.out.println("Reporte de tokens generado: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error generando reporte de tokens: " + e.getMessage());
        }
    }
    
    public void generarReporteErrores() {
        try {
            File file = new File("reporte_errores.html");
            FileWriter writer = new FileWriter(file);
            
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html><head><title>Reporte de Errores</title></head><body>\n");
            writer.write("<h1>Reporte de Errores</h1>\n");
            writer.write("<table border='1'>\n");
            writer.write("<tr><th>#</th><th>Tipo</th><th>Descripción</th><th>Línea</th><th>Columna</th></tr>\n");
            
            var errores = registry.obtenerErrores();
            for (int i = 0; i < errores.size(); i++) {
                writer.write("<tr><td>" + (i+1) + "</td><td>Léxico</td><td>" + errores.get(i) + "</td><td>1</td><td>1</td></tr>\n");
            }
            
            writer.write("</table></body></html>");
            writer.close();
            
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
            
            System.out.println("Reporte de errores generado: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error generando reporte de errores: " + e.getMessage());
        }
    }
    
    public void generarReporteAutomatas() {
        System.out.println("Generando reporte visual de autómatas...");
        System.out.println("(Funcionalidad de Graphviz pendiente de implementar)");
    }
    
    public void limpiarReportes() {
        System.out.println("Reportes limpiados para nueva ejecución.");
    }
}