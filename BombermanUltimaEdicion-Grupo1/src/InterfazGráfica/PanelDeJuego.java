package InterfazGráfica;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Clase encargada de generar la pantalla del juego
 */
public class PanelDeJuego extends JPanel {

    private JuegoIG juegoIG;

    public PanelDeJuego(Frame frame) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(JuegoIG.ANCHO * JuegoIG.ESCALA, JuegoIG.ALTURA * JuegoIG.ESCALA));
        juegoIG = new JuegoIG(frame);
        add(juegoIG);
        juegoIG.setVisible(true);
        setVisible(true);
        setFocusable(true);
    }

    public JuegoIG obtenerJuego() {
        return juegoIG;
    }

}

