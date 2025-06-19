package InterfazLógica;

import InterfazGráfica.JuegoIG;
import InterfazGráfica.Sprite;

/**
 * Esta clase se encarga de gestionar al BuhoEPN. Tanto de gestionar su movimiento como de
 * gestionar su visualización.
 */
public class BuhoEPN extends Enemigo {


    public BuhoEPN(int posicionX, int posicionY, ControladorLógico controladorLógico) {
        super(posicionX, posicionY, controladorLógico, Sprite.buhoMuere, JuegoIG.obtenerVelocidadDelJugador() / 2, 100);

        sprite = Sprite.buhoFrente1;

        IA = new IABaja();
        direccion = IA.calcularDirección();
    }

    /**
     * Este metodo tiene como funcion el obtener el Sprite respectivo del personaje.
     * @param
     * @return
     */
    @Override
    protected void elegirSprite() {
        switch(direccion) {
            case 0:
            case 1:
                sprite = Sprite.moviendoSprite(Sprite.buhoFrente2, Sprite.buhoIzquierda2, Sprite.buhoDerecha2, animación, 60);
                break;
            case 2:
            case 3:
                sprite = Sprite.moviendoSprite(Sprite.buhoFrente1, Sprite.buhoIzquierda1, Sprite.buhoDerecha1, animación, 60);
                break;
        }
    }
}
