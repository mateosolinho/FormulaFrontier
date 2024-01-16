package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Tools.MapLoader;
import com.mygdx.game.Tools.ShapeFactory;

// https://www.iforce2d.net/b2dtut/top-down-car <- Documento de explicación de las físicas 2d
public class PlayScreen implements Screen {

    // Variables para indicar la dirección de conducción
    private final static int DRIVE_DIRECTION_NONE = 0;
    private final static int DRIVE_DIRECTION_FORWARD = 1;
    private final static int DRIVE_DIRECTION_BACKWARD = 2;


    // Variables para indicar hacia donde es el giro
    private final static int TURN_DIRECTION_NONE = 0;
    private final static int TURN_DIRECTION_LEFT = 1;
    private final static int TURN_DIRECTION_RIGHT = 2;


    // Deceleración del vehiculo al deslizarse o girar
    private final static float DRIFT = 0.99f;

    // Velocidad de conducción
    private final static float DRIVE_SPEED = 400.0f;
    //    private static float DRIVE_SPEED = 120.0f;

    // Velocidad de giro
    private final static float TURN_SPEED = 3.0f;
    //    private static float TURN_SPEED = 2.0f;

    // Velocidad máxima
    private final static float MAX_SPEED = 50.0f;
//    private static float MAX_SPEED = 35.0f;

    // Variable para comprobar la dirección de conducción del objeto
    private int driveDirection = DRIVE_DIRECTION_NONE;
    // Variable para comprobar la direccion de giro del objeto
    private int turnDirection = TURN_DIRECTION_NONE;

    private ImageButton imageButtonDerecha;
    private ImageButton imageButtonIzquierda;
    private ImageButton imageButtonArriba;
    private ImageButton imageButtonAbajo;

    private Stage stage;
    private final SpriteBatch batch;
    private final World world;
    private final Box2DDebugRenderer b2rd;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Body player;
    Vector2 baseVector;
    int tick = 50;
    long ttotal = 0;

    //--------------------------
    TiledMap map;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    //--------------------------


    public PlayScreen() {
        batch = new SpriteBatch();
        // doSleep si esta en True los cuerpos inactivos "duermen" para ahorrar recursos
        world = new World(new Vector2(0, 0), true);
        b2rd = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.zoom = 30f;
        // Fitviewport hace que se ajuste al tamaño de la pantalla
        viewport = new FillViewport(Gdx.graphics.getWidth() / 50.0f, Gdx.graphics.getHeight() / 50.0f, camera);
        map = new TmxMapLoader().load("trackFiles/sinnombre.tmx");
//        MapProperties prop = map.getProperties();
//
//        int mapWidth = prop.get("width", Integer.class);
//        int mapHeight = prop.get("height", Integer.class);
//        int tilePixelWidth = prop.get("tilewidth", Integer.class);
//        int tilePixelHeight = prop.get("tileheight", Integer.class);
//
//        int mapPixelWidth = mapWidth * tilePixelWidth;
//        int mapPixelHeight = mapHeight * tilePixelHeight;


        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        player = getPlayer();
        getWalls();

        // Establece el amortiguamiento lineal del objeto player
        player.setLinearDamping(0.5f);
        createButtons();
        handleInput();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        baseVector = new Vector2(0, 0);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (System.currentTimeMillis() - ttotal > tick) {

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
            ttotal = System.currentTimeMillis();
        }

        update(delta);
        handleDrift();
        draw();
        stage.act(delta);
        stage.draw();
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
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

        // Comprueba si hay una entrada activa para avanzar o retroceder y la velocidad actual del objeto es menor que la velocidad máxima
        if (!baseVector.isZero() && player.getLinearVelocity().len() < MAX_SPEED) {
            // Aplica una fuerta en el centro del objeto
            player.applyForceToCenter(player.getWorldVector(baseVector), true);
        }
    }

