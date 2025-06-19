package InterfazGráfica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

import InterfazLógica.*;
import InterfazLógica.Exceptions.CargarNivelException;

/**
 * Esta clase se encarga de gestionar los archivos de nivel. Y de implementar los elementos del mapa
 * en base a los simbolos de los txt.
 */
public class ArchivoNivel extends Nivel {

    public ArchivoNivel(String ruta, ControladorLógico controladorLógico) throws CargarNivelException {
        super(ruta, controladorLógico);
    }

    @Override
    public void cargarNivel(String ruta) throws CargarNivelException {
        try {
            URL abastecerRuta = ArchivoNivel.class.getResource("/" + ruta);

            BufferedReader en = new BufferedReader(
                    new InputStreamReader(abastecerRuta.openStream()));

            String data = en.readLine();
            StringTokenizer tokens = new StringTokenizer(data);

            nivel = Integer.parseInt(tokens.nextToken());
            alto = Integer.parseInt(tokens.nextToken());
            ancho = Integer.parseInt(tokens.nextToken());

            lineaDeContenedor = new String[alto];

            for(int i = 0; i < alto; ++i) {
                lineaDeContenedor[i] = en.readLine().substring(0, ancho);
            }

            en.close();
        } catch (IOException e) {
            throw new CargarNivelException("Error al cargar el nivel " + ruta, e);
        }
    }

    @Override
    public void crearContenedor() {
        for (int y = 0; y < obtenerAltura(); y++) {
            for (int x = 0; x < obtenerAncho(); x++) {
                agregarContenedorANivel( lineaDeContenedor[y].charAt(x), x, y );
            }
        }
    }

    // El siguiente metodo se encarga de establecer que tipo de entidad se generara a partir de los simbolos del text mapa.

    public void agregarContenedorANivel(char caracter, int posicionX, int posicionY) {
        int posicion = posicionX + posicionY * obtenerAncho();

        switch(caracter) { // TODO: minimizar este metodo
            case '0':
                controladorLógico.agregarContenedor(posicion, new BloquePared(posicionX, posicionY, Sprite.bloqueIndestructible));
                break;
            case 'b':
                LayeredEntidad capa = new LayeredEntidad(posicionX, posicionY,
                        new BloquePiso(posicionX ,posicionY, Sprite.piso),
                        new BloqueLadrillo(posicionX ,posicionY, Sprite.bloqueDestructible));

                if(controladorLógico.estaUsandoPowerUp(posicionX, posicionY, nivel) == false) {
                    capa.agregarAnteriorSuperior(new PowerUpBomba(posicionX, posicionY, nivel, Sprite.powerUpBomba));
                }

                controladorLógico.agregarContenedor(posicion, capa);
                break;
            case 'v':
                capa = new LayeredEntidad(posicionX, posicionY,
                        new BloquePiso(posicionX ,posicionY, Sprite.piso),
                        new BloqueLadrillo(posicionX ,posicionY, Sprite.bloqueDestructible));

                if(controladorLógico.estaUsandoPowerUp(posicionX, posicionY, nivel) == false) {
                    capa.agregarAnteriorSuperior(new PowerUpVelocidad(posicionX, posicionY, nivel, Sprite.powerUpVelocidad));
                }

                controladorLógico.agregarContenedor(posicion, capa);
                break;
            case 'f':
                capa = new LayeredEntidad(posicionX, posicionY,
                        new BloquePiso(posicionX ,posicionY, Sprite.piso),
                        new BloqueLadrillo(posicionX ,posicionY, Sprite.bloqueDestructible));

                if(controladorLógico.estaUsandoPowerUp(posicionX, posicionY, nivel) == false) {
                    capa.agregarAnteriorSuperior(new PowerUpFuego(posicionX, posicionY, nivel, Sprite.powerUpFlama));
                }

                controladorLógico.agregarContenedor(posicion, capa);
                break;
            case '1':
                controladorLógico.agregarContenedor(posicion, new LayeredEntidad(posicionX, posicionY,
                        new BloquePiso(posicionX ,posicionY, Sprite.piso),
                        new BloqueLadrillo(posicionX ,posicionY, Sprite.bloqueDestructible)) );
                break;
            case 'x':
                controladorLógico.agregarContenedor(posicion, new LayeredEntidad(posicionX, posicionY,
                        new BloquePiso(posicionX ,posicionY, Sprite.piso),
                        new BloquePortal(posicionX ,posicionY, controladorLógico, Sprite.portal),
                        new BloqueLadrillo(posicionX ,posicionY, Sprite.bloqueDestructible)) );
                break;
            case ' ':
                controladorLógico.agregarContenedor(posicion, new BloquePiso(posicionX, posicionY, Sprite.piso) );
                break;
            case 'j':
                controladorLógico.agregarPersonaje( new Jugador(Coordenadas.tranformarDeContenedorAPixel(posicionX), Coordenadas.tranformarDeContenedorAPixel(posicionY) + JuegoIG.TAMAÑO_CONTENEDORES, controladorLógico) );
                Pantalla.setOffset(0, 0);

                controladorLógico.agregarContenedor(posicion, new BloquePiso(posicionX, posicionY, Sprite.piso) );
                break;
            //Enemigos
            case 'B':
                controladorLógico.agregarPersonaje( new BuhoEPN(Coordenadas.tranformarDeContenedorAPixel(posicionX), Coordenadas.tranformarDeContenedorAPixel(posicionY) + JuegoIG.TAMAÑO_CONTENEDORES, controladorLógico));
                controladorLógico.agregarContenedor(posicion, new BloquePiso(posicionX, posicionY, Sprite.piso) );
                break;
            case 't':
                controladorLógico.agregarPersonaje( new Trent(Coordenadas.tranformarDeContenedorAPixel(posicionX), Coordenadas.tranformarDeContenedorAPixel(posicionY) + JuegoIG.TAMAÑO_CONTENEDORES, controladorLógico));
                controladorLógico.agregarContenedor(posicion, new BloquePiso(posicionX, posicionY, Sprite.piso) );
                break;
            case 'i':
                controladorLógico.agregarPersonaje( new Flamita(Coordenadas.tranformarDeContenedorAPixel(posicionX), Coordenadas.tranformarDeContenedorAPixel(posicionY) + JuegoIG.TAMAÑO_CONTENEDORES, controladorLógico));
                controladorLógico.agregarContenedor(posicion, new BloquePiso(posicionX, posicionY, Sprite.piso) );
                break;
            case 'r', '5':
                controladorLógico.agregarPersonaje( new Rinoceronte(Coordenadas.tranformarDeContenedorAPixel(posicionX), Coordenadas.tranformarDeContenedorAPixel(posicionY) + JuegoIG.TAMAÑO_CONTENEDORES, controladorLógico));
                controladorLógico.agregarContenedor(posicion, new BloquePiso(posicionX, posicionY, Sprite.piso) );
                break;
            default:
                controladorLógico.agregarContenedor(posicion, new BloquePiso(posicionX, posicionY, Sprite.piso) );
                break;
        }
    }

}
