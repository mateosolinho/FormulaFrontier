package com.mygdx.game.Screens;

import static com.mygdx.game.Entities.Car.DRIVE_DIRECTION_BACKWARD;
import static com.mygdx.game.Entities.Car.DRIVE_DIRECTION_FORWARD;
import static com.mygdx.game.Entities.Car.DRIVE_DIRECTION_NONE;
import static com.mygdx.game.Entities.Car.TURN_DIRECTION_LEFT;
import static com.mygdx.game.Entities.Car.TURN_DIRECTION_NONE;
import static com.mygdx.game.Entities.Car.TURN_DIRECTION_RIGHT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Entities.Car;

// https://www.iforce2d.net/b2dtut/top-down-car <- Documento de explicación de las físicas 2d
public class PlayScreen implements Screen {
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer b2rd;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Car player;
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
        // Fillviewport hace que se ajuste al tamaño de la pantalla
        viewport = new FillViewport(640 / 50.0f, 480 / 50.0f , camera);
        mapLoader = new MapLoader(world);
        player = new Car(mapLoader.getPlayer());


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        update(delta);
        draw();
    }




    private void handleInput() {
        // TODO cuando funcione el programa cambiarlo a los botones en pantalla con EventListener
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.setDriveDirection(DRIVE_DIRECTION_FORWARD);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.setDriveDirection(DRIVE_DIRECTION_BACKWARD);
        } else {
            player.setDriveDirection(DRIVE_DIRECTION_NONE);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.setTurnDirection(TURN_DIRECTION_LEFT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.setTurnDirection(TURN_DIRECTION_RIGHT);
        } else {
            player.setTurnDirection(TURN_DIRECTION_NONE);
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
        player.update(delta);
        // Centrar la cámara en el jugador en cada render
        camera.position.set(player.getBody().getPosition(), 0);
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
