package InterfazLógica;

import InterfazGráfica.*;

/**
 * Clase con animación que gestiona las bombas que posee el jugador
 */
public class Bomba extends EntidadAnimada {
    protected double tiempoDeDetonación = 120; //2 segundos
    public int tiempoDeDesvanecimientoDeLaExplosion = 20; //tiempo en el que las explosiones desaparezcan.

    protected ControladorLógico controladorLógico;
    protected boolean permitirExplosion = true;
    protected DirecciónDeLaExplosión[] explosiones = null;
    protected boolean explotar = false;

    public Bomba(int posicionX, int posicionY, ControladorLógico controladorLógico) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.controladorLógico = controladorLógico;
        sprite = Sprite.bombaGrande;
    }

    @Override
    public void actualizar() {
        if(tiempoDeDetonación > 0)
            tiempoDeDetonación--;
        else {
            if(!explotar)
                generarRangoDeExplosion();
            else
                actualizarExplosiones();

            if(tiempoDeDesvanecimientoDeLaExplosion > 0)
                tiempoDeDesvanecimientoDeLaExplosion--;
            else
                removerElemento();
        }

        animar();
    }

    @Override
    public void renderizar(Pantalla pantalla) {
        if(explotar) {
            sprite =  Sprite.explosionCentral;
            renderizarExplosiones(pantalla);
        } else
            sprite = Sprite.moviendoSprite(Sprite.bombaGrande, Sprite.bombaMediana, Sprite.bombaPequeña, animación, 60);

        int xt = (int) posicionX << 4;
        int yt = (int) posicionY << 4;

        pantalla.renderizarContenedor(xt, yt , this);
    }

    /**
     * Función encargado de dibujar la bomba en pantalla
     * @param pantalla instancia en la cual se dibujará la boma
     */
    public void renderizarExplosiones(Pantalla pantalla) {
        for (int i = 0; i < explosiones.length; i++) {
            explosiones[i].renderizar(pantalla);
        }
    }

    /**
     * Gráfica la explosión en el rango que este posee
     */
    public void actualizarExplosiones() {
        for (int i = 0; i < explosiones.length; i++) {
            explosiones[i].actualizar();
        }
    }

    public void explotar() {
        tiempoDeDetonación = 0;
    }

    /**
     *Genera un rango de explosión a la bomba
     */
    protected void generarRangoDeExplosion() {
        permitirExplosion = true;
        explotar = true;

        Personaje personaje = controladorLógico.obtenerPersonajeEn(posicionX, posicionY);
        if(personaje != null)  {
            personaje.matar();
        }

        explosiones = new DirecciónDeLaExplosión[4];

        for (int i = 0; i < explosiones.length; i++) {
            explosiones[i] = new DirecciónDeLaExplosión((int) posicionX, (int) posicionY, i, JuegoIG.obtenerAlcanceDeBomba(), controladorLógico);
        }
    }

    /**
     *Crea una exploción en una posición que recorre su rango en forma de cruz
     * @param x posición en el eje x de la bomba
     * @param y posición en el eje y de la boma
     * @return una explosión en las coordenadas pasadas como parámetros
     */
    public Explosion explotarEn(int x, int y) {
        if(!explotar) return null;

        for (int i = 0; i < explosiones.length; i++) {
            if(explosiones[i] == null) return null;
            Explosion e = explosiones[i].explotarEn(x, y);
            if(e != null) return e;
        }
        return null;
    }

    @Override
    public boolean colisionar(ElementoDelMapa elementoDelMapa) {

        if(elementoDelMapa instanceof Jugador) {
            double diffX = elementoDelMapa.getX() - Coordenadas.tranformarDeContenedorAPixel(getX());
            double diffY = elementoDelMapa.getY() - Coordenadas.tranformarDeContenedorAPixel(getY());

            if(!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffY <= 28)) { // differences to see if the player has moved out of the bomb, tested values
                permitirExplosion = false;
            }

            return permitirExplosion;
        }

        if(elementoDelMapa instanceof DirecciónDeLaExplosión) {
            explotar();
            return true;
        }

        return false;
    }
}
