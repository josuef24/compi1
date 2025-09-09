package editor;

import olc1.proyecto1.controller.ProyectoController;
import olc1.proyecto1.services.*;
import olc1.proyecto1.domain.repo.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import services.CommandService;

public class MainWindows extends JFrame {
    private JTextArea editorArea;
    private JTextArea consoleArea;
    private JLabel statusLabel;
    private ProyectoController controller;
    private File currentFile;
    
    // Servicios
    private AutomataRegistry registry;
    private ExecutorService exec;
    private ReportService reports;
    private CommandService cmd;
    private ParserService parser;

    public MainWindows() {
        initializeServices();
        initializeGUI();
        setupMenuBar();
        setupToolBar();
        setupComponents();
    }
    
    private void initializeServices() {
        registry = new AutomataRegistry();
        exec = new ExecutorService(registry);
        reports = new ReportService(registry);
        cmd = new CommandService(registry, exec, reports);
        parser = new ParserService(registry);
        controller = new ProyectoController(parser, cmd);
    }

    private void initializeGUI() {
        setTitle("AutómataLab - OLC1 Proyecto 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menú Archivo
        JMenu fileMenu = new JMenu("Archivo");
        
        JMenuItem newItem = new JMenuItem("Nuevo");
        newItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        newItem.addActionListener(e -> nuevoArchivo());
        
        JMenuItem openItem = new JMenuItem("Abrir");
        openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        openItem.addActionListener(e -> abrirArchivo());
        
        JMenuItem saveItem = new JMenuItem("Guardar");
        saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        saveItem.addActionListener(e -> guardarArchivo());
        
        JMenuItem saveAsItem = new JMenuItem("Guardar Como...");
        saveAsItem.addActionListener(e -> guardarComo());
        
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);

        // Menú Herramientas
        JMenu toolsMenu = new JMenu("Herramientas");
        
        JMenuItem executeItem = new JMenuItem("Ejecutar");
        executeItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
        executeItem.addActionListener(e -> ejecutar());
        
        toolsMenu.add(executeItem);

        // Menú Reportes
        JMenu reportsMenu = new JMenu("Reportes");
        
        JMenuItem tokensItem = new JMenuItem("Reporte de Tokens");
        tokensItem.addActionListener(e -> mostrarReporteTokens());
        
        JMenuItem errorsItem = new JMenuItem("Reporte de Errores");
        errorsItem.addActionListener(e -> mostrarReporteErrores());
        
        JMenuItem automataItem = new JMenuItem("Reporte de Autómatas");
        automataItem.addActionListener(e -> mostrarReporteAutomatas());
        
        reportsMenu.add(tokensItem);
        reportsMenu.add(errorsItem);
        reportsMenu.add(automataItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        menuBar.add(reportsMenu);
        
        setJMenuBar(menuBar);
    }

    private void setupToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        // Botones de la barra de herramientas
        JButton newBtn = new JButton("Nuevo");
        newBtn.addActionListener(e -> nuevoArchivo());
        
        JButton openBtn = new JButton("Abrir");
        openBtn.addActionListener(e -> abrirArchivo());
        
        JButton saveBtn = new JButton("Guardar");
        saveBtn.addActionListener(e -> guardarArchivo());
        
        JButton executeBtn = new JButton("Ejecutar");
        executeBtn.setBackground(new Color(76, 175, 80));
        executeBtn.setForeground(Color.WHITE);
        executeBtn.addActionListener(e -> ejecutar());

        toolBar.add(newBtn);
        toolBar.add(openBtn);
        toolBar.add(saveBtn);
        toolBar.addSeparator();
        toolBar.add(executeBtn);

        add(toolBar, BorderLayout.NORTH);
    }

    private void setupComponents() {
        // Panel principal dividido
        JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        mainSplit.setDividerLocation(400);

        // Panel superior - Editor
        JPanel editorPanel = new JPanel(new BorderLayout());
        editorPanel.setBorder(new TitledBorder("Editor de Código (.atm)"));
        
        editorArea = new JTextArea();
        editorArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        editorArea.setTabSize(4);
        editorArea.setText(getPlantillaInicial());
        
        JScrollPane editorScroll = new JScrollPane(editorArea);
        editorPanel.add(editorScroll, BorderLayout.CENTER);

        // Panel inferior - Consola
        JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.setBorder(new TitledBorder("Consola de Salida"));
        
        consoleArea = new JTextArea();
        consoleArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        consoleArea.setBackground(new Color(30, 30, 30));
        consoleArea.setForeground(Color.WHITE);
        consoleArea.setEditable(false);
        
        JScrollPane consoleScroll = new JScrollPane(consoleArea);
        consolePanel.add(consoleScroll, BorderLayout.CENTER);

        mainSplit.setTopComponent(editorPanel);
        mainSplit.setBottomComponent(consolePanel);
        
        add(mainSplit, BorderLayout.CENTER);

        // Barra de estado
        statusLabel = new JLabel("Listo");
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        add(statusLabel, BorderLayout.SOUTH);
    }

