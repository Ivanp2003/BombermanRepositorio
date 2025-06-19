package InterfazLógica;

import InterfazGráfica.Sprite;

/**
 * Clase que genera un contenedor el cual no se puede destruir y será el espacio por el cual camine Bomberman
 */
public class BloquePiso extends Contenedor {

    public BloquePiso(int posicionX, int posicionY, Sprite sprite) {
        super(posicionX, posicionY, sprite);
    }

    @Override
    public boolean colisionar(ElementoDelMapa elementoDelMapa) {
        return true;
    }
}
