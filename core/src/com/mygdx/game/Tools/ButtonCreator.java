package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ButtonCreator {
    private Stage stage;
    private ImageButton imageButtonDerecha;
    private ImageButton imageButtonIzquierda;
    private ImageButton imageButtonArriba;
    private ImageButton imageButtonAbajo;
    private ImageButton imageButtonPause;
    public static Label lblVuelta;
    public static Label lblTiempo;
    public static Label lblBestTime;
    public static Label lblLastTime;
    private ImageButton imageButtonStart;
    private ImageButton imageButtonSettings;
    private ImageButton imageButtonPuntuaciones;

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    public ButtonCreator() {
        createStage();
    }

    private void createStage() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    public void createGameLabels() {
        Label.LabelStyle f1Font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/Formula1-Regular-1.fnt")), Color.WHITE);
        f1Font.font.getData().setScale(50/f1Font.font.getCapHeight());

        lblTiempo = new Label("", f1Font);
        lblVuelta = new Label("Lap: 0 / 5", f1Font);
        lblBestTime = new Label("", f1Font);
        lblLastTime = new Label("", f1Font);

        lblTiempo.setHeight(screenHeight * 0.15f);
        lblTiempo.setWidth(screenWidth * 0.15f);
        lblTiempo.setPosition(screenWidth / 2.4f, 6 * screenHeight / 20f);

        lblVuelta.setHeight(screenHeight * 0.15f);
        lblVuelta.setWidth(screenWidth * 0.15f);
        lblVuelta.setPosition(1.5f * screenWidth / 20f, 6 * screenHeight / 20f);

        lblBestTime.setHeight(screenHeight * 0.15f);
        lblBestTime.setWidth(screenWidth * 0.15f);
        lblBestTime.setPosition(13 * screenWidth / 20f, 6 * screenHeight / 20f);

        lblLastTime.setHeight(screenHeight * 0.15f);
        lblLastTime.setWidth(screenWidth * 0.15f);
        lblLastTime.setPosition(13 * screenWidth / 20f, 4 * screenHeight / 20f);

        stage.addActor(lblTiempo);
        stage.addActor(lblVuelta);
        stage.addActor(lblBestTime);
        stage.addActor(lblLastTime);
    }

    public void createGameButtons() {

        Texture buttonTextureDerecha = new Texture(Gdx.files.internal("UI/gameUI/derechaBien.png"));
        Texture buttonTextureIzquierda = new Texture(Gdx.files.internal("UI/gameUI/izquierdaBien.png"));
        Texture buttonTextureArriba = new Texture(Gdx.files.internal("UI/gameUI/arribaBien.png"));
        Texture buttonTextureAbajo = new Texture(Gdx.files.internal("UI/gameUI/abajoBien.png"));

        ImageButton.ImageButtonStyle styleDerecha = new ImageButton.ImageButtonStyle();
        styleDerecha.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureDerecha));
        imageButtonDerecha = new ImageButton(styleDerecha);

        ImageButton.ImageButtonStyle styleIzquierda = new ImageButton.ImageButtonStyle();
        styleIzquierda.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureIzquierda));
        imageButtonIzquierda = new ImageButton(styleIzquierda);

        ImageButton.ImageButtonStyle styleArriba = new ImageButton.ImageButtonStyle();
        styleArriba.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureArriba));
        imageButtonArriba = new ImageButton(styleArriba);

        ImageButton.ImageButtonStyle styleAbajo = new ImageButton.ImageButtonStyle();
        styleAbajo.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureAbajo));
        imageButtonAbajo = new ImageButton(styleAbajo);

        stage.getViewport().getCamera().position.set(screenWidth / 2f, 0, 0);

        imageButtonDerecha.setHeight(screenHeight * 0.15f);
        imageButtonDerecha.setWidth(screenWidth * 0.15f);
        imageButtonDerecha.setPosition(17 * screenWidth / 20f, -screenHeight / 3f);

        imageButtonIzquierda.setHeight(screenHeight * 0.15f);
        imageButtonIzquierda.setWidth(screenWidth * 0.15f);
        imageButtonIzquierda.setPosition(14 * screenWidth / 20f, -screenHeight / 3f);

        imageButtonArriba.setHeight(screenHeight * 0.15f);
        imageButtonArriba.setWidth(screenWidth * 0.15f);
        imageButtonArriba.setPosition(1 * screenWidth / 20f, -screenHeight / 5.1f);

        imageButtonAbajo.setHeight(screenHeight * 0.15f);
        imageButtonAbajo.setWidth(screenWidth * 0.15f);
        imageButtonAbajo.setPosition(1 * screenWidth / 20f, -screenHeight / 2.2f);

        styleDerecha.imageUp.setMinWidth(200f);
        styleDerecha.imageUp.setMinHeight(200f);

        styleIzquierda.imageUp.setMinWidth(200f);
        styleIzquierda.imageUp.setMinHeight(200f);

        styleArriba.imageUp.setMinWidth(200f);
        styleArriba.imageUp.setMinHeight(200f);

        styleAbajo.imageUp.setMinWidth(200f);
        styleAbajo.imageUp.setMinHeight(200f);

        stage.addActor(imageButtonDerecha);
        stage.addActor(imageButtonIzquierda);
        stage.addActor(imageButtonArriba);
        stage.addActor(imageButtonAbajo);

    }

    public Stage createPauseButton() {

        Texture buttonTexturePause = new Texture(Gdx.files.internal("UI/gameUI/pause.png"));

        ImageButton.ImageButtonStyle stylePause = new ImageButton.ImageButtonStyle();
        stylePause.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTexturePause));
        imageButtonPause = new ImageButton(stylePause);

        stage.getViewport().getCamera().position.set(screenWidth / 2f, 0, 0);

        imageButtonPause.setHeight(screenHeight * 0.1f);
        imageButtonPause.setWidth(screenWidth * 0.1f);
        imageButtonPause.setPosition(18.2f * screenWidth / 20f, 7.5f * screenHeight / 20f);

        stylePause.imageUp.setMinWidth(200f);
        stylePause.imageUp.setMinHeight(200f);

        stage.addActor(imageButtonPause);

        return stage;
    }

    public Stage createMainButtons() {

        Texture buttonTexturePause = new Texture(Gdx.files.internal("UI/mainUI/start.png"));
        Texture buttonTextureSettings = new Texture(Gdx.files.internal("UI/mainUI/settings.png"));
        Texture buttonTexturePuntuaciones = new Texture(Gdx.files.internal("UI/mainUI/puntuaciones.png"));


        ImageButton.ImageButtonStyle styleStart = new ImageButton.ImageButtonStyle();
        styleStart.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTexturePause));
        imageButtonStart = new ImageButton(styleStart);

        ImageButton.ImageButtonStyle styleSettings = new ImageButton.ImageButtonStyle();
        styleSettings.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureSettings));
        imageButtonSettings = new ImageButton(styleSettings);

        ImageButton.ImageButtonStyle stylePuntuaciones = new ImageButton.ImageButtonStyle();
        stylePuntuaciones.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTexturePuntuaciones));
        imageButtonPuntuaciones = new ImageButton(stylePuntuaciones);

        stage.getViewport().getCamera().position.set(screenWidth / 2, 0, 0);

        imageButtonStart.setHeight(screenHeight * 0.1f);
        imageButtonStart.setWidth(screenWidth * 0.1f);
        imageButtonStart.setPosition( screenWidth / 2.2f, 2f * screenHeight / 20f);

        imageButtonSettings.setHeight(screenHeight * 0.1f);
        imageButtonSettings.setWidth(screenWidth * 0.1f);
        imageButtonSettings.setPosition( screenWidth / 4.4f,  2 * -screenHeight / 20f);

        imageButtonPuntuaciones.setHeight(screenHeight * 0.1f);
        imageButtonPuntuaciones.setWidth(screenWidth * 0.1f);
        imageButtonPuntuaciones.setPosition( screenWidth / 4.4f,  6 * -screenHeight / 20f);

        styleStart.imageUp.setMinWidth(500f);
        styleStart.imageUp.setMinHeight(300f);

        styleSettings.imageUp.setMinWidth(500f);
        styleSettings.imageUp.setMinHeight(300f);

        stylePuntuaciones.imageUp.setMinWidth(500f);
        stylePuntuaciones.imageUp.setMinHeight(300f);

        stage.addActor(imageButtonStart);
        stage.addActor(imageButtonSettings);
        stage.addActor(imageButtonPuntuaciones);


        return stage;
    }

    public ImageButton getImageButtonDerecha() {
        return imageButtonDerecha;
    }

    public ImageButton getImageButtonIzquierda() {
        return imageButtonIzquierda;
    }

    public ImageButton getImageButtonArriba() {
        return imageButtonArriba;
    }

    public ImageButton getImageButtonAbajo() {
        return imageButtonAbajo;
    }

    public ImageButton getImageButtonPause(){return  imageButtonPause;}

    public void updateVueltas(int vueltas) {
        lblVuelta.setText("Lap: " + vueltas +" / 5");
    }
}
