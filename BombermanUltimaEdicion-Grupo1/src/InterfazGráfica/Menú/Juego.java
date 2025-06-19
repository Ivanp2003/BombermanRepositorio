package InterfazGráfica.Menú;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import InterfazGráfica.Frame;

/**
 * Clase que contiene la opción juego del menú la cual iniciara un nuevo juego en caso de presionar
 * en Nuevo Juego
 */
public class Juego extends JMenu {

    public Frame frame;

    public Juego(Frame frame) {
        super("Juego");
        this.frame = frame;
        JMenuItem nuevoJuego = new JMenuItem("Nuevo Juego");
        nuevoJuego.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        nuevoJuego.addActionListener(new MenuActionListener(frame));
        add(nuevoJuego);
    }

    class MenuActionListener implements ActionListener {
        public Frame frame;
        public MenuActionListener(Frame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand().equals("Nuevo Juego")) {
                frame.nuevoJuego();
            }
        }
    }
}

