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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Game;
import com.mygdx.game.Gamemodes.TimeAttack;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.ButtonCreator;
import com.mygdx.game.Tools.MapLoader;
import com.mygdx.game.Tools.PreferencesManager;
import com.mygdx.game.Tools.SensorContactListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Class of representing the main game screen where the game action takes place.
 */
public class PlayScreen implements Screen {

    /**
     * Maximum game FPS.
     */
    private static final float TARGET_FPS = 60;

    /**
     * FPS calculation.
     */
    private static final float TIME_PER_FRAME = 1.3f / TARGET_FPS;

    /**
     * Threshold that detects when the turbo is applied.
     */
    private static final float BOOST_THRESHOLD = 12;

    /**
     * Boost force for turbo.
     */
    private static final float BOOST_FORCE = 250.0f;

    /**
     * Driving direction: none.
     */
    private final static int DRIVE_DIRECTION_NONE = 0;

    /**
     * Driving direction: forward.
     */
    private final static int DRIVE_DIRECTION_FORWARD = 1;

    /**
     * Driving direction: backward.
     */
    private final static int DRIVE_DIRECTION_BACKWARD = 2;

    /**
     * Turning direction: none.
     */
    private final static int TURN_DIRECTION_NONE = 0;

    /**
     * Turning direction: left.
     */
    private final static int TURN_DIRECTION_LEFT = 1;

    /**
     * Turning direction: right.
     */
    private final static int TURN_DIRECTION_RIGHT = 2;

    /**
     * Drift coefficient for vehicle slippage.
     */
    private final static float DRIFT = 0.99f;

    /**
     * Default driving speed.
     */
    private static float DRIVE_SPEED = 150.0f;

    /**
     * Default turning speed.
     */
    private static float TURN_SPEED = 2.5f;

    /**
     * Maximum speed allowed.
     */
    private static float MAX_SPEED = 55.0f;

    /**
     * Vehicle's current driving direction: none.
     */
    private int driveDirection = DRIVE_DIRECTION_NONE;

    /**
     * Current vehicle turning direction: none.
     */
    private int turnDirection = TURN_DIRECTION_NONE;

    /**
     * Time interval for updating processes in milliseconds.
     */
    private final int TICK = 50;

    /**
     * Stage instance.
     */
    private final Stage stage;

    /**
     * Base vector for position calculations.
     */
    private Vector2 baseVector;

    /**
     * Orthographic camera for game view.
     */
    private final OrthographicCamera camera;

    /**
     * Tiled map renderer.
     */
    private final TiledMapRenderer tiledMapRenderer;

    /**
     * Bundle of sprites for rendering.
     */
    private final SpriteBatch batch;

    /**
     * Box2D world for physics simulation.
     */
    private final World world;

    /**
     * Box2D Debug Renderer.
     */
    private final Box2DDebugRenderer b2rd;

    /**
     * Player's body in the Box2D world.
     */
    private final Body player;

    /**
     * Game instance.
     */
    private final Game game;

    /**
     * ButtonCreator instance.
     */
    private final ButtonCreator buttonCreator;

    /**
     * Player texture.
     */
    private final Texture playerTexture;

    /**
     * Player sprite.
     */
    private final Sprite playerSprite;

    /**
     * Map loader to load game maps.
     */
    private final MapLoader mapLoader;

    /**
     * Audio manager for the game.
     */
    private final AudioManager audioManager;

    /**
     * TimeAttack instance.
     */
    public static TimeAttack timeAttack = new TimeAttack();

    /**
     * List that stores traffic light textures.
     */
    private final ArrayList<Texture> semaforos = new ArrayList<>();

    /**
     * Total accumulated time.
     */
    private long tTotal = 0;

    /**
     * Indicator if the pause button has been pressed.
     */
    private boolean isPausePressed;

    /**
     * Indicator if the traffic light is active.
     */
    private boolean isSemaforoActive;

    /**
     * Time elapsed since the activation of the traffic light.
     */
    private float timeSemaforo = 0;

    /**
     * Traffic light sprite index.
     */
    private int spriteSemaforo = 0;

    /**
     * Date format to represent time in "HH:mm:ss" format.
     */
    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:SS");

