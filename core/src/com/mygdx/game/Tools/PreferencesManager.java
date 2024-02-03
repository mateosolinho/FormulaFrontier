package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class PreferencesManager {

    public PreferencesManager() {

    }

    public void GuardarDatos(String name) {
        Preferences prefs = Gdx.app.getPreferences("Datos");
        prefs.putString("nombre", name);
        prefs.putInteger("puntuacion", 1000);
        prefs.flush();
    }

    public void CargarDatos() {
        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        String nombre = prefs.getString("nombre");
        int puntuacion = prefs.getInteger("puntuacion");
    }
}