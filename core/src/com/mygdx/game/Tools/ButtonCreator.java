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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Clase encargada de crear y gestionar los botones y etiquetas utilizados en la interfaz de usuario del juego.
 */
public class ButtonCreator {
    /**
     * Stage que contiene todos los elementos visuales de la pantalla.
     */
    private Stage stage;

    /**
     * Gestor de preferencias utilizado para manejar las preferencias de la aplicación.
     */
    private final PreferencesManager preferencesManager;

    /**
     * Etiqueta para mostrar el número total de vueltas.
     */
    public static Label lblVueltasTotales;

    /**
     * Etiqueta para mostrar el mejor tiempo.
     */
    public static Label lblBestTime1;

    /**
     * Etiqueta para mostrar el tiempo total de la carrera.
     */
    public static Label lblTiempoTotal;

    /**
     * Etiqueta para mostrar la vuelta actual.
     */
    public static Label lblVuelta;

    /**
     * Etiqueta para mostrar el tiempo de la vuelta actual.
     */
    public static Label lblTiempo;

    /**
     * Etiqueta para mostrar el mejor tiempo.
     */
    public static Label lblBestTime;

    /**
     * Etiqueta para mostrar el último tiempo registrado.
     */
    public static Label lblLastTime;

    /**
     * ImageButton para moverse hacia la derecha.
     */
    private ImageButton imageButtonDerecha;

    /**
     * ImageButton para moverse hacia la izquierda.
     */
    private ImageButton imageButtonIzquierda;

    /**
     * ImageButton para moverse hacia adelante.
     */
    private ImageButton imageButtonArriba;

    /**
     * ImageButton para moverse hacia atrás.
     */
    private ImageButton imageButtonAbajo;

    /**
     * ImageButton para pausar el juego.
     */
    private ImageButton imageButtonPause;

    /**
     * ImageButton para iniciar el juego.
     */
    private ImageButton imageButtonStart;

    /**
     * ImageButton para acceder a la configuración.
     */
    private ImageButton imageButtonSettings;

    /**
     * ImageButton para ver los records.
     */
    private ImageButton imageButtonRecords;

    /**
     * ImageButton para acceder al tutorial.
     */
    private ImageButton imageButtonTutorial;

    /**
     * ImageButton para salir del juego.
     */
    private ImageButton imageButtonExit;

    /**
     * ImageButton para acceder a los créditos.
     */
    private ImageButton imageButtonCreditos;

    /**
     * ImageButton para volver atrás.
     */
    private ImageButton imageButtonVolver;

    /**
     * ImageButton para activar/desactivar la música.
     */
    private ImageButton imageButtonMusicON;

    /**
     * ImageButton para activar/desactivar la vibración.
     */
    private ImageButton imageButtonVibracion;

    /**
     * ImageButton para cambiar de idioma.
     */
    private ImageButton imageButtonIdioma;

    /**
     * ImageButton para seleccionar la pista 1.
     */
    private ImageButton imageButtonTrack1;

    /**
     * ImageButton para seleccionar la pista 2.
     */
    private ImageButton imageButtonTrack2;

    /**
     * ImageButton para mostrar una flecha.
     */
    private ImageButton imageButtonFlecha;

    /**
     * Lista que contiene todos los botones de imagen relacionados con los coches.
     */
    private final ArrayList<ImageButton> listaCoches = new ArrayList<>();

    /**
     * Variable que almacena el ancho de la pantalla.
     */
    float screenWidth = Gdx.graphics.getWidth();

    /**
     * Variable que alamacena el alto de la pantalla.
     */
    float screenHeight = Gdx.graphics.getHeight();

    /**
     * Formato de fecha para representar el tiempo en formato "HH:mm:ss".
     */
    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:SS");

    /**
     * Instancia de Calendar utilizada para trabajar con la fecha y la hora del sistema.
     */
    private static final Calendar cal = Calendar.getInstance();

