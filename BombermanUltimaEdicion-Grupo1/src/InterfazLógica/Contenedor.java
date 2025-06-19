package InterfazLógica;

import InterfazGráfica.Coordenadas;
import InterfazGráfica.Pantalla;
import InterfazGráfica.Sprite;

/**
 * Clase abstracta que hace referencia al bloque
 */
public abstract class Contenedor extends ElementoDelMapa {

    public Contenedor(int x, int y, Sprite sprite) {
        posicionX = x;
        posicionY = y;
        this.sprite = sprite;
    }

    @Override
    public boolean colisionar(ElementoDelMapa elementoDelMapa) {
        return false;
    }

    @Override
    public void renderizar(Pantalla pantalla) {
        pantalla.renderizarContenedor( Coordenadas.tranformarDeContenedorAPixel(posicionX), Coordenadas.tranformarDeContenedorAPixel(posicionY), this);
    }
    @Override
    public void actualizar() {}
}
