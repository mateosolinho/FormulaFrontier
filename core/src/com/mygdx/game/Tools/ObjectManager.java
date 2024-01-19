package com.mygdx.game.Tools;


import static com.mygdx.game.Constants.META;
import static com.mygdx.game.Constants.PLAYER;
import static com.mygdx.game.Constants.WALLS;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class ObjectManager {

    private final World world;

    public ObjectManager(World world){
        this.world = world;
    }

    public Body createPlayer(TiledMap map) {
        Rectangle playerRectangle = map.getLayers().get(PLAYER).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        return ShapeFactory.createRectangle(
                new Vector2(playerRectangle.getX() + playerRectangle.getWidth() / 2, playerRectangle.getY() + playerRectangle.getHeight() / 2),
                new Vector2(playerRectangle.getWidth() / 2, playerRectangle.getHeight() / 2),
                BodyDef.BodyType.DynamicBody, world, 0.4f, false);
    }

    public void createWalls(TiledMap map) {
        Array<RectangleMapObject> walls = map.getLayers().get(WALLS).getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject rObject : new Array.ArrayIterator<>(walls)) {
            Rectangle rectangle = rObject.getRectangle();
            ShapeFactory.createRectangle(
                    new Vector2((rectangle.getX() + rectangle.getWidth() / 2), (rectangle.getY() + rectangle.getHeight() / 2)),
                    new Vector2(rectangle.getWidth() / 2, rectangle.getHeight() / 2),
                    BodyDef.BodyType.StaticBody, world, 1f, false);
        }
    }

    public void createMeta(TiledMap map) {
        Array<RectangleMapObject> metas = map.getLayers().get(META).getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject rObject : new Array.ArrayIterator<>(metas)) {
            Rectangle rectangle = rObject.getRectangle();
            ShapeFactory.createRectangle(
                    new Vector2((rectangle.getX() + rectangle.getWidth() / 2), (rectangle.getY() + rectangle.getHeight() / 2)),
                    new Vector2(rectangle.getWidth() / 2, rectangle.getHeight() / 2),
                    BodyDef.BodyType.StaticBody, world, 1f, true);
        }
    }
}
