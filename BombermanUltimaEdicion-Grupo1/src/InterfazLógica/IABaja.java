package InterfazLógica;

/**
 * Esta clase se encarga de manejar la IA de los enemigos, dandoles movimientos aleatorios.
 */
public class IABaja extends IA {

    @Override
    public int calcularDirección() {
        return random.nextInt(4);
    }

}