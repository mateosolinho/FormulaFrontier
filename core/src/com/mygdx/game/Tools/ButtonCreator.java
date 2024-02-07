package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game;

import java.util.ArrayList;

public class ButtonCreator {
    private Stage stage;
    public static Label lblVuelta;
    public static Label lblTiempo;
    public static Label lblBestTime;
    public static Label lblLastTime;
    private Label lblTitulo;
    private ImageButton imageButtonDerecha;
    private ImageButton imageButtonIzquierda;
    private ImageButton imageButtonArriba;
    private ImageButton imageButtonAbajo;
    private ImageButton imageButtonPause;
    private ImageButton imageButtonStart;
    private ImageButton imageButtonSettings;
    private ImageButton imageButtonPuntuaciones;
    private ImageButton imageButtonTutorial;
    private ImageButton imageButtonExit;
    private ImageButton imageButtonVolver;
    private ImageButton imageButtonMusicON;
    private ImageButton imageButtonVibracion;
    private ImageButton imageButtonRace;
    private ImageButton imageButtonTimeAttack;
    private ImageButton imageButtonSelectionCar;
    private ImageButton imageButtonSelectionCircuit;
    private ImageButton imageButtonTrack1;
    private ImageButton imageButtonTrack2;
    private final ArrayList<ImageButton> listaCoches = new ArrayList<>();

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
        f1Font.font.getData().setScale(50 / f1Font.font.getCapHeight());

        lblTiempo = new Label("", f1Font);
        lblVuelta = new Label("Lap: ", f1Font);
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

