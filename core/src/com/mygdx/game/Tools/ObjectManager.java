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
 * Clase que se encarga de gestionar la creación de objetos físicos a partir de un mapa tiled.
 */
public class ObjectManager {

    /**
     * El mundo Box2D en el que se crean los objetos físicos.
     */
    private final World world;

    /**
     * Constructor de la clase ObjectManager.
     *
     * @param world El mundo Box2D donde se crean los objetos físicos.
     */
    public ObjectManager(World world){
        this.world = world;
    }


    /**
     * Crea el cuerpo del jugador a partir de un mapa tiled.
     *
     * @param map El mapa tiled que contiene la información del jugador.
     * @return El cuerpo del jugador creado.
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
     * Crea el cuerpo de las paredes a partir de un mapa tiled.
     *
     * @param map El mapa tiled que contiene la información del las paredes.
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
     * Crea el cuerpo de la meta a partir de un mapa tiled.
     *
     * @param map El mapa tiled que contiene la información del la meta.
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
     * Crea el cuerpo del checkpoint1 a partir de un mapa tiled.
     *
     * @param map El mapa tiled que contiene la información del checkpoint1.
     */
    public void createCheckpoint1(TiledMap map){
        Rectangle playerRectangle = map.getLayers().get(CHECKPOINT1).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        Body check1 = ShapeFactory.createRectangle(
                new Vector2(playerRectangle.getX() + playerRectangle.getWidth() / 2, playerRectangle.getY() + playerRectangle.getHeight() / 2),
                new Vector2(playerRectangle.getWidth() / 2, playerRectangle.getHeight() / 2),
                BodyDef.BodyType.StaticBody, world, 0.4f, true);
        check1.setUserData("check1");
    }

    /**
     * Crea el cuerpo del checkpoint2 a partir de un mapa tiled.
     *
     * @param map El mapa tiled que contiene la información del checkpoint2.
     */
    public void createCheckpoint2(TiledMap map){
        Rectangle playerRectangle = map.getLayers().get(CHECKPOINT2).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        Body check2 = ShapeFactory.createRectangle(
                new Vector2(playerRectangle.getX() + playerRectangle.getWidth() / 2, playerRectangle.getY() + playerRectangle.getHeight() / 2),
                new Vector2(playerRectangle.getWidth() / 2, playerRectangle.getHeight() / 2),
                BodyDef.BodyType.StaticBody, world, 0.4f, true);
        check2.setUserData("check2");
    }

    /**
     * Crea el cuerpo del checkpoint3 a partir de un mapa tiled.
     *
     * @param map El mapa tiled que contiene la información del checkpoint3.
     */
    public void createCheckpoint3(TiledMap map){
        Rectangle playerRectangle = map.getLayers().get(CHECKPOINT3).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        Body check3 = ShapeFactory.createRectangle(
                new Vector2(playerRectangle.getX() + playerRectangle.getWidth() / 2, playerRectangle.getY() + playerRectangle.getHeight() / 2),
                new Vector2(playerRectangle.getWidth() / 2, playerRectangle.getHeight() / 2),
                BodyDef.BodyType.StaticBody, world, 0.4f, true);
        check3.setUserData("check3");
    }

    /**
     * Crea el cuerpo del exterior del mapa a partir de un mapa tiled.
     *
     * @param map El mapa tiled que contiene la información del exterior del mapa.
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
