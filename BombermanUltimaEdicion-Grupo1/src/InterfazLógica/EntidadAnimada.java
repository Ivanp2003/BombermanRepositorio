package InterfazLógica;

/**
 * Esta clase se encarga de gestionar los elementos del mapa que tengan movimiento.
 */
public abstract class EntidadAnimada extends ElementoDelMapa {
    protected int animación = 0;
    protected final int MAX_ANIMACIÓN = 7500; //guarda el estado de la animación
    protected void animar() {
        if(animación < MAX_ANIMACIÓN) animación++; else animación = 0; //resetea la animación
    }

}