    /**
     * Calendar instance used to work with system date and time.
     */
    private static final Calendar cal = Calendar.getInstance();

    /**
     * Function to format the time in 'mm' to the indicated format.
     *
     * @param tiempo Unit time in 'mm'.
     * @return String of the string already formatted over time.
     */
    public static String formatTiempo(long tiempo) {
        cal.setTimeInMillis(tiempo - 3600000);
        return dateFormat.format(cal.getTime());
    }

    /**
     * PlayScreen constructor.
     *
     * @param game Game instance.
     */
    public PlayScreen(Game game) {
        this.game = game;

        semaforos.add(new Texture(Gdx.files.internal("UI/gameUI/sem_4.png")));
        semaforos.add(new Texture(Gdx.files.internal("UI/gameUI/sem_1.png")));
        semaforos.add(new Texture(Gdx.files.internal("UI/gameUI/sem_2.png")));
        semaforos.add(new Texture(Gdx.files.internal("UI/gameUI/sem_3.png")));
        semaforos.add(new Texture(Gdx.files.internal("UI/gameUI/sem_4.png")));
        semaforos.add(new Texture(Gdx.files.internal("UI/gameUI/sem_5.png")));

        batch = new SpriteBatch();

        isPausePressed = false;

        world = new World(new Vector2(0, 0), true);
        b2rd = new Box2DDebugRenderer();
        mapLoader = new MapLoader(world);
        camera = mapLoader.getCamera();
        tiledMapRenderer = mapLoader.getTileMapRenderer();

        playerTexture = new Texture(Gdx.files.internal(CarSelectionScreen.rutaCoche));
        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(2f, 3.6f);
        playerSprite.setOrigin(playerSprite.getWidth() / 2f, playerSprite.getHeight() / 2f);

        TimeAttack.resetTimes();

        player = mapLoader.getPlayer();
        player.setLinearDamping(0.5f);

        buttonCreator = new ButtonCreator();
        buttonCreator.createGameLabels();
        stage = buttonCreator.createGameButtons();
        world.setContactListener(new SensorContactListener(buttonCreator));
        audioManager = MainScreen.getAudioManager();

        if (PreferencesManager.getMusica()) {
            audioManager.stopMusicMenu();
            audioManager.startSemaforoMusic();
        }
    }

    /**
     * Gets the stage of the user interface.
     *
     * @return The UI stage used on the game screen.
     */
    public Stage getStage() {
        return stage;
    }

    @Override
    public void show() {
    }

