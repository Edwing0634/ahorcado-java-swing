package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Estado y reglas de una partida del ahorcado. Lógica pura, sin Swing:
 * se puede instanciar y probar sin abrir ninguna ventana.
 */
public class Partida {

    private final Palabra palabra;
    private final NivelDificultad nivel;
    private final String objetivoNormalizado;

    private final List<Character> letrasAcertadas = new ArrayList<>();
    private final List<Character> letrasFalladas = new ArrayList<>();

    private int intentosRestantes;
    private int pistasUsadas = 0;
    private final Random aleatorio = new Random();

    public Partida(Palabra palabra, NivelDificultad nivel) {
        this.palabra = palabra;
        this.nivel = nivel;
        this.objetivoNormalizado = palabra.getTextoNormalizado();
        this.intentosRestantes = nivel.getIntentosMaximos();
    }

    /**
     * Intenta una letra. No penaliza si la letra ya se había usado.
     * La comparación ignora mayúsculas y tildes.
     */
    public ResultadoIntento intentarLetra(char letra) {
        char c = Palabra.normalizarLetra(letra);
        if (letrasAcertadas.contains(c) || letrasFalladas.contains(c)) {
            return ResultadoIntento.YA_USADA;
        }
        if (objetivoNormalizado.indexOf(c) >= 0) {
            letrasAcertadas.add(c);
            return ResultadoIntento.ACIERTO;
        }
        letrasFalladas.add(c);
        intentosRestantes--;
        return ResultadoIntento.FALLO;
    }

    /**
     * Usa la siguiente pista disponible. Pista 1: categoría;
     * pista 2: revela una letra oculta; pista 3: la pista escrita.
     * Si no quedan pistas, devuelve un aviso (no lanza excepción).
     */
    public String usarPista() {
        if (pistasUsadas >= nivel.getPistasDisponibles()) {
            return "No quedan pistas disponibles en este nivel.";
        }
        pistasUsadas++;
        switch (pistasUsadas) {
            case 1:
                return "Categoría: " + palabra.getCategoria().getNombreLegible();
            case 2:
                return revelarLetraAleatoria();
            case 3:
                return "Pista: " + palabra.getPista();
            default:
                return "No quedan pistas disponibles en este nivel.";
        }
    }

    private String revelarLetraAleatoria() {
        List<Character> ocultas = new ArrayList<>();
        for (char c : objetivoNormalizado.toCharArray()) {
            if (!letrasAcertadas.contains(c) && !ocultas.contains(c)) {
                ocultas.add(c);
            }
        }
        if (ocultas.isEmpty()) {
            return "Ya no hay letras por revelar.";
        }
        char elegida = ocultas.get(aleatorio.nextInt(ocultas.size()));
        letrasAcertadas.add(elegida);
        return "Se reveló la letra: " + Character.toUpperCase(elegida);
    }

    /** Palabra con guiones para las letras no adivinadas. Ej: {@code s o _}. */
    public String getPalabraVisible() {
        StringBuilder sb = new StringBuilder();
        char[] orig = palabra.getTexto().toCharArray();
        for (int i = 0; i < orig.length; i++) {
            char norm = Palabra.normalizarLetra(orig[i]);
            if (letrasAcertadas.contains(norm)) {
                sb.append(orig[i]);
            } else {
                sb.append('_');
            }
            if (i < orig.length - 1) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    public boolean estaGanada() {
        for (char c : objetivoNormalizado.toCharArray()) {
            if (!letrasAcertadas.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public boolean estaPerdida() {
        return intentosRestantes <= 0;
    }

    public boolean estaTerminada() {
        return estaGanada() || estaPerdida();
    }

    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    /** Errores cometidos = 0..intentosMáximos. Sirve para el dibujo. */
    public int getErroresCometidos() {
        return nivel.getIntentosMaximos() - intentosRestantes;
    }

    public int getPistasRestantes() {
        return nivel.getPistasDisponibles() - pistasUsadas;
    }

    public List<Character> getLetrasFalladas() {
        return new ArrayList<>(letrasFalladas);
    }

    public Palabra getPalabra() {
        return palabra;
    }

    public NivelDificultad getNivel() {
        return nivel;
    }
}
