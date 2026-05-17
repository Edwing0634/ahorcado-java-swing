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
import modelo.Partida;
import modelo.ResultadoIntento;

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
        pruebaPartidaAciertosYFallos();
        pruebaPartidaGanar();
        pruebaPartidaPerder();
        pruebaPartidaPistas();

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

    static Partida nuevaPartidaCon(String texto, Categoria cat, String pista,
                                   NivelDificultad nivel) {
        return new Partida(new Palabra(texto, cat, pista), nivel);
    }

    static void pruebaPartidaAciertosYFallos() {
        Partida p = nuevaPartidaCon("sol", Categoria.OBJETOS,
                "ilumina de día", new Facil());

        afirmarIgual("Palabra visible inicial oculta",
                "_ _ _", p.getPalabraVisible());
        afirmarIgual("Intentos iniciales = 8 (Fácil)",
                8, p.getIntentosRestantes());

        afirmarIgual("Acertar 's' devuelve ACIERTO",
                ResultadoIntento.ACIERTO, p.intentarLetra('s'));
        afirmarIgual("Visible tras 's'", "s _ _", p.getPalabraVisible());
        afirmarIgual("Intentos siguen en 8 tras acierto",
                8, p.getIntentosRestantes());

        afirmarIgual("Fallar 'z' devuelve FALLO",
                ResultadoIntento.FALLO, p.intentarLetra('z'));
        afirmarIgual("Intentos bajan a 7 tras fallo",
                7, p.getIntentosRestantes());
        afirmarIgual("Errores cometidos = 1", 1, p.getErroresCometidos());
        afirmar("Letras falladas contiene 'z'",
                p.getLetrasFalladas().contains('z'));

        afirmarIgual("Repetir 's' devuelve YA_USADA",
                ResultadoIntento.YA_USADA, p.intentarLetra('s'));
        afirmarIgual("Repetir 'z' devuelve YA_USADA",
                ResultadoIntento.YA_USADA, p.intentarLetra('z'));
        afirmarIgual("Intentos no cambian tras YA_USADA (siguen 7)",
                7, p.getIntentosRestantes());

        afirmar("Acierto con tilde: 'í' en \"león\" cuenta",
                nuevaPartidaCon("león", Categoria.ANIMALES, "rey de la selva",
                        new Facil()).intentarLetra('o') == ResultadoIntento.ACIERTO);
    }

    static void pruebaPartidaGanar() {
        Partida p = nuevaPartidaCon("sol", Categoria.OBJETOS,
                "ilumina de día", new Facil());
        p.intentarLetra('s');
        p.intentarLetra('o');
        afirmar("No ganada aún", !p.estaGanada());
        p.intentarLetra('l');
        afirmar("Ganada al completar la palabra", p.estaGanada());
        afirmar("estaTerminada true tras ganar", p.estaTerminada());
        afirmar("No está perdida", !p.estaPerdida());
        afirmarIgual("Visible muestra palabra completa",
                "s o l", p.getPalabraVisible());
    }

    static void pruebaPartidaPerder() {
        Partida p = nuevaPartidaCon("sol", Categoria.OBJETOS,
                "ilumina de día", new Dificil()); // 6 intentos
        char[] malas = { 'a', 'b', 'c', 'd', 'e', 'f' };
        for (char c : malas) {
            p.intentarLetra(c);
        }
        afirmarIgual("Tras 6 fallos, intentos = 0", 0, p.getIntentosRestantes());
        afirmar("Está perdida", p.estaPerdida());
        afirmar("estaTerminada true tras perder", p.estaTerminada());
        afirmar("No está ganada", !p.estaGanada());
        afirmarIgual("Errores cometidos = 6", 6, p.getErroresCometidos());
    }

    static void pruebaPartidaPistas() {
        Partida p = nuevaPartidaCon("camino", Categoria.OBJETOS,
                "se recorre a pie", new Facil()); // 3 pistas

        afirmarIgual("Pistas restantes iniciales = 3 (Fácil)",
                3, p.getPistasRestantes());

        String p1 = p.usarPista();
        afirmar("Pista 1 menciona la categoría (Objetos)",
                p1.toLowerCase().contains("objetos"));
        afirmarIgual("Pistas restantes = 2 tras pista 1",
                2, p.getPistasRestantes());

        String p2 = p.usarPista();
        afirmar("Pista 2 revela una letra (visible ya no está todo oculto)",
                !p.getPalabraVisible().equals("_ _ _ _ _ _"));
        afirmar("Pista 2 devuelve texto no vacío", !p2.isBlank());

        String p3 = p.usarPista();
        afirmar("Pista 3 es la pista escrita",
                p3.contains("se recorre a pie"));
        afirmarIgual("Pistas restantes = 0 tras usar las 3",
                0, p.getPistasRestantes());

        String p4 = p.usarPista();
        afirmar("Pista 4 (sin pistas) avisa que no quedan",
                p4.toLowerCase().contains("no") );

        // En Difícil solo hay 1 pista
        Partida d = nuevaPartidaCon("biblioteca", Categoria.OBJETOS,
                "guarda libros", new Dificil());
        afirmarIgual("Difícil: 1 pista disponible",
                1, d.getPistasRestantes());
        d.usarPista();
        afirmarIgual("Difícil: 0 pistas tras usar la única",
                0, d.getPistasRestantes());
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
