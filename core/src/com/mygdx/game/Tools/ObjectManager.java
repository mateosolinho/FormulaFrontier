package com.mygdx.game.Tools;


import static com.mygdx.game.Constants.CHECKPOINT1;
import static com.mygdx.game.Constants.CHECKPOINT2;
import static com.mygdx.game.Constants.CHECKPOINT3;
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

/**
 * Class to manage the creation of physical objects from a tiled map.
 */
public class ObjectManager {

    /**
     * Box2D world in which physical objects are created.
     */
    private final World world;

    /**
     * ObjectManager constructor.
     *
     * @param world Box2D world in which physical objects are created.
     */
    public ObjectManager(World world) {
        this.world = world;
    }


    /**
     * Creates the player's body from a tiled map.
     *
     * @param map Tiled map containing the player information.
     * @return Created player body.
     */
    public Body createPlayer(TiledMap map) {
        Rectangle playerRectangle = map.getLayers().get(PLAYER).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        Body playerBody = ShapeFactory.createRectangle(
                new Vector2(playerRectangle.getX() + playerRectangle.getWidth() / 2, playerRectangle.getY() + playerRectangle.getHeight() / 2),
                new Vector2(playerRectangle.getWidth() / 2, playerRectangle.getHeight() / 2),
                BodyDef.BodyType.DynamicBody, world, 0.4f, false);
        playerBody.setUserData("player");
        return playerBody;
    }

    /**
     * Create the body of the walls from a tiled map.
     *
     * @param map Tiled map containing wall information. 
     */
    public void createWalls(TiledMap map) {
        Array<RectangleMapObject> walls = map.getLayers().get(WALLS).getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject rObject : new Array.ArrayIterator<>(walls)) {
            Rectangle rectangle = rObject.getRectangle();
            Body bodyWalls = ShapeFactory.createRectangle(
                    new Vector2((rectangle.getX() + rectangle.getWidth() / 2), (rectangle.getY() + rectangle.getHeight() / 2)),
                    new Vector2(rectangle.getWidth() / 2, rectangle.getHeight() / 2),
                    BodyDef.BodyType.StaticBody, world, 1f, false);
            bodyWalls.setUserData("wall");
        }
    }

    /**
     * Create the body of the goal from a tiled map.
     *
     * @param map Tiled map containing goal information.
     */
    public void createMeta(TiledMap map) {
        Rectangle playerRectangle = map.getLayers().get(META).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        Body bodyMeta = ShapeFactory.createRectangle(
                new Vector2(playerRectangle.getX() + playerRectangle.getWidth() / 2, playerRectangle.getY() + playerRectangle.getHeight() / 2),
                new Vector2(playerRectangle.getWidth() / 2, playerRectangle.getHeight() / 2),
                BodyDef.BodyType.DynamicBody, world, 0.4f, true);
        bodyMeta.setUserData("meta");
    }

    /**
     * Creates the body of checkpoint1 from a tiled map.
     *
     * @param map Tiled map containing checkpoint1 information.
     */
    public void createCheckpoint1(TiledMap map) {
        Rectangle playerRectangle = map.getLayers().get(CHECKPOINT1).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        Body check1 = ShapeFactory.createRectangle(
                new Vector2(playerRectangle.getX() + playerRectangle.getWidth() / 2, playerRectangle.getY() + playerRectangle.getHeight() / 2),
                new Vector2(playerRectangle.getWidth() / 2, playerRectangle.getHeight() / 2),
                BodyDef.BodyType.StaticBody, world, 0.4f, true);
        check1.setUserData("check1");
    }

    /**
     * Creates the body of checkpoint2 from a tiled map.
     *
     * @param map Tiled map containing checkpoint2 information.
     */
    public void createCheckpoint2(TiledMap map) {
        Rectangle playerRectangle = map.getLayers().get(CHECKPOINT2).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        Body check2 = ShapeFactory.createRectangle(
                new Vector2(playerRectangle.getX() + playerRectangle.getWidth() / 2, playerRectangle.getY() + playerRectangle.getHeight() / 2),
                new Vector2(playerRectangle.getWidth() / 2, playerRectangle.getHeight() / 2),
                BodyDef.BodyType.StaticBody, world, 0.4f, true);
        check2.setUserData("check2");
    }

    /**
     * Creates the body of checkpoint3 from a tiled map.
     *
     * @param map Tiled map containing checkpoint3 information.
     */
    public void createCheckpoint3(TiledMap map) {
        Rectangle playerRectangle = map.getLayers().get(CHECKPOINT3).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        Body check3 = ShapeFactory.createRectangle(
                new Vector2(playerRectangle.getX() + playerRectangle.getWidth() / 2, playerRectangle.getY() + playerRectangle.getHeight() / 2),
                new Vector2(playerRectangle.getWidth() / 2, playerRectangle.getHeight() / 2),
                BodyDef.BodyType.StaticBody, world, 0.4f, true);
        check3.setUserData("check3");
    }

    /**
     * Creates the body of the exterior of the map from a tiled map.
     *
     * @param map Tiled map that contains the information on the outside of the map.
     */
    public void createExterior(TiledMap map) {
        Array<RectangleMapObject> walls = map.getLayers().get("fuera").getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject rObject : new Array.ArrayIterator<>(walls)) {
            Rectangle rectangle = rObject.getRectangle();
            Body bodyExt = ShapeFactory.createRectangle(
                    new Vector2((rectangle.getX() + rectangle.getWidth() / 2), (rectangle.getY() + rectangle.getHeight() / 2)),
                    new Vector2(rectangle.getWidth() / 2, rectangle.getHeight() / 2),
                    BodyDef.BodyType.StaticBody, world, 1f, true);
            bodyExt.setUserData("ext");
        }
    }
}
