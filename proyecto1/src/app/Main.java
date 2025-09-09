package app;

import java.io.FileReader;
import analizadores.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Usa "entrada.atm" por defecto si no se pasa argumento
        String archivo = (args.length > 0) ? args[0] : "entrada.atm";

        Lexer lexer = new Lexer(new FileReader(archivo));
        Parser parser = new Parser(lexer);

        parser.parse();
        System.out.println("✓ Análisis finalizado.");
    }
}
