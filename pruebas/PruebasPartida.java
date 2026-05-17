import modelo.Categoria;
import modelo.Palabra;
import modelo.NivelDificultad;
import modelo.Facil;
import modelo.Medio;
import modelo.Dificil;
import java.util.ArrayList;
import java.util.List;
import modelo.BancoPalabras;
import modelo.BancoPalabrasException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        pruebaBancoPalabras();

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

    static void pruebaBancoPalabras() {
        try {
            // Crear un archivo temporal de prueba
            Path tmp = Files.createTempFile("banco_test", ".txt");
            List<String> lineas = new ArrayList<>();
            lineas.add("ANIMALES;gato;Maulla y caza ratones");
            lineas.add("PAISES;brasil;País del carnaval");
            lineas.add("# comentario que se ignora");
            lineas.add("");                       // vacía: se ignora
            lineas.add("LINEA_MAL_FORMADA");      // sin ';': se ignora
            lineas.add("CATEGORIA_INVALIDA;x;y"); // categoría inexistente: se ignora
            lineas.add("DEPORTES;futbol;Se juega con los pies");
            Files.write(tmp, lineas);

            BancoPalabras banco = new BancoPalabras(tmp.toString());
            banco.cargar();

            afirmarIgual("Banco carga 3 palabras válidas (ignora 4 inválidas)",
                    3, banco.getCantidad());
            afirmar("palabraAleatoria(Facil) devuelve no nulo",
                    banco.palabraAleatoria(new Facil()) != null);
            // 'gato'(4) apta para Fácil; 'brasil'(6) y 'futbol'(6) para Medio
            afirmarIgual("Banco filtra 1 palabra para Fácil (gato)",
                    "gato", banco.palabraAleatoria(new Facil()).getTexto());

            Files.deleteIfExists(tmp);
        } catch (IOException e) {
            afirmar("No debería lanzar IOException: " + e.getMessage(), false);
        }

        // Archivo inexistente debe lanzar BancoPalabrasException
        boolean lanzo = false;
        try {
            new BancoPalabras("ruta/que/no/existe_xyz.txt").cargar();
        } catch (BancoPalabrasException e) {
            lanzo = true;
        }
        afirmar("Archivo inexistente lanza BancoPalabrasException", lanzo);
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
