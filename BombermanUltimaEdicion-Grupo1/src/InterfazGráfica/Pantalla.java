package InterfazGráfica;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import InterfazLógica.ControladorLógico;
import InterfazLógica.ElementoDelMapa;
import InterfazLógica.Jugador;



/**
 * Proporciona funcionalidades para renderizar elementos en la pantalla del juego,
 * incluyendo la capacidad de dibujar texto y gestionar el desplazamiento (scrolling)
 * de la pantalla. Esta clase es fundamental en el juego para mostrar gráficos en tiempo real.
 */

public class Pantalla {
    protected int ancho, altura;
    public int[] pixels;
    // "Ox" es en referencia a un numero hexagesimal.
    // "000000" es el codigo del color negro.

    private int colorTransparente = 0x000000;

    public static int colocarXEnOf = 0, colocarYEnOf = 0;

    public Pantalla(int ancho, int altura) {
        this.ancho = ancho;
        this.altura = altura;
        pixels = new int[ancho * altura];
    }

    /**
     * Establece todos los píxeles en la pantalla como negros, es decir, limpia la pantalla.
     */
    public void limpiar() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    /**
     * Renderiza un elemento del mapa en una posición especificada en la pantalla.
     * Los píxeles del elemento se copian en el arreglo de píxeles de la pantalla.
     * @param pixelesEnX
     * @param pixelesEnY
     * @param elementoDelMapa
     */
    public void renderizarContenedor(int pixelesEnX, int pixelesEnY, ElementoDelMapa elementoDelMapa) { //guardar píxeles de entidad
        pixelesEnX -= colocarXEnOf;
        pixelesEnY -= colocarYEnOf;
        for (int y = 0; y < elementoDelMapa.obtenerSprite().obtenerTamaño(); y++) {
            int ya = y + pixelesEnY; //agregar offset
            for (int x = 0; x < elementoDelMapa.obtenerSprite().obtenerTamaño(); x++) {
                int xa = x + pixelesEnX; //agregar offset
                if(xa < -elementoDelMapa.obtenerSprite().obtenerTamaño() || xa >= ancho || ya < 0 || ya >= altura) break; //fix black margins
                if(xa < 0) xa = 0; //start at 0 from left
                int color = elementoDelMapa.obtenerSprite().obtenerPixel(x + y * elementoDelMapa.obtenerSprite().obtenerTamaño());
                if(color != colorTransparente) pixels[xa + ya * ancho] = color;
            }
        }
    }

    /**
     * Similar a renderizarContenedor, pero si un píxel del elemento es transparente,
     * se utiliza el píxel correspondiente de otro sprite que este debajo.
     * @param pixelEnX
     * @param pixelEnY
     * @param elementoDelMapa
     * @param debajo
     */
    public void renderizarContenedorConElSiguienteSprite(int pixelEnX, int pixelEnY, ElementoDelMapa elementoDelMapa, Sprite debajo) {
        pixelEnX -= colocarXEnOf;
        pixelEnY -= colocarYEnOf;
        for (int y = 0; y < elementoDelMapa.obtenerSprite().obtenerTamaño(); y++) {
            int ya = y + pixelEnY;
            for (int x = 0; x < elementoDelMapa.obtenerSprite().obtenerTamaño(); x++) {
                int xa = x + pixelEnX;
                if(xa < -elementoDelMapa.obtenerSprite().obtenerTamaño() || xa >= ancho || ya < 0 || ya >= altura) break; //fix black margins
                if(xa < 0) xa = 0;
                int color = elementoDelMapa.obtenerSprite().obtenerPixel(x + y * elementoDelMapa.obtenerSprite().obtenerTamaño());
                if(color != colorTransparente)
                    pixels[xa + ya * ancho] = color;
                else
                    pixels[xa + ya * ancho] = debajo.obtenerPixel(x + y * debajo.obtenerTamaño());
            }
        }
    }

    /**
     * Establece el desplazamiento (offset) actual en las coordenadas X e Y de la pantalla.
     * @param xO
     * @param yO
     */
    public static void setOffset(int xO, int yO) {
        colocarXEnOf = xO;
        colocarYEnOf = yO;
    }

    /**
     * Calcula el desplazamiento en la coordenada X de la pantalla, generalmente utilizado
     * para seguir al jugador dentro del juego.
     * @param controladorLógico
     * @param jugador
     * @return
     */
    public static int calcularXOffset(ControladorLógico controladorLógico, Jugador jugador) {
        if(jugador == null) return 0;
        int temp = colocarXEnOf;

        double jugadorX = jugador.getX() / 16;
        double complemento = 0.5;
        int primerBreakpoint = controladorLógico.obtenerAnchoGeneral() / 4;
        int ultimoBreakpoint = controladorLógico.obtenerAnchoGeneral() - primerBreakpoint;

        if( jugadorX > primerBreakpoint + complemento && jugadorX < ultimoBreakpoint - complemento) {
            temp = (int) jugador.getX()  - (JuegoIG.ANCHO / 2);
        }
        return temp;
    }

    public void dibujarPantallaDeFinDeJuego(Graphics gráficos, int puntos) {
        gráficos.setColor(Color.gray);
        gráficos.fillRect(0, 0, obtenerAnchoReal(), obtenerAlturaReal());

        Font font = new Font("Impact", Font.PLAIN, 20 * JuegoIG.ESCALA);
        gráficos.setFont(font);
        gráficos.setColor(Color.black);
        dibujarPalabraCentrada("GAME OVER", obtenerAnchoReal(), obtenerAlturaReal(), gráficos);

        font = new Font("Impact", Font.PLAIN, 10 * JuegoIG.ESCALA);
        gráficos.setFont(font);
        gráficos.setColor(Color.red);
        dibujarPalabraCentrada("PUNTOS: " + puntos, obtenerAnchoReal(), obtenerAlturaReal() + (JuegoIG.TAMAÑO_CONTENEDORES * 2) * JuegoIG.ESCALA, gráficos);
    }

    public void dibujarPantallaDeCambioDeNivel(Graphics gráficos, int nivel) {
        gráficos.setColor(Color.gray);
        gráficos.fillRect(0, 0, obtenerAnchoReal(), obtenerAlturaReal());

        Font font = new Font("Impact", Font.PLAIN, 40 * JuegoIG.ESCALA);
        gráficos.setFont(font);
        gráficos.setColor(Color.black);
        dibujarPalabraCentrada("NIVEL " + nivel, obtenerAnchoReal(), obtenerAlturaReal(), gráficos);

    }

    public void dibujarPantallaDePausa(Graphics gráfico) {
        Font font = new Font("Impact", Font.PLAIN, 20 * JuegoIG.ESCALA);
        gráfico.setFont(font);
        gráfico.setColor(Color.white);
        dibujarPalabraCentrada("PAUSA", obtenerAnchoReal(), obtenerAlturaReal(), gráfico);

    }

    public void dibujarPalabraCentrada(String palabraAMostrar, int ancho, int altura, Graphics gráfico) {
        FontMetrics fm = gráfico.getFontMetrics();
        int x = (ancho - fm.stringWidth(palabraAMostrar)) / 2;
        int y = (fm.getAscent() + (altura - (fm.getAscent() + fm.getDescent())) / 2);
        gráfico.drawString(palabraAMostrar, x, y);
    }

    public int getWidth() {
        return ancho;
    }

    public int getHeight() {
        return altura;
    }

    public int obtenerAnchoReal() {
        return ancho * JuegoIG.ESCALA;
    }

    public int obtenerAlturaReal() {
        return altura * JuegoIG.ESCALA;
    }
}
