package InterfazLógica;

import java.util.Random;

/**
 * Esta clase sirve como clase Padre de la IA Baja e IA media, sienda estas los niveles de
 * dificultad de cada enemigo.
 */
public abstract class IA {

    protected Random random = new Random();

    public abstract int calcularDirección();
}