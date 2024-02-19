package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class PreferencesManager {
    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
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
        if (!prefs.contains("laps")) {
            return 0;
        }
        return prefs.getInteger("laps");
    }

    //==============================================================

    public void guardarTiempo1(String tiempo1) {
        prefs.putString("tiempo1", tiempo1);
        prefs.flush();
    }

    public String getTiempo1() {
        if (!prefs.contains("tiempo1")) {
            return "00:00:00";
        }
        return prefs.getString("tiempo1");
    }

    //==============================================================

    public void guardarTiempo2(String tiempo2) {
        prefs.putString("tiempo2", tiempo2);
        prefs.flush();
    }

    public String getTiempo2() {
        if (!prefs.contains("tiempo2")) {
            return "00:00:00";
        }
        return prefs.getString("tiempo2");
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
}