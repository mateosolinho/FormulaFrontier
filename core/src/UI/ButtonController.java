package UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ButtonController {

    private Stage stage;

    private OrthographicCamera camera;

    // Mediante el constructor le pasamos el Stage instanciado y llama a la función que crea los botones
    public ButtonController(Stage stage, OrthographicCamera camera) {
        this.stage = stage;
        this.camera = camera;
        setupButtons();
    }

    // Función que sirve para pasarle el Stage a la clase principal
    public Stage getStage() {
        if (stage == null) {
            stage = new Stage(new ScreenViewport());
            Gdx.input.setInputProcessor(stage);
            //setupButtons();
        }
        return stage;
    }

    // Función mediante la cual se crean los botones
    public void setupButtons () {
        // Crea una nueva instancia del Stage usando un viewport basado en pantalla (pixeles)
        stage = new Stage(new ScreenViewport());
        // Establece el Stage como el procesador de entrada principal (Gestión de eventos, click, toques en pantalla...)
        Gdx.input.setInputProcessor(stage);

        // Se le asgina una textura a cada botón
        Texture buttonTextureDerecha = new Texture(Gdx.files.internal("UI/flecha-derecha.png"));
        Texture buttonTextureIzquierda = new Texture(Gdx.files.internal("UI/flecha-izquierda.png"));
        Texture buttonTextureArriba = new Texture(Gdx.files.internal("UI/flecha-arriba.png"));
        Texture buttonTextureAbajo = new Texture(Gdx.files.internal("UI/flecha-abajo.png"));

        // Creación de cada botón y asignación de la textura y el estilo
        ImageButton.ImageButtonStyle styleDerecha = new ImageButton.ImageButtonStyle();
        styleDerecha.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureDerecha));
        ImageButton imageButtonDerecha = new ImageButton(styleDerecha);

        ImageButton.ImageButtonStyle styleIzquierda = new ImageButton.ImageButtonStyle();
        styleIzquierda.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureIzquierda));
        ImageButton imageButtonIzquierda = new ImageButton(styleIzquierda);

        ImageButton.ImageButtonStyle styleArriba = new ImageButton.ImageButtonStyle();
        styleArriba.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureArriba));
        ImageButton imageButtonArriba = new ImageButton(styleArriba);

        ImageButton.ImageButtonStyle styleAbajo = new ImageButton.ImageButtonStyle();
        styleAbajo.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTextureAbajo));
        ImageButton imageButtonAbajo = new ImageButton(styleAbajo);

        // Asignación de posición y tamaño acada botón
        imageButtonDerecha.setPosition(1650, 150);
        imageButtonDerecha.setSize(150, 150);

        imageButtonIzquierda.setPosition(1400, 150);
        imageButtonIzquierda.setSize(150, 150);

        imageButtonArriba.setPosition(200, 300);
        imageButtonArriba.setSize(150, 150);

        imageButtonAbajo.setPosition(200, 50);
        imageButtonAbajo.setSize(150, 150);

        // Gestíón de eventos en cada botón
        imageButtonDerecha.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ImageButtonDerecha", "DERECHA!");
                camera.translate(30,0);
            }
        });
        imageButtonIzquierda.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ImageButtonIzquierda", "IZQUIERDA!");
                camera.translate(-30,0);
            }
        });

        imageButtonArriba.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ImageButtonIzquierda", "ARRIBA!");
                camera.translate(0,30);
            }
        });

        imageButtonAbajo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ImageButtonIzquierda", "ABAJO!");
                camera.translate(0,-30);
            }
        });

        // Añadimos al Stage cada botón
        stage.addActor(imageButtonDerecha);
        stage.addActor(imageButtonIzquierda);
        stage.addActor(imageButtonArriba);
        stage.addActor(imageButtonAbajo);


    }
}
