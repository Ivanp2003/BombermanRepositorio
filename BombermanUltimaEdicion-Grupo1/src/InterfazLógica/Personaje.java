package InterfazLógica;

import InterfazGráfica.JuegoIG;
import InterfazGráfica.Pantalla;

/**
 * Esta clase controla el movimiento y las acciones de los personajes del juego, desde al jugador
 * hasta los enemigos.
 */
public abstract class Personaje extends EntidadAnimada {

    protected ControladorLógico controladorLógico;
    protected int direccion = -1;
    protected boolean estaVivo = true;
    protected boolean seEstaMovimiento = false;
    public int tiempoDespues = 80;

    public Personaje(int posicionX, int posicionY, ControladorLógico controladorLógico) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.controladorLógico = controladorLógico;
    }

    @Override
    public abstract void actualizar();

    @Override
    public abstract void renderizar(Pantalla pantalla);

    protected abstract void calcularMovimiento();

    protected abstract void movimiento(double xa, double ya);

    public abstract void matar();

    protected abstract void despuesDeMatar();

    protected abstract boolean puedeMoverse(double x, double y);

    protected double obtenerXMensaje() {
        return (posicionX * JuegoIG.ESCALA) + (sprite.TAMAÑO / 2 * JuegoIG.ESCALA);
    }

    protected double obtenerYMensaje() {
        return (posicionY * JuegoIG.ESCALA) - (sprite.TAMAÑO / 2 * JuegoIG.ESCALA);
    }

}
