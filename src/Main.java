import controlador.ControladorJuego;
import modelo.BancoPalabras;
import modelo.BancoPalabrasException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Punto de entrada. Carga el banco de palabras y, si todo va bien,
 * lanza la interfaz. Cualquier fallo de carga se muestra al usuario
 * con un mensaje claro (sin stack trace).
 */
public class Main {

    public static void main(String[] args) {
        BancoPalabras banco = new BancoPalabras("recursos/palabras.txt");
        try {
            banco.cargar();
        } catch (BancoPalabrasException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo iniciar el juego:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        SwingUtilities.invokeLater(() -> new ControladorJuego(banco).iniciar());
    }
}
