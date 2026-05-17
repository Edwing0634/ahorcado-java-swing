package modelo;

/**
 * Categorías del banco de palabras. El enunciado exige al menos 5.
 */
public enum Categoria {
    ANIMALES("Animales"),
    PAISES("Países"),
    DEPORTES("Deportes"),
    OBJETOS("Objetos"),
    COMIDAS("Comidas");

    private final String nombreLegible;

    Categoria(String nombreLegible) {
        this.nombreLegible = nombreLegible;
    }

    /** Nombre para mostrar en la interfaz. */
    public String getNombreLegible() {
        return nombreLegible;
    }

    /**
     * Convierte un texto del archivo (ej. "ANIMALES") a su enum.
     * @throws IllegalArgumentException si no corresponde a ninguna categoría.
     */
    public static Categoria desde(String texto) {
        return Categoria.valueOf(texto.trim().toUpperCase());
    }
}
