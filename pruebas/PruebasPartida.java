import modelo.Categoria;
import modelo.Palabra;

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
        pruebaPalabraNormaliza();

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

    static void pruebaPalabraNormaliza() {
        Palabra p = new Palabra("Camión", Categoria.OBJETOS, "Sirve para transportar carga");
        afirmarIgual("getTexto conserva original", "Camión", p.getTexto());
        afirmarIgual("getTextoNormalizado quita tildes y pasa a minúscula",
                "camion", p.getTextoNormalizado());
        afirmarIgual("getCategoria correcta", Categoria.OBJETOS, p.getCategoria());
        afirmarIgual("getPista correcta",
                "Sirve para transportar carga", p.getPista());
        afirmar("normalizarLetra('Á') == 'a'", Palabra.normalizarLetra('Á') == 'a');
        afirmar("normalizarLetra('Ñ') == 'ñ' (la Ñ se conserva)",
                Palabra.normalizarLetra('Ñ') == 'ñ');
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
