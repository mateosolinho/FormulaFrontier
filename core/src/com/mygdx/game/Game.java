package com.mygdx.game;

import com.mygdx.game.Screens.PlayScreen;

public class Game extends com.badlogic.gdx.Game {
    public static final float PPM=100;

    @Override
    public void create() {
        setScreen(new PlayScreen());
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
