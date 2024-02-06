package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Game;
import com.mygdx.game.Tools.ButtonCreator;

public class CarSelectionScreen implements Screen {

    private Game game;
    private ButtonCreator buttonCreator;
    private Stage stage;
    public static String rutaCoche;

    public CarSelectionScreen(Game game) {
        this.game = game;
        buttonCreator = new ButtonCreator();
        stage = buttonCreator.createCarButtons();
        handleInput();
    }

    private void handleInput() {
        for (final ImageButton b : buttonCreator.getImageButtonCars()) {
            b.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    CarSelectionScreen.rutaCoche ="Cars/" +  b.getName().trim() + ".png";
                    Gdx.app.log("Coche,", b.getName() + ".png");
                    if (rutaCoche != null){
                        game.setScreen(new PlayScreen(game));
                    }
                }
            });
        }
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

//    public String getRutaCoche() {
//        Gdx.app.log("sel", rutaCoche + " ");
//        return rutaCoche;
//    }
}
