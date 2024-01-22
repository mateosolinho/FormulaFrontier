package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.BodyHolder;
import com.mygdx.game.Tools.ButtonCreator;
import com.mygdx.game.Tools.MapLoader;
import com.mygdx.game.Tools.ObjectManager;
import com.mygdx.game.Tools.SensorContactListener;

// https://www.iforce2d.net/b2dtut/top-down-car <- Documento de explicación de las físicas 2d
public class PlayScreen implements Screen {

    private final static int DRIVE_DIRECTION_NONE = 0;
    private final static int DRIVE_DIRECTION_FORWARD = 1;
    private final static int DRIVE_DIRECTION_BACKWARD = 2;

    private final static int TURN_DIRECTION_NONE = 0;
    private final static int TURN_DIRECTION_LEFT = 1;
    private final static int TURN_DIRECTION_RIGHT = 2;

    private final static float DRIFT = 0.99f;

    private final static float DRIVE_SPEED = 150.0f;
    private final static float TURN_SPEED = 2.5f;
    private final static float MAX_SPEED = 50.0f;

    private int driveDirection = DRIVE_DIRECTION_NONE;
    private int turnDirection = TURN_DIRECTION_NONE;

    private final int TICK = 50;

    private Stage stage;
    private Vector2 baseVector;
    private final OrthographicCamera camera;
    private final TiledMapRenderer tiledMapRenderer;
    private final SpriteBatch batch;
    private final World world;
    private final Box2DDebugRenderer b2rd;
    private final Body player;
    private long tTotal = 0;

    private final ButtonCreator buttonCreator;
    private final Texture playerTexture;
    private final Sprite playerSprite;
    private final MapLoader mapLoader;

    public PlayScreen() {

        batch = new SpriteBatch();

        world = new World(new Vector2(0, 0), true);
        b2rd = new Box2DDebugRenderer();

        mapLoader = new MapLoader(world);

        camera = mapLoader.getCamera();
        tiledMapRenderer = mapLoader.getTileMapRenderer();

        playerTexture = new Texture(Gdx.files.internal("Cars/hyundaiVerde.png"));
        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(3f, 4.2f);
        playerSprite.setOrigin(playerSprite.getWidth() / 2f, playerSprite.getHeight() / 2f);

        player = mapLoader.getPlayer();
        player.setLinearDamping(0.5f);
        buttonCreator = new ButtonCreator(stage);
        stage = buttonCreator.createButtons();
        handleInput();

        world.setContactListener(new SensorContactListener(buttonCreator));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update();
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
        playerSprite.setRotation(player.getAngle() * MathUtils.radiansToDegrees);
        playerSprite.draw(batch);
        batch.end();
    }

    private void update() {
        camera.position.set(player.getPosition(), 0);
        camera.update();

        world.step(1/60f,5,3);
    }

    @Override
    public void resize(int width, int height) {
        mapLoader.getViewport().update(width, height, true);
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
        playerTexture.dispose();
        playerTexture.dispose();
    }
}