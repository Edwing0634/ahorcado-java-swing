package modelo;

/** Nivel difícil: pocos intentos, palabras largas, una sola pista. */
public class Dificil extends NivelDificultad {

    @Override
    public int getIntentosMaximos() {
        return 6;
    }

    @Override
    public int getPistasDisponibles() {
        return 1;
    }

    @Override
    public String getNombre() {
        return "Difícil";
    }

    @Override
    protected boolean aplicaLongitud(int n) {
        return n >= 9;
    }
}