    /**
     * Function that renders the game screen.
     *
     * @param delta The time since the last frame in seconds.
     */
    @Override
    public void render(float delta) {
        timeSemaforo = timeSemaforo + delta;

        float deltaTime = Gdx.graphics.getDeltaTime();

        if (deltaTime < TIME_PER_FRAME) {
            try {
                Thread.sleep((long) ((TIME_PER_FRAME - deltaTime) * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        baseVector = new Vector2(0, 0);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timeAttack.getBestTime();

        ButtonCreator.lblTiempo.setText(TimeAttack.getTiempoActual());
        ButtonCreator.lblBestTime.setText(Game.bundle.get("mejorTiempo") + ": " + formatTiempo(PreferencesManager.getTiempo1Milis()));

        if (SensorContactListener.vVueltas > 0) {
            ButtonCreator.lblLastTime.setText(timeAttack.getLastTime() + " ");
        }

        if (isPausePressed) {
            isPausePressed = false;
        }

        update();

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
     * Function to manage the sliding of the object.
     * First, the forward and lateral velocity of the object is obtained.
     * The speeds are then combined to manage the slip.
     */
    private void handleDrift() {
        Vector2 forwardSpeed = getForwardVelocity();
        Vector2 lateralSpeed = getLateralVelocity();
        player.setLinearVelocity(forwardSpeed.x + lateralSpeed.x * DRIFT, forwardSpeed.y + lateralSpeed.y * DRIFT);
    }

    /**
     * Indicates if the up arrow key is pressed.
     */
    boolean isUpPressed = false;

    /**
     * Indicates if the down arrow key is pressed.
     */
    boolean isDownPressed = false;

    /**
     * Indicates if the right arrow key is pressed.
     */
    boolean isRightPressed = false;

    /**
     * Indicates if the left arrow key is pressed.
     */
    boolean isLeftPressed = false;

    /**
     * Processes player input and applies corresponding physics to the player in the Box2D world.
     */
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
     * Function to manage the forward speed of the object.
     *
     * @return multiply the dot product * the normal vector to get the forward velocity.
     */
    private Vector2 getForwardVelocity() {
        Vector2 currentNormal = player.getWorldVector(new Vector2(0, 1));
        float dotProduct = currentNormal.dot(player.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    /**
     * Function to manage the lateral speed of the object.
     *
     * @return multiply the dot product * the normal vector to get the lateral velocity.
     */
    private Vector2 getLateralVelocity() {
        Vector2 currentNormal = player.getWorldVector(new Vector2(1, 0));
        float dotProduct = currentNormal.dot(player.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    /**
     * Vector to multiply a scalar by a three-dimensional vector.
     *
     * @param a The scalar to multiply.
     * @param v The two-dimensional vector to multiply. 
     * @return New two-dimensional vector result of the multiplication.
     */
    private Vector2 multiply(float a, Vector2 v) {
        return new Vector2(a * v.x, a * v.y);
    }

    /**
     * Function that handles user input.
     */
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

        buttonCreator.getImageButtonPause().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseScreen(game));
                isPausePressed = true;

            }
        });

    }

    /**
     * Modifies the player's driving speeds.
     *
     * @param newSpeed     The player's new forward speed.
     * @param newTurnSpeed The player's new turning speed.
     * @param newMaxSpeed  The player's new maximum speed.
     */
    public static void modifyDriveSpeed(float newSpeed, float newTurnSpeed, float newMaxSpeed) {
        DRIVE_SPEED = newSpeed;
        TURN_SPEED = newTurnSpeed;
        MAX_SPEED = newMaxSpeed;
    }

    /**
     * Draw the visual elements on the game screen.
     */
    private void draw() {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        playerSprite.setPosition(player.getPosition().x - playerSprite.getWidth() / 2, player.getPosition().y - playerSprite.getHeight() / 2);
        playerSprite.setRotation(player.getAngle() * MathUtils.radiansToDegrees);
        playerSprite.draw(batch);

        float tick = 1f;

        if (timeSemaforo > tick) {
            spriteSemaforo++;
            timeSemaforo = 0;
        }
        if (spriteSemaforo < semaforos.size()) {
            batch.draw(semaforos.get(spriteSemaforo),
                    player.getPosition().x - 15 / 2f,
                    player.getPosition().y - 11.1f / 2f,
                    15, 11.1f);
            isSemaforoActive = true;
        }
        batch.end();
        if (spriteSemaforo == 5) {
            isSemaforoActive = false;
            handleInput();
            spriteSemaforo = 6;
            audioManager.startMusicCarrera();
            if (!PreferencesManager.getMusica()) {
                audioManager.stopMusicCarrera();
            }
        }
    }

    /**
     * Accelerometer value in the Z axis.
     */
    float accelerometerZ;

    /**
     * Update game status.
     * This function is to update the camera position, check the accelerometer
     * to apply a pulse and update the world simulation.
     */
    private void update() {
        camera.position.set(player.getPosition(), 0);
        camera.update();

        accelerometerZ = Gdx.input.getAccelerometerZ();

        if (!isSemaforoActive) {
            if (accelerometerZ > BOOST_THRESHOLD) {
                accelerometerZ = 0;
                applyBoost();
            }
        }

        world.step(1 / 60f, 5, 3);
    }

    /**
     * Apply force on the player.
     */
    private void applyBoost() {
        player.applyForceToCenter(player.getWorldVector(new Vector2(0, BOOST_FORCE)), true);
    }

    /**
     * Method invoked when the screen changes size.
     *
     * @param width  New width of the screen.
     * @param height New height of the screen.
     */
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

    /**
     * Release the resources used by the screen.
     */
    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        b2rd.dispose();
        stage.dispose();
        playerTexture.dispose();

        for (Texture texture : semaforos) {
            texture.dispose();
        }
    }
}