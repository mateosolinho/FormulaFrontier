package com.mygdx.game;

import com.mygdx.game.Screens.MainScreen;

public class Game extends com.badlogic.gdx.Game {
    private MainScreen mainScreen;

    @Override
    public void create() {
        mainScreen = new MainScreen(this);
        setScreen(mainScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void setMainScreen() {
        setScreen(mainScreen);
    }
}