    private String getPlantillaInicial() {
        return "// AutómataLab - Ejemplo inicial\n" +
               "// Definir aquí sus autómatas\n\n" +
               "<AFD Nombre=\"EjemploAFD\">\n" +
               "N = {q0, q1};\n" +
               "T = {0, 1};\n" +
               "I = {q0};\n" +
               "A = {q1};\n" +
               "Transiciones:\n" +
               "q0 -> 0, q0 | 1, q1;\n" +
               "q1 -> 0, q0 | 1, q1;\n" +
               "</AFD>\n\n" +
               "// Comandos\n" +
               "verAutomatas();\n" +
               "desc(EjemploAFD);\n" +
               "EjemploAFD(\"101\");\n";
    }

    // Métodos de archivo
    private void nuevoArchivo() {
        if (confirmarGuardado()) {
            editorArea.setText(getPlantillaInicial());
            currentFile = null;
            updateTitle();
            consoleArea.setText("");
            statusLabel.setText("Nuevo archivo creado");
        }
    }

    private void abrirArchivo() {
        if (!confirmarGuardado()) return;
        
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos ATM (*.atm)", "atm"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                currentFile = chooser.getSelectedFile();
                String content = readFile(currentFile);
                editorArea.setText(content);
                updateTitle();
                statusLabel.setText("Archivo cargado: " + currentFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al abrir el archivo: " + e.getMessage());
            }
        }
    }

    private void guardarArchivo() {
        if (currentFile == null) {
            guardarComo();
        } else {
            try {
                writeFile(currentFile, editorArea.getText());
                statusLabel.setText("Archivo guardado: " + currentFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
            }
        }
    }

    private void guardarComo() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos ATM (*.atm)", "atm"));
        
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                currentFile = chooser.getSelectedFile();
                if (!currentFile.getName().endsWith(".atm")) {
                    currentFile = new File(currentFile.getAbsolutePath() + ".atm");
                }
                writeFile(currentFile, editorArea.getText());
                updateTitle();
                statusLabel.setText("Archivo guardado como: " + currentFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
            }
        }
    }

    private void ejecutar() {
        try {
            // Guardar contenido en archivo temporal
            File tempFile = File.createTempFile("automatalab_", ".atm");
            writeFile(tempFile, editorArea.getText());
            
            // Limpiar consola y reportes
            consoleArea.setText("");
            reports.limpiarReportes();
            
            // Redirigir salida a la consola de la GUI
            redirectSystemOut();
            
            statusLabel.setText("Ejecutando...");
            
            // Ejecutar análisis
            controller.cargarYCompilar(tempFile.getAbsolutePath());
            controller.ejecutarScriptComandos();
            
            statusLabel.setText("Ejecución completada");
            tempFile.delete();
            
        } catch (Exception e) {
            appendToConsole("Error durante la ejecución: " + e.getMessage());
            statusLabel.setText("Error en ejecución");
        }
    }

    // Métodos de reportes
    private void mostrarReporteTokens() {
        reports.generarReporteTokens();
    }

    private void mostrarReporteErrores() {
        reports.generarReporteErrores();
    }

    private void mostrarReporteAutomatas() {
        reports.generarReporteAutomatas();
    }

    // Métodos auxiliares
    private boolean confirmarGuardado() {
        // Implementar lógica para confirmar si se quiere guardar cambios
        return true;
    }

    private void updateTitle() {
        String title = "AutómataLab - OLC1 Proyecto 1";
        if (currentFile != null) {
            title += " - " + currentFile.getName();
        }
        setTitle(title);
    }

    private String readFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private void writeFile(File file, String content) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.write(content);
        }
    }

    private void redirectSystemOut() {
        PrintStream originalOut = System.out;
        PrintStream guiOut = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                SwingUtilities.invokeLater(() -> {
                    consoleArea.append(String.valueOf((char) b));
                    consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
                });
            }
        });
        System.setOut(guiOut);
    }

    private void appendToConsole(String text) {
        SwingUtilities.invokeLater(() -> {
            consoleArea.append(text + "\n");
            consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}