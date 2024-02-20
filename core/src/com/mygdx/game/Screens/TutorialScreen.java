package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Game;

public class TutorialScreen implements Screen {
    private final Texture[] textures;
    private final Stage stage;
    private int currentTextureIndex;
    private final Game game;
    private boolean canTouch;
    private float elapsedTime = 0f;

    public TutorialScreen(Game game) {
        this.game = game;
        textures = new Texture[]{
                new Texture("Tutorial/tutorial4.jpg"),
                new Texture("Tutorial/tutorial5.jpg"),
                new Texture("Tutorial/tutorial1.jpg"),
                new Texture("Tutorial/tutorial2.jpg"),
                new Texture("Tutorial/tutorial3.jpg"),
        };

        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        currentTextureIndex = 0;
        canTouch = true;

        Image image = new Image(textures[currentTextureIndex]);
        stage.addActor(image);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isTouched() && canTouch) {
            currentTextureIndex++;

            if (currentTextureIndex >= textures.length) {
                Gdx.input.setInputProcessor(MainScreen.getStage());
                game.setMainScreen();
                dispose();
            } else {
                Image image = new Image(textures[currentTextureIndex]);
                stage.clear();
                stage.addActor(image);
            }
            canTouch = false;
            elapsedTime = 0;
        }


        elapsedTime += delta;

        float cooldownTime = 0.3f;
        if (elapsedTime >= cooldownTime) {
            canTouch = true;
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}
