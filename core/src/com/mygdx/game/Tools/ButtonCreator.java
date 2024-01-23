package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Screens.PlayScreen;

public class ButtonCreator {
    private Stage stage;
    private ImageButton imageButtonDerecha;
    private ImageButton imageButtonIzquierda;
    private ImageButton imageButtonArriba;
    private ImageButton imageButtonAbajo;
    public static Label lblVuelta;
    public static Label lblTiempo;
    public static Label t2;

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    public ButtonCreator(Stage stage) {
        this.stage = stage;
        createStage();
        createButtons();
        createLabels();
    }


    private void createStage() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    private void createLabels() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/Formula1-Regular-1.fnt")), Color.WHITE);
        font.font.getData().setScale(50/font.font.getCapHeight());

        Table table = new Table();
       table.center();
        table.setFillParent(true);

        lblTiempo = new Label("TIEMPO", font);
        lblVuelta = new Label("Vuelta: 0/5", font);
        t2 = new Label("Vuelta2: ", font);

        table.row();
        table.add(lblVuelta).expandX().padTop(200f).padLeft(130f).left();
        table.add(lblTiempo).expandX().padTop(400f).padRight(300f).right();
        table.add(t2).expandX().padTop(200f).padRight(300f).right();
        stage.addActor(table);
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

    public void updateVueltas(int vueltas) {
        lblVuelta.setText("Vuelta: " + vueltas +" / 5");
    }
}
