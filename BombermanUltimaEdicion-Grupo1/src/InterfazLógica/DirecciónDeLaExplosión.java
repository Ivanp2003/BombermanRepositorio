package InterfazLógica;

import InterfazGráfica.Explosion;
import InterfazGráfica.Pantalla;

/**
 * Esta clase sirve para controlar la explosion de la bomba de manera logica.
 */
public class DirecciónDeLaExplosión extends ElementoDelMapa {

    protected ControladorLógico controladorLógico;
    protected int direcion;
    private int alcance;
    protected int origenX, origenY;
    protected Explosion[] explosiones;

    public DirecciónDeLaExplosión(int posicionX, int posicionY, int direcion, int alcance, ControladorLógico controladorLógico) {
        origenX = posicionX;
        origenY = posicionY;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.direcion = direcion;
        this.alcance = alcance;
        this.controladorLógico = controladorLógico;

        explosiones = new Explosion[ calcularPerimetroDeDistancia() ];
        crearExplosion();
    }

    private void crearExplosion() {
        boolean último = false;

        int x = (int) posicionX;
        int y = (int) posicionY;
        for (int i = 0; i < explosiones.length; i++) {
            último = i == explosiones.length -1 ? true : false;

            switch (direcion) {
                case 0: y--; break;
                case 1: x++; break;
                case 2: y++; break;
                case 3: x--; break;
            }
            explosiones[i] = new Explosion(x, y, direcion, último, controladorLógico);
        }
    }

    private int calcularPerimetroDeDistancia() {
        int radio = 0;
        int x = (int) posicionX;
        int y = (int) posicionY;
        while(radio < alcance) {
            if(direcion == 0) y--;
            if(direcion == 1) x++;
            if(direcion == 2) y++;
            if(direcion == 3) x--;

            ElementoDelMapa nuevoContenedor = controladorLógico.obtenerContenedor(x, y, null);

            if(nuevoContenedor instanceof Personaje) ++radio;
            if(nuevoContenedor.colisionar(this) == false)
                break;
            ++radio;
        }
        return radio;
    }

    public Explosion explotarEn(int x, int y) {
        for (int i = 0; i < explosiones.length; i++) {
            if(explosiones[i].getX() == x && explosiones[i].getY() == y)
                return explosiones[i];
        }
        return null;
    }

    @Override
    public void actualizar() {}

    @Override
    public void renderizar(Pantalla pantalla) {

        for (int i = 0; i < explosiones.length; i++) {
            explosiones[i].renderizar(pantalla);
        }
    }

    @Override
    public boolean colisionar(ElementoDelMapa elementoDelMapa) {
        return true;
    }
}

