package InterfazGráfica;

import java.awt.Color;
import InterfazLógica.ElementoDelMapa;

/**
 * Esta clase se encargara unicamente de mostrar un pequeño mensaje en el momento en el que un personaje
 * muera.
 */
public class Mensaje extends ElementoDelMapa {

    protected String mensaje;
    protected int duración;
    protected Color color;
    protected int tamaño;

    public Mensaje(String mensaje, double posicionX, double posicionY, int duración, Color color, int tamaño) {
        this.posicionX =posicionX;
        this.posicionY = posicionY;
        this.mensaje = mensaje;
        this.duración = duración * 60; //segundos
        this.color = color;
        this.tamaño = tamaño;
    }

    public int obtenerDuraciónDelMensaje() {
        return duración;
    }

    public void generarDuraciónDelMensaje(int duraciónDelMensaje) {
        this.duración = duraciónDelMensaje;
    }

    public String obtenerMensaje() {
        return mensaje;
    }

    public Color obtenerColorDelMensaje() {
        return color;
    }

    public int obtenerTamañoDelMensaje() {
        return tamaño;
    }

    @Override
    public void actualizar() {
    }

    @Override
    public void renderizar(Pantalla pantalla) {
    }

    @Override
    public boolean colisionar(ElementoDelMapa elementoDelMapa) {
        return true;
    }

}