    /**
     * Método encargado de formatear el tiempo en mm al formato indicado.
     *
     * @param tiempo Unidad de tiempo en mm.
     * @return String de la cadena ya formateada con el tiempo.
     */
    public static String formatTiempo(long tiempo) {
        cal.setTimeInMillis(tiempo - 3600000);
        return dateFormat.format(cal.getTime());
    }

    /**
     * Constructor de la clase ButtonCreator, inicializa el stage y el gestor de preferencias.
     */
    public ButtonCreator() {
        createStage();
        preferencesManager = new PreferencesManager();
    }

    /**
     * Método privado utilizado para crear el stage de la interfaz de usuario.
     */
    private void createStage() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Crea y configura etiquetas del juego para mostrar información en la interfaz de usuario.
     * Las etiquetas incluyen información sobre el tiempo, la vuelta actual, el mejor tiempo y el último tiempo.
     */
    public void createGameLabels() {
        Label.LabelStyle f1Font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/Formula1-Regular-1.fnt")), Color.WHITE);
        f1Font.font.getData().setScale(50 / f1Font.font.getCapHeight());

        lblTiempo = new Label("", f1Font);
        lblVuelta = new Label(Game.bundle.get("vuelta") + ": ", f1Font);
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

    /**
     * Crea y configura un botón que se encarga de gestionar la vuelta a la pantalla principal
     */
    public void createFlecha() {
        Texture buttonTextureFlecha = new Texture(Gdx.files.internal("UI/mainUI/flecha.png"));

        ImageButton.ImageButtonStyle styleFlecha = new ImageButton.ImageButtonStyle();
        styleFlecha.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureFlecha));
        imageButtonFlecha = new ImageButton(styleFlecha);

        imageButtonFlecha.setHeight(screenHeight * 0.1f);
        imageButtonFlecha.setWidth(screenWidth * 0.1f);
        imageButtonFlecha.setPosition(0f * screenWidth / 2f, 17.5f * screenHeight / 20f);

