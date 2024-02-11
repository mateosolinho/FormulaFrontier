package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Game;
import com.mygdx.game.Tools.ButtonCreator;

public class SelectionScreen implements Screen {

    private Game game;
    private ButtonCreator buttonCreator;
    private Stage stage;
    private String rutaCoche;

    public SelectionScreen(Game game) {
        this.game = game;
        buttonCreator = new ButtonCreator();
//        stage = buttonCreator.createSelectionButtons();
        handleInput();
    }

    private void handleInput() {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

    }

    public String getRutaCoche() {
        return rutaCoche;
    }
}