package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.Button;

public class MyGdxGame extends ApplicationAdapter {

	private TiledMap map;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	OrthographicCamera camera;

	Stage stage;
	TextButton button;
	TextButton.TextButtonStyle textButtonStyle;
	BitmapFont font;
	Skin mySkin = new Skin(Gdx.files.internal("assets/buttons/skin/clean-crispy-ui.json"));


	@Override
	public void create () {
		map = new TmxMapLoader().load("trackFiles/track.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		// Boton
		stage = new Stage(new ScreenViewport());

		button = new TextButton("Presiona", textButtonStyle);
		button.setPosition(Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, 20);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Lógica que se ejecutará cuando se haga clic en el botón
				System.out.println("Botón presionado");
			}
		});

		stage.addActor(button);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		Gdx.input.setInputProcessor(stage);
		stage.act();
		stage.draw();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();


	}

	@Override
	public void dispose () {

		map.dispose();
		stage.dispose();
		mySkin.dispose();
	}
}