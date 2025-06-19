package InterfazLógica;

import java.util.LinkedList;

import InterfazGráfica.Pantalla;

/**
 * Esta clase se encarga de gestionar una entidad compuesta por múltiples elementos
 * apilados en capas y proporciona métodos para actualizar y renderizar la capa superior
 * de esta entidad.
 */
public class LayeredEntidad extends ElementoDelMapa {

    protected LinkedList<ElementoDelMapa> contenedores = new LinkedList<ElementoDelMapa>();
    public LayeredEntidad(int posicionX, int posicionY, ElementoDelMapa... contenedores) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;

        // En este for lo que se hace es agregar los elementos del mapa a un contenedor especifico.
        for (int i = 0; i < contenedores.length; i++) {
            this.contenedores.add(contenedores[i]);

            // Aqui se realiza un proceso especial para los bloques destructibles que agrega
            // un sprite adicional debajo del bloque para la renderización de explosiones.
            if(i > 1) {
                if(contenedores[i] instanceof BloqueDestructible)
                    ((BloqueDestructible)contenedores[i]).agregarSpriteAbajo(contenedores[i-1].obtenerSprite());
            }
        }
    }

    /**
     * Este metodo se encarga de actualizar la pantalla.
     */
    @Override
    public void actualizar() {
        limpiarYRemover();
        obtenerContenedorSuperior().actualizar();
    }

    /**
     * Este metodo se encarga de renderizar la pantalla.
     */
    @Override
    public void renderizar(Pantalla pantalla) {
        obtenerContenedorSuperior().renderizar(pantalla);
    }

    public ElementoDelMapa obtenerContenedorSuperior() {
        return contenedores.getLast();
    }

    /**
     * Este metodo se encarga de remover los elementos que han dejado de participar en el
     * nivel. Si retira un bloque Destructible (superior), aparesera otro elemento del mapa que
     * este debajo de ese bloque.
     */
    private void limpiarYRemover() {
        ElementoDelMapa superior  = obtenerContenedorSuperior();
        if(superior.estaRemovida())  {
            contenedores.removeLast();
        }
    }

    public void agregarAnteriorSuperior(ElementoDelMapa e) {
        contenedores.add(contenedores.size() - 1, e);
    }

    @Override
    public boolean colisionar(ElementoDelMapa elementoDelMapa) {
        return obtenerContenedorSuperior().colisionar(elementoDelMapa);
    }

}