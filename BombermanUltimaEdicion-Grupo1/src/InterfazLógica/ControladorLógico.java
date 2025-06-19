package InterfazLógica;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import InterfazGráfica.*;
import InterfazLógica.Exceptions.CargarNivelException;

// TODO: 6/9/2023 Revisar si debe ir al paquete gráfico.

public class ControladorLógico implements Renderización {

    protected Nivel nivel;
    protected JuegoIG juegoIG;
    protected Keyboard tecla;
    protected Pantalla pantalla;

    public ElementoDelMapa[] elementoDelMapas;
    public List<Personaje> personajes = new ArrayList<Personaje>();
    protected List<Bomba> bombas = new ArrayList<Bomba>();
    private List<Mensaje> mensajes = new ArrayList<Mensaje>();

    private int tipoDePantallaAPresentar = -1; //1:finDelJuego, 2:cambioDeNivel, 3:pausar

    private int tiempo = JuegoIG.TIEMPO;
    private int puntos = JuegoIG.PUNTOS;
    private int vidas = JuegoIG.VIDAS;

    public ControladorLógico(JuegoIG juegoIG, Keyboard tecla, Pantalla pantalla) {
        this.juegoIG = juegoIG;
        this.tecla = tecla;
        this.pantalla = pantalla;

        cambiarDeNivel(1); //empezar en el nivel 1
    }

    /**
     * Este metodo actualiza la lógica del juego, incluyendo elementos del mapa,
     * personajes, bombas, mensajes y detecta el fin del juego.
     */
    @Override
    public void actualizar() {
        if( juegoIG.estaPausadoElJuego() ) return;

        actualizarElementosDelMapa();
        actualizarPersonajes();
        actualizarBombas();
        actualizarMensajes();
        detectarFinDelJuego();

        for (int i = 0; i < personajes.size(); i++) {
            Personaje nuevoPersonaje = personajes.get(i);
            if(((ElementoDelMapa)nuevoPersonaje).estaRemovida()) personajes.remove(i);
        }
    }

