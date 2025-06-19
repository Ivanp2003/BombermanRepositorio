package InterfazLógica;

import InterfazLógica.Exceptions.CargarNivelException;

/**
 * Esta clase se encarga de gestionar cada uno de los niveles, desde el tamaño hasta el nivel en si.
 */
public abstract class Nivel implements RutaNivel {
    protected int ancho, alto, nivel;
    protected String[] lineaDeContenedor;
    protected ControladorLógico controladorLógico;

    public Nivel(String ruta, ControladorLógico controladorLógico) throws CargarNivelException {
        cargarNivel(ruta);
        this.controladorLógico = controladorLógico;
    }

    @Override
    public abstract void cargarNivel(String ruta) throws CargarNivelException;

    /**
     * Este metodo crea el espacio en donde se depositaran los elementos del nivel.
     */
    public abstract void crearContenedor();

    public int obtenerAncho() {
        return ancho;
    }

    public int obtenerAltura() {
        return alto;
    }

    public int obtenerNivel() {
        return nivel;
    }
}
