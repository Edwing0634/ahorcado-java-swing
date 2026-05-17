package controlador;

import modelo.BancoPalabras;
import modelo.NivelDificultad;
import modelo.Partida;
import modelo.ResultadoIntento;
import vista.PantallaFin;
import vista.PantallaInicio;
import vista.PantallaJuego;
import vista.VentanaPrincipal;

/**
 * Une la vista con el modelo. Crea las pantallas, registra los
 * eventos y mantiene la partida en curso. Único punto de contacto
 * entre la capa de vista y la capa de modelo.
 */
public class ControladorJuego {

    private final VentanaPrincipal ventana;
    private final BancoPalabras banco;
    private final PantallaJuego pantallaJuego;
    private final PantallaFin pantallaFin;

    private Partida partida;

    public ControladorJuego(BancoPalabras banco) {
        this.banco = banco;
        this.ventana = new VentanaPrincipal();
        this.pantallaJuego = new PantallaJuego();
        this.pantallaFin = new PantallaFin(this::irAInicio, this::salir);

        PantallaInicio pantallaInicio =
                new PantallaInicio(this::iniciarPartida, this::salir);

        ventana.agregarPantalla(pantallaInicio, VentanaPrincipal.INICIO);
        ventana.agregarPantalla(pantallaJuego, VentanaPrincipal.JUEGO);
        ventana.agregarPantalla(pantallaFin, VentanaPrincipal.FIN);

        pantallaJuego.alPulsarLetra(this::procesarLetra);
        pantallaJuego.alUsarPista(this::procesarPista);
    }

    /** Muestra la ventana en la pantalla de inicio. */
    public void iniciar() {
        ventana.mostrar(VentanaPrincipal.INICIO);
        ventana.setVisible(true);
    }

    private void iniciarPartida(NivelDificultad nivel) {
        partida = new Partida(banco.palabraAleatoria(nivel), nivel);
        pantallaJuego.habilitarTeclado(true);
        pantallaJuego.setCategoria(" ");
        pantallaJuego.setMensajePista(" ");
        pantallaJuego.setLetrasFalladas("");
        pantallaJuego.setErrores(0);
        pantallaJuego.setPistasRestantes(partida.getPistasRestantes());
        actualizarVista();
        ventana.mostrar(VentanaPrincipal.JUEGO);
    }

    private void procesarLetra(char letra) {
        if (partida == null || partida.estaTerminada()) {
            return;
        }
        ResultadoIntento r = partida.intentarLetra(letra);
        if (r == ResultadoIntento.YA_USADA) {
            return;
        }
        pantallaJuego.marcarLetra(letra, r == ResultadoIntento.ACIERTO);
        actualizarVista();
        if (partida.estaTerminada()) {
            terminarPartida();
        }
    }

    private void procesarPista() {
        if (partida == null || partida.estaTerminada()) {
            return;
        }
        String mensaje = partida.usarPista();
        pantallaJuego.setMensajePista(mensaje);
        pantallaJuego.setPistasRestantes(partida.getPistasRestantes());
        actualizarVista();
        if (partida.estaTerminada()) {
            terminarPartida();
        }
    }

    private void actualizarVista() {
        pantallaJuego.setPalabraVisible(partida.getPalabraVisible());
        pantallaJuego.setIntentos(partida.getIntentosRestantes(),
                partida.getNivel().getIntentosMaximos());
        pantallaJuego.setErrores(partida.getErroresCometidos());
        StringBuilder sb = new StringBuilder();
        for (char c : partida.getLetrasFalladas()) {
            sb.append(Character.toUpperCase(c)).append(' ');
        }
        pantallaJuego.setLetrasFalladas(sb.toString().trim());
    }

    private void terminarPartida() {
        pantallaJuego.habilitarTeclado(false);
        pantallaFin.mostrarResultado(partida.estaGanada(),
                partida.getPalabra().getTexto());
        ventana.mostrar(VentanaPrincipal.FIN);
    }

    private void irAInicio() {
        ventana.mostrar(VentanaPrincipal.INICIO);
    }

    private void salir() {
        System.exit(0);
    }
}
