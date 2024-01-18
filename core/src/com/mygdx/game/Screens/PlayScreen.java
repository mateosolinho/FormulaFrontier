package com.mygdx.game.Screens;

import static com.mygdx.game.Constants.PLAYER;
import static com.mygdx.game.Constants.PPM;
import static com.mygdx.game.Constants.WALLS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Tools.ButtonCreator;
import com.mygdx.game.Tools.ShapeFactory;

// https://www.iforce2d.net/b2dtut/top-down-car <- Documento de explicación de las físicas 2d
public class PlayScreen implements Screen {

    private final static int DRIVE_DIRECTION_NONE = 0;
    private final static int DRIVE_DIRECTION_FORWARD = 1;
    private final static int DRIVE_DIRECTION_BACKWARD = 2;

    private final static int TURN_DIRECTION_NONE = 0;
    private final static int TURN_DIRECTION_LEFT = 1;
    private final static int TURN_DIRECTION_RIGHT = 2;

    private final static float DRIFT = 0.99f;

    private final static float DRIVE_SPEED = 100.0f;
    private final static float TURN_SPEED = 2.5f;
    private final static float MAX_SPEED = 30.0f;

    private int driveDirection = DRIVE_DIRECTION_NONE;
    private int turnDirection = TURN_DIRECTION_NONE;

    private final int TICK = 50;

    private Stage stage;
    private Vector2 baseVector;
    private final SpriteBatch batch;
    private final World world;
    private final Box2DDebugRenderer b2rd;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Body player;
    private long tTotal = 0;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer tiledMapRenderer;

    private ButtonCreator buttonCreator;
    private Texture playerTexture;
    private Sprite playerSprite;

    public PlayScreen() {
        batch = new SpriteBatch();

        map = new TmxMapLoader().load("TrackFiles/Track1/track1.tmx");
        float mapWidth = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
        float mapHeight = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);

