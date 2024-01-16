package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import com.mygdx.game.Tools.ShapeFactory;

public class MapLoader implements Disposable {

    // "wall" que hace referencia a la clase Objeto del .tmx de los muros
    private static final String MAP_WALL = "wall";
    // "player" que hace referencia a la clase Objeto del .tmx del player
    private static final String MAP_PLAYER = "player";
    private static final float OBJECT_DENSITY = 1f;
    private static final float PLAYER_DENSITY = 0.4f;


    private final World mWorld;
    private final TiledMap mMap;
    private final String file;


    public MapLoader(World world, String file) {
        this.mWorld = world;
        this.file = file;
        mMap = new TmxMapLoader().load(file);

        // Debido a que son varios objetos debemos recorrerlos todos
        final Array<RectangleMapObject> walls = mMap.getLayers().get(MAP_WALL).getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject rObject : new Array.ArrayIterator<RectangleMapObject>(walls)) {
            Rectangle rectangle = rObject.getRectangle();
            // Llamando a createRectangle creamos el rectangulo que hace referencia a los limites del mapa
            ShapeFactory.createRectangle(
                    new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2), // position
                    new Vector2(rectangle.getWidth() / 2, rectangle.getHeight() / 2), // size
                    BodyDef.BodyType.StaticBody, mWorld, OBJECT_DENSITY, false);
        }
    }

    public Body getPlayer() {
        final Rectangle rectangle = mMap.getLayers().get(MAP_PLAYER).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        // Llamando a createRectangle creamos el rectangulo que hace referencia al coche del jugador
        return ShapeFactory.createRectangle(
                new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2), // position
                new Vector2(rectangle.getWidth() / 2, rectangle.getHeight() / 2), // size
                BodyDef.BodyType.DynamicBody, mWorld, PLAYER_DENSITY, false);
    }


    @Override
    public void dispose() {
        mMap.dispose();
    }
}