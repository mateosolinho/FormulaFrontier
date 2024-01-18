package com.mygdx.game;

import com.mygdx.game.Screens.PlayScreen;

public class Game extends com.badlogic.gdx.Game {

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
