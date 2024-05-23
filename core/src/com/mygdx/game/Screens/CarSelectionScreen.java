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
 * Class to represent the vehicle selection screen.
 */
public class CarSelectionScreen implements Screen {

    /**
     * Game instance.
     */
    private final Game game;

    /**
     * ButtonCreator instance.
     */
    private final ButtonCreator buttonCreator;

    /**
     * Instance of the Stage that contains the visual elements of the screen.
     */
    private final Stage stage;

    /**
     * File path of the selected vehicle.
     */
    public static String rutaCoche;

    /**
     * CarSelectionScreen constructor.
     *
     * @param game Game instance.
     */
    public CarSelectionScreen(Game game) {
        this.game = game;
        buttonCreator = new ButtonCreator();
        stage = buttonCreator.createCarButtons();
        handleInput();
    }

    /**
     * Handles user input, detecting clicks on vehicle buttons.
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
     * Render the car selection screen.
     *
     * @param delta Time in seconds since the last render.
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