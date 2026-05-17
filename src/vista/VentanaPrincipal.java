package vista;

import javax.swing.JFrame;
import java.awt.CardLayout;

/**
 * Ventana única del juego. Usa CardLayout para alternar entre
 * las pantallas de inicio, juego y fin.
 */
public class VentanaPrincipal extends JFrame {

    private final CardLayout cardLayout = new CardLayout();

    public static final String INICIO = "INICIO";
    public static final String JUEGO = "JUEGO";
    public static final String FIN = "FIN";

    public VentanaPrincipal() {
        setTitle("Juego del Ahorcado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(cardLayout);
        setSize(760, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void agregarPantalla(java.awt.Component pantalla, String nombre) {
        add(pantalla, nombre);
    }

    public void mostrar(String nombre) {
        cardLayout.show(getContentPane(), nombre);
    }
}
