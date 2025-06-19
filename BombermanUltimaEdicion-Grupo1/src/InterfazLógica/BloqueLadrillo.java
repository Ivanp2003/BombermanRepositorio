package InterfazLógica;

import InterfazGráfica.Coordenadas;
import InterfazGráfica.Sprite;
import InterfazGráfica.Pantalla;

/**
 * Clase que hereda la capacidad de destruirse con las bombas de Bomberman
 */
public class BloqueLadrillo extends BloqueDestructible {

    public BloqueLadrillo(int posicionX, int posicionY, Sprite sprite) {
        super(posicionX, posicionY, sprite);
    }

    @Override
    public void actualizar() {
        super.actualizar();
    }

    @Override
    public void renderizar(Pantalla pantalla) {
        int x = Coordenadas.tranformarDeContenedorAPixel(posicionX);
        int y = Coordenadas.tranformarDeContenedorAPixel(posicionY);

        if(destruido) {
            sprite = moviendoSprite(Sprite.bloqueExplosion1, Sprite.bloqueExplosion2, Sprite.bloqueExplosion3);

            pantalla.renderizarContenedorConElSiguienteSprite(x, y, this, debajoDelSprite);
        }
        else
            pantalla.renderizarContenedor( x, y, this);
    }

    @Override
    public boolean colisionar(ElementoDelMapa elementoDelMapa) {

        if(elementoDelMapa instanceof DirecciónDeLaExplosión)
            destruir();

        if(elementoDelMapa instanceof Rinoceronte)
            return true;

        return false;
    }


}
