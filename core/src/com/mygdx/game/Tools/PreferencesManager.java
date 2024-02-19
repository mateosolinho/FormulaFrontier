package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class PreferencesManager {

    Preferences prefs = Gdx.app.getPreferences("Datos");

    public void GuardarDatos() {
        prefs.putInteger("laps", 1000);
        prefs.putInteger("tiempo1", 1000);
        prefs.putInteger("tiempo2", 1000);
        prefs.putInteger("tiempotTotal", 1000);
        prefs.flush();
    }

    public void CargarDatos() {
        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        int vueltasTotales = prefs.getInteger("laps");
        int tiempo1 = prefs.getInteger("tiempo1");
        int tiempo2 = prefs.getInteger("tiempo2");
        int tiempoTotal = prefs.getInteger("tiempoTotal");
    }
}