        camera = new OrthographicCamera(mapWidth,mapHeight);
        camera.setToOrtho(false, mapWidth/ PPM , mapHeight / PPM);
        camera.zoom = 0.3f;
        viewport = new FitViewport(mapWidth/PPM, mapHeight/PPM,camera);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1/PPM);

        world = new World(new Vector2(0, 0), true);
        b2rd = new Box2DDebugRenderer();

        playerTexture = new Texture(Gdx.files.internal("Cars/pitstop_car_16.png"));
        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(1.9f, 3f);
        playerSprite.setOrigin(playerSprite.getWidth() / 2f, playerSprite.getHeight() / 2f);

        player = getPlayer();
        getWalls();

        player.setLinearDamping(0.5f);
        buttonCreator = new ButtonCreator(stage);
        stage = buttonCreator.createButtons();
        handleInput();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        baseVector = new Vector2(0, 0);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();


        if (System.currentTimeMillis() - tTotal > TICK) {

            if (isUpPressed) {
                driveDirection = DRIVE_DIRECTION_FORWARD;
            } else if (isDownPressed) {
                driveDirection = DRIVE_DIRECTION_BACKWARD;
            } else {
                driveDirection = DRIVE_DIRECTION_NONE;
            }

            if (isRightPressed) {
                turnDirection = TURN_DIRECTION_RIGHT;
            } else if (isLeftPressed) {
                turnDirection = TURN_DIRECTION_LEFT;
            } else {
                turnDirection = TURN_DIRECTION_NONE;
            }
            processInput();
            tTotal = System.currentTimeMillis();
        }
        // TODO pasar el handleinput a otra clase
        handleDrift();
        draw();
        stage.act(delta);
        stage.draw();
    }

    /**
     * Método para gestionar el deslizamiento del objeto
     * Primero se obtiene la velocidad hacia adelante y lateral del objeto
     * Posteriormente se combinan las velocidades para gestionar el deslizamiento
     */
    private void handleDrift() {
        Vector2 forwardSpeed = getForwardVelocity();
        Vector2 lateralSpeed = getLateralVelocity();
        player.setLinearVelocity(forwardSpeed.x + lateralSpeed.x * DRIFT, forwardSpeed.y + lateralSpeed.y * DRIFT);
    }

    boolean isUpPressed = false;
    boolean isDownPressed = false;
    boolean isRightPressed = false;
    boolean isLeftPressed = false;

    private void processInput() {
        switch (turnDirection) {
            case TURN_DIRECTION_RIGHT:
                player.setAngularVelocity(-TURN_SPEED);
                break;
            case TURN_DIRECTION_LEFT:
                player.setAngularVelocity(TURN_SPEED);
                break;
            default:
                if (turnDirection == TURN_DIRECTION_NONE && player.getAngularVelocity() != 0) {
                    player.setAngularVelocity(0.0f);
                }
                break;
        }

        switch (driveDirection) {
            case DRIVE_DIRECTION_FORWARD:
                baseVector.set(0, DRIVE_SPEED);
                break;
            case DRIVE_DIRECTION_BACKWARD:
                baseVector.set(0, -DRIVE_SPEED);
                break;
        }

        if (!baseVector.isZero() && player.getLinearVelocity().len() < MAX_SPEED) {
            player.applyForceToCenter(player.getWorldVector(baseVector), true);
        }
    }

    /**
     * Metodo para gestionar la velocidad hacia adelante del objeto
     *
     * @return multiplica el producto punto * el vector normal para obtener la velocidad hacia adelante
     */
    private Vector2 getForwardVelocity() {
        Vector2 currentNormal = player.getWorldVector(new Vector2(0, 1));
        float dotProduct = currentNormal.dot(player.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    /**
     * Metodo para obtener la velocidad lateral del objeto
     *
     * @return multiplica el producto punto * el vector normal para obtener la velocidad lateral
     */
    private Vector2 getLateralVelocity() {
        Vector2 currentNormal = player.getWorldVector(new Vector2(1, 0));
        float dotProduct = currentNormal.dot(player.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    /**
     * Vector para multiplicar un escalar por un vector tridimensional
     *
     * @param a El escalar a multiplicar
     * @param v El vector bidimensional a multiplicar
     * @return Un nuevo vector bidimensional resultado de la multiplicación.
     */
    private Vector2 multiply(float a, Vector2 v) {
        return new Vector2(a * v.x, a * v.y);
    }

    private void handleInput() {
        // -------------------------------------------------------------------------------------
        buttonCreator.getImageButtonArriba().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isUpPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isUpPressed = false;
            }
        });

        buttonCreator.getImageButtonAbajo().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isDownPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isDownPressed = false;
            }
        });

        buttonCreator.getImageButtonDerecha().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isRightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isRightPressed = false;
            }
        });

        buttonCreator.getImageButtonIzquierda().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isLeftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isLeftPressed = false;
            }
        });
    }

    private void draw() {
        batch.setProjectionMatrix(camera.combined);
        b2rd.render(world, camera.combined);

        batch.begin();
        playerSprite.setPosition(player.getPosition().x - playerSprite.getWidth() / 2, player.getPosition().y - playerSprite.getHeight() / 2);
        playerSprite.setRotation(player.getAngle() * MathUtils.radiansToDegrees);  // Convierte radianes a grados
        playerSprite.draw(batch);
        batch.end();
    }

    private void update(float delta) {
        // Centrar la cámara en el jugador en cada render
        camera.position.set(player.getPosition(), 0);
        camera.update();

        world.step(1/60f,5,3);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        b2rd.dispose();
        stage.dispose();
        map.dispose();
        playerTexture.dispose();
        playerTexture.dispose();
    }

    public Body getPlayer() {
        final Rectangle rectangle = map.getLayers().get(PLAYER).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        return ShapeFactory.createRectangle(
                new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2), // position
                new Vector2(rectangle.getWidth() / 2, rectangle.getHeight() / 2), // size
                BodyDef.BodyType.DynamicBody, world, 0.4f, false);
    }

    public void getWalls(){
        final Array<RectangleMapObject> walls = map.getLayers().get(WALLS).getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject rObject : new Array.ArrayIterator<RectangleMapObject>(walls)) {
            Rectangle rectangle = rObject.getRectangle();
            ShapeFactory.createRectangle(
                    new Vector2((rectangle.getX()+rectangle.getWidth()/2), (rectangle.getY()+rectangle.getHeight()/2)), // position
                    new Vector2(rectangle.getWidth()/2, rectangle.getHeight()/2), // size
                    BodyDef.BodyType.StaticBody, world, 1f, false);
        }
    }

}