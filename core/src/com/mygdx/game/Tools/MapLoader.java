package com.mygdx.game.Tools;

import static com.mygdx.game.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screens.CircuitSelectionScreen;

/**
 * Clase encargada de cargar y renderizar el mapa del juego.
 */
public class MapLoader implements Disposable {

    /**
     * Cámara ortográfica utilizada para visualizar la escena.
     */
    private final OrthographicCamera camera;

    /**
     * Viewport que define el área visible dentro de la ventana.
     */
    private final Viewport viewport;

    /**
     * Renderizador de mapas tiled ortogonales utilizado para renderizar el mapa.
     */
    private final OrthogonalTiledMapRenderer tiledMapRenderer;

    /**
     * Mapa tiled utilizado para representar la estructura del mundo del juego.
     */
    private final TiledMap map;

    /**
     * Cuerpo del jugador en el mundo físico del juego.
     */
    private final Body player;

    /**
     * Constructor de la clase MapLoader.
     *
     * @param world El mundo Box2D donde se cargará el mapa.
     */
    public MapLoader(World world) {
        map = new TmxMapLoader().load(CircuitSelectionScreen.rutaCircuito);
        float mapWidth = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
        float mapHeight = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);

        camera = new OrthographicCamera(mapWidth, mapHeight);
        camera.setToOrtho(false, mapWidth / PPM, mapHeight / PPM);

        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera.zoom = 100000 / (mapWidth * aspectRatio);
        Gdx.app.log("camara",  100000/ (mapWidth * aspectRatio) + " ");

        viewport = new FillViewport(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM, camera);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        ObjectManager objectManager = new ObjectManager(world);
        player = objectManager.createPlayer(map);
        objectManager.createWalls(map);
        objectManager.createMeta(map);
        objectManager.createCheckpoint1(map);
        objectManager.createCheckpoint2(map);
        objectManager.createCheckpoint3(map);
        objectManager.createExterior(map);
    }

    /**
     * Obtiene el cuerpo del jugador.
     *
     * @return El cuerpo del jugador.
     */
    public Body getPlayer() {
        return player;
    }

    /**
     * Obtiene la cámara ortográfica.
     *
     * @return La cámara ortográfica.
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Obtiene el viewport.
     *
     * @return El viewport.
     */
    public Viewport getViewport() {
        return viewport;
    }

    /**
     * Obtiene el renderizador de mapas de mosaicos.
     *
     * @return El renderizador de mapas de mosaicos.
     */
    public TiledMapRenderer getTileMapRenderer() {
        return tiledMapRenderer;
    }

    /**
     * Libera los recursos utilizados por MapLoader al eliminarla.
     */
    @Override
    public void dispose() {
        map.dispose();
        tiledMapRenderer.dispose();
    }
}