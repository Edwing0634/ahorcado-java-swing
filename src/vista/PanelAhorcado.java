package vista;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Panel que dibuja la horca y el muñeco con Java2D según el número
 * de errores cometidos (0 a 7). 7 = ahorcado completo (derrota).
 *
 * Orden de partes: 1 cabeza, 2 torso, 3 brazo derecho, 4 brazo izquierdo,
 * 5 pierna derecha, 6 pierna izquierda, 7 cuerda final.
 */
public class PanelAhorcado extends JPanel {

    private int errores = 0;

    public PanelAhorcado() {
        setPreferredSize(new Dimension(300, 360));
        setBackground(Color.WHITE);
    }

    /** Actualiza cuántas partes dibujar y repinta. */
    public void setErrores(int errores) {
        this.errores = Math.max(0, Math.min(errores, 7));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(4f));
        g2.setColor(new Color(90, 60, 30));

        dibujarHorca(g2);

        g2.setColor(new Color(40, 40, 40));

        // Cabeza
        if (errores >= 1) {
            g2.drawOval(170, 90, 50, 50);
        }
        // Torso
        if (errores >= 2) {
            g2.drawLine(195, 140, 195, 230);
        }
        // Brazo derecho (a la izquierda en pantalla, lado derecho del muñeco)
        if (errores >= 3) {
            g2.drawLine(195, 160, 160, 200);
        }
        // Brazo izquierdo
        if (errores >= 4) {
            g2.drawLine(195, 160, 230, 200);
        }
        // Pierna derecha
        if (errores >= 5) {
            g2.drawLine(195, 230, 165, 290);
        }
        // Pierna izquierda
        if (errores >= 6) {
            g2.drawLine(195, 230, 225, 290);
        }
        // Cuerda final (se resalta en rojo: ahorcado completo)
        if (errores >= 7) {
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3f));
            g2.drawLine(195, 50, 195, 90);
        }
    }

    private void dibujarHorca(Graphics2D g2) {
        g2.drawLine(40, 330, 160, 330);   // base
        g2.drawLine(80, 330, 80, 30);     // poste vertical
        g2.drawLine(80, 30, 195, 30);     // viga horizontal
        g2.drawLine(195, 30, 195, 50);    // cuerda corta
    }
}
