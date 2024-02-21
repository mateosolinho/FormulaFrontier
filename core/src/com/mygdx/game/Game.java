package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.Screens.MainScreen;
import com.mygdx.game.Tools.PreferencesManager;

/**
 * La clase Game representa el punto principal del juego y controla la gestión de las pantallas y el idioma del juego.
 */
public class Game extends com.badlogic.gdx.Game {

    private MainScreen mainScreen;
    /**
     * Almacena las traducciones del juego
     */
    public static I18NBundle bundle;

    /**
     * Método llamado al inicio del juego. Configura el idioma del juego y carga los archivos de traducción.
     */
    @Override
    public void create() {
        Gdx.app.log("idi", PreferencesManager.getLocale() + " ");
        FileHandle fileHandle = Gdx.files.internal("Traductions/" + PreferencesManager.getPropertyFile());
        bundle = I18NBundle.createBundle(fileHandle, PreferencesManager.getLocale());

        mainScreen = new MainScreen(this);
        setScreen(mainScreen);
    }

    /**
     * Método llamado continuamente para renderizar el juego.
     */
    @Override
    public void render() {
        super.render();
    }

    /**
     * Método llamado cuando el juego se cierra para liberar recursos.
     */
    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Cambia la pantalla actual a la pantalla principal.
     */
    public void setMainScreen() {
        setScreen(mainScreen);
    }

    /**
     * Método estático para actualizar el idioma del juego.
     */
    public static void actualizaridioma() {
        FileHandle fileHandle = Gdx.files.internal("Traductions/" + PreferencesManager.getPropertyFile());
        Game.bundle = I18NBundle.createBundle(fileHandle, PreferencesManager.getLocale());
    }
}
