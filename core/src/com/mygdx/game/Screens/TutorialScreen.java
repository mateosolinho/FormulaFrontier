package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game;

/**
 * Pantalla que muestra un tutorial con varias imágenes.
 */
public class TutorialScreen implements Screen {

    /**
     * Array de texturas que contiene las imágenes del tutorial.
     */
    private final Texture[] textures;

    /**
     * Stage donde se muestran las imágenes del tutorial.
     */
    private final Stage stage;

    /**
     * Indice de la textura actual que se está mostrando en el tutorial.
     */
    private int currentTextureIndex;

    /**
     * Instancia del juego principal al que pertenece la pantalla del tutorial.
     */
    private final Game game;

    /**
     * Indica si la pantalla acepta eventos en un momento dado.
     */
    private boolean canTouch;

    /**
     * Tiempo transcurrido desde la última interacción en la pantalla.
     */
    private float elapsedTime = 0f;

    /**
     * Constructor de la clase TutorialScreen.
     * @param game Instancia del juego principal.
     */
    public TutorialScreen(Game game) {
        this.game = game;
        textures = new Texture[]{
                new Texture("Tutorial/tutorial4.jpg"),
                new Texture("Tutorial/tutorial5.jpg"),
                new Texture("Tutorial/tutorial1.jpg"),
                new Texture("Tutorial/tutorial2.jpg"),
                new Texture("Tutorial/tutorial3.jpg"),
        };

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        currentTextureIndex = 0;
        canTouch = true;

        Image image = new Image(textures[currentTextureIndex]);
        image.setFillParent(true);
        stage.addActor(image);
    }

    @Override
    public void show() {

    }

    /**
     * Renderiza la pantalla del tutorial.
     *
     * @param delta El tiempo transcurrido desde el último fotograma.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isTouched() && canTouch) {
            currentTextureIndex++;

            if (currentTextureIndex >= textures.length) {
                Gdx.input.setInputProcessor(MainScreen.getStage());
                game.setMainScreen();
                dispose();
            } else {
                Image image = new Image(textures[currentTextureIndex]);
                image.setFillParent(true);
                stage.clear();
                stage.addActor(image);
            }
            canTouch = false;
            elapsedTime = 0;
        }

        elapsedTime += delta;

        float cooldownTime = 0.3f;
        if (elapsedTime >= cooldownTime) {
            canTouch = true;
        }
    }

    /**
     * Ajusta el tamaño del viewport del stage cuando la ventana de la aplicación se redimensiona.
     *
     * @param width El nuevo ancho de la ventana.
     * @param height La nueva altura de la ventana.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

    /**
     * Libera los recursos de la pantalla del tutorial.
     */
    @Override
    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}