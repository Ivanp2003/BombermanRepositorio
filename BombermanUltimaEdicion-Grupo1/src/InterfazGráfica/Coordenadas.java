package InterfazGráfica;

/**
 * Proporciona métodos estáticos para realizar conversiones entre coordenadas en píxeles
 * y coordenadas en contenedores en el contexto del juego. Estos métodos son útiles
 * para convertir entre diferentes sistemas de coordenadas dentro del juego.
 */
public class Coordenadas {

    /**
     * Este método toma una coordenada en píxeles (representada como un número decimal)
     * y la convierte en una coordenada en contenedores (un número entero).
     * @param i
     * @return
     */
    public static int tranformarDePixelAContenedor(double i) {
        return (int)(i / JuegoIG.TAMAÑO_CONTENEDORES);
    }

    /**
     * Este método toma una coordenada en contenedores (un número entero) y la convierte
     * en una coordenada en píxeles (un número entero).
     * @param i
     * @return
     */
    public static int tranformarDeContenedorAPixel(int i) {
        return i * JuegoIG.TAMAÑO_CONTENEDORES;
    }

    /**
     * Este método es similar al anterior, pero toma una coordenada en contenedores
     * representada como un número decimal y la convierte en una coordenada en
     * píxeles (un número entero) utilizando la constante
     * @param i
     * @return
     */
    public static int tranformarDeContenedorAPixel(double i) {
        return (int)(i * JuegoIG.TAMAÑO_CONTENEDORES);
    }


}
