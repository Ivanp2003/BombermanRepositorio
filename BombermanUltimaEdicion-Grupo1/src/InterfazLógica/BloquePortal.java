package InterfazLógica;

import InterfazGráfica.Sprite;

/**
 * Clase que genera un contenedor con la capacidad de no destruirse y de poder avanzar al siguiente nivel al jugador
 */
public class BloquePortal extends Contenedor {

    protected ControladorLógico controladorLógico;

    public BloquePortal(int posicionX, int posicionY, ControladorLógico controladorLógico, Sprite sprite) {
        super(posicionX, posicionY, sprite);
        this.controladorLógico = controladorLógico;
    }

    @Override
    public boolean colisionar(ElementoDelMapa elementoDelMapa) {

        if(elementoDelMapa instanceof Jugador) {

            if(controladorLógico.noHayEnemigos() == false)
                return false;

            if(elementoDelMapa.obtenerContenedorEnX() == getX() && elementoDelMapa.obtenerContenedorEnY() == getY()) {
                if(controladorLógico.noHayEnemigos())
                    controladorLógico.siguienteNivel();
            }

            return true;
        }

        return false;
    }

}
