package InterfazLógica;

import InterfazGráfica.Coordenadas;
import InterfazGráfica.Sprite;
import InterfazGráfica.Pantalla;
import InterfazGráfica.Renderización;

/**
 * Esta clase se encarga de controlar los elementos del mapa, siendo esta una clase Padre.
 */
public abstract class ElementoDelMapa implements Renderización {

    protected double posicionX, posicionY;
    protected boolean removerElemento = false;
    protected Sprite sprite;

    /**
     * Actualiza el estado de los elementos del mapa en el juego
     */
    @Override
    public abstract void actualizar();

    /**
     * Redibuja los elementos del mapa.
     * @param pantalla
     */
    @Override
    public abstract void renderizar(Pantalla pantalla);

    public void removerElemento() {
        removerElemento = true;
    }

    public boolean estaRemovida() {
        return removerElemento;
    }

    public Sprite obtenerSprite() {
        return sprite;
    }

    public abstract boolean colisionar(ElementoDelMapa elementoDelMapa);

    public double getX() {
        return posicionX;
    }

    public double getY() {
        return posicionY;
    }

    /**
     *  Calculan y devuelven las coordenadas del contenedor (celda del mapa) en
     *  las que se encuentra este elemento. Esto se utiliza para gestionar la
     *  posición del elemento en la cuadrícula del juego.
     * @return
     */
    public int obtenerContenedorEnX() {
        return Coordenadas.tranformarDePixelAContenedor(posicionX + sprite.TAMAÑO / 2); //más medio bloque para mayor precisión
    }

    public int obtenerContenedorEnY() {
        return Coordenadas.tranformarDePixelAContenedor(posicionY - sprite.TAMAÑO / 2); //mas media cuadra
    }
}

