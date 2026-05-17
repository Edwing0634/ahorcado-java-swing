package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Banco de palabras cargado desde un archivo de texto.
 * Formato por línea: {@code categoria;palabra;pista}.
 * Líneas vacías, comentarios (#) o mal formadas se omiten.
 */
public class BancoPalabras {

    private final String ruta;
    private final List<Palabra> palabras = new ArrayList<>();
    private final Random aleatorio = new Random();
    private int lineasIgnoradas = 0;

    public BancoPalabras(String ruta) {
        this.ruta = ruta;
    }

    /**
     * Lee el archivo y llena la lista de palabras.
     * @throws BancoPalabrasException si el archivo no se puede leer
     *         o no contiene ninguna palabra válida.
     */
    public void cargar() {
        Path path = Path.of(ruta);
        if (!Files.exists(path)) {
            throw new BancoPalabrasException(
                    "No se encontró el banco de palabras: " + ruta);
        }
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                procesarLinea(linea);
            }
        } catch (IOException e) {
            throw new BancoPalabrasException(
                    "Error leyendo el banco de palabras: " + ruta, e);
        }
        if (palabras.isEmpty()) {
            throw new BancoPalabrasException(
                    "El banco de palabras no contiene ninguna palabra válida.");
        }
    }

    private void procesarLinea(String linea) {
        String l = linea.trim();
        if (l.isEmpty() || l.startsWith("#")) {
            return;
        }
        String[] partes = l.split(";", 3);
        if (partes.length != 3) {
            lineasIgnoradas++;
            return;
        }
        try {
            Categoria cat = Categoria.desde(partes[0]);
            String texto = partes[1].trim();
            String pista = partes[2].trim();
            if (texto.isEmpty() || pista.isEmpty()) {
                lineasIgnoradas++;
                return;
            }
            palabras.add(new Palabra(texto, cat, pista));
        } catch (IllegalArgumentException e) {
            // Categoría no reconocida → se ignora la línea
            lineasIgnoradas++;
        }
    }

    /** Número de palabras válidas cargadas. */
    public int getCantidad() {
        return palabras.size();
    }

    /** Número de líneas que se ignoraron por estar mal formadas. */
    public int getLineasIgnoradas() {
        return lineasIgnoradas;
    }

    /**
     * Devuelve una palabra aleatoria apta para el nivel dado.
     * Si el filtro deja la lista vacía, hace fallback a todo el banco.
     */
    public Palabra palabraAleatoria(NivelDificultad nivel) {
        List<Palabra> aptas = nivel.filtrarPalabras(palabras);
        List<Palabra> fuente = aptas.isEmpty() ? palabras : aptas;
        return fuente.get(aleatorio.nextInt(fuente.size()));
    }
}
