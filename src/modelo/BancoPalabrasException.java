package modelo;

/**
 * Error al cargar el banco de palabras (archivo ausente, ilegible
 * o sin ninguna palabra válida).
 */
public class BancoPalabrasException extends RuntimeException {
    public BancoPalabrasException(String mensaje) {
        super(mensaje);
    }

    public BancoPalabrasException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
