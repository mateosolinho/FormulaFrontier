package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Game;
import com.mygdx.game.Gamemodes.TimeAttack;
import com.mygdx.game.Tools.ButtonCreator;
import com.mygdx.game.Tools.SensorContactListener;

public class GameModeScreen implements Screen {

    private Game game;
    private Stage stage;
    private ButtonCreator buttonCreator;
    public GameModeScreen(Game game){
        this.game = game;
        buttonCreator = new ButtonCreator();
        stage = buttonCreator.createGamemodeButtons();
        handleInput();
    }

    private void handleInput() {
        buttonCreator.getImageButtonTimeAttack().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TimeAttack.resetTimes();
                game.setScreen(CircuitSelectionScreen.playScreen = new PlayScreen(game));
                SensorContactListener.vVueltas = 0;
            }
        });

        buttonCreator.getImageButtonRace().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Race", "Pulsado Race");
            }
        });
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

}
