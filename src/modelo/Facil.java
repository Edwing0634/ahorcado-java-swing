package modelo;

/** Nivel fácil: muchos intentos, palabras cortas, todas las pistas. */
public class Facil extends NivelDificultad {

    @Override
    public int getIntentosMaximos() {
        return 8;
    }

    @Override
    public int getPistasDisponibles() {
        return 3;
    }

    @Override
    public String getNombre() {
        return "Fácil";
    }

    @Override
    protected boolean aplicaLongitud(int n) {
        return n >= 3 && n <= 5;
    }
}
