package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game;

/**
 * Tutorial screen representation class.
 */
public class TutorialScreen implements Screen {
   
    /**
     * Instance o the main game to which the tutorial screen belongs.
     */
    private final Game game;

    /**
     * Textures array containing the tutorial images.
     */
    private final Texture[] textures;

    /**
     * Stage where the tutorial images are displayed.
     */
    private final Stage stage;

    /**
     * Index of the current texture being displayed in the tutorial.
     */
    private int currentTextureIndex;

    /**
     * Set if the screen accepts events.
     */
    private boolean canTouch;

    /**
     * Time elapsed since last screen interaction.
     */
    private float elapsedTime = 0f;

    /**
     * TutorialScreen constructor
     *
     * @param game Game instance.
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
     * Render the tutorial screen.
     *
     * @param delta Time elapsed since the last frame.
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
     * Adjust the size of the stage viewport when the application window is resized.
     *
     * @param width  New window width.
     * @param height New window height.
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
     * Free up tutorial screen resources.
     */
    @Override
    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}