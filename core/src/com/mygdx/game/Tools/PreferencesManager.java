package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * Class to manage application preferences and data persistence.
 */
public class PreferencesManager {
    /**
     * Date format to represent time in "HH:mm:ss" format.
     */
    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * Calendar instance used to work with system date and time.
     */
    private static final Calendar cal = Calendar.getInstance();

    /**
     * Function to format the time in mm to the indicated format.
     *
     * @param tiempo Time unit in 'mm'.
     * @return String of the string already formatted over time.
     */
    public static String formatTiempo(long tiempo) {
        cal.setTimeInMillis(tiempo - 3600000);
        return dateFormat.format(cal.getTime());
    }

    /**
     * Preferences that store data in the application.
     */
    static Preferences prefs = Gdx.app.getPreferences("Datos");

    /**
     * Saves the number of total laps.
     *
     * @param vueltasTotales Total number of laps to save.
     */
    public void guardarVueltas(int vueltasTotales) {
        prefs.putInteger("laps", vueltasTotales);
        prefs.flush();
    }

    /**
     * Gets the number of total laps.
     *
     * @return Number of total turns if available, otherwise it returns 0.
     */
    public int getVueltas() {
        if (!prefs.contains("laps")) {
            return 0;
        }
        return prefs.getInteger("laps");
    }

    /**
     * Saves time in milliseconds.
     *
     * @param tiempo1 Time in milliseconds to save.
     */
    public void guardarTiempo1Milis(long tiempo1) {
        prefs.putLong("tiempo1Milis", tiempo1);
        prefs.flush();
    }

    /**
     * Gets the time in milliseconds.
     *
     * @return Time in milliseconds if available, otherwise it returns 0.
     */
    public static long getTiempo1Milis() {
        if (!prefs.contains("tiempo1Milis")) {
            return 0;
        }
        return prefs.getLong("tiempo1Milis");
    }

    /**
     * Saves the total session time.
     *
     * @param tiempoTotal Total session time to save in milliseconds.
     */
    public void guardarTiempoTotal(long tiempoTotal) {
        prefs.putLong("tiempoTotal", tiempoTotal);
        prefs.flush();
    }

    /**
     * Gets the total time in milliseconds.
     *
     * @return Time in milliseconds if available, otherwise 0.
     */
    public String getTiempoTotal() {
        if (!prefs.contains("tiempoTotal")) {
            return "00:00:00";
        }
        return formatTiempo(prefs.getLong("tiempoTotal"));
    }

    /**
     * Save the music preference.
     *
     * @param musica Music preference to save.
     */
    public void guardarMusica(boolean musica) {
        prefs.putBoolean("musica", musica);
        prefs.flush();
    }

    /**
     * Gets the music preference.
     *
     * @return Music preference if available, otherwise returns true.
     */
    public static boolean getMusica() {
        if (!prefs.contains("musica")) {
            return true;
        }
        return prefs.getBoolean("musica");
    }

    /**
     * Save the vibration preference.
     *
     * @param vibracion Vibration preference to save.
     */
    public void guardarVibracion(boolean vibracion) {
        if (!prefs.contains("vibracion")) {
            prefs.putBoolean("vibracion", true);
            prefs.flush();
        } else {
            prefs.putBoolean("vibracion", vibracion);
            prefs.flush();
        }
    }

    /**
     * Gets the vibration preference.
     *
     * @return Vibration preference if available, otherwise returns true.
     */
    public static boolean getVibracion() {
        if (!prefs.contains("vibracion")) {
            return true;
        }
        return prefs.getBoolean("vibracion");
    }

    /**
     * Set the English language preference.
     *
     * @param ingles Boolean value that indicates whether the English language is preferred.
     */
    public static void setIngles(boolean ingles) {
        Gdx.app.log("ingles", ingles + " valor guardado");
        prefs.putBoolean("ingles", ingles);
        prefs.flush();
    }

    /**
     * Gets English language preference.
     *
     * @return True if the language preference is English, false otherwise.
     */
    public static boolean getIngles() {
        return prefs.getBoolean("ingles");
    }

    /**
     * Gets the name of the properties file based on the language preference.
     *
     * @return Name of the properties file ("textos_en" for English or "textos_es" for Spanish).
     */
    public static String getPropertyFile() {
        return getIngles() ? "textos_en" : "textos_es";
    }

    /**
     * Gets Locale based on language preference.
     *
     * @return Locale object that represents the selected language (English or Spanish).
     */
    public static Locale getLocale() {
        return getIngles() ? new Locale("en") : new Locale("es");
    }
}