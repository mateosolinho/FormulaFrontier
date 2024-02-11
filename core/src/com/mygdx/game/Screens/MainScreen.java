package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Game;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.ButtonCreator;

public class MainScreen implements Screen {
    private final ButtonCreator buttonCreator;
    private final Stage stage;
    private final Game game;
    public static AudioManager audioManager;

    public MainScreen(Game game) {
        this.game = game;
        buttonCreator = new ButtonCreator();
        stage = buttonCreator.createMainButtons();
        audioManager = new AudioManager();
        handleInput();
        if (Game.musica) {
            audioManager.startMusicMenu();
        } else {
            audioManager.stopMusicMenu();
        }
    }

    public static AudioManager getAudioManager() {
        return audioManager;
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

    private void handleInput() {
        buttonCreator.getImageButtonStart().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CarSelectionScreen(game));
            }
        });

        buttonCreator.getImageButtonSettings().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainPauseScreen(game));
            }
        });

        buttonCreator.getImageButtonPuntuaciones().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CircuitRecordsScreen(game));
                Gdx.app.log("Puntos", "Pulsado Puntos");
            }
        });

        buttonCreator.getImageButtonTutorial().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SelectionScreen(game));
                Gdx.app.log("Tuto", "Pulsado Tuto");
            }
        });

        buttonCreator.getImageButtonExit().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Salir", "Pulsado Salir");
                Gdx.app.exit();
            }
        });
    }
}