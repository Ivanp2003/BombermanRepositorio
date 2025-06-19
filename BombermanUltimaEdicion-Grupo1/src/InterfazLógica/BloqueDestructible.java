package InterfazLógica;

import InterfazGráfica.Sprite;

/**
 * Clase que hereda de Contenedor, la cual tiene la capacidad de destruirse según acciones de su entorne
 */
public class BloqueDestructible extends Contenedor {

    private final int MAX_ANIMACION = 7500;
    private int animar = 0;
    protected boolean destruido = false;
    protected int tiempoParaDesaparecer = 20;
    protected Sprite debajoDelSprite = Sprite.piso; //default

    public BloqueDestructible(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void actualizar() {
        if(destruido) {
            if(animar < MAX_ANIMACION) animar++; else animar = 0; //reset animation
            if(tiempoParaDesaparecer > 0)
                tiempoParaDesaparecer--;
            else
                removerElemento();
        }
    }

    public void destruir() {
        destruido = true;
    }

    @Override
    public boolean colisionar(ElementoDelMapa elementoDelMapa) {

        if(elementoDelMapa instanceof DirecciónDeLaExplosión)
            destruir();

        return false;
    }

    /**
     * Método encargado de colocar el sprite según algún factor externo debajo un bloque en un sprite
     * que se encuentra en estado normal
     * @param sprite es la imagen la cual se quiere pasar
     */
    public void agregarSpriteAbajo(Sprite sprite) {
        debajoDelSprite = sprite;
    }

    /**
     * @param normal hace referencia a la imagen del bloque sin que sea destruido
     * @param x1 hace referencia a la primera imagen de bloque destruyendose
     * @param x2 hace referencia a la segunaa imagen de bloque destruyendose
     * @return la imagen a cargarse
     */
    protected Sprite moviendoSprite(Sprite normal, Sprite x1, Sprite x2) {
        int calc = animar % 30;
        if(calc < 10) {
            return normal;
        }
        if(calc < 20) {
            return x1;
        }
        return x2;
    }

}
