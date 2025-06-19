package InterfazGráfica;

/**
 * Clase encargada de asignar todos los dibujos de los personajes a variables, esto mediante su ubicación
 * dentro de un png obtenido por SpriteSheet contenedor
 */

public class Sprite {

    public final int TAMAÑO;
    private int tamaño = 16;
    private int posicionX, posicionY;
    public int[] pixels;
    private SpriteSheet contenedor;

    //*************************** Ambiente Sprites ***************************

    public static Sprite piso = new Sprite(6, 0, SpriteSheet.contenedor);
    public static Sprite bloqueDestructible = new Sprite(7, 0, SpriteSheet.contenedor);
    public static Sprite bloqueIndestructible = new Sprite(5, 0, SpriteSheet.contenedor);
    public static Sprite portal = new Sprite(4, 0, SpriteSheet.contenedor);

    //*************************** Sprites de jugador ***************************

    public static Sprite jugadorArriba = new Sprite(0, 0, SpriteSheet.contenedor);
    public static Sprite jugadorAbajo = new Sprite(2, 0, SpriteSheet.contenedor);
    public static Sprite jugadorIzquierda = new Sprite(3, 0, SpriteSheet.contenedor);
    public static Sprite jugadorDerecha = new Sprite(1, 0, SpriteSheet.contenedor);

    public static Sprite jugadorArriba1 = new Sprite(0, 1, SpriteSheet.contenedor);
    public static Sprite jugadorArriba2 = new Sprite(0, 2, SpriteSheet.contenedor);

    public static Sprite jugadorAbajo1 = new Sprite(2, 1, SpriteSheet.contenedor);
    public static Sprite jugadorAbajo2 = new Sprite(2, 2, SpriteSheet.contenedor);

    public static Sprite jugadorIzquierda1 = new Sprite(3, 1, SpriteSheet.contenedor);
    public static Sprite jugadorIzquierda2 = new Sprite(3, 2, SpriteSheet.contenedor);

    public static Sprite jugadorDerecha1 = new Sprite(1, 1, SpriteSheet.contenedor);
    public static Sprite jugadorDerecha2 = new Sprite(1, 2, SpriteSheet.contenedor);

    public static Sprite jugadorMuere = new Sprite(4, 2, SpriteSheet.contenedor);

    //*************************** Enemigos ***************************

    //Buho EPN
    public static Sprite buhoFrente1 = new Sprite(9, 0, SpriteSheet.contenedor);
    public static Sprite buhoIzquierda1 = new Sprite(9, 1, SpriteSheet.contenedor);
    public static Sprite buhoDerecha1 = new Sprite(9, 2, SpriteSheet.contenedor);

    public static Sprite buhoFrente2 = new Sprite(10, 0, SpriteSheet.contenedor);
    public static Sprite buhoIzquierda2 = new Sprite(10, 1, SpriteSheet.contenedor);
    public static Sprite buhoDerecha2 = new Sprite(10, 2, SpriteSheet.contenedor);

    public static Sprite buhoMuere = new Sprite(9, 3, SpriteSheet.contenedor);

    //Trent
    public static Sprite trentFrente1 = new Sprite(11, 0, SpriteSheet.contenedor);
    public static Sprite trentIzquierda1 = new Sprite(11, 1, SpriteSheet.contenedor);
    public static Sprite trentDerecha1 = new Sprite(11, 2, SpriteSheet.contenedor);

    public static Sprite trentFrente2 = new Sprite(12, 0, SpriteSheet.contenedor);
    public static Sprite trentIzquierda2 = new Sprite(12, 1, SpriteSheet.contenedor);
    public static Sprite trentDerecha2 = new Sprite(12, 2, SpriteSheet.contenedor);

    public static Sprite trentMuere = new Sprite(11, 3, SpriteSheet.contenedor);

    //Flamita
    public static Sprite flamitaFrente1 = new Sprite(13, 0, SpriteSheet.contenedor);
    public static Sprite flamitaDerecha1 = new Sprite(13, 1, SpriteSheet.contenedor);
    public static Sprite flamitaIzquierda1 = new Sprite(13, 2, SpriteSheet.contenedor);

    public static Sprite flamitaFrente2 = new Sprite(14, 0, SpriteSheet.contenedor);
    public static Sprite flamitaDerecha2 = new Sprite(14, 1, SpriteSheet.contenedor);
    public static Sprite flamitaIzquierda2 = new Sprite(14, 2, SpriteSheet.contenedor);

    public static Sprite flamitaMuere = new Sprite(13, 3, SpriteSheet.contenedor);

    //Rinoceronte
    public static Sprite rinoceronteFrente1 = new Sprite(4, 5, SpriteSheet.contenedor);
    public static Sprite rinoceronteDerecha1 = new Sprite(4, 6, SpriteSheet.contenedor);
    public static Sprite rinoceronteIzquierda1 = new Sprite(4, 7, SpriteSheet.contenedor);

