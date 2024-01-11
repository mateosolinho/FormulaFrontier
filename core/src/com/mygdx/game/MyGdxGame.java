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

import UI.ButtonController;

public class MyGdxGame extends ApplicationAdapter {

	private TiledMap map;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	OrthographicCamera camera;

	private Stage stage;

	private SpriteBatch batch;
	private Player player;

	@Override
	public void create () {
		// Carga el mapa
		map = new TmxMapLoader().load("trackFiles/track.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

		// Crea la cámara
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		// Gestion coche
		batch = new SpriteBatch();
		player = new Player();

		// Llama a la clase donde estan los botones y le asignamos el stage de los botones al stage de esta clase para que aparezca por pantalla
		ButtonController buttonController = new ButtonController(stage, camera);
		stage = buttonController.getStage();
	}

	@Override
	public void render() {
		// Pintamos el fondo de color blanco
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Dibujar el personaje
		batch.begin();
		player.render(batch);
		batch.end();

		// Actualizamos la camara y aplicamos el zoom
		camera.zoom = 1.0f;

		// Ajustamos la posición de la cámara para centrarse en el jugador
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);

		// Actualizamos la camara y renderizamos el mapa
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		// Tiempo de actualizacion de los frames de la pantalla (30)
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		// Dibujamos el stage en pantalla
		stage.draw();

	}

	@Override
	public void dispose () {
		// Libera los recursos y limpia memoria
		map.dispose();
		stage.dispose();
		batch.dispose();
		player.dispose();
	}
}