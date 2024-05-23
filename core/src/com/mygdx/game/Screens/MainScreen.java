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

/**
 * Main screen representation class.
 */
public class MainScreen implements Screen {
    
    /**
     * Game instance
     */
    private final Game game;

    /**
     * ButtonCreator instance.
     */
    private final ButtonCreator buttonCreator;

    /**
     * Stage instance.
     */
    private static Stage stage;


    /**
     * AudioManager instance.
     */
    private static AudioManager audioManager;

    /**
     * Check if it is in the MainScreen screen.
     */
    public static boolean isMainScreen;

    /**
     * Check if the music is playing.
     */
    public static boolean musicStarted = false;

    /**
     * MainScreen constructor.
     *
     * @param game Game instance.
     */
    public MainScreen(Game game) {
        this.game = game;
        buttonCreator = new ButtonCreator();
        stage = buttonCreator.createMainButtons();
        audioManager = new AudioManager();
        handleInput();
    }

    /**
     * Gets the stage of the main screen.
     *
     * @return Main screen stage.
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Gets the audio manager.
     *
     * @return Audio manager.
     */
    public static AudioManager getAudioManager() {
        return audioManager;
    }

    /**
     * Runs when this screen is displayed.
     */
    @Override
    public void show() {
        if (PreferencesManager.getMusica() && !MainScreen.musicStarted) {
            audioManager.startMusicMenu();
            MainScreen.musicStarted = true;
        }
    }

    /**
     * Renders the main screen.
     *
     * @param delta Time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        if (isMainScreen) {
            actualizarImagenesMain();
            MainScreen.isMainScreen = false;
        }
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

    /**
     * Set input handling for buttons.
     */
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

        buttonCreator.getImageButtonRecords().addListener(new ClickListener() {
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

    /**
     * Update images based on current language.
     */
    public void actualizarImagenesMain() {
        Texture buttonTextureStart = new Texture(Gdx.files.internal(Game.bundle.get("botonStart")));
        Texture buttonTextureSettings = new Texture(Gdx.files.internal(Game.bundle.get("botonAjustes")));
        Texture buttonTextureCreditos = new Texture(Gdx.files.internal(Game.bundle.get("botonCreditos")));
        Texture buttoTextureTurtorial = new Texture(Gdx.files.internal(Game.bundle.get("botonGuia")));
        Texture buttonTextureSalir = new Texture(Gdx.files.internal(Game.bundle.get("botonSalir")));

        createStyle(buttonTextureStart, buttonCreator.getImageButtonStart());
        createStyle(buttonTextureSettings, buttonCreator.getImageButtonSettings());
        createStyle(buttonTextureCreditos, buttonCreator.getImageButtonCreditos());
        createStyle(buttoTextureTurtorial, buttonCreator.getImageButtonTutorial());
        createStyle(buttonTextureSalir, buttonCreator.getImageButtonExit());
    }

    /**
     * Style a button with the specified texture.
     *
     * @param texture     Button texture.
     * @param imageButton Button to which the style will be applied.
     */
    public void createStyle(Texture texture, ImageButton imageButton) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));
        style.imageUp.setMinWidth(500);
        style.imageUp.setMinHeight(300);
        imageButton.setStyle(style);
    }
}