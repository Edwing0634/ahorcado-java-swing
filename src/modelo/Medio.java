package modelo;

/** Nivel medio (default): 7 intentos como pide el enunciado. */
public class Medio extends NivelDificultad {

    @Override
    public int getIntentosMaximos() {
        return 7;
    }

    @Override
    public int getPistasDisponibles() {
        return 2;
    }

    @Override
    public String getNombre() {
        return "Medio";
    }

    @Override
    protected boolean aplicaLongitud(int n) {
        return n >= 6 && n <= 8;
    }
}
