package InterfazGráfica;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Clase para detectar las teclas presionadas por el jugador durante la ejecución del juego
 */
public class Keyboard implements KeyListener {

    private boolean[] keys = new boolean[120]; //120 es suficiente para este juego.
    public boolean arriba, abajo, izquierda, derecha, espacio;

    public void actualizarTeclas() {
        arriba = keys[KeyEvent.VK_W];// || keys[KeyEvent.VK_UP], podemos añadir mas comandos
        abajo = keys[KeyEvent.VK_S];
        izquierda = keys[KeyEvent.VK_A];
        derecha = keys[KeyEvent.VK_D];
        espacio = keys[KeyEvent.VK_SPACE];
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

}
