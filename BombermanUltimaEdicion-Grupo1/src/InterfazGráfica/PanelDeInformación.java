package InterfazGráfica;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase encargada de mostrar en pantalla el tiempo, puntaje y vida del personaje durante la ejecución
 */

public class PanelDeInformación extends JPanel {

    private JLabel tiempoLabel;
    private JLabel puntosLabel;
    private JLabel vidasLabel;

    public PanelDeInformación(JuegoIG JuegoIG) {
        setLayout(new GridLayout());

        tiempoLabel = new JLabel("TIEMPO: " + JuegoIG.extraerControladorLógico().obtenerTiempo());
        tiempoLabel.setForeground(Color.white);
        tiempoLabel.setHorizontalAlignment(JLabel.CENTER);

        puntosLabel = new JLabel("PUNTOS: " + JuegoIG.extraerControladorLógico().obtenerPuntos());
        puntosLabel.setForeground(Color.white);
        puntosLabel.setHorizontalAlignment(JLabel.CENTER);

        vidasLabel = new JLabel("VIDAS: " + JuegoIG.extraerControladorLógico().obtenerVidasDelJugador());
        vidasLabel.setForeground(Color.white);
        vidasLabel.setHorizontalAlignment(JLabel.CENTER);

        add(tiempoLabel);
        add(puntosLabel);
        add(vidasLabel);


        setBackground(Color.darkGray);
        setPreferredSize(new Dimension(0, 40));
    }

    public void colocarTiempo(int tiempo) {
        tiempoLabel.setText("TIEMPO: " + tiempo);
    }

    public void colocarVidas(int vidas) {
        vidasLabel.setText("VIDAS: " + vidas);

    }

    public void colocarPuntos(int puntos) {
        puntosLabel.setText("PUNTOS: " + puntos);
    }

}

