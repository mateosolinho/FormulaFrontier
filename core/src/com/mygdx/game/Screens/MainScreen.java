package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Game;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.ButtonCreator;
import com.mygdx.game.Tools.PreferencesManager;

public class MainScreen implements Screen {
    private final ButtonCreator buttonCreator;
    private static Stage stage;
    private final Game game;
    private static AudioManager audioManager;

    public MainScreen(Game game) {
        this.game = game;
        buttonCreator = new ButtonCreator();
        stage = buttonCreator.createMainButtons();
        audioManager = new AudioManager();
        handleInput();
    }

    public static Stage getStage() {
        return stage;
    }


    public static AudioManager getAudioManager() {
        return audioManager;
    }

    @Override
    public void show() {
        if (PreferencesManager.getMusica() && !MainPauseScreen.musicStarted) {
            audioManager.startMusicMenu();
        }
    }

    @Override
    public void render(float delta) {
        actualizarImegenes();
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
                dispose();
            }
        });

        buttonCreator.getImageButtonSettings().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainPauseScreen(game));
                dispose();
            }
        });

        buttonCreator.getImageButtonPuntuaciones().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CircuitRecordsScreen(game));
                dispose();
            }
        });

        buttonCreator.getImageButtonTutorial().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TutorialScreen(game));
                dispose();
            }
        });

        buttonCreator.getImageButtonExit().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        buttonCreator.getImageButtonCreditos().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CreditsScreen(game));
                dispose();
            }
        });
    }

    public void actualizarImegenes() {
        Texture buttonTextureStart = new Texture(Gdx.files.internal(Game.bundle.get("botonStart")));
        Texture buttonTextureSettings = new Texture(Gdx.files.internal(Game.bundle.get("botonAjustes")));
        Texture buttonTextureCreditos = new Texture(Gdx.files.internal(Game.bundle.get("botonCreditos")));
        Texture buttoTextureTurtorial = new Texture(Gdx.files.internal(Game.bundle.get("botonGuia")));

        createStyle(buttonTextureStart, buttonCreator.getImageButtonStart());
        createStyle(buttonTextureSettings, buttonCreator.getImageButtonSettings());
        createStyle(buttonTextureCreditos, buttonCreator.getImageButtonCreditos());
        createStyle(buttoTextureTurtorial, buttonCreator.getImageButtonTutorial());
    }

    public void createStyle(Texture texture, ImageButton imageButton) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));
        style.imageUp.setMinWidth(500);
        style.imageUp.setMinHeight(300);
        imageButton.setStyle(style);
    }
}
