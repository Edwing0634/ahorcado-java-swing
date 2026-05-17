package vista;

import modelo.Dificil;
import modelo.Facil;
import modelo.Medio;
import modelo.NivelDificultad;

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
import java.util.function.Consumer;

/**
 * Pantalla de inicio: título y selección de dificultad.
 * Notifica la dificultad elegida mediante un callback.
 */
public class PantallaInicio extends JPanel {

    public PantallaInicio(Consumer<NivelDificultad> alElegirNivel,
                          Runnable alSalir) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(30, 40, 60));
        setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));

        JLabel titulo = etiqueta("JUEGO DEL AHORCADO", 34, Color.WHITE);
        JLabel subtitulo = etiqueta("Elige un nivel de dificultad", 16,
                new Color(200, 210, 230));

        add(titulo);
        add(Box.createVerticalStrut(10));
        add(subtitulo);
        add(Box.createVerticalStrut(40));
        add(botonNivel("FÁCIL  (8 intentos · 3 pistas)",
                new Color(60, 140, 90), () -> alElegirNivel.accept(new Facil())));
        add(Box.createVerticalStrut(15));
        add(botonNivel("MEDIO  (7 intentos · 2 pistas)",
                new Color(70, 110, 170), () -> alElegirNivel.accept(new Medio())));
        add(Box.createVerticalStrut(15));
        add(botonNivel("DIFÍCIL  (6 intentos · 1 pista)",
                new Color(180, 80, 70), () -> alElegirNivel.accept(new Dificil())));
        add(Box.createVerticalStrut(40));
        add(botonNivel("SALIR", new Color(90, 90, 90), alSalir));
    }

    private JLabel etiqueta(String texto, int tam, Color color) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.BOLD, tam));
        l.setForeground(color);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    private JButton botonNivel(String texto, Color fondo, Runnable accion) {
        JButton b = new JButton(texto);
        b.setFont(new Font("SansSerif", Font.BOLD, 16));
        b.setForeground(Color.WHITE);
        b.setBackground(fondo);
        b.setFocusPainted(false);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(360, 50));
        b.addActionListener(e -> accion.run());
        return b;
    }
}
