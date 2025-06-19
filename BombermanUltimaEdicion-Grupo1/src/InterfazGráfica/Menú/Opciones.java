package InterfazGráfica.Menú;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import InterfazGráfica.Frame;

/**
 * Clase que contiene la opción opciones del menú, en la cual se genera una acción dependiendo
 * de si se presina en Pausar o Reanudar
 */

public class Opciones extends JMenu  {
    Frame frame;
    public Opciones(Frame frame) {
        super("Opciones");

        this.frame = frame;

        JMenuItem pausar = new JMenuItem("Pausar");
        pausar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        pausar.addActionListener(new MenuActionListener(frame));
        add(pausar);

        JMenuItem reanudar = new JMenuItem("Reanudar");
        reanudar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        reanudar.addActionListener(new MenuActionListener(frame));
        add(reanudar);

        addSeparator();
    }


    class MenuActionListener implements ActionListener {
        public Frame frame;
        public MenuActionListener(Frame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand().equals("Pausar")) {
                frame.pausarJuego();
            }

            if(e.getActionCommand().equals("Reanudar")) {
                frame.reanudarJuego();
            }
        }
    }
}
