package vista;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Pantalla final: muestra si ganó o perdió y la palabra correcta.
 * Permite volver a jugar o salir.
 */
public class PantallaFin extends JPanel {

    private final JLabel lblResultado;
    private final JLabel lblPalabra;

    public PantallaFin(Runnable alJugarDeNuevo, Runnable alSalir) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(30, 40, 60));
        setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        lblResultado = new JLabel("");
        lblResultado.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblResultado.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblPalabra = new JLabel("");
        lblPalabra.setFont(new Font("SansSerif", Font.PLAIN, 20));
        lblPalabra.setForeground(Color.WHITE);
        lblPalabra.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(lblResultado);
        add(Box.createVerticalStrut(20));
        add(lblPalabra);
        add(Box.createVerticalStrut(50));
        add(boton("JUGAR DE NUEVO", new Color(70, 110, 170), alJugarDeNuevo));
        add(Box.createVerticalStrut(15));
        add(boton("SALIR", new Color(90, 90, 90), alSalir));
    }

    /** Configura el mensaje según el resultado de la partida. */
    public void mostrarResultado(boolean gano, String palabraCorrecta) {
        if (gano) {
            lblResultado.setText("¡GANASTE! 🎉");
            lblResultado.setForeground(new Color(120, 220, 130));
        } else {
            lblResultado.setText("PERDISTE 💀");
            lblResultado.setForeground(new Color(230, 110, 100));
        }
        lblPalabra.setText("La palabra era: " + palabraCorrecta.toUpperCase());
    }

    private JButton boton(String texto, Color fondo, Runnable accion) {
        JButton b = new JButton(texto);
        b.setFont(new Font("SansSerif", Font.BOLD, 16));
        b.setForeground(Color.WHITE);
        b.setBackground(fondo);
        b.setFocusPainted(false);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(320, 50));
        b.addActionListener(e -> accion.run());
        return b;
    }
}
