package modelo;

import java.text.Normalizer;

/**
 * Una palabra del banco: su texto, categoría y pista escrita asociada.
 * Inmutable. La normalización permite comparar sin distinguir
 * mayúsculas ni tildes (pero conservando la Ñ como letra propia).
 */
public class Palabra {

    private final String texto;
    private final Categoria categoria;
    private final String pista;

    public Palabra(String texto, Categoria categoria, String pista) {
        this.texto = texto;
        this.categoria = categoria;
        this.pista = pista;
    }

    public String getTexto() {
        return texto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getPista() {
        return pista;
    }

    /** Longitud en número de letras de la palabra original. */
    public int getLongitud() {
        return texto.length();
    }

    /** Texto en minúsculas y sin tildes (la Ñ se conserva). */
    public String getTextoNormalizado() {
        StringBuilder sb = new StringBuilder(texto.length());
        for (char c : texto.toCharArray()) {
            sb.append(normalizarLetra(c));
        }
        return sb.toString();
    }

    /**
     * Normaliza una sola letra: minúscula y sin tilde, pero la Ñ/ñ
     * se preserva como 'ñ' (es una letra distinta en español).
     */
    public static char normalizarLetra(char c) {
        char min = Character.toLowerCase(c);
        if (min == 'ñ') {
            return 'ñ';
        }
        String sinTilde = Normalizer
                .normalize(String.valueOf(min), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return sinTilde.isEmpty() ? min : sinTilde.charAt(0);
    }
}
