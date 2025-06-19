package InterfazGráfica;

/**
 * Define una estructura que permite a las clases que la implementen
 * actualizar su estado y dibujarse en la pantalla del juego.
 */
public interface Renderización {

    /**
     *  Este método se utiliza para actualizar el estado de un objeto o componente en el juego.
     */
    public void actualizar();

    /**
     * Este método se utiliza para renderizar o dibujar el objeto en la pantalla del juego.
     * @param pantalla
     */
    public void renderizar(Pantalla pantalla);
}