    /**
     * Metodo para gestionar la velocidad hacia adelante del objeto
     *
     * @return multiplica el producto punto * el vector normal para obtener la velocidad hacia adelante
     */
    private Vector2 getForwardVelocity() {
        // Obtiene el vector normal del objeto en relación con el eje y
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
        imageButtonArriba.addListener(new InputListener() {
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

        imageButtonAbajo.addListener(new InputListener() {
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

        imageButtonDerecha.addListener(new InputListener() {
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

        imageButtonIzquierda.addListener(new InputListener() {
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
// -------------------------------------------------------------------------------------

//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//            driveDirection = DRIVE_DIRECTION_FORWARD;
//        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//            driveDirection = DRIVE_DIRECTION_BACKWARD;
//        } else {
//            driveDirection = DRIVE_DIRECTION_NONE;
//        }
//
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            turnDirection = TURN_DIRECTION_LEFT;
//        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            turnDirection = TURN_DIRECTION_RIGHT;
//        } else {
//            turnDirection = TURN_DIRECTION_NONE;
//        }
//
//        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
//            Gdx.app.exit();
//        }
    }

    private void draw() {
        batch.setProjectionMatrix(camera.combined);
        b2rd.render(world, camera.combined);
    }

    private void update(float delta) {
        // Centrar la cámara en el jugador en cada render
        camera.position.set(player.getPosition(), 0);
        camera.update();
        viewport.apply();

        world.step(delta, 6, 2);
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
    }

    public void createButtons() {

        // Crea una nueva instancia del Stage usando un viewport basado en pantalla (pixeles)
        stage = new Stage(new ScreenViewport());
        // Establece el Stage como el procesador de entrada principal (Gestión de eventos, click, toques en pantalla...)
        Gdx.input.setInputProcessor(stage);

        // Se le asgina una textura a cada botón
        Texture buttonTextureDerecha = new Texture(Gdx.files.internal("UI/flecha-derecha.png"));
        Texture buttonTextureIzquierda = new Texture(Gdx.files.internal("UI/flecha-izquierda.png"));
        Texture buttonTextureArriba = new Texture(Gdx.files.internal("UI/flecha-arriba.png"));
        Texture buttonTextureAbajo = new Texture(Gdx.files.internal("UI/flecha-abajo.png"));

        // Creación de cada botón y asignación de la textura y el estilo
        ImageButton.ImageButtonStyle styleDerecha = new ImageButton.ImageButtonStyle();
        styleDerecha.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureDerecha));
        imageButtonDerecha = new ImageButton(styleDerecha);

        ImageButton.ImageButtonStyle styleIzquierda = new ImageButton.ImageButtonStyle();
        styleIzquierda.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureIzquierda));
        imageButtonIzquierda = new ImageButton(styleIzquierda);

        ImageButton.ImageButtonStyle styleArriba = new ImageButton.ImageButtonStyle();
        styleArriba.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureArriba));
        imageButtonArriba = new ImageButton(styleArriba);

        ImageButton.ImageButtonStyle styleAbajo = new ImageButton.ImageButtonStyle();
        styleAbajo.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureAbajo));
        imageButtonAbajo = new ImageButton(styleAbajo);

        // Obtén las dimensiones de la pantalla
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        stage.getViewport().getCamera().position.set(screenWidth / 2f, 0, 0);


        // TODO los eventos de los botones no coinciden con donde estan los botones en pantalla
        // Asignación de posición y tamaño acada botón
        imageButtonDerecha.setHeight(screenHeight * 0.15f);
        imageButtonDerecha.setWidth(screenWidth * 0.15f);
        imageButtonDerecha.setWidth(screenWidth * 0.15f);
        imageButtonDerecha.setPosition(17 * screenWidth / 20f, -screenHeight / 3f);

        imageButtonIzquierda.setHeight(screenHeight * 0.15f);
        imageButtonIzquierda.setWidth(screenWidth * 0.15f);
        imageButtonIzquierda.setPosition(14 * screenWidth / 20f, -screenHeight / 3f);

        imageButtonArriba.setHeight(screenHeight * 0.15f);
        imageButtonArriba.setWidth(screenWidth * 0.15f);
        imageButtonArriba.setPosition(1 * screenWidth / 20f, -screenHeight / 5f);

        imageButtonAbajo.setHeight(screenHeight * 0.15f);
        imageButtonAbajo.setWidth(screenWidth * 0.15f);
        imageButtonAbajo.setPosition(1 * screenWidth / 20f, -screenHeight / 2.5f);


        // Añadimos al Stage cada botón
        stage.addActor(imageButtonDerecha);
        stage.addActor(imageButtonIzquierda);
        stage.addActor(imageButtonArriba);
        stage.addActor(imageButtonAbajo);

    }

    public Body getPlayer() {
        final Rectangle rectangle = map.getLayers().get("player").getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        // Llamando a createRectangle creamos el rectangulo que hace referencia al coche del jugador
        return ShapeFactory.createRectangle(
                new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2), // position
                new Vector2(rectangle.getWidth() / 2, rectangle.getHeight() / 2), // size
                BodyDef.BodyType.DynamicBody, world, 0.4f, false);
    }

    public void getWalls(){
        final Array<RectangleMapObject> walls = map.getLayers().get("wall").getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject rObject : new Array.ArrayIterator<RectangleMapObject>(walls)) {
            Rectangle rectangle = rObject.getRectangle();
            // Llamando a createRectangle creamos el rectangulo que hace referencia a los limites del mapa
            ShapeFactory.createRectangle(
                    new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2), // position
                    new Vector2(rectangle.getWidth() / 2, rectangle.getHeight() / 2), // size
                    BodyDef.BodyType.StaticBody, world, 1f, false);
        }
    }

}