package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Nivel de dificultad del juego. Clase abstracta: define el contrato
 * y la lógica común de filtrado; las subclases concretan los valores
 * y el criterio de longitud (herencia + polimorfismo).
 */
public abstract class NivelDificultad {

    /** Intentos máximos (errores permitidos) para este nivel. */
    public abstract int getIntentosMaximos();

    /** Cuántas de las 3 pistas están disponibles en este nivel. */
    public abstract int getPistasDisponibles();

    /** Nombre legible del nivel (para la interfaz). */
    public abstract String getNombre();

    /**
     * Indica si una palabra de longitud {@code n} es apta para este nivel.
     * Cada subclase define su rango (método plantilla).
     */
    protected abstract boolean aplicaLongitud(int n);

    /**
     * Filtra el banco dejando solo las palabras aptas para este nivel.
     * Método polimórfico: el comportamiento depende de {@link #aplicaLongitud}.
     */
    public List<Palabra> filtrarPalabras(List<Palabra> banco) {
        List<Palabra> aptas = new ArrayList<>();
        for (Palabra p : banco) {
            if (aplicaLongitud(p.getLongitud())) {
                aptas.add(p);
            }
        }
        return aptas;
    }
}
