package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.Screens.MainScreen;
import com.mygdx.game.Tools.PreferencesManager;

/**
 * Class that represents the main point of the game and controls the management of the screens and the game language.
 */
public class Game extends com.badlogic.gdx.Game {

    private MainScreen mainScreen;
    /**
     * Stores game translations.
     */
    public static I18NBundle bundle;

    /**
     * Function called at the start of the game. Set the game language and load the translation files.
     */
    @Override
    public void create() {
        FileHandle fileHandle = Gdx.files.internal("Traductions/" + PreferencesManager.getPropertyFile());
        bundle = I18NBundle.createBundle(fileHandle, PreferencesManager.getLocale());

        mainScreen = new MainScreen(this);
        setScreen(mainScreen);
    }

    /**
     * Function called continuously to render the game.
     */
    @Override
    public void render() {
        super.render();
    }

    /**
     * Function called when the game is closed to free resources.
     */
    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Changes the current screen to the main screen.
     */
    public void setMainScreen() {
        setScreen(mainScreen);
    }

    /**
     * Static function to update game language.
     */
    public static void actualizaridioma() {
        FileHandle fileHandle = Gdx.files.internal("Traductions/" + PreferencesManager.getPropertyFile());
        Game.bundle = I18NBundle.createBundle(fileHandle, PreferencesManager.getLocale());
    }
}
