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
 * Clase encargada de representar la pantalla de juego principal donde se desarrolla la acción del juego.
 */
public class PlayScreen implements Screen {

    /**
     * FPS máximos del juego
     */
    private static final float TARGET_FPS = 60;

    /**
     * Calculo de los FPS
     */
    private static final float TIME_PER_FRAME = 1.3f / TARGET_FPS;

    /**
     * Umbral que detetecta cuando se aplica el turbo
     */
    private static final float BOOST_THRESHOLD = 12;

    /**
     * Fuerza del impulso para el turbo
     */
    private static final float BOOST_FORCE = 250.0f;

    /**
     * Dirección de conducción: ninguna.
     */
    private final static int DRIVE_DIRECTION_NONE = 0;

    /**
     * Dirección de conducción: hacia adelante.
     */
    private final static int DRIVE_DIRECTION_FORWARD = 1;

    /**
     * Dirección de conducción: hacia atrás.
     */
    private final static int DRIVE_DIRECTION_BACKWARD = 2;

    /**
     * Dirección de giro: ninguna.
     */
    private final static int TURN_DIRECTION_NONE = 0;

    /**
     * Dirección de giro: izquierda.
     */
    private final static int TURN_DIRECTION_LEFT = 1;

    /**
     * Dirección de giro: derecha.
     */
    private final static int TURN_DIRECTION_RIGHT = 2;

    /**
     * Coeficiente de drift para el deslizamiento del vehículo.
     */
    private final static float DRIFT = 0.99f;

    /**
     * Velocidad de conducción predeterminada.
     */
    private static float DRIVE_SPEED = 150.0f;

    /**
     * Velocidad de giro predeterminada.
     */
    private static float TURN_SPEED = 2.5f;

    /**
     * Velocidad de maxima permitida.
     */
    private static float MAX_SPEED = 55.0f;

    /**
     * Dirección de la conducción actual del vehículo: ninguna.
     */
    private int driveDirection = DRIVE_DIRECTION_NONE;

    /**
     * Dirección actual del giro del vehículo: ninguna.
     */
    private int turnDirection = TURN_DIRECTION_NONE;

    /**
     * Intervalo de tiempo para la actualización de ciertos procesos en milisegundos.
     */
    private final int TICK = 50;

    private final Stage stage;
    private Vector2 baseVector;
    private final OrthographicCamera camera;
    private final TiledMapRenderer tiledMapRenderer;
    private final SpriteBatch batch;
    private final World world;
    private final Box2DDebugRenderer b2rd;
    private final Body player;
    private final Game game;
    private final ButtonCreator buttonCreator;
    private final Texture playerTexture;
    private final Sprite playerSprite;
    private final MapLoader mapLoader;
    private final AudioManager audioManager;
    static public TimeAttack timeAttack = new TimeAttack();

    private final ArrayList<Texture> semaforos = new ArrayList<>();

    private long tTotal = 0;
    private boolean isPausePressed;
    private boolean isSemaforoActive;
    private float timeSemaforo = 0;
    private int spriteSemaforo = 0;

    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:SS");
    private static final Calendar cal = Calendar.getInstance();

    public static String formatTiempo(long tiempo) {
        cal.setTimeInMillis(tiempo-3600000);
        return dateFormat.format(cal.getTime());
    }

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

    public Stage getStage() {
        return stage;
    }

    @Override
    public void show() {
    }

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

        buttonCreator.getImageButtonPause().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseScreen(game));
                isPausePressed = true;

            }
        });

    }

    public static void modifyDriveSpeed(float newSpeed, float newTurnSpeed, float newMaxSpeed) {
        DRIVE_SPEED = newSpeed;
        TURN_SPEED = newTurnSpeed;
        MAX_SPEED = newMaxSpeed;
    }

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
    float accelerometerX;

    private void update() {
        camera.position.set(player.getPosition(), 0);
        camera.update();

        accelerometerX = Gdx.input.getAccelerometerZ();

        if (!isSemaforoActive ) {
            if (accelerometerX > BOOST_THRESHOLD) {
                accelerometerX = 0;
                applyBoost();
            }
        }

        world.step(1 / 60f, 5, 3);
    }

    private void applyBoost() {
        player.applyForceToCenter(player.getWorldVector(new Vector2(0, BOOST_FORCE)), true);
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

        for (Texture texture : semaforos) {
            texture.dispose();
        }
    }
}