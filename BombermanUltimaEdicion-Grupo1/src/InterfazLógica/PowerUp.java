package InterfazLógica;

import InterfazGráfica.Sprite;

/**
 * Eta clase se encarga de gestionar los PowerUps como una clase Padre.
 */
public abstract class PowerUp extends Contenedor {
    protected int duraciónDePowerUp = -1; // -1 is infinito, duracion por vidas
    protected boolean activarPowerUp = false;
    protected int nivel;

    public PowerUp(int posiciónX, int posiciónY, int nivel, Sprite sprite) {
        super(posiciónX, posiciónY, sprite);
        this.nivel = nivel;
    }

    public abstract void colocarValoresDePowerUp();

    /**
     * Este metodo no se usa mayormente en el código, puede que se lo elimine en un futuro.
     * Sin embargo, tal vez se le pueda dar un uso en un futuro tambien.
     */
    public void removerVida() {
        if(duraciónDePowerUp > 0)
            duraciónDePowerUp--;

        if(duraciónDePowerUp == 0)
            activarPowerUp = false;
    }

    public int obtenerNivel() {
        return nivel;
    }

    public boolean estaActivado() {
        return activarPowerUp;
    }

}