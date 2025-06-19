package InterfazLógica;

import InterfazGráfica.JuegoIG;
import InterfazGráfica.Sprite;

/**
 * Esta clase se encarga de gestionar al Rinoceronete. Tanto de gestionar su movimiento como de
 * gestionar su visualización.
 */
public class Rinoceronte extends Enemigo {
    public Rinoceronte(int posiciónX, int posiciónY, ControladorLógico controladorLógico) {
        super(posiciónX, posiciónY, controladorLógico, Sprite.rinoceronteMuere, JuegoIG.obtenerVelocidadDelJugador() * 2, 800);
        sprite = Sprite.rinoceronteFrente2;
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
                    sprite = Sprite.moviendoSprite(Sprite.rinoceronteFrente2, Sprite.rinoceronteDerecha2, Sprite.rinoceronteIzquierda2, animación, 60);
                else
                    sprite = Sprite.rinoceronteFrente1;
                break;
            case 2:
            case 3:
                if(seEstaMovimiento)
                    sprite = Sprite.moviendoSprite(Sprite.rinoceronteFrente1, Sprite.rinoceronteDerecha1, Sprite.rinoceronteIzquierda1, animación, 60);
                else
                    sprite = Sprite.rinoceronteFrente1;
                break;
        }
    }
}