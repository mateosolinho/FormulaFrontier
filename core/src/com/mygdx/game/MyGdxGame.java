package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class MyGdxGame extends ApplicationAdapter {

	private TiledMap map;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	OrthographicCamera camera;

	private Stage stage;

	SpriteBatch batch;

	@Override
	public void create () {
		map = new TmxMapLoader().load("trackFiles/track.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		// Boton
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		Texture buttonTextureDerecha = new Texture(Gdx.files.internal("UI/flecha-derecha.png"));
		Texture buttonTextureIzquierda = new Texture(Gdx.files.internal("UI/flecha-izquierda.png"));
		Texture buttonTextureArriba = new Texture(Gdx.files.internal("UI/flecha-arriba.png"));
		Texture buttonTextureAbajo = new Texture(Gdx.files.internal("UI/flecha-abajo.png"));

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


		imageButtonDerecha.setPosition(1650, 150);
		imageButtonDerecha.setSize(150, 150);

		imageButtonIzquierda.setPosition(1400, 150);
		imageButtonIzquierda.setSize(150, 150);

		imageButtonArriba.setPosition(250, 300);
		imageButtonArriba.setSize(150, 150);

		imageButtonAbajo.setPosition(250, 50);
		imageButtonAbajo.setSize(150, 150);

		imageButtonDerecha.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("ImageButtonDerecha", "DERECHA!");
			}
		});
		imageButtonIzquierda.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("ImageButtonIzquierda", "IZQUIERDA!");
			}
		});

		imageButtonArriba.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("ImageButtonIzquierda", "ARRIBA!");
			}
		});

		imageButtonAbajo.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("ImageButtonIzquierda", "ABAJO!");
			}
		});

		stage.addActor(imageButtonDerecha);
		stage.addActor(imageButtonIzquierda);
		stage.addActor(imageButtonArriba);
		stage.addActor(imageButtonAbajo);



	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

	}

	@Override
	public void dispose () {

		map.dispose();
		stage.dispose();
		//mySkin.dispose();
	}
}