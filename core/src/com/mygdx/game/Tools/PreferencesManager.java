package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class PreferencesManager {
    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:SS");
    private static final Calendar cal = Calendar.getInstance();

    public static String formatTiempo(long tiempo) {
        cal.setTimeInMillis(tiempo);
        return dateFormat.format(cal.getTime());
    }

    static Preferences prefs = Gdx.app.getPreferences("Datos");

    public void guardarVueltas(int vueltasTotales) {
        prefs.putInteger("laps", vueltasTotales);
        prefs.flush();
    }

    public int getVueltas() {
        return prefs.getInteger("laps");
    }

    //==============================================================

    public void guardarTiempo1(int tiempo1) {
        prefs.putInteger("tiempo1", tiempo1);
        prefs.flush();
    }

    public int getTiempo1() {
        return prefs.getInteger("tiempo1");
    }

    //==============================================================

    public void guardarTiempo2(int tiempo2) {
        prefs.putInteger("tiempo2", tiempo2);
        prefs.flush();
    }

    public int getTiempo2() {
        return prefs.getInteger("tiempo2");
    }

    //==============================================================

    public void guardarTiempoTotal(int tiempoTotal) {
        prefs.putInteger("tiempoTotal", tiempoTotal);
        prefs.flush();
    }

    public int getTiempoTotal() {
        return prefs.getInteger("tiempoTotal");
    }

    //==============================================================

    public void guardarMusica(boolean musica) {
        prefs.putBoolean("musica", musica);
        prefs.flush();
    }

    public static boolean getMusica() {
        if (!prefs.contains("musica")) {
            return true;
        }
        return prefs.getBoolean("musica");
    }

    //==============================================================


    public void guardarVibracion(boolean vibracion) {
        if (!prefs.contains("vibracion")) {
            prefs.putBoolean("vibracion", true);
            prefs.flush();
        } else {
            prefs.putBoolean("vibracion", vibracion);
            prefs.flush();
        }
    }

    public static boolean getVibracion() {
        if (!prefs.contains("vibracion")) {
            return true;
        }
        return prefs.getBoolean("vibracion");
    }
}