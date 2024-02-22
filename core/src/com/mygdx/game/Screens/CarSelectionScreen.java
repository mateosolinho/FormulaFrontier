package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Game;
import com.mygdx.game.Tools.ButtonCreator;

/**
 * Clase encargada de representar la pantalla de selección de vehículos.
 */
public class CarSelectionScreen implements Screen {

    /**
     * Instancia principal del juego.
     */
    private final Game game;

    /**
     * Instancia de buttoncreator
     */
    private final ButtonCreator buttonCreator;

    /**
     * Instancia del stage que contiene los elementos visuales de la pantalla.
     */
    private final Stage stage;

    /**
     * Ruta del archivo del vehículo seleccionado.
     */
    public static String rutaCoche;

    /**
     * Constructor de la clase CarSelectionScreen.
     * @param game Instancia principal del juego.
     */
    public CarSelectionScreen(Game game) {
        this.game = game;
        buttonCreator = new ButtonCreator();
        stage = buttonCreator.createCarButtons();
        handleInput();
    }

    /**
     * Maneja la entrada del usuario, detectando clics en los botones de vehículos.
     */
    private void handleInput() {
        for (final ImageButton b : buttonCreator.getImageButtonCars()) {
            b.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    CarSelectionScreen.rutaCoche = "Cars/" + b.getName().trim() + ".png";
                    game.setScreen(new CircuitSelectionScreen(game));
                }
            });
        }
    }

    @Override
    public void show() {

    }

    /**
     * Renderiza la pantalla de selección de coches.
     * @param delta The time in seconds since the last render.
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
