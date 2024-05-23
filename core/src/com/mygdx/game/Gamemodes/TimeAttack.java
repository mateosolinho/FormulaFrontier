package com.mygdx.game.Gamemodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Game;
import com.mygdx.game.Tools.ButtonCreator;
import com.mygdx.game.Tools.PreferencesManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Class for tracking time, recording new times and managing them.
 */
public class TimeAttack {

    /**
     * Date format to represent time in "HH:mm:ss" format.
     */
    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:SS");

    /**
     * Calendar instance used to work with system date and time.
     */
    private static final Calendar cal = Calendar.getInstance();

    /**
     * List that stores the times recorded during the game.
     */
    public static ArrayList<Long> tiempos = new ArrayList<>();

    /**
     * Indicates if the timer has started or not.
     */
    private static boolean startTime = false;

    /**
     * Current time in milliseconds.
     */
    private static long tiempo = 0;

    /**
     * Total time accumulated during the game.
     */
    private static long tiempoTotal;

    /**
     * Last recorded time.
     */
    private long lastTime = 0;

    /**
     * Preferences manager to manage game preferences.
     */
    private static final PreferencesManager preferencesManager = new PreferencesManager();

    /**
     * Restarts recorded times and stops the timer.
     */
    public static void resetTimes() {
        tiempos.clear();
        startTime = false;
        tiempo = 0;
    }

    /**
     * Get the current time in "mm:ss:SS" string format".
     * Calculate the elapsed time if the timer is active.
     *
     * @return Current time in String format.
     */
    public static String getTiempoActual() {
        if (startTime) {
            tiempo += Gdx.graphics.getDeltaTime() * 1000;
            tiempoTotal += Gdx.graphics.getDeltaTime() * 1000;
            preferencesManager.guardarTiempoTotal(tiempoTotal);
        }
        return formatTiempo(tiempo);
    }

    /**
     * Formats a given time in milliseconds in the format "mm:ss:SS"
     *
     * @param tiempo Tiempo en milisegundos a formatear.
     * @return Tiempo formateado en formato de cadena.
     */
    public static String formatTiempo(long tiempo) {
        cal.setTimeInMillis(tiempo);
        return dateFormat.format(cal.getTime());
    }

    // TODO Surely it can be deleted
    /**
     * Formatea un tiempo dado en milisegundos en el formato "mm:ss:SS".
     *
     * @param tiempo Tiempo en milisegundos a formatear.
     * @return Tiempo formateado en formato de cadena.
     */
    public static String formatTiempoMarcas(long tiempo) {
        cal.setTimeInMillis(tiempo);
        return dateFormat.format(cal.getTime());
    }

    /**
     * Add a new time to the list of recorded times.
     */
    public static void addNewTime() {
        tiempos.add(tiempo);
    }

    /**
     * Set the current time.
     *
     * @param tiempo El nuevo tiempo actual en milisegundos.
     */
    public static void setTiempo(long tiempo) {
        TimeAttack.tiempo = tiempo;
    }

    /**
     * Set if the timer is active or not.
     *
     * @param startTime Indicates if the timer is active.
     */
    public void setStartTime(boolean startTime) {
        TimeAttack.startTime = startTime;
    }


    /**
     * Gets the best recorded time and saves it in preferences.
     * Updates the formatted time for display on the interface.
     */
    public void getBestTime() {
        if (tiempos.isEmpty()) {
            return;
        }
        long bestTime = Collections.min(tiempos);
        Gdx.app.log("besty", bestTime + " ");

        long bestTimeFromPreferences = PreferencesManager.getTiempo1Milis();

        Gdx.app.log("besty", bestTimeFromPreferences + " ");
        if (bestTime < bestTimeFromPreferences || bestTimeFromPreferences == 0) {
            preferencesManager.guardarTiempo1Milis(bestTime);
        }

        formatTiempo(PreferencesManager.getTiempo1Milis());
    }

    /**
     * Gets the last recorded time and updates the color of the corresponding label in the interface
     *
     * @return The last recorded time in String format
     */
    public String getLastTime() {
        if (tiempos.size() > 0) {
            lastTime = tiempos.get(tiempos.size() - 1);
        }

        if (PreferencesManager.getTiempo1Milis() > lastTime) {
            ButtonCreator.lblLastTime.setColor(Color.GREEN);
        } else if (PreferencesManager.getTiempo1Milis() < lastTime) {
            ButtonCreator.lblLastTime.setColor(Color.RED);
        } else {
            ButtonCreator.lblLastTime.setColor(Color.GREEN);
        }
        return Game.bundle.get("ultimoTiempo") + ": " + formatTiempoMarcas(lastTime);
    }
}