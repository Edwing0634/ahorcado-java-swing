import modelo.Categoria;
import modelo.Palabra;
import modelo.NivelDificultad;
import modelo.Facil;
import modelo.Medio;
import modelo.Dificil;
import java.util.ArrayList;
import java.util.List;

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
        pruebaNivelesDificultad();

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

    static void pruebaNivelesDificultad() {
        NivelDificultad facil = new Facil();
        NivelDificultad medio = new Medio();
        NivelDificultad dificil = new Dificil();

        afirmarIgual("Fácil: 8 intentos", 8, facil.getIntentosMaximos());
        afirmarIgual("Fácil: 3 pistas", 3, facil.getPistasDisponibles());
        afirmarIgual("Fácil: nombre", "Fácil", facil.getNombre());

        afirmarIgual("Medio: 7 intentos", 7, medio.getIntentosMaximos());
        afirmarIgual("Medio: 2 pistas", 2, medio.getPistasDisponibles());
        afirmarIgual("Medio: nombre", "Medio", medio.getNombre());

        afirmarIgual("Difícil: 6 intentos", 6, dificil.getIntentosMaximos());
        afirmarIgual("Difícil: 1 pista", 1, dificil.getPistasDisponibles());
        afirmarIgual("Difícil: nombre", "Difícil", dificil.getNombre());

        // Polimorfismo: misma llamada, filtrado distinto por longitud
        List<Palabra> banco = new ArrayList<>();
        banco.add(new Palabra("sol", Categoria.OBJETOS, "ilumina de día"));        // 3
        banco.add(new Palabra("camino", Categoria.OBJETOS, "se recorre"));         // 6
        banco.add(new Palabra("biblioteca", Categoria.OBJETOS, "guarda libros"));  // 10

        afirmarIgual("Fácil filtra solo 3-5 letras (sol)",
                1, facil.filtrarPalabras(banco).size());
        afirmarIgual("Medio filtra solo 6-8 letras (camino)",
                1, medio.filtrarPalabras(banco).size());
        afirmarIgual("Difícil filtra solo 9+ letras (biblioteca)",
                1, dificil.filtrarPalabras(banco).size());

        // Polimorfismo real vía referencia a la clase base
        NivelDificultad[] niveles = { facil, medio, dificil };
        int totalFiltrado = 0;
        for (NivelDificultad n : niveles) {
            totalFiltrado += n.filtrarPalabras(banco).size();
        }
        afirmarIgual("Suma de filtrados polimórficos = 3", 3, totalFiltrado);
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
