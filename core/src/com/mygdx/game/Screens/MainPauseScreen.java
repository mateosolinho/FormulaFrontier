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

public class MainPauseScreen implements Screen {
    private final ButtonCreator buttonCreator;
    private final Stage stage;
    private final Game game;
    PreferencesManager preferencesManager;
    AudioManager audioManager;

    public MainPauseScreen(Game game) {
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
        buttonCreator.getImageButtonVolver().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(MainScreen.getStage());
                game.setMainScreen();
                dispose();
            }
        });
        buttonCreator.getImageButtonExit().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(MainScreen.getStage());
                game.setMainScreen();
                dispose();
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
                    audioManager.stopMusicMenu();
                } else {
                    style.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/musicaON.png"))));
                    preferencesManager.guardarMusica(true);
                    audioManager.startMusicMenu();
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

    }


}
