package InterfazGráfica.Menú;

import javax.swing.JMenuBar;

import InterfazGráfica.Frame;

/**
 * Clase que en la pantalla genera una barra de menú la cual me genera acciones conforme a los botones seleccionados
 *
 */

public class Menú extends JMenuBar {

    public Menú(Frame frame) {
        add( new Juego(frame) );
        add( new Opciones(frame) );
    }

}
