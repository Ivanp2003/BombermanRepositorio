package InterfazGráfica;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import InterfazGráfica.Menú.Menú;

/**
 * Clase encargada de generar la pantalla en general y ubicar cada componenete en su respectivo espacio
 */

public class Frame extends JFrame {

    public PanelDeJuego panelDeJuego;
    private JPanel panelContenedor;
    private PanelDeInformación panelDeInformación;

    private JuegoIG juegoIG;

    public Frame() {
        setIconImage(getIconImage());
        setJMenuBar(new Menú(this));

        panelContenedor = new JPanel(new BorderLayout());
        panelDeJuego = new PanelDeJuego(this);
        panelDeInformación = new PanelDeInformación(panelDeJuego.obtenerJuego());

        panelContenedor.add(panelDeInformación, BorderLayout.PAGE_START);
        panelContenedor.add(panelDeJuego, BorderLayout.PAGE_END);

        juegoIG = panelDeJuego.obtenerJuego();

        add(panelContenedor);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        juegoIG.empezarJuego();
    }

    /**
     * Método encargado de general el juego
     */
    public void nuevoJuego() {
        juegoIG.extraerControladorLógico().nuevoJuego();
    }

    public void pausarJuego() {
        juegoIG.extraerControladorLógico().pausarJuego();
    }

    public void reanudarJuego() {
        juegoIG.extraerControladorLógico().reanudarJuego();
    }

    public void obtenerTiempo(int time) {
        panelDeInformación.colocarTiempo(time);
    }

    public void obtenerVidas(int lives) {
        panelDeInformación.colocarVidas(lives);
    }

    public void obtenerPuntos(int points) {
        panelDeInformación.colocarPuntos(points);
    }

    /**
     * Este metodo se encarga simplemente de modificar el icono del juego.
     * @return icono
     */
    @Override
    public Image getIconImage(){
        Image icono = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Imagenes/icono.jpg"));
        return icono;
    }

}

