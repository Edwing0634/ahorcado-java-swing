package vista;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Pantalla principal de juego: dibujo del ahorcado, palabra oculta,
 * datos de la partida, teclado A-Z+Ñ y botones de pista.
 * No contiene lógica de juego; expone métodos para que el
 * controlador actualice la vista y registre los listeners.
 */
public class PantallaJuego extends JPanel {

    private final PanelAhorcado panelAhorcado = new PanelAhorcado();
    private final JLabel lblPalabra = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblCategoria = new JLabel(" ", SwingConstants.CENTER);
    private final JLabel lblIntentos = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblFalladas = new JLabel(" ", SwingConstants.CENTER);
    private final JLabel lblMensajePista = new JLabel(" ", SwingConstants.CENTER);

    private final Map<Character, JButton> botonesLetra = new LinkedHashMap<>();
    private final JButton btnPista = new JButton("Usar pista (3)");

    private static final String LETRAS = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";

    public PantallaJuego() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(construirPanelSuperior(), BorderLayout.NORTH);
        add(panelAhorcado, BorderLayout.WEST);
        add(construirPanelCentral(), BorderLayout.CENTER);
        add(construirTeclado(), BorderLayout.SOUTH);
    }

    private JPanel construirPanelSuperior() {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setOpaque(false);
        lblIntentos.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblCategoria.setFont(new Font("SansSerif", Font.ITALIC, 15));
        lblCategoria.setForeground(new Color(80, 90, 110));
        p.add(lblIntentos);
        p.add(lblCategoria);
        return p;
    }

    private JPanel construirPanelCentral() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setOpaque(false);
        lblPalabra.setFont(new Font("Monospaced", Font.BOLD, 40));
        lblPalabra.setForeground(new Color(25, 35, 55));
        lblFalladas.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblFalladas.setForeground(new Color(190, 70, 60));
        lblMensajePista.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblMensajePista.setForeground(new Color(70, 110, 170));

        JPanel centro = new JPanel(new GridLayout(3, 1, 5, 5));
        centro.setOpaque(false);
        centro.add(lblPalabra);
        centro.add(lblFalladas);
        centro.add(lblMensajePista);

        p.add(centro, BorderLayout.CENTER);
        btnPista.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnPista.setBackground(new Color(240, 200, 90));
        btnPista.setFocusPainted(false);
        p.add(btnPista, BorderLayout.SOUTH);
        return p;
    }

    private JPanel construirTeclado() {
        JPanel teclado = new JPanel(new GridLayout(0, 9, 4, 4));
        teclado.setOpaque(false);
        for (char c : LETRAS.toCharArray()) {
            JButton b = new JButton(String.valueOf(c));
            b.setFont(new Font("SansSerif", Font.BOLD, 16));
            b.setFocusPainted(false);
            b.setBackground(Color.WHITE);
            botonesLetra.put(c, b);
            teclado.add(b);
        }
        return teclado;
    }

    // ---- API para el controlador ----

    /** Registra el listener de cada letra (recibe la letra pulsada). */
    public void alPulsarLetra(Consumer<Character> accion) {
        for (Map.Entry<Character, JButton> e : botonesLetra.entrySet()) {
            char letra = e.getKey();
            e.getValue().addActionListener(ev -> accion.accept(letra));
        }
    }

    /** Registra el listener del botón de pista. */
    public void alUsarPista(Runnable accion) {
        btnPista.addActionListener(e -> accion.run());
    }

    public void setPalabraVisible(String texto) {
        lblPalabra.setText(texto);
    }

    public void setIntentos(int restantes, int maximos) {
        lblIntentos.setText("Intentos restantes: " + restantes + " / " + maximos);
    }

    public void setCategoria(String texto) {
        lblCategoria.setText(texto);
    }

    public void setLetrasFalladas(String texto) {
        lblFalladas.setText(texto.isBlank() ? " " : "Falladas: " + texto);
    }

    public void setMensajePista(String texto) {
        lblMensajePista.setText(texto == null || texto.isBlank() ? " " : texto);
    }

    public void setErrores(int errores) {
        panelAhorcado.setErrores(errores);
    }

    public void setPistasRestantes(int restantes) {
        btnPista.setText("Usar pista (" + restantes + ")");
        btnPista.setEnabled(restantes > 0);
    }

    /** Colorea y deshabilita la letra usada (verde acierto / rojo fallo). */
    public void marcarLetra(char letra, boolean acierto) {
        JButton b = botonesLetra.get(Character.toUpperCase(letra));
        if (b != null) {
            b.setEnabled(false);
            b.setBackground(acierto ? new Color(120, 200, 130)
                                    : new Color(225, 120, 110));
        }
    }

    /** Activa o desactiva todo el teclado (al terminar la partida). */
    public void habilitarTeclado(boolean activo) {
        for (JButton b : botonesLetra.values()) {
            if (activo) {
                b.setEnabled(true);
                b.setBackground(Color.WHITE);
            } else {
                b.setEnabled(false);
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(720, 520);
    }
}
