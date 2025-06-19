package InterfazGráfica;

import InterfazGráfica.Sprite;
import InterfazGráfica.Pantalla;
import InterfazLógica.ControladorLógico;
import InterfazLógica.ElementoDelMapa;
import InterfazLógica.Personaje;

/**
 * Esta clase sirve para gestionar la visualizacion de la explosion de la bomba
 */
public class Explosion extends ElementoDelMapa {
    protected boolean último = false;
    protected ControladorLógico controladorLógico;
    protected Sprite sprite1, sprite2;

    public Explosion(int posicionX, int posicionY, int direción, boolean último, ControladorLógico controladorLógico) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.último = último;
        this.controladorLógico = controladorLógico;

        /**
         * Este metodo sirve para gestionar los sprites de la explosion.
         */
        switch (direción) {
            case 0:
                if(último == false) {
                    sprite = Sprite.explosionVerticalCentral;
                } else {
                    sprite = Sprite.explosionVerticalIzquierda;
                }
                break;
            case 1:
                if(último == false) {
                    sprite = Sprite.explosionHorizontalCentral;
                } else {
                    sprite = Sprite.explosionHorizontalDerecha;
                }
                break;
            case 2:
                if(último == false) {
                    sprite = Sprite.explosionVerticalCentral;
                } else {
                    sprite = Sprite.explosionVerticalDerecha;
                }
                break;
            case 3:
                if(último == false) {
                    sprite = Sprite.explosionHorizontalCentral;
                } else {
                    sprite = Sprite.explosionHorizontalIzquierda;
                }
                break;
        }
    }

    @Override
    public void renderizar(Pantalla pantalla) {
        int xt = (int) posicionX << 4;
        int yt = (int) posicionY << 4;

        pantalla.renderizarContenedor(xt, yt , this);
    }

    @Override
    public void actualizar() {}

    @Override
    public boolean colisionar(ElementoDelMapa elementoDelMapa) {
        /**
         * Este metodo sirve para que cuando la explosion alcance a algun personaje, este muera.
         */
        if(elementoDelMapa instanceof Personaje) {
            ((Personaje) elementoDelMapa).matar();
        }
        return true;
    }

}
