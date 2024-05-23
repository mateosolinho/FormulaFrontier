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
 * Class to load and render the game map.
 */
public class MapLoader implements Disposable {

    /**
     * Orthographic camera used to view the scene.
     */
    private final OrthographicCamera camera;

    /**
     * Viewport that defines the visible area within the window.
     */
    private final Viewport viewport;

    /**
     * Orthogonal tiled map renderer used to render the map.
     */
    private final OrthogonalTiledMapRenderer tiledMapRenderer;

    /**
     * Tiled map used to represent the structure of the game world.
     */
    private final TiledMap map;

    /**
     * Player's body in the physical world of the game.
     */
    private final Body player;

    /**
     * MapLoader constructor
     *
     * @param world Box2D world where the map will be loaded.
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
     * Gets the player's body.
     *
     * @return Player's body.
     */
    public Body getPlayer() {
        return player;
    }

    /**
     * Gets the orthographic camera.
     *
     * @return Orthographic camera.
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Gets the viewport.
     *
     * @return Viewport.
     */
    public Viewport getViewport() {
        return viewport;
    }

    /**
     * Gets the tilemap renderer.
     *
     * @return Tilemap renderer.
     */
    public TiledMapRenderer getTileMapRenderer() {
        return tiledMapRenderer;
    }

    /**
     * Frees the resources used by MapLoader when delete it.
     */
    @Override
    public void dispose() {
        map.dispose();
        tiledMapRenderer.dispose();
    }
}