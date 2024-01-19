package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ButtonCreator {
    private Stage stage;
    private ImageButton imageButtonDerecha;
    private ImageButton imageButtonIzquierda;
    private ImageButton imageButtonArriba;
    private ImageButton imageButtonAbajo;
    private Label lblVuelta;

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    public ButtonCreator(Stage stage) {
        this.stage = stage;
        createStage();
        createButtons();
//        createLabels();
    }


    private void createStage() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    private void createLabels() {
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Formula1-Regular-1.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = 20; // Tamaño de la fuente
//        Skin skin = new Skin();
//        BitmapFont fontlbl = new BitmapFont(Gdx.files.internal("Fonts/formula1Regular.ttf"));
//        skin.add("default", fontlbl);
//
//        Label.LabelStyle labelStyle = new Label.LabelStyle();
//        labelStyle.font = skin.getFont("default");
//
//        lblVuelta = new Label("HOLAAAAAAAAAAAAAAA", labelStyle);
//        lblVuelta.setPosition(14 * screenWidth / 20f, -screenHeight / 3f);

    }

    public Stage createButtons() {


        Texture buttonTextureDerecha = new Texture(Gdx.files.internal("UI/derechaBien.png"));
        Texture buttonTextureIzquierda = new Texture(Gdx.files.internal("UI/izquierdaBien.png"));
        Texture buttonTextureArriba = new Texture(Gdx.files.internal("UI/arribaBien.png"));
        Texture buttonTextureAbajo = new Texture(Gdx.files.internal("UI/abajoBien.png"));

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
}
