package InterfazGráfica;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class SpriteSheet {

    private String ruta;
    public final int TAMAÑO;
    public int[] pixels;

    public static SpriteSheet contenedor = new SpriteSheet("/Sprites/HojaDeSprites.png", 256);

    public SpriteSheet(String ruta, int tamaño) {
        this.ruta = ruta;
        TAMAÑO = tamaño;
        pixels = new int[TAMAÑO * TAMAÑO];
        cargar();
    }

    private void cargar() {
        try {
            URL a = SpriteSheet.class.getResource(ruta);
            BufferedImage imagen = ImageIO.read(a);
            int ancho = imagen.getWidth();
            int altura = imagen.getHeight();
            imagen.getRGB(0, 0, ancho, altura, pixels, 0, ancho);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