    public static Sprite rinoceronteFrente2 = new Sprite(5, 5, SpriteSheet.contenedor);
    public static Sprite rinoceronteDerecha2 = new Sprite(5, 6, SpriteSheet.contenedor);
    public static Sprite rinoceronteIzquierda2 = new Sprite(5, 7, SpriteSheet.contenedor);

    public static Sprite rinoceronteMuere = new Sprite(4, 8, SpriteSheet.contenedor);

    //Muerte de los enemigos
    public static Sprite muerteEnemigo1 = new Sprite(15, 0, SpriteSheet.contenedor);
    public static Sprite muerteEnemigo2 = new Sprite(15, 1, SpriteSheet.contenedor);
    public static Sprite muerteEnemigo3 = new Sprite(15, 2, SpriteSheet.contenedor);

    //*************************** Bomba Sprites ***************************

    public static Sprite bombaGrande = new Sprite(0, 3, SpriteSheet.contenedor);
    public static Sprite bombaMediana = new Sprite(1, 3, SpriteSheet.contenedor);
    public static Sprite bombaPequeña = new Sprite(2, 3, SpriteSheet.contenedor);

    //*************************** Explosion Sprites ***************************

    public static Sprite explosionCentral = new Sprite(0, 6, SpriteSheet.contenedor);
    public static Sprite explosionVerticalIzquierda = new Sprite(3, 4, SpriteSheet.contenedor);
    public static Sprite explosionVerticalCentral = new Sprite(3, 5, SpriteSheet.contenedor);
    public static Sprite explosionVerticalDerecha = new Sprite(3, 6, SpriteSheet.contenedor);
    public static Sprite explosionHorizontalIzquierda = new Sprite(0, 9, SpriteSheet.contenedor);
    public static Sprite explosionHorizontalCentral = new Sprite(1, 9, SpriteSheet.contenedor);
    public static Sprite explosionHorizontalDerecha = new Sprite(2, 9, SpriteSheet.contenedor);

    //***** Exlosion de los bloques destructibles (Animacion) ********

    public static Sprite bloqueExplosion1 = new Sprite(7, 1, SpriteSheet.contenedor);
    public static Sprite bloqueExplosion2 = new Sprite(7, 2, SpriteSheet.contenedor);
    public static Sprite bloqueExplosion3 = new Sprite(7, 3, SpriteSheet.contenedor);

    //*************************** Power Ups ***************************

    public static Sprite powerUpBomba = new Sprite(0, 10, SpriteSheet.contenedor);
    public static Sprite powerUpFlama = new Sprite(1, 10, SpriteSheet.contenedor);
    public static Sprite powerUpVelocidad = new Sprite(2, 10, SpriteSheet.contenedor);

    public Sprite(int posicionX, int posicionY, SpriteSheet contenedor) {
        TAMAÑO = tamaño;
        pixels = new int[TAMAÑO * TAMAÑO];
        this.posicionX = posicionX * TAMAÑO;
        this.posicionY = posicionY * TAMAÑO;
        this.contenedor = contenedor;
        cargar();
    }


    public Sprite(int tamaño, int color) {
        TAMAÑO = tamaño;
        pixels = new int[TAMAÑO * TAMAÑO];
        colocarColor(color);
    }

    /**
     * Este método asigna un color específico a todos los píxeles del sprite.
     * @param color
     */
    private void colocarColor(int color) {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = color;
        }
    }

    /**
     * Este método se utiliza en el primer constructor para cargar los píxeles del sprite
     * desde una hoja de sprites. Recorre los píxeles del sprite y los obtiene de la hoja
     * de sprites en función de la posición especificada por posicion X y posicion Y.
     */
    private void cargar() {
        for (int y = 0; y < TAMAÑO; y++) {
            for (int x = 0; x < TAMAÑO; x++) {
                pixels[x + y * TAMAÑO] = contenedor.pixels[(x + posicionX) + (y + posicionY) * contenedor.TAMAÑO];
            }
        }
    }

    /**
     * Este método estático se utiliza para animar un sprite en función del tiempo.
     * La función determina cuál de los tres sprites mostrar en función de la animación
     * actual y el tiempo. Esta función se utiliza para crear animaciones de sprites
     * que cambian con el tiempo.
     * @param normal
     * @param x1
     * @param x2
     * @param animacion
     * @param tiempo
     * @return
     */

    public static Sprite moviendoSprite(Sprite normal, Sprite x1, Sprite x2, int animacion, int tiempo) {
        int calc = animacion % tiempo;
        int diff = tiempo / 3;

        if(calc < diff) {
            return normal;
        }

        if(calc < diff * 2) {
            return x1;
        }

        return x2;
    }

    /**
     * Este método estático es similar al anterior, pero toma
     * solo dos sprites y realiza una animación más simple entre ellos.
     * @param x1
     * @param x2
     * @param animacion
     * @param tiempo
     * @return
     */
    public static Sprite moviendoSprite(Sprite x1, Sprite x2, int animacion, int tiempo) {
        int diff = tiempo / 2;
        return (animacion % tiempo > diff) ? x1 : x2;
    }

    public int obtenerTamaño() {
        return TAMAÑO;
    }

    public int obtenerPixel(int i) {
        return pixels[i];
    }

}