    public Stage createGameButtons() {

        Texture buttonTextureDerecha = new Texture(Gdx.files.internal("UI/gameUI/derechaBien.png"));
        Texture buttonTextureIzquierda = new Texture(Gdx.files.internal("UI/gameUI/izquierdaBien.png"));
        Texture buttonTextureArriba = new Texture(Gdx.files.internal("UI/gameUI/arribaBien.png"));
        Texture buttonTexturePause = new Texture(Gdx.files.internal("UI/gameUI/pause.png"));
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

        ImageButton.ImageButtonStyle stylePause = new ImageButton.ImageButtonStyle();
        stylePause.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTexturePause));
        imageButtonPause = new ImageButton(stylePause);

        stage.getViewport().getCamera().position.set(screenWidth / 2f, 0, 0);

        imageButtonPause.setHeight(screenHeight * 0.1f);
        imageButtonPause.setWidth(screenWidth * 0.1f);
        imageButtonPause.setPosition(18.2f * screenWidth / 20f, 7.5f * screenHeight / 20f);

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

        stylePause.imageUp.setMinWidth(200f);
        stylePause.imageUp.setMinHeight(200f);

        stage.addActor(imageButtonDerecha);
        stage.addActor(imageButtonIzquierda);
        stage.addActor(imageButtonArriba);
        stage.addActor(imageButtonAbajo);
        stage.addActor(imageButtonPause);


        return stage;
    }

    public Stage createPauseScreenButtons() {
        Texture buttonTextureExitPause = new Texture(Gdx.files.internal("UI/mainUI/exit.png"));
        Texture buttonTexturePuntosPause = new Texture(Gdx.files.internal("UI/mainUI/puntuaciones.png"));
        Texture buttonTextureVolver = new Texture(Gdx.files.internal("UI/pauseUI/volver.png"));
        Texture buttonTextureMusicON = new Texture(Gdx.files.internal("UI/pauseUI/musicaON.png"));
        Texture buttonTextureVibracion = new Texture(Gdx.files.internal("UI/pauseUI/vibracionON.png"));

        ImageButton.ImageButtonStyle stylePauseExit = new ImageButton.ImageButtonStyle();
        stylePauseExit.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureExitPause));
        imageButtonExit = new ImageButton(stylePauseExit);

        ImageButton.ImageButtonStyle stylePauseVolver = new ImageButton.ImageButtonStyle();
        stylePauseVolver.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureVolver));
        imageButtonVolver = new ImageButton(stylePauseVolver);

        ImageButton.ImageButtonStyle stylePuntuaciones = new ImageButton.ImageButtonStyle();
        stylePuntuaciones.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTexturePuntosPause));
        imageButtonPuntuaciones = new ImageButton(stylePuntuaciones);

        ImageButton.ImageButtonStyle stylePauseMusicON = new ImageButton.ImageButtonStyle();
        stylePauseMusicON.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureMusicON));
        imageButtonMusicON = new ImageButton(stylePauseMusicON);

        ImageButton.ImageButtonStyle styleVibracion = new ImageButton.ImageButtonStyle();
        styleVibracion.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureVibracion));
        imageButtonVibracion = new ImageButton(styleVibracion);

        stage.getViewport().getCamera().position.set(screenWidth / 2f, 0, 0);

        imageButtonExit.setHeight(screenHeight * 0.1f);
        imageButtonExit.setWidth(screenWidth * 0.1f);
        imageButtonExit.setPosition((screenWidth - imageButtonExit.getWidth()) / 2, 6 * -screenHeight / 20f);

        imageButtonPuntuaciones.setHeight(screenHeight * 0.1f);
        imageButtonPuntuaciones.setWidth(screenWidth * 0.1f);
        imageButtonPuntuaciones.setPosition((screenWidth - imageButtonExit.getWidth()) / 2, -screenHeight / 20f);

        imageButtonVolver.setHeight(screenHeight * 0.1f);
        imageButtonVolver.setWidth(screenWidth * 0.1f);
        imageButtonVolver.setPosition((screenWidth - imageButtonVolver.getWidth()) / 2, 4 * screenHeight / 20f);

        imageButtonMusicON.setHeight(screenHeight * 0.1f);
        imageButtonMusicON.setWidth(screenWidth * 0.1f);
        imageButtonMusicON.setPosition((2.80f * screenWidth - imageButtonExit.getWidth()) / 4f, -screenHeight / 20f);

        imageButtonVibracion.setHeight(screenHeight * 0.1f);
        imageButtonVibracion.setWidth(screenWidth * 0.1f);
        imageButtonVibracion.setPosition((screenWidth - imageButtonVibracion.getWidth()) / 4f, -screenHeight / 20f);


        if (Game.vibracion) {
            styleVibracion.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/vibracionON.png"))));
        } else {
            styleVibracion.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/vibracionOFF.png"))));
        }
        stylePauseExit.imageUp.setMinWidth(500);
        stylePauseExit.imageUp.setMinHeight(300);

        stylePauseVolver.imageUp.setMinWidth(500);
        stylePauseVolver.imageUp.setMinHeight(300);

        stylePuntuaciones.imageUp.setMinWidth(500);
        stylePuntuaciones.imageUp.setMinHeight(300);

        stylePauseMusicON.imageUp.setMinWidth(2400);
        stylePauseMusicON.imageUp.setMinHeight(1700);

        styleVibracion.imageUp.setMinWidth(2400);
        styleVibracion.imageUp.setMinHeight(1700);

        stage.addActor(imageButtonExit);
        stage.addActor(imageButtonVolver);
        stage.addActor(imageButtonPuntuaciones);
        stage.addActor(imageButtonMusicON);
        stage.addActor(imageButtonVibracion);

        return stage;
    }

    public Stage createMainButtons() {
        Label.LabelStyle f1FontTitle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/Formula1-Wide.fnt")), Color.WHITE);
        f1FontTitle.font.getData().setScale(50 / f1FontTitle.font.getCapHeight());

        lblTitulo = new Label("FORMULA \nFRONTIER", f1FontTitle);

        float labelWidth = lblTitulo.getWidth();

        lblTitulo.setHeight(screenHeight * 0.15f);
        lblTitulo.setWidth(screenWidth * 0.15f);
        lblTitulo.setPosition((1.02f * screenWidth - labelWidth) / 2, 4f * screenHeight / 20f);

        Texture buttonTexturePause = new Texture(Gdx.files.internal("UI/mainUI/start.png"));
        Texture buttonTextureSettings = new Texture(Gdx.files.internal("UI/mainUI/settings.png"));
        Texture buttonTexturePuntuaciones = new Texture(Gdx.files.internal("UI/mainUI/puntuaciones.png"));
        Texture buttonTextureTutorial = new Texture(Gdx.files.internal("UI/mainUI/tutorial.png"));
        Texture buttonTextureExit = new Texture(Gdx.files.internal("UI/mainUI/exit.png"));

        ImageButton.ImageButtonStyle styleStart = new ImageButton.ImageButtonStyle();
        styleStart.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTexturePause));
        imageButtonStart = new ImageButton(styleStart);

        ImageButton.ImageButtonStyle styleSettings = new ImageButton.ImageButtonStyle();
        styleSettings.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureSettings));
        imageButtonSettings = new ImageButton(styleSettings);

        ImageButton.ImageButtonStyle stylePuntuaciones = new ImageButton.ImageButtonStyle();
        stylePuntuaciones.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTexturePuntuaciones));
        imageButtonPuntuaciones = new ImageButton(stylePuntuaciones);

        ImageButton.ImageButtonStyle styleTutorial = new ImageButton.ImageButtonStyle();
        styleTutorial.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureTutorial));
        imageButtonTutorial = new ImageButton(styleTutorial);

        ImageButton.ImageButtonStyle styleExit = new ImageButton.ImageButtonStyle();
        styleExit.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureExit));
        imageButtonExit = new ImageButton(styleExit);

        stage.getViewport().getCamera().position.set(screenWidth / 2, 0, 0);

        imageButtonStart.setHeight(screenHeight * 0.1f);
        imageButtonStart.setWidth(screenWidth * 0.1f);
        imageButtonStart.setPosition((screenWidth - imageButtonStart.getWidth()) / 2, (-screenHeight) / 20);

        imageButtonSettings.setHeight(screenHeight * 0.1f);
        imageButtonSettings.setWidth(screenWidth * 0.1f);
        imageButtonSettings.setPosition(screenWidth / 4f, 4 * -screenHeight / 20f);

        imageButtonPuntuaciones.setHeight(screenHeight * 0.1f);
        imageButtonPuntuaciones.setWidth(screenWidth * 0.1f);
        imageButtonPuntuaciones.setPosition(screenWidth / 4f, 8 * -screenHeight / 20f);

        imageButtonTutorial.setHeight(screenHeight * 0.1f);
        imageButtonTutorial.setWidth(screenWidth * 0.1f);
        imageButtonTutorial.setPosition((2.75f * screenWidth - imageButtonPuntuaciones.getWidth()) / 4f, 4 * -screenHeight / 20f);

        imageButtonExit.setHeight(screenHeight * 0.1f);
        imageButtonExit.setWidth(screenWidth * 0.1f);
        imageButtonExit.setPosition((2.75f * screenWidth - imageButtonExit.getWidth()) / 4f, 8 * -screenHeight / 20f);

        styleStart.imageUp.setMinWidth(500);
        styleStart.imageUp.setMinHeight(300);

        styleSettings.imageUp.setMinWidth(500);
        styleSettings.imageUp.setMinHeight(300);

        stylePuntuaciones.imageUp.setMinWidth(500);
        stylePuntuaciones.imageUp.setMinHeight(300);

        styleTutorial.imageUp.setMinWidth(500);
        styleTutorial.imageUp.setMinHeight(300);

        styleExit.imageUp.setMinWidth(500);
        styleExit.imageUp.setMinHeight(300);

        stage.addActor(imageButtonStart);
        stage.addActor(imageButtonSettings);
        stage.addActor(imageButtonPuntuaciones);
        stage.addActor(imageButtonTutorial);
        stage.addActor(imageButtonExit);
        stage.addActor(lblTitulo);

        return stage;
    }

    public Stage createGamemodeButtons() {
        Texture buttonTextureRace = new Texture(Gdx.files.internal("UI/mainUI/gamemodeRace.png"));
        Texture buttonTextureTime = new Texture(Gdx.files.internal("UI/mainUI/gamemodeTime.png"));

        ImageButton.ImageButtonStyle styleRace = new ImageButton.ImageButtonStyle();
        styleRace.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureRace));
        imageButtonRace = new ImageButton(styleRace);

        ImageButton.ImageButtonStyle styleTime = new ImageButton.ImageButtonStyle();
        styleTime.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureTime));
        imageButtonTimeAttack = new ImageButton(styleTime);

        styleRace.imageUp.setMinWidth(500);
        styleRace.imageUp.setMinHeight(300);

        styleTime.imageUp.setMinWidth(500);
        styleTime.imageUp.setMinHeight(300);

        imageButtonRace.setHeight(screenHeight * 0.5f);
        imageButtonRace.setWidth(screenWidth * 0.5f);
        imageButtonRace.setPosition(0.05f * (screenWidth - imageButtonRace.getWidth()) / 2, (screenHeight - imageButtonRace.getHeight()) / 2);

        imageButtonTimeAttack.setHeight(screenHeight * 0.5f);
        imageButtonTimeAttack.setWidth(screenWidth * 0.5f);
        imageButtonTimeAttack.setPosition(2 * (screenWidth - imageButtonTimeAttack.getWidth()) / 2, (screenHeight - imageButtonTimeAttack.getHeight()) / 2);

        stage.addActor(imageButtonRace);
        stage.addActor(imageButtonTimeAttack);

        return stage;
    }

    public Stage createSelectionButtons() {
        Texture buttonTextureRace = new Texture(Gdx.files.internal("UI/mainUI/cars.png"));
        Texture buttonTextureTime = new Texture(Gdx.files.internal("UI/mainUI/circuit.png"));

//        Gdx.input.getTextInput(new NameInputListener(), "Enter your name", "", "Name");

        ImageButton.ImageButtonStyle styleCars = new ImageButton.ImageButtonStyle();
        styleCars.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureRace));
        imageButtonSelectionCar = new ImageButton(styleCars);

        ImageButton.ImageButtonStyle styleCircuit = new ImageButton.ImageButtonStyle();
        styleCircuit.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureTime));
        imageButtonSelectionCircuit = new ImageButton(styleCircuit);

        styleCars.imageUp.setMinWidth(500);
        styleCars.imageUp.setMinHeight(300);

        styleCircuit.imageUp.setMinWidth(500);
        styleCircuit.imageUp.setMinHeight(300);

        imageButtonSelectionCar.setHeight(screenHeight * 0.5f);
        imageButtonSelectionCar.setWidth(screenWidth * 0.5f);
        imageButtonSelectionCar.setPosition(0.05f * (screenWidth - imageButtonSelectionCar.getWidth()) / 2, (screenHeight - imageButtonSelectionCar.getHeight()) / 2);

        imageButtonSelectionCircuit.setHeight(screenHeight * 0.5f);
        imageButtonSelectionCircuit.setWidth(screenWidth * 0.5f);
        imageButtonSelectionCircuit.setPosition(2 * (screenWidth - imageButtonSelectionCircuit.getWidth()) / 2, (screenHeight - imageButtonSelectionCircuit.getHeight()) / 2);

        stage.addActor(imageButtonSelectionCar);
        stage.addActor(imageButtonSelectionCircuit);

        return stage;
    }

    public Stage createCarButtons() {
        Label.LabelStyle f1FontTitle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/Formula1-Wide.fnt")), Color.WHITE);
        f1FontTitle.font.getData().setScale(50 / f1FontTitle.font.getCapHeight());

        Label lblSelection = new Label("Car Selection", f1FontTitle);

        float labelWidth = lblSelection.getWidth();

        lblSelection.setHeight(screenHeight * 0.15f);
        lblSelection.setWidth(screenWidth * 0.15f);
        lblSelection.setPosition((screenWidth - labelWidth) / 2, 14.5f * screenHeight / 20);

        stage.addActor(lblSelection);
        FileHandle assetsFolder = Gdx.files.internal("Cars");
        if (assetsFolder.isDirectory()) {
            FileHandle[] files = assetsFolder.list();

            float initialPositionX = (0.42f * screenWidth) / 2;
            float initialPositionY = (0.9f * screenHeight) / 2;
            float auxX = initialPositionX;
            float auxY = initialPositionY;

            float gapx = 0.19f * screenWidth;
            float gapY = 0.35f * screenHeight;
            for (int i = 0; i < files.length; i++) {
                FileHandle file = files[i];
                Gdx.app.log("File", file + " ");

                Texture buttonTextureCars = new Texture(Gdx.files.internal(file.toString()));
                ImageButton.ImageButtonStyle styleCars = new ImageButton.ImageButtonStyle();
                styleCars.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureCars));
                ImageButton imageButtonCars = new ImageButton(styleCars);

                imageButtonCars.setHeight(screenHeight * 0.2f);
                imageButtonCars.setWidth(screenWidth * 0.2f);

                imageButtonCars.setPosition(auxX, auxY);
                imageButtonCars.setName(" " + i);
                auxX += gapx;

                if (i == files.length / 2 - 1) {
                    auxX = initialPositionX;
                    auxY -= gapY;
                }
                Gdx.app.log("pos", auxX + ":" + auxY);
                listaCoches.add(imageButtonCars);
                stage.addActor(imageButtonCars);


            }
        }
        return stage;
    }

    public Stage createMapsButtons() {
        Label.LabelStyle f1FontTitle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/Formula1-Wide.fnt")), Color.WHITE);
        f1FontTitle.font.getData().setScale(50 / f1FontTitle.font.getCapHeight());

        Label lblSelection = new Label("Circuit Selection", f1FontTitle);

        float labelWidth = lblSelection.getWidth();

        lblSelection.setHeight(screenHeight * 0.15f);
        lblSelection.setWidth(screenWidth * 0.15f);
        lblSelection.setPosition((screenWidth - labelWidth) / 2, 14.5f * screenHeight / 20);

        Texture buttonTextureRace = new Texture(Gdx.files.internal("TrackFiles/Track1/track1.png"));
        Texture buttonTextureTime = new Texture(Gdx.files.internal("TrackFiles/Track2/track2.png"));

        ImageButton.ImageButtonStyle styleTrack1 = new ImageButton.ImageButtonStyle();
        styleTrack1.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureRace));
        imageButtonTrack1 = new ImageButton(styleTrack1);

        ImageButton.ImageButtonStyle styleTrack2 = new ImageButton.ImageButtonStyle();
        styleTrack2.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureTime));
        imageButtonTrack2 = new ImageButton(styleTrack2);

        imageButtonTrack1.setHeight(screenHeight * 0.5f);
        imageButtonTrack1.setWidth(screenWidth * 0.5f);
        imageButtonTrack1.setPosition(0.05f * (screenWidth - imageButtonTrack1.getWidth()) / 2, (screenHeight - imageButtonTrack1.getHeight()) / 2);

        imageButtonTrack2.setHeight(screenHeight * 0.5f);
        imageButtonTrack2.setWidth(screenWidth * 0.5f);
        imageButtonTrack2.setPosition(2 * (screenWidth - imageButtonTrack2.getWidth()) / 2, (screenHeight - imageButtonTrack2.getHeight()) / 2);

        stage.addActor(imageButtonTrack1);
        stage.addActor(imageButtonTrack2);
        stage.addActor(lblSelection);

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

    public ImageButton getImageButtonPause() {
        return imageButtonPause;
    }

    public ImageButton getImageButtonStart() {
        return imageButtonStart;
    }

    public ImageButton getImageButtonSettings() {
        return imageButtonSettings;
    }

    public ImageButton getImageButtonPuntuaciones() {
        return imageButtonPuntuaciones;
    }

    public ImageButton getImageButtonTutorial() {
        return imageButtonTutorial;
    }

    public ImageButton getImageButtonExit() {
        return imageButtonExit;
    }

    public ImageButton getImageButtonVolver() {
        return imageButtonVolver;
    }

    public ImageButton getImageButtonMusicON() {
        return imageButtonMusicON;
    }

    public ImageButton getImageButtonVibracion() {
        return imageButtonVibracion;
    }

    public ImageButton getImageButtonRace() {
        return imageButtonRace;
    }

    public ImageButton getImageButtonTimeAttack() {
        return imageButtonTimeAttack;
    }

    public ImageButton getImageButtonTrack1() {
        return imageButtonTrack1;
    }

    public ImageButton getImageButtonTrack2() {
        return imageButtonTrack2;
    }

    public ArrayList<ImageButton> getImageButtonCars() {
        return listaCoches;
    }

    public void updateVueltas(int vueltas) {
        lblVuelta.setText("Lap: " + vueltas);
    }
}