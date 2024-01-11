package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.sql.Wrapper;

import Tools.ShapeFactory;

public class PlayScreen implements Screen {
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
        world = new World(GRAVITY, true);
        b2rd = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.zoom = ZOOM;
        viewport = new FitViewport(640 / 50.0f, 480 / 50.0f , camera);
        mapLoader = new MapLoader(world);
        player = mapLoader.getPlayer();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        draw();
    }

    private void draw() {
        batch.setProjectionMatrix(camera.combined);
        b2rd.render(world, camera.combined);
    }

    private void update(float delta) {
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
