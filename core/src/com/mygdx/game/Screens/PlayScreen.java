package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.PooledLinkedList;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

// https://www.iforce2d.net/b2dtut/top-down-car <- Documento de explicación de las físicas 2d
public class PlayScreen implements Screen {

    // Variables para indicar la dirección de conducción
    private static int DRIVE_DIRECTION_NONE = 0;
    private static int DRIVE_DIRECTION_FORWARD = 1;
    private static int DRIVE_DIRECTION_BACKWARD = 2;


    // Variables para indicar hacia donde es el giro
    private  static int TURN_DIRECTION_NONE = 0;
    private  static int TURN_DIRECTION_LEFT = 1;
    private  static int TURN_DIRECTION_RIGHT = 2;


    // Deceleración del vehiculo al deslizarse o girar
    private static float DRIFT = 0.99f;
    // Velocidad de conducción
    private static float DRIVE_SPEED = 120.0f;
    // Velocidad de giro
    private static float TURN_SPEED = 2.0f;
    // Velocidad máxima
    private static float MAX_SPEED = 35.0f;

    // Variable para comprobar la dirección de conducción del objeto
    private int driveDirection = DRIVE_DIRECTION_NONE;
    // Variable para comprobar la direccion de giro del objeto
    private int turnDirection = TURN_DIRECTION_NONE;

    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer b2rd;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Body player;
    private Vector2 GRAVITY = new Vector2(0,0);
    private float ZOOM = 6f;
    private MapLoader mapLoader;

    public PlayScreen(){
        batch = new SpriteBatch();
        // doSleep si esta en True los cuerpos inactivos "duermen" para ahorrar recursos
        world = new World(GRAVITY, true);
        b2rd = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.zoom = ZOOM;
        // Fitviewport hace que se ajuste al tamaño de la pantalla
        viewport = new FitViewport(640 / 50.0f, 480 / 50.0f , camera);
        mapLoader = new MapLoader(world);
        player = mapLoader.getPlayer();

        // Establece el amortiguamiento lineal del objeto player
        player.setLinearDamping(0.5f);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        processInput();
        update(delta);
        handleDrift();
        draw();
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

    private void processInput() {
        Vector2 baseVector = new Vector2(0,0);

        if (turnDirection == TURN_DIRECTION_RIGHT){
            player.setAngularVelocity(-TURN_SPEED);
        } else if (turnDirection == TURN_DIRECTION_LEFT) {
            player.setAngularVelocity(TURN_SPEED);
        } else if (turnDirection == TURN_DIRECTION_NONE  && player.getAngularVelocity() != 0) {
            player.setAngularVelocity(0.0f);
        }

        if (driveDirection == DRIVE_DIRECTION_FORWARD){
            baseVector.set(0, DRIVE_SPEED);
        } else if (driveDirection == DRIVE_DIRECTION_BACKWARD){
            baseVector.set(0, -DRIVE_SPEED);
        }

        // Comprueba si hay una entrada activa para avanzar o retroceder y la velocidad actual del objeto es menor que la velocidad máxima
        if (!baseVector.isZero() && player.getLinearVelocity().len() < MAX_SPEED){
            // Aplica una fuerta en el centro del objeto
            player.applyForceToCenter(player.getWorldVector(baseVector), true);
        }
    }

    /**
     * Metodo para gestionar la velocidad hacia adelante del objeto
     * @return multiplica el producto punto * el vector normal para obtener la velocidad hacia adelante
     */
    private Vector2 getForwardVelocity(){
        // Obtiene el vector normal del objeto en relación con el eje y
        Vector2 currentNormal = player.getWorldVector(new Vector2(0,1));
        float dotProduct = currentNormal.dot(player.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    /**
     * Metodo para obtener la velocidad lateral del objeto
     * @return multiplica el producto punto * el vector normal para obtener la velocidad lateral
     */
    private Vector2 getLateralVelocity(){
        Vector2 currentNormal = player.getWorldVector(new Vector2(1,0));
        float dotProduct = currentNormal.dot(player.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    /**
     * Vector para multiplicar un escalar por un vector tridimensional
     * @param a El escalar a multiplicar
     * @param v El vector bidimensional a multiplicar
     * @return Un nuevo vector bidimensional resultado de la multiplicación.
     */
    private Vector2 multiply(float a, Vector2 v){
        return new Vector2(a * v.x , a * v.y);
    }
    private void handleInput() {
        // TODO cuando funcione el programa cambiarlo a los botones en pantalla con EventListener
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            driveDirection = DRIVE_DIRECTION_FORWARD;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            driveDirection = DRIVE_DIRECTION_BACKWARD;
        } else {
            driveDirection = DRIVE_DIRECTION_NONE;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            turnDirection = TURN_DIRECTION_LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            turnDirection = TURN_DIRECTION_RIGHT;
        } else {
            turnDirection = TURN_DIRECTION_NONE;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)){
            camera.zoom -= 0.4f;
            Gdx.app.log("Input Q", "Q");
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)){
            camera.zoom += 0.4f;
            Gdx.app.log("Input E", "E");
        }
    }

    private void draw() {
        // Se convierten las coordenadas del mundo en coordenadas de pantalla
        batch.setProjectionMatrix(camera.combined);
        // Dibuja esos elementos en pantalla
        b2rd.render(world, camera.combined);
    }

    private void update(float delta) {
        // Centrar la cámara en el jugador en cada render
        camera.position.set(player.getPosition(),0);
        camera.update();

        world.step(delta, 6,2);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        mapLoader.dispose();
    }
}
