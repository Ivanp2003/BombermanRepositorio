package InterfazLógica;

import InterfazLógica.Exceptions.CargarNivelException;

/**
 * El objetivo de esta interfaz es el de cargar la ruta del nivel respectivo. Simplemente carga la ruta.
 */
public interface RutaNivel {

    public void cargarNivel(String ruta) throws CargarNivelException;
}