package InterfazLógica;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import InterfazGráfica.*;

/**
 * Esta clase se encarga de gestionar al jugador.
 */
public class Jugador extends Personaje {
    private List<Bomba> bombas;
    protected Keyboard tecla;
    protected int tiempoEntrePonerBomba = 0;
    public static List<PowerUp> powerUpsDelJugador = new ArrayList<PowerUp>();


    public Jugador(int posicionX, int posicionY, ControladorLógico controladorLógico) {
        super(posicionX, posicionY, controladorLógico);
        bombas = this.controladorLógico.obtenerBombas();
        tecla = this.controladorLógico.obtenerTecla();
        sprite = Sprite.jugadorDerecha;
    }


    /**
     * Actualiza el estado del jugador en el juego, gestionando la colocación de bombas,
     * animaciones, movimiento y detección de colisiones.
     */
    @Override
    public void actualizar() {
        limpiarBombas();
        if(estaVivo == false) {
            despuesDeMatar();
            return;
        }
        if(tiempoEntrePonerBomba < -7500) tiempoEntrePonerBomba = 0; else tiempoEntrePonerBomba--;
        animar();
        calcularMovimiento();
        detectarPosicionDeLaBomba();
    }

    /**
     * Gestiona la representación gráfica del jugador en la pantalla del juego.
     * @param pantalla
     */
    @Override
    public void renderizar(Pantalla pantalla) {
        calcularXOffset();
        if(estaVivo)
            elegirSprite();
        else
            sprite = Sprite.jugadorMuere;
        pantalla.renderizarContenedor((int) posicionX, (int) posicionY - sprite.TAMAÑO, this);
    }

    /**
     * Calcula el desplazamiento horizontal en la pantalla.
     */
    public void calcularXOffset() {
        int xScroll = Pantalla.calcularXOffset(controladorLógico, this);
        Pantalla.setOffset(xScroll, 0);
    }

    /**
     * Detecta si el jugador quiere colocar una bomba y lo hace si es posible.
     */
    private void detectarPosicionDeLaBomba() {
        if(tecla.espacio && JuegoIG.obtenerCantidadDeBombas() > 0 && tiempoEntrePonerBomba < 0) {
            int xt = Coordenadas.tranformarDePixelAContenedor(posicionX + sprite.obtenerTamaño() / 2);
            int yt = Coordenadas.tranformarDePixelAContenedor( (posicionY + sprite.obtenerTamaño() / 2) - sprite.obtenerTamaño() );
            posicionDeLaBomba(xt,yt);
            JuegoIG.agregarCantidadDeBombasExtra(-1);
            tiempoEntrePonerBomba = 30;
        }
    }

    /**
     * Coloca una bomba en una posición específica en el juego.
     * @param x: posicion en el eje x
     * @param y: posicion en el eje y
     */
    protected void posicionDeLaBomba(int x, int y) {
        Bomba nuevaBomba = new Bomba(x, y, controladorLógico);
        controladorLógico.agregarBomba(nuevaBomba);
    }

    private void limpiarBombas() {
        Iterator<Bomba> bs = bombas.iterator();
        Bomba nuevaBomba;
        while(bs.hasNext()) {
            nuevaBomba = bs.next();
            if(nuevaBomba.estaRemovida())  {
                bs.remove();
                JuegoIG.agregarCantidadDeBombasExtra(1);
            }
        }

    }

    /**
     * Maneja la muerte del jugador, reduce vidas y muestra un mensaje.
     */
    @Override
    public void matar() {
        if(!estaVivo) return;
        estaVivo = false;
        controladorLógico.agregarVida(-1);
        Mensaje nuevoMensaje = new Mensaje("-1 Vida", obtenerXMensaje(), obtenerYMensaje(), 2, Color.black, 14);
        controladorLógico.añadirMesanje(nuevoMensaje);
    }

    /**
     * Gestiona las acciones después de la muerte del jugador, como reiniciar el nivel o finalizar el juego.
     */
    @Override
    protected void despuesDeMatar() {
        if(tiempoDespues > 0) --tiempoDespues;
        else {
            if(bombas.size() == 0) {
                if(controladorLógico.obtenerVidasDelJugador() == 0)
                    controladorLógico.finalizarJuego();
                else
                    controladorLógico.reiniciarNivel();
            }
        }
    }

    /**
     * Calcula el movimiento del jugador en función de la entrada del teclado.
     */
    @Override
    protected void calcularMovimiento() {
        int xa = 0, ya = 0;
        if(tecla.arriba) ya--;
        if(tecla.abajo) ya++;
        if(tecla.izquierda) xa--;
        if(tecla.derecha) xa++;

        if(xa != 0 || ya != 0)  {
            movimiento(xa * JuegoIG.obtenerVelocidadDelJugador(), ya * JuegoIG.obtenerVelocidadDelJugador());
            seEstaMovimiento = true;
        } else {
            seEstaMovimiento = false;
        }

    }

