package InterfazLógica.Exceptions;

/**
 * Esta excepcion se encarga de que cuando se vaya pasando de nivel, si llega a un nivel que no
 * esta en los txt, en lugar de que tire error mandara a que termine el juego.
 */
public class CargarNivelException extends Exception {

    public CargarNivelException(String str, Throwable cause) {
        super(str, cause);
    }

}
