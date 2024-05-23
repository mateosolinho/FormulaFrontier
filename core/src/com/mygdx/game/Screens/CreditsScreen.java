package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Game;
import com.mygdx.game.Tools.ButtonCreator;

/**
 * Records screen representation class.
 */
public class CreditsScreen implements Screen {

    /**
     * Game instance.
     */
    private final Game game;

    /**
     * ButtonCreator instance.
     */
    private final ButtonCreator buttonCreator;

    /**
     * 
     * Stage containing the visual elements of the screen.
     */
    private final Stage stage;

    /**
     * CreditsScreen class constructor.
     *
     * @param game Game instance.
     */
    public CreditsScreen(Game game) {
        this.game = game;
        buttonCreator = new ButtonCreator();
        stage = buttonCreator.createLabelsCredits();
        handleInput();
    }

    /**
     * Function that handles user input.
     */
    private void handleInput() {
        buttonCreator.getImageButtonFlecha().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(MainScreen.getStage());
                game.setMainScreen();
                dispose();
            }
        });
    }

    @Override
    public void show() {

    }

    /**
     * Renders the record screen.
     *
     * @param delta Time since the last frame in seconds.
     */
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
}