    /**
     * Verifica si el jugador puede moverse a una nueva posición sin colisionar con obstáculos.
     * @param x: Posicion en el eje x
     * @param y: Posicion en el eje y
     * @return boolean
     */
    @Override
    public boolean puedeMoverse(double x, double y) {
        for (int c = 0; c < 4; c++) { //Detección de colisión para cada esquina del jugador.
            double xt = ((posicionX + x) + c % 2 * 11) / JuegoIG.TAMAÑO_CONTENEDORES;
            double yt = ((posicionY + y) + c / 2 * 12 - 13) / JuegoIG.TAMAÑO_CONTENEDORES;

            ElementoDelMapa nuevoContenedor = controladorLógico.obtenerContenedor(xt, yt, this);

            if(!nuevoContenedor.colisionar(this))
                return false;
        }
        return true;
    }

    /**
     * Mueve al jugador en la dirección especificada si es posible.
     * @param xa: Posicion en el eje x
     * @param ya: Posicion en el eje y
     */
    @Override
    public void movimiento(double xa, double ya) {
        if(xa > 0) direccion = 1;
        if(xa < 0) direccion = 3;
        if(ya > 0) direccion = 2;
        if(ya < 0) direccion = 0;

        if(puedeMoverse(0, ya)) { //Separa los movimientos para que el jugador pueda deslizarse cuando choca.
            posicionY += ya;
        }
        if(puedeMoverse(xa, 0)) {
            posicionX += xa;
        }
    }

    /**
     * Maneja colisiones con otros elementos del juego, como explosiones o enemigos.
     * @param elementoDelMapa
     * @return boolean
     */
    @Override
    public boolean colisionar(ElementoDelMapa elementoDelMapa) {
        if(elementoDelMapa instanceof DirecciónDeLaExplosión) {
            matar();
            return false;
        }

        if(elementoDelMapa instanceof Enemigo) {
            matar();
            return true;
        }
        return true;
    }

    /**
     * Agrega un powerUp al jugador.
     * @param powerUp
     */
    public void agregarPowerUp(PowerUp powerUp) {
        if(powerUp.estaRemovida()) return;

        powerUpsDelJugador.add(powerUp);

        powerUp.colocarValoresDePowerUp();
    }

    /**
     * Retira un powerUp del jugador.
     */
    public void limpiarPowerUpsUsados() {
        PowerUp powerUp;
        for (int i = 0; i < powerUpsDelJugador.size(); i++) {
            powerUp = powerUpsDelJugador.get(i);
            if(powerUp.estaActivado() == false)
                powerUpsDelJugador.remove(i);
        }
    }

    /**
     * Elimina todos los powerUps.
     */
    public void removerPowerups() {
        for (int i = 0; i < powerUpsDelJugador.size(); i++) {
            powerUpsDelJugador.remove(i);
        }
    }

    /**
     * Gestiona los sprites de jugador. Hubiera sido mejor que este metodo este en una clase jugador
     * en el paquete grafico.
     */
    private void elegirSprite() {
        switch(direccion) {
            case 0:
                sprite = Sprite.jugadorArriba;
                if(seEstaMovimiento) {
                    sprite = Sprite.moviendoSprite(Sprite.jugadorArriba1, Sprite.jugadorArriba2, animación, 20);
                }
                break;
            case 1:
                sprite = Sprite.jugadorDerecha;
                if(seEstaMovimiento) {
                    sprite = Sprite.moviendoSprite(Sprite.jugadorDerecha1, Sprite.jugadorDerecha2, animación, 20);
                }
                break;
            case 2:
                sprite = Sprite.jugadorAbajo;
                if(seEstaMovimiento) {
                    sprite = Sprite.moviendoSprite(Sprite.jugadorAbajo1, Sprite.jugadorAbajo2, animación, 20);
                }
                break;
            case 3:
                sprite = Sprite.jugadorIzquierda;
                if(seEstaMovimiento) {
                    sprite = Sprite.moviendoSprite(Sprite.jugadorIzquierda1, Sprite.jugadorIzquierda2, animación, 20);
                }
                break;
            default:
                sprite = Sprite.jugadorDerecha;
                if(seEstaMovimiento) {
                    sprite = Sprite.moviendoSprite(Sprite.jugadorDerecha1, Sprite.jugadorDerecha2, animación, 20);
                }
                break;
        }
    }
}

