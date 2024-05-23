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
 * Pause screen representation class.
 */
public class PauseScreen implements Screen {

    /**
     * Game instance.
     */
    private final Game game;

    /**
     * ButtonCreator instance.
     */
    private final ButtonCreator buttonCreator;

    /**
     * Stage instance.
     */
    private final Stage stage;

    /**
     * PreferencesManager instance.
     */
    private final PreferencesManager preferencesManager;

    /**
     * AudioManager instance.
     */
    private final AudioManager audioManager;

    /**
     * PauseScreen constructor.
     *
     * @param game Game instance.
     */
    public PauseScreen(Game game) {
        this.game = game;
        preferencesManager = new PreferencesManager();
        buttonCreator = new ButtonCreator();
        stage = buttonCreator.createPauseScreenButtons();
        audioManager = MainScreen.getAudioManager();
        handleInput();
    }

    @Override
    public void show() {

    }

    /**
     * Render main screen.
     *
     * @param delta Time in seconds since last render.
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

    /**
     * Set input handling for buttons.
     */
    private void handleInput() {
        buttonCreator.getImageButtonVolver().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(CircuitSelectionScreen.playScreen.getStage());
                game.setScreen(CircuitSelectionScreen.playScreen);
            }
        });

        buttonCreator.getImageButtonExit().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                audioManager.stopMusicCarrera();
                audioManager.startMusicMenu();
                Gdx.input.setInputProcessor(MainScreen.getStage());
                game.setMainScreen();
            }
        });

        buttonCreator.getImageButtonMusicON().addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                ImageButton button = buttonCreator.getImageButtonMusicON();
                ImageButton.ImageButtonStyle style = button.getStyle();

                if (PreferencesManager.getMusica()) {
                    style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/musicaOFF.png"))));
                    preferencesManager.guardarMusica(false);
                    audioManager.stopMusicCarrera();
                    MainScreen.musicStarted = false;
                } else {
                    style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/musicaON.png"))));
                    preferencesManager.guardarMusica(true);

                    audioManager.startMusicCarrera();
                    MainScreen.musicStarted = true;
                }
                style.imageUp.setMinWidth(2400);
                style.imageUp.setMinHeight(1700);
                button.setStyle(style);
            }
        });

        buttonCreator.getImageButtonVibracion().addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                ImageButton button = buttonCreator.getImageButtonVibracion();
                ImageButton.ImageButtonStyle style = button.getStyle();

                if (PreferencesManager.getVibracion()) {
                    style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/vibracionOFF.png"))));
                    preferencesManager.guardarVibracion(false);
                } else {
                    style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/vibracionON.png"))));
                    preferencesManager.guardarVibracion(true);
                }
                style.imageUp.setMinWidth(2400);
                style.imageUp.setMinHeight(1700);
                button.setStyle(style);
            }
        });

        buttonCreator.getImageButtonIdioma().addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                ImageButton button = buttonCreator.getImageButtonIdioma();
                ImageButton.ImageButtonStyle style = button.getStyle();

                if (PreferencesManager.getIngles()) {
                    style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/uk.png"))));
                    PreferencesManager.setIngles(false);
                    Game.actualizaridioma();
                    actualizarImegenes();
                } else {
                    style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/espana.png"))));
                    PreferencesManager.setIngles(true);
                    Game.actualizaridioma();
                    actualizarImegenes();
                }
                style.imageUp.setMinWidth(2400);
                style.imageUp.setMinHeight(1700);
                button.setStyle(style);
            }
        });
    }

    /**
     * Update images based on current language.
     */
    public void actualizarImegenes() {
        Texture buttonTextureExitPause = new Texture(Gdx.files.internal(Game.bundle.get("botonSalir")));
        Texture buttonTextureVolver = new Texture(Gdx.files.internal(Game.bundle.get("botonVolver")));

        createStyle(buttonTextureExitPause, buttonCreator.getImageButtonExit());
        createStyle(buttonTextureVolver, buttonCreator.getImageButtonVolver());
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
