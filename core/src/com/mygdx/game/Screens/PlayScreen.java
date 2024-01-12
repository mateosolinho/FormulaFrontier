package com.mygdx.game.Screens;

import static com.mygdx.game.Entities.Car.DRIVE_DIRECTION_BACKWARD;
import static com.mygdx.game.Entities.Car.DRIVE_DIRECTION_FORWARD;
import static com.mygdx.game.Entities.Car.DRIVE_DIRECTION_NONE;
import static com.mygdx.game.Entities.Car.TURN_DIRECTION_LEFT;
import static com.mygdx.game.Entities.Car.TURN_DIRECTION_NONE;
import static com.mygdx.game.Entities.Car.TURN_DIRECTION_RIGHT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Entities.Car;
import com.mygdx.game.Tools.MapLoader;

import UI.ButtonController;

// https://www.iforce2d.net/b2dtut/top-down-car <- Documento de explicación de las físicas 2d
public class PlayScreen extends InputAdapter implements Screen {

    private Stage stage;
    private final SpriteBatch batch;
    private final World world;
    private final Box2DDebugRenderer b2dr;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Car player;
    private final MapLoader mapLoader;
    private final Vector2 GRAVITY = new Vector2(0,0);
    private final float ZOOM = 6f;

    /**
     * Constructor de la pantalla de juego.
     */
    public PlayScreen() {
        batch = new SpriteBatch();
        world = new World(GRAVITY, true);
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.zoom = ZOOM;
        viewport = new FillViewport(640 / 50.0f, 480 / 50.0f , camera);
        mapLoader = new MapLoader(world);
        player = new Car(35.0f, 0.8f, 80, mapLoader, Car.DRIVE_4WD, world);
    }

    @Override
    public void show() {
        ButtonController buttonController = new ButtonController(stage,camera);
        stage = buttonController.getStage();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        update(delta);
        draw();
        stage.draw();
    }

    /**
     * Maneja la entrada del usuario, como las teclas presionadas.
     */
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.setDriveDirection(DRIVE_DIRECTION_FORWARD);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.setDriveDirection(DRIVE_DIRECTION_BACKWARD);
        } else {
            player.setDriveDirection(DRIVE_DIRECTION_NONE);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.setTurnDirection(TURN_DIRECTION_LEFT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.setTurnDirection(TURN_DIRECTION_RIGHT);
        } else {
            player.setTurnDirection(TURN_DIRECTION_NONE);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= ZOOM;
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom += ZOOM;
        }
    }

    private void draw() {
        batch.setProjectionMatrix(camera.combined);
        b2dr.render(world, camera.combined);
    }

    private void update(float delta) {
        player.update(delta);
        camera.position.set(player.getBody().getPosition(), 0);
        camera.update();
        world.step(delta, 6, 2);
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
        b2dr.dispose();
        mapLoader.dispose();
        stage.dispose();
    }
}
