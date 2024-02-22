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
 * Clase encargada de respresentar la pantalla de records.
 */
public class CreditsScreen implements Screen {

    /**
     * Instancia principal del juego
     */
    private final Game game;

    /**
     * Instancia de buttoncreator
     */
    private final ButtonCreator buttonCreator;

    /**
     * Instancia del stage que contiene los elementos visuales
     */
    private final Stage stage;

    /**
     * Constructor de la clase CreditsScreen.
     *
     * @param game La instancia principal del juego.
     */
    public CreditsScreen(Game game) {
        this.game = game;
        buttonCreator = new ButtonCreator();
        stage = buttonCreator.createLabelsCredits();
        handleInput();
    }

    /**
     * Método que maneja la entrada del usuario.
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
     * Renderiza la pantalla de records.
     * @param delta El tiempo transcurrido desde el último fotograma en segundos.
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
