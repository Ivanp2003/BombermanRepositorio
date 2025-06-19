package InterfazGráfica;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import InterfazLógica.ControladorLógico;

/**
 * Se encarga de la gestión del ciclo de vida y la ejecución del juego de Bomberman,
 * incluyendo la actualización de la lógica del juego y la renderización en la pantalla.
 * También gestiona la configuración del juego y la interacción del usuario a través del teclado.
 */
public class JuegoIG extends Canvas {

    public static final double VERSION = 1.9;

    public static final int TAMAÑO_CONTENEDORES = 16,
            ANCHO = TAMAÑO_CONTENEDORES * (int)(31 / 2), //menos uno para ajustar la ventana
            ALTURA = 13 * TAMAÑO_CONTENEDORES;

    public static int ESCALA = 3;

    public static final String TITULO = "Bomberman " + VERSION;

    //Configuraciones Iniciales
    private static final int CANTIDAD_BOMBAS = 1;
    private static final int ALCANCE_BOMBA = 1;
    private static final double VELOCIDAD_JUGADOR = 1.0;

    public static final int TIEMPO = 300;
    public static final int PUNTOS = 0;
    public static final int VIDAS = 2;

    protected static int RETRASO_PANTALLA = 3;

    public static int cantidadDeBombas = CANTIDAD_BOMBAS;
    public static int alcanceDeBomba = ALCANCE_BOMBA;
    public static double velocidadDelJugador = VELOCIDAD_JUGADOR;

    //Tiempo en el que aparece la pantalla
    protected int retrasoPantalla = RETRASO_PANTALLA;

    private Keyboard tecla;
    private boolean correr = false;
    private boolean pausar = true;

    private ControladorLógico controladorLógico;
    private Pantalla pantalla;
    private Frame frame;

    //Esto se usará para renderizar el juego, cada renderizado es una imagen calculada guardada aquí
    private BufferedImage imagen = new BufferedImage(ANCHO, ALTURA, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) imagen.getRaster().getDataBuffer()).getData();

    public JuegoIG(Frame frame) {
        this.frame = frame;
        this.frame.setTitle(TITULO);

        pantalla = new Pantalla(ANCHO, ALTURA);
        tecla = new Keyboard();

        controladorLógico = new ControladorLógico(this, tecla, pantalla);
        addKeyListener(tecla);
    }


    /**
     * Renderiza las pantallas del juego, como la de pausa o cambio de nivel.
     */
    private void renderizarJuego() { //El render se ejecutará el máximo de veces posible por segundo.
        BufferStrategy buffer = getBufferStrategy(); //crear un búfer para almacenar imágenes usando Canva
        if(buffer == null) {
            createBufferStrategy(3);
            return;
        }
        pantalla.limpiar();
        controladorLógico.renderizar(pantalla);

        for (int i = 0; i < pixels.length; i++) { //crear la imagen a renderizar
            pixels[i] = pantalla.pixels[i];
        }

        Graphics gráfico = buffer.getDrawGraphics();

        gráfico.drawImage(imagen, 0, 0, getWidth(), getHeight(), null);
        controladorLógico.renderizarMensajes(gráfico);

        gráfico.dispose(); //liberar recursos
        buffer.show(); //hacer visible el siguiente buffer
    }

    /**
     * Renderiza las pantallas del juego, como la de pausa o cambio de nivel.
     */
    private void renderizarPantalla() {
        BufferStrategy buffer = getBufferStrategy();
        if(buffer == null) {
            createBufferStrategy(3);
            return;
        }
        pantalla.limpiar();
        Graphics gráficos = buffer.getDrawGraphics();
        controladorLógico.dibujarPantallas(gráficos);
        gráficos.dispose();
        buffer.show();
    }

    /**
     * Actualiza el estado del juego, incluyendo la entrada del teclado y la lógica del juego.
     */
    private void actualizarJuego() {
        tecla.actualizarTeclas();
        controladorLógico.actualizar();
    }

    /**
     * Inicia el ciclo de juego, actualizando y renderizando el juego en un bucle principal.
     */
    public void empezarJuego() {
        correr = true;

        long  últimoTiempo = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double nanoSegundos = 1000000000.0 / 60.0; //fps
        double delta = 0;
        requestFocus();
        while(correr) {
            long ahora = System.nanoTime();
            delta += (ahora - últimoTiempo) / nanoSegundos;
            últimoTiempo = ahora;
            while(delta >= 1) {
                actualizarJuego();
                delta--;
            }

            if(pausar) {
                if(retrasoPantalla <= 0) { //¿pasó el tiempo? Vamos a restablecer el estado para mostrar el juego.
                    controladorLógico.setShow(-1);
                    pausar = false;
                }
                renderizarPantalla();
            } else {
                renderizarJuego();
            }

            ;
            if(System.currentTimeMillis() - timer > 1000) { //once per second
                frame.obtenerTiempo(controladorLógico.subtractTime());
                frame.obtenerPuntos(controladorLógico.obtenerPuntos());
                frame.obtenerVidas(controladorLógico.obtenerVidasDelJugador());
                timer += 1000;
                frame.setTitle(TITULO);

                if(controladorLógico.getShow() == 2)
                    --retrasoPantalla;
            }
        }
    }

    public static double obtenerVelocidadDelJugador() {
        return velocidadDelJugador;
    }

    public static int obtenerCantidadDeBombas() {
        return cantidadDeBombas;
    }

    public static int obtenerAlcanceDeBomba() {
        return alcanceDeBomba;
    }

    public static void agregarvelocidadAJugador(double aumentoDeVelocidad) {
        velocidadDelJugador += aumentoDeVelocidad;
    }

    public static void agregarAlcanceDeBomba(int aumentoDeAlcance) {
        alcanceDeBomba += aumentoDeAlcance;
    }

    public static void agregarCantidadDeBombasExtra(int bombasExtra) {
        cantidadDeBombas += bombasExtra;
    }
    public int obtenerRetrasoDePantalla() {
        return retrasoPantalla;
    }

    public void decrecerRetrasoDePantalla() {
        retrasoPantalla--;
    }

    public void reiniciarRetrasoDePantalla() {
        retrasoPantalla = RETRASO_PANTALLA;
    }

    public Keyboard obtenerTeclas() {
        return tecla;
    }

    public ControladorLógico extraerControladorLógico() {
        return controladorLógico;
    }

    public void correrJuego() {
        correr = true;
        pausar = false;
    }

    public void parar() {
        correr = false;
    }

    public boolean estaCorriendoElJuego() {
        return correr;
    }

    public boolean estaPausadoElJuego() {
        return pausar;
    }

    public void pausar() {
        pausar = true;
    }

}