    /**
     * Renderiza los elementos del juego en la pantalla.
     * @param pantalla
     */
    @Override
    public void renderizar(Pantalla pantalla) {
        if( juegoIG.estaPausadoElJuego() ) return;

        // Solo renderiza la parte visible de la pantalla
        int x0 = Pantalla.colocarXEnOf >> 4; //precisión del contenedor, -> izquierda X
        int x1 = (Pantalla.colocarXEnOf + pantalla.getWidth() + JuegoIG.TAMAÑO_CONTENEDORES) / JuegoIG.TAMAÑO_CONTENEDORES; // -> derecha X
        int y0 = Pantalla.colocarYEnOf >> 4;
        int y1 = (Pantalla.colocarYEnOf + pantalla.getHeight()) / JuegoIG.TAMAÑO_CONTENEDORES; //renderizar un contenedor más para arreglar los márgenes negros

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                elementoDelMapas[x + y * nivel.obtenerAncho()].renderizar(pantalla);
            }
        }
        renderizarBombs(pantalla);
        renderizarPersonajes(pantalla);
    }

    /**
     * Este metodo crea un nuevo juego.
     */
    public void nuevoJuego() {
        reiniciarPropiedades();
        cambiarDeNivel(1);
    }

    /**
     * Este metodo se encarga de reiniciar las propiedades del juego.
     */
    private void reiniciarPropiedades() {
        puntos = JuegoIG.PUNTOS;
        vidas = JuegoIG.VIDAS;
        Jugador.powerUpsDelJugador.clear();

        juegoIG.velocidadDelJugador = 1.0;
        juegoIG.alcanceDeBomba = 1;
        juegoIG.cantidadDeBombas = 1;

    }

    public void reiniciarNivel() {
        cambiarDeNivel(nivel.obtenerNivel());
    }

    public void siguienteNivel() {
        cambiarDeNivel(nivel.obtenerNivel() + 1);
    }

    /**
     * Este metodo se encarga de realizar el cambio de nivel.
     * @param nivel
     */
    public void cambiarDeNivel(int nivel) {
        tiempo = JuegoIG.TIEMPO;
        tipoDePantallaAPresentar = 2;
        juegoIG.reiniciarRetrasoDePantalla();
        juegoIG.pausar();
        personajes.clear();
        bombas.clear();
        mensajes.clear();

        /**
         * Esta excepcion se encarga de que cuando se vaya pasando de nivel, si llega a un nivel que no
         * esta en los txt, en lugar de que tire error mandara a que termine el juego.
         */
        try {
            this.nivel = new ArchivoNivel("Niveles/Level" + nivel + ".txt", this);
            elementoDelMapas = new ElementoDelMapa[this.nivel.obtenerAltura() * this.nivel.obtenerAncho()];

            this.nivel.crearContenedor();
        } catch (CargarNivelException e) {
            finalizarJuego(); //No se pudo cargar... entonces... ¿no hay más niveles?
        }
    }

    public boolean estaUsandoPowerUp(int x, int y, int level) {
        PowerUp p;
        for (int i = 0; i < Jugador.powerUpsDelJugador.size(); i++) {
            p = Jugador.powerUpsDelJugador.get(i);
            if(p.getX() == x && p.getY() == y && level == p.obtenerNivel())
                return true;
        }

        return false;
    }

    protected void detectarFinDelJuego() {
        if(tiempo <= 0)
            reiniciarNivel();
    }

    public void finalizarJuego() {
        tipoDePantallaAPresentar = 1;
        juegoIG.reiniciarRetrasoDePantalla();
        juegoIG.pausar();
    }

    public boolean noHayEnemigos() {
        int total = 0;
        for (int i = 0; i < personajes.size(); i++) {
            if(personajes.get(i) instanceof Jugador == false)
                ++total;
        }

        return total == 0;
    }

    public void pausarJuego() {
        juegoIG.reiniciarRetrasoDePantalla();
        if(tipoDePantallaAPresentar <= 0)
            tipoDePantallaAPresentar = 3;
        juegoIG.pausar();
    }

    public void reanudarJuego() {
        juegoIG.reiniciarRetrasoDePantalla();
        tipoDePantallaAPresentar = -1;
        juegoIG.correrJuego();
    }

    public void dibujarPantallas(Graphics gráficos) {
        switch (tipoDePantallaAPresentar) {
            case 1:
                pantalla.dibujarPantallaDeFinDeJuego(gráficos, puntos);
                break;
            case 2:
                pantalla.dibujarPantallaDeCambioDeNivel(gráficos, nivel.obtenerNivel());
                break;
            case 3:
                pantalla.dibujarPantallaDePausa(gráficos);
                break;
        }
    }

    /**
     * Este es un método que se utiliza para obtener un elemento del mapa (objeto de la clase
     * ElementoDelMapa) en una posición específica (x, y) del juego, teniendo en cuenta
     * la interacción del personaje pasado como argumento.
     * @param x
     * @param y
     * @param personaje
     * @return
     */
    public ElementoDelMapa obtenerContenedor(double x, double y, Personaje personaje) {

        ElementoDelMapa elementoDelMapa = null;

        elementoDelMapa = obtenerExplosionEn((int)x, (int)y);
        if( elementoDelMapa != null) return elementoDelMapa;

        elementoDelMapa = obtenerBombaEn(x, y);
        if( elementoDelMapa != null) return elementoDelMapa;

        elementoDelMapa = obtenerPersonajeAExcluir((int)x, (int)y, personaje);
        if( elementoDelMapa != null) return elementoDelMapa;

        elementoDelMapa = obtenerContenedorEn((int)x, (int)y);

        return elementoDelMapa;
    }

    public List<Bomba> obtenerBombas() {
        return bombas;
    }

    /**
     * Este metodo se encarga de buscar y obtener una bomba en las coordenadas indicadas.
     * @param x
     * @param y
     * @return una bomba
     */
    public Bomba obtenerBombaEn(double x, double y) {
        Iterator<Bomba> bs = bombas.iterator();
        Bomba bomba;
        while(bs.hasNext()) {
            bomba = bs.next();
            if(bomba.getX() == (int)x && bomba.getY() == (int)y)
                return bomba;
        }
        return null;
    }

    /**
     * Este metodo se encarga de buscar y obtener un personaje en las coordenadas indicadas.
     * @param x
     * @param y
     * @return personaje.
     */
    public Personaje obtenerPersonajeEn(double x, double y) {
        Iterator<Personaje> personajeIterator = personajes.iterator();

        Personaje nuevoPersonaje;
        while(personajeIterator.hasNext()) {
            nuevoPersonaje = personajeIterator.next();

            if(nuevoPersonaje.obtenerContenedorEnX() == x && nuevoPersonaje.obtenerContenedorEnY() == y)
                return nuevoPersonaje;
        }
        return null;
    }

    /**
     * Este metodo se encarga de buscar y obtener al jugador en las coordenadas indicadas.
     * @return jugador
     */
    public Jugador obtenerJugador() {
        Iterator<Personaje> iteradorDePersonajes = personajes.iterator();

        Personaje nuevoPersonaje;
        while(iteradorDePersonajes.hasNext()) {
            nuevoPersonaje = iteradorDePersonajes.next();

            if(nuevoPersonaje instanceof Jugador)
                return (Jugador) nuevoPersonaje;
        }

        return null;
    }

    /**
     * Se utiliza para buscar y devolver un objeto de la clase Personaje que se encuentra
     * en las coordenadas (x, y) especificadas en el juego, excluyendo un personaje específico.
     * @param x
     * @param y
     * @param personaje
     * @return
     */
    public Personaje obtenerPersonajeAExcluir(int x, int y, Personaje personaje) {
        Iterator<Personaje> itr = personajes.iterator();
        Personaje nuevoPersonaje;
        while(itr.hasNext()) {
            nuevoPersonaje = itr.next();
            if(nuevoPersonaje == personaje) {
                continue;
            }
            if(nuevoPersonaje.obtenerContenedorEnX() == x && nuevoPersonaje.obtenerContenedorEnY() == y) {
                return nuevoPersonaje;
            }
        }
        return null;
    }

    /**
     * Este metodo se encarga de buscar y obtener la explosion en las coordenadas indicadas.
     * @param x
     * @param y
     * @return
     */
    public Explosion obtenerExplosionEn(int x, int y) {
        Iterator<Bomba> bs = bombas.iterator();
        Bomba nuevaBomba;
        while(bs.hasNext()) {
            nuevaBomba = bs.next();

            Explosion nuevaExplosion = nuevaBomba.explotarEn(x, y);
            if(nuevaExplosion != null) {
                return nuevaExplosion;
            }

        }
        return null;
    }

    /**
     * Este metodo se encarga de buscar y obtener un contenedor en las coordenadas indicadas.
     * @param x
     * @param y
     * @return
     */
    public ElementoDelMapa obtenerContenedorEn(double x, double y) {
        return elementoDelMapas[(int)x + (int)y * nivel.obtenerAncho()];
    }

    public void agregarContenedor(int posicion, ElementoDelMapa elementoDelMapa) {
        elementoDelMapas[posicion] = elementoDelMapa;
    }
    public void agregarPersonaje(Personaje personaje) {
        personajes.add(personaje);
    }

    public void agregarBomba(Bomba bomba) {
        bombas.add(bomba);
    }

    public void añadirMesanje(Mensaje mensaje) {
        mensajes.add(mensaje);
    }

    protected void renderizarContenedores(Pantalla pantalla) {
        for (int i = 0; i < elementoDelMapas.length; i++) {
            elementoDelMapas[i].renderizar(pantalla);
        }
    }

    protected void renderizarPersonajes(Pantalla pantalla) {
        Iterator<Personaje> itr = personajes.iterator();

        while(itr.hasNext())
            itr.next().renderizar(pantalla);
    }

    protected void renderizarBombs(Pantalla pantalla) {
        Iterator<Bomba> itr = bombas.iterator();

        while(itr.hasNext())
            itr.next().renderizar(pantalla);
    }

    public void renderizarMensajes(Graphics gráficos) {
        Mensaje mensaje;
        for (int i = 0; i < mensajes.size(); i++) {
            mensaje = mensajes.get(i);
            gráficos.setFont(new Font("Arial", Font.PLAIN, mensaje.obtenerTamañoDelMensaje()));
            gráficos.setColor(mensaje.obtenerColorDelMensaje());
            gráficos.drawString(mensaje.obtenerMensaje(), (int) mensaje.getX() - Pantalla.colocarXEnOf * JuegoIG.ESCALA, (int) mensaje.getY());
        }
    }

    protected void actualizarElementosDelMapa() {
        if( juegoIG.estaPausadoElJuego() ) return;
        for (int i = 0; i < elementoDelMapas.length; i++) {
            elementoDelMapas[i].actualizar();
        }
    }

    protected void actualizarPersonajes() {
        if( juegoIG.estaPausadoElJuego() ) return;
        Iterator<Personaje> itr = personajes.iterator();

        while(itr.hasNext() && !juegoIG.estaPausadoElJuego())
            itr.next().actualizar();
    }

    protected void actualizarBombas() {
        if( juegoIG.estaPausadoElJuego() ) return;
        Iterator<Bomba> itr = bombas.iterator();

        while(itr.hasNext())
            itr.next().actualizar();
    }

    protected void actualizarMensajes() {
        if( juegoIG.estaPausadoElJuego() ) return;
        Mensaje m;
        int left = 0;
        for (int i = 0; i < mensajes.size(); i++) {
            m = mensajes.get(i);
            left = m.obtenerDuraciónDelMensaje();

            if(left > 0)
                m.generarDuraciónDelMensaje(--left);
            else
                mensajes.remove(i);
        }
    }

    public Keyboard obtenerTecla() {
        return tecla;
    }

    public int getShow() {
        return tipoDePantallaAPresentar;
    }

    public void setShow(int i) {
        tipoDePantallaAPresentar = i;
    }

    public int obtenerTiempo() {
        return tiempo;
    }

    public int obtenerVidasDelJugador() {
        return vidas;
    }

    public int subtractTime() {
        if(juegoIG.estaPausadoElJuego())
            return this.tiempo;
        else
            return this.tiempo--;
    }

    public int obtenerPuntos() {
        return puntos;
    }

    public void agregarPuntos(int points) {
        this.puntos += points;
    }

    public void agregarVida(int lives) {
        this.vidas += lives;
    }

    public int obtenerAnchoGeneral() {
        return nivel.obtenerAncho();
    }

}

