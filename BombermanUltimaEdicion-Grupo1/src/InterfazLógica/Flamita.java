package InterfazLógica;

import InterfazGráfica.JuegoIG;
import InterfazGráfica.Sprite;
/**
 * Esta clase se encarga de gestionar a la Flamita. Tanto de gestionar su movimiento como de
 * gestionar su visualización.
 */
public class Flamita extends Enemigo {


    public Flamita(int posicionX, int posicionY, ControladorLógico controladorLógico) {
        super(posicionX, posicionY, controladorLógico, Sprite.flamitaMuere, JuegoIG.obtenerVelocidadDelJugador(), 400);

        sprite = Sprite.flamitaFrente2;

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
                if(seEstaMovimiento)
                    sprite = Sprite.moviendoSprite(Sprite.flamitaFrente2, Sprite.flamitaDerecha2, Sprite.flamitaIzquierda2, animación, 60);
                else
                    sprite = Sprite.flamitaFrente1;
                break;
            case 2:
            case 3:
                if(seEstaMovimiento)
                    sprite = Sprite.moviendoSprite(Sprite.flamitaFrente1, Sprite.flamitaDerecha1, Sprite.flamitaIzquierda1, animación, 60);
                else
                    sprite = Sprite.flamitaFrente1;
                break;
        }
    }
}
