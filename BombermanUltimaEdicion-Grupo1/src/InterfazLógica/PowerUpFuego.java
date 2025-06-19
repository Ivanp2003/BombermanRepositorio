package InterfazLógica;

import InterfazGráfica.JuegoIG;
import InterfazGráfica.Sprite;

/**
 * Esta clase se encarga de gestionar unicamente el PowerUp de Fuego.
 */
public class PowerUpFuego extends PowerUp {
    public PowerUpFuego(int posicionX, int posicionY, int nivel, Sprite sprite) {
        super(posicionX, posicionY, nivel, sprite);
    }

    /**
     * Este metodo en esta clase se encarga de controlar que el jugador sea quien adquiera
     * el PowerUp.
     * @param elementoDelMapa: Elemento del mapa con el que hace contacto.
     * @return un boolean.
     */
    @Override
    public boolean colisionar(ElementoDelMapa elementoDelMapa) {
        if(elementoDelMapa instanceof Jugador) {
            ((Jugador) elementoDelMapa).agregarPowerUp(this);
            removerElemento();
            return true;
        }
        return false;
    }

    /**
     * Este metodo se encarga de implementar el PowerUp; en este caso, aumentando la velocidad.
     */
    @Override
    public void colocarValoresDePowerUp() {
        activarPowerUp = true;
        JuegoIG.agregarAlcanceDeBomba(1);
    }
}