        stage.addActor(imageButtonFlecha);
    }

    /**
     * Crea los botones del juego y los agrega al stage.
     *
     * @return El stage con los botones agregados.
     */
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

    /**
     * Crea los botones del menu de pausa del juego y los agrega al stage.
     *
     * @return El stage con los botones agregados.
     */
    public Stage createPauseScreenButtons() {
        Texture buttonTextureExitPause = new Texture(Gdx.files.internal(Game.bundle.get("botonSalir")));
        Texture buttonTextureVolver = new Texture(Gdx.files.internal(Game.bundle.get("botonVolver")));
        Texture buttonTextureMusicON = new Texture(Gdx.files.internal("UI/pauseUI/musicaON.png"));
        Texture buttonTextureVibracion = new Texture(Gdx.files.internal("UI/pauseUI/vibracionON.png"));
        Texture buttonTextureEspanol = new Texture(Gdx.files.internal("UI/pauseUI/espana.png"));

        ImageButton.ImageButtonStyle stylePauseExit = new ImageButton.ImageButtonStyle();
        stylePauseExit.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureExitPause));
        imageButtonExit = new ImageButton(stylePauseExit);

        ImageButton.ImageButtonStyle stylePauseVolver = new ImageButton.ImageButtonStyle();
        stylePauseVolver.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureVolver));
        imageButtonVolver = new ImageButton(stylePauseVolver);

        ImageButton.ImageButtonStyle stylePauseMusicON = new ImageButton.ImageButtonStyle();
        stylePauseMusicON.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureMusicON));
        imageButtonMusicON = new ImageButton(stylePauseMusicON);

        ImageButton.ImageButtonStyle styleVibracion = new ImageButton.ImageButtonStyle();
        styleVibracion.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureVibracion));
        imageButtonVibracion = new ImageButton(styleVibracion);

        ImageButton.ImageButtonStyle styleIdioma = new ImageButton.ImageButtonStyle();
        styleIdioma.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureEspanol));
        imageButtonIdioma = new ImageButton(styleIdioma);

        stage.getViewport().getCamera().position.set(screenWidth / 2f, 0, 0);

        imageButtonExit.setHeight(screenHeight * 0.1f);
        imageButtonExit.setWidth(screenWidth * 0.1f);
        imageButtonExit.setPosition((screenWidth - imageButtonExit.getWidth()) / 2, 6 * -screenHeight / 20f);

        imageButtonVolver.setHeight(screenHeight * 0.1f);
        imageButtonVolver.setWidth(screenWidth * 0.1f);
        imageButtonVolver.setPosition((screenWidth - imageButtonVolver.getWidth()) / 2, 4 * screenHeight / 20f);

        imageButtonMusicON.setHeight(screenHeight * 0.1f);
        imageButtonMusicON.setWidth(screenWidth * 0.1f);
        imageButtonMusicON.setPosition((2.80f * screenWidth - imageButtonExit.getWidth()) / 4f, -screenHeight / 20f);

        imageButtonVibracion.setHeight(screenHeight * 0.1f);
        imageButtonVibracion.setWidth(screenWidth * 0.1f);
        imageButtonVibracion.setPosition((screenWidth - imageButtonVibracion.getWidth()) / 4f, -screenHeight / 20f);

        imageButtonIdioma.setHeight(screenHeight * 0.1f);
        imageButtonIdioma.setWidth(screenWidth * 0.1f);
        imageButtonIdioma.setPosition((screenWidth - imageButtonIdioma.getWidth()) / 2, -screenHeight / 20f);

        if (PreferencesManager.getIngles()) {
            styleIdioma.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/espana.png"))));
        } else {
            styleIdioma.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/uk.png"))));
        }

        if (PreferencesManager.getVibracion()) {
            styleVibracion.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/vibracionON.png"))));
        } else {
            styleVibracion.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/vibracionOFF.png"))));
        }

        if (PreferencesManager.getMusica()) {
            stylePauseMusicON.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/musicaON.png"))));
        } else {
            stylePauseMusicON.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/pauseUI/musicaOFF.png"))));
        }
        stylePauseExit.imageUp.setMinWidth(500);
        stylePauseExit.imageUp.setMinHeight(300);

        stylePauseVolver.imageUp.setMinWidth(500);
        stylePauseVolver.imageUp.setMinHeight(300);

        stylePauseMusicON.imageUp.setMinWidth(2400);
        stylePauseMusicON.imageUp.setMinHeight(1700);

        styleVibracion.imageUp.setMinWidth(2400);
        styleVibracion.imageUp.setMinHeight(1700);

        stage.addActor(imageButtonExit);
        stage.addActor(imageButtonVolver);
        stage.addActor(imageButtonMusicON);
        stage.addActor(imageButtonVibracion);
        stage.addActor(imageButtonIdioma);

        return stage;
    }

    /**
     * Crea los botones y etiquetas de la pantalla principal y los agrega al stage.
     *
     * @return El stage con los botones y etiquetas agregados.
     */
    public Stage createMainButtons() {
        Label.LabelStyle f1FontTitle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/Formula1-Wide.fnt")), Color.WHITE);
        f1FontTitle.font.getData().setScale(50 / f1FontTitle.font.getCapHeight());
        Label lblTitulo = new Label(Game.bundle.get("titulo"), f1FontTitle);

        float labelWidth = lblTitulo.getWidth();

        lblTitulo.setHeight(screenHeight * 0.15f);
        lblTitulo.setWidth(screenWidth * 0.15f);
        lblTitulo.setPosition((1.02f * screenWidth - labelWidth) / 2, 4f * screenHeight / 20f);

        Texture buttonTexturePause = new Texture(Gdx.files.internal(Game.bundle.get("botonStart")));
        Texture buttonTextureSettings = new Texture(Gdx.files.internal(Game.bundle.get("botonAjustes")));
        Texture buttonTexturePuntuaciones = new Texture(Gdx.files.internal("UI/mainUI/es/puntuacionesES.png"));
        Texture buttonTextureTutorial = new Texture(Gdx.files.internal(Game.bundle.get("botonGuia")));
        Texture buttonTextureExit = new Texture(Gdx.files.internal(Game.bundle.get("botonSalir")));
        Texture buttonTextureCredits = new Texture(Gdx.files.internal(Game.bundle.get("botonCreditos")));

        ImageButton.ImageButtonStyle styleStart = new ImageButton.ImageButtonStyle();
        styleStart.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTexturePause));
        imageButtonStart = new ImageButton(styleStart);

        ImageButton.ImageButtonStyle styleSettings = new ImageButton.ImageButtonStyle();
        styleSettings.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureSettings));
        imageButtonSettings = new ImageButton(styleSettings);

        ImageButton.ImageButtonStyle stylePuntuaciones = new ImageButton.ImageButtonStyle();
        stylePuntuaciones.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTexturePuntuaciones));
        imageButtonRecords = new ImageButton(stylePuntuaciones);

        ImageButton.ImageButtonStyle styleTutorial = new ImageButton.ImageButtonStyle();
        styleTutorial.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureTutorial));
        imageButtonTutorial = new ImageButton(styleTutorial);

        ImageButton.ImageButtonStyle styleExit = new ImageButton.ImageButtonStyle();
        styleExit.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureExit));
        imageButtonExit = new ImageButton(styleExit);

        ImageButton.ImageButtonStyle styleCreditos = new ImageButton.ImageButtonStyle();
        styleCreditos.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureCredits));
        imageButtonCreditos = new ImageButton(styleCreditos);

        stage.getViewport().getCamera().position.set(screenWidth / 2, 0, 0);

        imageButtonStart.setHeight(screenHeight * 0.1f);
        imageButtonStart.setWidth(screenWidth * 0.1f);
        imageButtonStart.setPosition((screenWidth - imageButtonStart.getWidth()) / 2, (-screenHeight) / 20);

        imageButtonSettings.setHeight(screenHeight * 0.1f);
        imageButtonSettings.setWidth(screenWidth * 0.1f);
        imageButtonSettings.setPosition(screenWidth / 4f, 4 * -screenHeight / 20f);

        imageButtonRecords.setHeight(screenHeight * 0.1f);
        imageButtonRecords.setWidth(screenWidth * 0.1f);
        imageButtonRecords.setPosition(screenWidth / 4f, 8 * -screenHeight / 20f);

        imageButtonTutorial.setHeight(screenHeight * 0.1f);
        imageButtonTutorial.setWidth(screenWidth * 0.1f);
        imageButtonTutorial.setPosition((2.75f * screenWidth - imageButtonRecords.getWidth()) / 4f, 4 * -screenHeight / 20f);

        imageButtonExit.setHeight(screenHeight * 0.1f);
        imageButtonExit.setWidth(screenWidth * 0.1f);
        imageButtonExit.setPosition((2.75f * screenWidth - imageButtonExit.getWidth()) / 4f, 8 * -screenHeight / 20f);

        imageButtonCreditos.setHeight(screenHeight * 0.1f);
        imageButtonCreditos.setWidth(screenWidth * 0.1f);
        imageButtonCreditos.setPosition((screenWidth - imageButtonStart.getWidth()) / 2, 6f * (-screenHeight) / 20);

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

        styleCreditos.imageUp.setMinWidth(500);
        styleCreditos.imageUp.setMinHeight(300);

        stage.addActor(imageButtonStart);
        stage.addActor(imageButtonSettings);
        stage.addActor(imageButtonRecords);
        stage.addActor(imageButtonTutorial);
        stage.addActor(imageButtonExit);
        stage.addActor(lblTitulo);
        stage.addActor(imageButtonCreditos);

        return stage;
    }

    /**
     * Crea los botones y etiquetas de seleccion de coche y los agrega al stage.
     *
     * @return El stage con los botones y etiquetas agregados.
     */
    public Stage createCarButtons() {
        Label.LabelStyle f1FontTitle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/Formula1-Wide.fnt")), Color.WHITE);
        f1FontTitle.font.getData().setScale(50 / f1FontTitle.font.getCapHeight());

        Label lblSelection = new Label(Game.bundle.get("tituloCoches"), f1FontTitle);

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

    /**
     * Crea las etiquetas de la pantalla records y los agrega al stage.
     *
     * @return El stage con los botones y etiquetas agregados.
     */
    public Stage createLabelsRecords() {
        createFlecha();

        Label.LabelStyle f1FontTitle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/Formula1-Wide.fnt")), Color.WHITE);
        f1FontTitle.font.getData().setScale(35 / f1FontTitle.font.getCapHeight());


        Label lblRecords = new Label(Game.bundle.get("tituloRecords"), f1FontTitle);
        Label lblVueltasDadas = new Label(Game.bundle.get("vueltasRecords") + " : ", f1FontTitle);
        Label lblTiempo = new Label(Game.bundle.get("tiempoRecords") + " : ", f1FontTitle);
        Label lblTime1 = new Label(Game.bundle.get("tiempoMejorRecords") + " : ", f1FontTitle);
        lblVueltasTotales = new Label(preferencesManager.getVueltas() + " ", f1FontTitle);
        lblTiempoTotal = new Label(preferencesManager.getTiempoTotal(), f1FontTitle);
        lblBestTime1 = new Label(formatTiempo(PreferencesManager.getTiempo1Milis()), f1FontTitle);

        float labelWidthR = lblRecords.getWidth();

        lblRecords.setHeight(screenHeight * 0.15f);
        lblRecords.setWidth(screenWidth * 0.15f);
        lblRecords.setPosition((screenWidth - labelWidthR) / 2, 14.5f * screenHeight / 20);

        lblVueltasDadas.setHeight(screenHeight * 0.15f);
        lblVueltasDadas.setWidth(screenWidth * 0.15f);
        lblVueltasDadas.setPosition((0.3f * screenWidth) / 2, 10.5f * screenHeight / 20);

        lblTiempo.setHeight(screenHeight * 0.15f);
        lblTiempo.setWidth(screenWidth * 0.15f);
        lblTiempo.setPosition((0.3f * screenWidth) / 2, 7.5f * screenHeight / 20);

        lblTime1.setHeight(screenHeight * 0.15f);
        lblTime1.setWidth(screenWidth * 0.15f);
        lblTime1.setPosition((0.3f * screenWidth) / 2, 4.5f * screenHeight / 20);

        float labelWidthVueltasTotales = lblVueltasTotales.getWidth();

        lblVueltasTotales.setHeight(screenHeight * 0.15f);
        lblVueltasTotales.setWidth(screenWidth * 0.15f);
        lblVueltasTotales.setPosition((1.6f * screenWidth - labelWidthVueltasTotales) / 2, 10.5f * screenHeight / 20);

        float labelTimeTotal = lblTiempoTotal.getWidth();

        lblTiempoTotal.setHeight(screenHeight * 0.15f);
        lblTiempoTotal.setWidth(screenWidth * 0.15f);
        lblTiempoTotal.setPosition((1.6f * screenWidth - labelTimeTotal) / 2, 7.5f * screenHeight / 20);

        float labelTime1 = lblBestTime1.getWidth();

        lblBestTime1.setHeight(screenHeight * 0.15f);
        lblBestTime1.setWidth(screenWidth * 0.15f);
        lblBestTime1.setPosition((1.6f * screenWidth - labelTime1) / 2, 4.5f * screenHeight / 20);

        stage.addActor(lblVueltasDadas);
        stage.addActor(lblTiempo);
        stage.addActor(lblTime1);
        stage.addActor(lblRecords);
        stage.addActor(lblVueltasTotales);
        stage.addActor(lblTiempoTotal);
        stage.addActor(lblBestTime1);

        return stage;
    }

    /**
     * Crea los botones de la pantalla de selección de circuito y los agrega al stage.
     */
    public void createMapButtonsDefault() {
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
        imageButtonTrack1.setPosition(0.05f * (screenWidth - imageButtonTrack1.getWidth()) / 2, 0.5f * (screenHeight - imageButtonTrack1.getHeight()) / 2);

        imageButtonTrack2.setHeight(screenHeight * 0.5f);
        imageButtonTrack2.setWidth(screenWidth * 0.5f);
        imageButtonTrack2.setPosition(2 * (screenWidth - imageButtonTrack2.getWidth()) / 2, 0.6f * (screenHeight - imageButtonTrack2.getHeight()) / 2);

        stage.addActor(imageButtonTrack1);
        stage.addActor(imageButtonTrack2);
    }

    /**
     * Crea las etiquetas de la pantalla de seleccion de circuito y los agrega al stage.
     *
     * @return El stage con las etiquetas agregadas.
     */
    public Stage createMapsButtons() {
        Label.LabelStyle f1FontTitle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/Formula1-Wide.fnt")), Color.WHITE);
        f1FontTitle.font.getData().setScale(50 / f1FontTitle.font.getCapHeight());

        Label lblSelection = new Label(Game.bundle.get("tituloCircuitos"), f1FontTitle);

        float labelWidth = lblSelection.getWidth();

        lblSelection.setHeight(screenHeight * 0.15f);
        lblSelection.setWidth(screenWidth * 0.15f);
        lblSelection.setPosition((screenWidth - labelWidth) / 2, 14.5f * screenHeight / 20);

        stage.addActor(lblSelection);

        createMapButtonsDefault();

        return stage;
    }

    /**
     * Crea las etiquetas de la pantalla de creditos y los agrega al stage.
     *
     * @return El stage con las etiquetas agregadas.
     */
    public Stage createLabelsCredits() {
        createFlecha();

        Label.LabelStyle f1FontTitle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/Formula1-Wide.fnt")), Color.WHITE);
        f1FontTitle.font.getData().setScale(30 / f1FontTitle.font.getCapHeight());

        Label lblSelection = new Label(Game.bundle.get("tituloCreditos"), f1FontTitle);

        float labelWidth = lblSelection.getWidth();

        lblSelection.setHeight(screenHeight * 0.15f);
        lblSelection.setWidth(screenWidth * 0.15f);
        lblSelection.setPosition((screenWidth - labelWidth) / 2, 14.5f * screenHeight / 20);

        Label lblCreditos = new Label(Game.bundle.get("fisicas") + " : Onibojan \n\n" +
                Game.bundle.get("fisicas") + " : www.iforce2d.net \n\n" +
                "Assets : itch.io \n\n" +
                "Assets : GameDev Market \n\n" +
                "Assets : www.f1assets \n\n" +
                Game.bundle.get("musica") + " : www.jamendo.com", f1FontTitle);

        float labelWidthVTt = lblCreditos.getWidth();

        lblCreditos.setHeight(screenHeight * 0.15f);
        lblCreditos.setWidth(screenWidth * 0.15f);
        lblCreditos.setPosition((screenWidth - labelWidthVTt) / 2, 6f * screenHeight / 20f);

        stage.addActor(lblCreditos);
        stage.addActor(lblSelection);

        return stage;
    }

    /**
     * Obtiene el botón para moverse hacia la derecha.
     *
     * @return El botón para moverse hacia la derecha.
     */
    public ImageButton getImageButtonDerecha() {
        return imageButtonDerecha;
    }

    /**
     * Obtiene el botón para moverse hacia la izquierda.
     *
     * @return El botón para moverse hacia la izquierda.
     */
    public ImageButton getImageButtonIzquierda() {
        return imageButtonIzquierda;
    }

    /**
     * Obtiene el botón para moverse hacia adelante.
     *
     * @return El botón para moverse hacia adelante.
     */
    public ImageButton getImageButtonArriba() {
        return imageButtonArriba;
    }

    /**
     * Obtiene el botón para moverse hacia atrás.
     *
     * @return El botón para moverse hacia atrás.
     */
    public ImageButton getImageButtonAbajo() {
        return imageButtonAbajo;
    }

    /**
     * Obtiene el botón de pausa del juego.
     *
     * @return El botón de pausa del juego.
     */
    public ImageButton getImageButtonPause() {
        return imageButtonPause;
    }

    /**
     * Obtiene el botón de inicio del juego.
     *
     * @return El botón de inicio del juego.
     */
    public ImageButton getImageButtonStart() {
        return imageButtonStart;
    }

    /**
     * Obtiene el botón de configuración.
     *
     * @return El botón de configuración.
     */
    public ImageButton getImageButtonSettings() {
        return imageButtonSettings;
    }

    /**
     * Obtiene el botón de récords.
     *
     * @return El botón de récords.
     */
    public ImageButton getImageButtonRecords() {
        return imageButtonRecords;
    }

    /**
     * Obtiene el botón de la guia.
     *
     * @return El botón de la guia.
     */
    public ImageButton getImageButtonTutorial() {
        return imageButtonTutorial;
    }

    /**
     * Obtiene el botón de salida del juego.
     *
     * @return El botón de salida del juego.
     */
    public ImageButton getImageButtonExit() {
        return imageButtonExit;
    }

    /**
     * Obtiene el botón de regreso.
     *
     * @return El botón de regreso.
     */
    public ImageButton getImageButtonVolver() {
        return imageButtonVolver;
    }

    /**
     * Obtiene el botón de música.
     *
     * @return El botón de música.
     */
    public ImageButton getImageButtonMusicON() {
        return imageButtonMusicON;
    }

    /**
     * Obtiene el botón de vibración.
     *
     * @return El botón de vibración.
     */
    public ImageButton getImageButtonVibracion() {
        return imageButtonVibracion;
    }

    /**
     * Obtiene el botón de la pista 1.
     *
     * @return El botón de la pista 1.
     */
    public ImageButton getImageButtonTrack1() {
        return imageButtonTrack1;
    }

    /**
     * Obtiene el botón de la pista 2.
     *
     * @return El botón de la pista 2.
     */
    public ImageButton getImageButtonTrack2() {
        return imageButtonTrack2;
    }

    /**
     * Obtiene el botón de la flecha.
     *
     * @return El botón de la flecha.
     */
    public ImageButton getImageButtonFlecha() {
        return imageButtonFlecha;
    }

    /**
     * Obtiene el botón de créditos.
     *
     * @return El botón de créditos.
     */
    public ImageButton getImageButtonCreditos() {
        return imageButtonCreditos;
    }

    /**
     * Obtiene el botón de idioma.
     *
     * @return El botón de idioma.
     */
    public ImageButton getImageButtonIdioma() {
        return imageButtonIdioma;
    }

    /**
     * Obtiene la lista de botones de coches.
     *
     * @return La lista de botones de coches.
     */
    public ArrayList<ImageButton> getImageButtonCars() {
        return listaCoches;
    }

    /**
     * Actualiza el texto de las vueltas en el juego.
     *
     * @param vueltas El número de vueltas.
     */
    public void updateVueltas(int vueltas) {
        lblVuelta.setText(Game.bundle.get("vuelta") + ": " + vueltas);
    }
}