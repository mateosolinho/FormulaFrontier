package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.Screens.MainScreen;
import com.mygdx.game.Tools.PreferencesManager;

public class Game extends com.badlogic.gdx.Game {
    private MainScreen mainScreen;
    public static I18NBundle bundle;

    @Override
    public void create() {
        Gdx.app.log("idi", PreferencesManager.getLocale() + " ");
        FileHandle fileHandle = Gdx.files.internal("Traductions/" + PreferencesManager.getPropertyFile());
        bundle = I18NBundle.createBundle(fileHandle, PreferencesManager.getLocale());

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

    public static void actualizaridioma() {
        FileHandle fileHandle = Gdx.files.internal("Traductions/" + PreferencesManager.getPropertyFile());
        Game.bundle = I18NBundle.createBundle(fileHandle, PreferencesManager.getLocale());
    }
}
