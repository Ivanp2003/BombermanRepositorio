package InterfazLógica;

import InterfazGráfica.JuegoIG;
import InterfazGráfica.Sprite;

/**
 * Esta clase se encarga de gestionar a Trent. Tanto de gestionar su movimiento como de
 * gestionar su visualización.
 */
public class Trent extends Enemigo {

    public Trent(int posiciónX, int posiciónY, ControladorLógico controladorLógico) {
        super(posiciónX, posiciónY, controladorLógico, Sprite.trentMuere, JuegoIG.obtenerVelocidadDelJugador(), 200);
        sprite = Sprite.trentFrente1;
        IA = new IAMedia(this.controladorLógico.obtenerJugador(), this);
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
                if(seEstaMovimiento)
                    sprite = Sprite.moviendoSprite(Sprite.trentFrente2, Sprite.trentIzquierda2, Sprite.trentDerecha2, animación, 60);
                else
                    sprite = Sprite.trentFrente1;
                break;
            case 2:
            case 3:
                if(seEstaMovimiento)
                    sprite = Sprite.moviendoSprite(Sprite.trentFrente1, Sprite.trentIzquierda1, Sprite.trentDerecha1, animación, 60);
                else
                    sprite = Sprite.trentFrente1;
                break;
        }
    }
}