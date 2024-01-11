package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private Texture texture;
    private Sprite sprite;

    private float width, height;
    private float x,y;

    public Player(){
        // Cargar la textura del personaje desde el archivo de imagen
        texture = new Texture(Gdx.files.internal("Cars/FerrariSS.PNG"));

        // Crear un sprite usando la textura
        sprite = new Sprite(texture);

        // Establecer la posición inicial del personaje
        sprite.setPosition(100, 100);

        //sprite.setSize(50,50);

        x = 100;
        y = 100;
        width = texture.getWidth();
        height = texture.getHeight();
    }

    public void render(SpriteBatch batch) {
        // Dibujar el sprite en el SpriteBatch
        sprite.draw(batch);
    }

    public void dispose() {
        // Liberar los recursos al salir del juego
        texture.dispose();
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
