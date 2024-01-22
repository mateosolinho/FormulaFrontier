package com.mygdx.game.Tools;

import static com.mygdx.game.Constants.PPM;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MapLoader implements Disposable {

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final OrthogonalTiledMapRenderer tiledMapRenderer;

    private final TiledMap map;
    private final Body player;

    public MapLoader(World world) {
        map = new TmxMapLoader().load("TrackFiles/Track1/track1.tmx");
        float mapWidth = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
        float mapHeight = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);


        camera = new OrthographicCamera(mapWidth,mapHeight);
        camera.setToOrtho(false, mapWidth/ PPM , mapHeight / PPM);
        camera.zoom = 0.3f;
        viewport = new FitViewport(mapWidth/PPM, mapHeight/PPM,camera);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1/PPM);

        ObjectManager objectManager = new ObjectManager(world);
        player = objectManager.createPlayer(map);
        objectManager.createWalls(map);
        objectManager.createMeta(map);
    }

    public Body getPlayer(){
        return player;
    }

    public OrthographicCamera getCamera(){
        return camera;
    }

    public Viewport getViewport(){
        return viewport;
    }

    public TiledMapRenderer getTileMapRenderer(){
        return tiledMapRenderer;
    }
    
    @Override
    public void dispose() {
        map.dispose();
    }
}