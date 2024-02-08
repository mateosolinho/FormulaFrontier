package com.mygdx.game;

import com.mygdx.game.Screens.MainScreen;

public class Game extends com.badlogic.gdx.Game {
    public static boolean vibracion = true;
    public static boolean musica = true;

    @Override
    public void create() {
        setScreen(new MainScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
