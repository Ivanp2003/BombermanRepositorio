package InterfazLógica;

import InterfazGráfica.Sprite;

/**
 * Clase que genera un contenedor con la capacidad de no destruirse, ocupada para bordear el mapa
 * y estar en ciertos lugares del mapa
 */

public class BloquePared extends Contenedor {

    public BloquePared(int posicionx, int posicionY, Sprite sprite) {
        super(posicionx, posicionY, sprite);
    }

}
