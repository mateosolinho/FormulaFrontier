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
 * Clase encargada de representar la pantalla principal.
 */
public class MainScreen implements Screen {

    /**
     * Instancia de buttoncreator
     */
    private final ButtonCreator buttonCreator;

    /**
     * Instancia de stage
     */
    private static Stage stage;

    /**
     * Instancia de game
     */
    private final Game game;

    /**
     * Instancia de audiomanager
     */
    private static AudioManager audioManager;

    /**
     * Comprueba si esta en la pantalla MainScreen
     */
    public static boolean isMainScreen;

    /**
     * Comprueba si esta en la musica esta sonando
     */
    public static boolean musicStarted = false;

    /**
     * Constructor de la clase MainScreen.
     *
     * @param game La instancia del juego.
     */
    public MainScreen(Game game) {
        this.game = game;
        buttonCreator = new ButtonCreator();
        stage = buttonCreator.createMainButtons();
        audioManager = new AudioManager();
        handleInput();
    }

    /**
     * Obtiene el stage de la pantalla principal.
     *
     * @return El stage de la pantalla principal.
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Obtiene el administrador de audio.
     *
     * @return El administrador de audio.
     */
    public static AudioManager getAudioManager() {
        return audioManager;
    }

    /**
     * Se ejecuta cuando esta pantalla se muestra.
     */
    @Override
    public void show() {
        if (PreferencesManager.getMusica() && !MainScreen.musicStarted) {
            audioManager.startMusicMenu();
            MainScreen.musicStarted = true;
        }
    }

    /**
     * Renderiza la pantalla principal.
     *
     * @param delta El tiempo en segundos desde el último renderizado.
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
     * Configura el manejo de entrada para los botones.
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
     * Actualiza las imágenes según el idioma actual.
     */
    public void actualizarImagenesMain() {
        Texture buttonTextureStart = new Texture(Gdx.files.internal(Game.bundle.get("botonStart")));
        Texture buttonTextureSettings = new Texture(Gdx.files.internal(Game.bundle.get("botonAjustes")));
        Texture buttonTextureCreditos = new Texture(Gdx.files.internal(Game.bundle.get("botonCreditos")));
        Texture buttoTextureTurtorial = new Texture(Gdx.files.internal(Game.bundle.get("botonGuia")));

        createStyle(buttonTextureStart, buttonCreator.getImageButtonStart());
        createStyle(buttonTextureSettings, buttonCreator.getImageButtonSettings());
        createStyle(buttonTextureCreditos, buttonCreator.getImageButtonCreditos());
        createStyle(buttoTextureTurtorial, buttonCreator.getImageButtonTutorial());
    }

    /**
     * Crea el estilo de un botón con la textura especificada.
     *
     * @param texture     La textura para el botón.
     * @param imageButton El botón al que se le aplicará el estilo.
     */
    public void createStyle(Texture texture, ImageButton imageButton) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));
        style.imageUp.setMinWidth(500);
        style.imageUp.setMinHeight(300);
        imageButton.setStyle(style);
    }
}
