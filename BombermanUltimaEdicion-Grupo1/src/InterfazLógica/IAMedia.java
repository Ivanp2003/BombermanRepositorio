package InterfazLógica;

/**
 * Esta clase se encarga de manejar la IA de los enemigos, dandoles movimientos aleatorios, detectando
 * y persiguiendo al enemigo.
 */
public class IAMedia extends IA {
    Jugador jugador;
    Enemigo enemigo;

    public IAMedia(Jugador jugador, Enemigo enemigo) {
        this.jugador = jugador;
        this.enemigo = enemigo;
    }

    /**
     * Calcula la dirección del movimiento de un enemigo entorno a la del jugador.
     * @return
     */
    @Override
    public int calcularDirección() {
        if(jugador == null)
            return random.nextInt(4);

        int vertical = random.nextInt(2);

        if(vertical == 1) {
            int v = calcularDirecciónDeFila();
            if(v != -1)
                return v;
            else
                return calcularDirecciónDeColumna();

        } else {
            int h = calcularDirecciónDeColumna();

            if(h != -1)
                return h;
            else
                return calcularDirecciónDeFila();
        }
    }

    protected int calcularDirecciónDeColumna() {
        if(jugador.obtenerContenedorEnX() < enemigo.obtenerContenedorEnX())
            return 3;
        else if(jugador.obtenerContenedorEnX() > enemigo.obtenerContenedorEnX())
            return 1;

        return -1;
    }

    protected int calcularDirecciónDeFila() {
        if(jugador.obtenerContenedorEnY() < enemigo.obtenerContenedorEnY())
            return 0;
        else if(jugador.obtenerContenedorEnY() > enemigo.obtenerContenedorEnY())
            return 2;
        return -1;
    }


}
