package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class PreferencesManager {
    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final Calendar cal = Calendar.getInstance();

    public static String formatTiempo(long tiempo) {
        cal.setTimeInMillis(tiempo - 3600000);
        return dateFormat.format(cal.getTime());
    }

    static Preferences prefs = Gdx.app.getPreferences("Datos");

    public void guardarVueltas(int vueltasTotales) {
        prefs.putInteger("laps", vueltasTotales);
        prefs.flush();
    }

    public int getVueltas() {
        if (!prefs.contains("laps")) {
            return 0;
        }
        return prefs.getInteger("laps");
    }

    //==============================================================
    public void guardarTiempo1Milis(long tiempo1) {
        prefs.putLong("tiempo1Milis", tiempo1);
        prefs.flush();
    }

    public static long getTiempo1Milis() {
        if (!prefs.contains("tiempo1Milis")) {
            return 0;
        }
        return prefs.getLong("tiempo1Milis");
    }

    //==============================================================

    public void guardarTiempoTotal(long tiempoTotal) {
        prefs.putLong("tiempoTotal", tiempoTotal);
        prefs.flush();
    }

    public String getTiempoTotal() {
        if (!prefs.contains("tiempoTotal")) {
            return "00:00:00";
        }
        Gdx.app.log("tiempo", prefs.getLong("tiempoTotal") + " ");
        Gdx.app.log("tiempo", formatTiempo(prefs.getLong("tiempoTotal")) + " ");
        return formatTiempo(prefs.getLong("tiempoTotal"));
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

    //==============================================================

    public static void setIngles(boolean ingles) {
        Gdx.app.log("ingles" , ingles + " ");
        prefs.putBoolean("ingles", ingles);
        prefs.flush();
    }

    public static boolean getIngles() {
        return prefs.getBoolean("ingles");
    }

    public static String getPropertyFile() {
        return getIngles() ? "textos_en" : "textos_es";
    }

    public static Locale getLocale() {
        return getIngles() ? new Locale("en") : new Locale("es");
    }
}