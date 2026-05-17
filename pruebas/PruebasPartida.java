import modelo.Categoria;

/**
 * Runner manual de pruebas de la lógica pura (sin Swing).
 * Imprime ✓/✗ por caso y sale con código 1 si alguna falla.
 */
public class PruebasPartida {

    private static int pasadas = 0;
    private static int fallidas = 0;

    public static void main(String[] args) {
        System.out.println("=== Pruebas de la lógica del Ahorcado ===\n");

        pruebaCategoriaTieneCincoValores();

        System.out.println("\n=== Resultado: " + pasadas + " pasadas, " + fallidas + " fallidas ===");
        if (fallidas > 0) {
            System.exit(1);
        }
    }

    static void pruebaCategoriaTieneCincoValores() {
        afirmar("Categoria tiene exactamente 5 valores",
                Categoria.values().length == 5);
        afirmar("Categoria.ANIMALES tiene nombre legible no vacío",
                !Categoria.ANIMALES.getNombreLegible().isBlank());
    }

    // ---- utilidades de aserción ----

    static void afirmar(String descripcion, boolean condicion) {
        if (condicion) {
            pasadas++;
            System.out.println("  ✓ " + descripcion);
        } else {
            fallidas++;
            System.out.println("  ✗ " + descripcion);
        }
    }

    static void afirmarIgual(String descripcion, Object esperado, Object real) {
        boolean ok = (esperado == null && real == null)
                || (esperado != null && esperado.equals(real));
        if (ok) {
            pasadas++;
            System.out.println("  ✓ " + descripcion);
        } else {
            fallidas++;
            System.out.println("  ✗ " + descripcion
                    + " (esperado=" + esperado + ", real=" + real + ")");
        }
    }
}
