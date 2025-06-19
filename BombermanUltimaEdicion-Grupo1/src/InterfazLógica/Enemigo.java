package InterfazLógica;

import java.awt.Color;

import InterfazGráfica.*;

/**
 * Esta clase gestiona a los enemigos de cada nivel.
 */
public abstract class Enemigo extends Personaje {
    protected int puntos;
    protected double velocidad; //La velocidad debería cambiar en la transición de niveles.
    protected IA IA;
    //Necesario para corregir movimiento
    protected final double PASOS_MAXIMOS;
    protected final double reposar;
    protected double pasos;
    protected int animaciónFinal = 30;
    protected Sprite spriteMuerto;

    public Enemigo(int posicionX, int posicionY, ControladorLógico controladorLógico, Sprite spriteMuerte, double velocidad, int puntos) {
        super(posicionX, posicionY, controladorLógico);

        this.puntos = puntos;
        this.velocidad = velocidad;

        PASOS_MAXIMOS = JuegoIG.TAMAÑO_CONTENEDORES / this.velocidad;
        reposar = (PASOS_MAXIMOS - (int) PASOS_MAXIMOS) / PASOS_MAXIMOS;
        pasos = PASOS_MAXIMOS;

        tiempoDespues = 20;
        spriteMuerto = spriteMuerte;
    }


    @Override
    public void actualizar() {
        animar();
        if(estaVivo == false) {
            despuesDeMatar();
            return;
        }
        if(estaVivo)
            calcularMovimiento();
    }

    @Override
    public void renderizar(Pantalla pantalla) {

        if(estaVivo)
            elegirSprite();
        else {
            if(tiempoDespues > 0) {
                sprite = spriteMuerto;
                animación = 0;
            } else {
                sprite = Sprite.moviendoSprite(Sprite.muerteEnemigo1, Sprite.muerteEnemigo2, Sprite.muerteEnemigo3, animación, 60);
            }

        }

        pantalla.renderizarContenedor((int) posicionX, (int) posicionY - sprite.TAMAÑO, this);
    }

    /**
     * Calcula el movimiento del enemigo a partir de la direccion de las IAs.
     */
    @Override
    public void calcularMovimiento() {
        int xa = 0, ya = 0;
        if(pasos <= 0){
            direccion = IA.calcularDirección();
            pasos = PASOS_MAXIMOS;
        }

        if(direccion == 0) ya--;
        if(direccion == 2) ya++;
        if(direccion == 3) xa--;
        if(direccion == 1) xa++;

        /**
         * Verifica si el enemigo puede moverse a una nueva posición sin colisionar con obstáculos.
         * @param x: Posicion en el eje x
         * @param y: Posicion en el eje y
         * @return boolean
         */
        if(puedeMoverse(xa, ya)) {
            pasos -= 1 + reposar;
            movimiento(xa * velocidad, ya * velocidad);
            seEstaMovimiento = true;
        } else {
            pasos = 0;
            seEstaMovimiento = false;
        }
    }

    /**
     * Mueve al enemigo en la dirección especificada si es posible.
     * @param xa: Posicion en el eje x
     * @param ya: Posicion en el eje y
     */
    @Override
    public void movimiento(double xa, double ya) {
        if(!estaVivo) return;
        posicionY += ya;
        posicionX += xa;
    }

    @Override
    public boolean puedeMoverse(double x, double y) {

        double xr = posicionX, yr = posicionY - 16; //resta y para obtener resultados más precisos

        //La cuestión es que restamos 15 a 16 (tamaño del sprite), por lo que si agregamos 1 contenedor obtenemos el siguiente mosaico de píxeles con esto,
        //Evitamos las sacudidas del interior de las baldosas con la ayuda de escalones.
        if(direccion == 0) { yr += sprite.obtenerTamaño() -1 ; xr += sprite.obtenerTamaño()/2; }
        if(direccion == 1) {yr += sprite.obtenerTamaño()/2; xr += 1;}
        if(direccion == 2) { xr += sprite.obtenerTamaño()/2; yr += 1;}
        if(direccion == 3) { xr += sprite.obtenerTamaño() -1; yr += sprite.obtenerTamaño()/2;}

        int xx = Coordenadas.tranformarDePixelAContenedor(xr) +(int)x;
        int yy = Coordenadas.tranformarDePixelAContenedor(yr) +(int)y;

        ElementoDelMapa nuevoContenedor = controladorLógico.obtenerContenedor(xx, yy, this); //contenedor de la posición a la que queremos ir

        return nuevoContenedor.colisionar(this);
    }


    @Override
    public boolean colisionar(ElementoDelMapa elementoDelMapa) {
        if(elementoDelMapa instanceof DirecciónDeLaExplosión) {
            matar();
            return false;
        }

        if(elementoDelMapa instanceof Jugador) {
            ((Jugador) elementoDelMapa).matar();
            return false;
        }

        return true;
    }

    @Override
    public void matar() {
        if(estaVivo == false) return;
        estaVivo = false;

        controladorLógico.agregarPuntos(puntos);

        Mensaje nuevoMensaje = new Mensaje("+" + puntos, obtenerXMensaje(), obtenerYMensaje(), 2, Color.white, 14);
        controladorLógico.añadirMesanje(nuevoMensaje);
    }


    @Override
    protected void despuesDeMatar() {
        if(tiempoDespues > 0) --tiempoDespues;
        else {

            if(animaciónFinal > 0) --animaciónFinal;
            else
                removerElemento();
        }
    }

    protected abstract void elegirSprite();
}

