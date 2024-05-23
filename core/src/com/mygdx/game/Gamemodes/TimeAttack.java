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
 * Clase encargada de realizar el seguimiento del tiempo, registrar nuevos tiempos y gestionarlos.
 */
public class TimeAttack {

    /**
     * Formato de fecha para representar el tiempo en formato "HH:mm:ss".
     */
    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:SS");

    /**
     * Instancia de Calendar utilizada para trabajar con la fecha y la hora del sistema.
     */
    private static final Calendar cal = Calendar.getInstance();

    /**
     * Lista que almacena los tiempos registrados durante el juego.
     */
    public static ArrayList<Long> tiempos = new ArrayList<>();

    /**
     * Indica si el temporizador ha comenzado o no.
     */
    private static boolean startTime = false;

    /**
     * Tiempo actual en milisegundos.
     */
    private static long tiempo = 0;

    /**
     * Tiempo total acumulado durante el juego.
     */
    private static long tiempoTotal;

    /**
     * Último tiempo registrado.
     */
    private long lastTime = 0;

    /**
     * Gestor de preferencias para manejar las preferencias del juego.
     */
    private static final PreferencesManager preferencesManager = new PreferencesManager();

    /**
     * Restablece los tiempos registrados y detiene el temporizador.
     */
    public static void resetTimes() {
        tiempos.clear();
        startTime = false;
        tiempo = 0;
    }

    /**
     * Obtiene el tiempo actual en formato de cadena "mm:ss:SS".
     * Calcula el tiempo transcurrido si el temporizador está activo.
     *
     * @return Tiempo actual en formato de cadena.
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
     * Formatea un tiempo dado en milisegundos en el formato "mm:ss:SS".
     *
     * @param tiempo Tiempo en milisegundos a formatear.
     * @return Tiempo formateado en formato de cadena.
     */
    public static String formatTiempo(long tiempo) {
        cal.setTimeInMillis(tiempo);
        return dateFormat.format(cal.getTime());
    }

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
     * Agrega un nuevo tiempo a la lista de tiempos registrados.
     */
    public static void addNewTime() {
        tiempos.add(tiempo);
    }

    /**
     * Establece el tiempo actual.
     *
     * @param tiempo El nuevo tiempo actual en milisegundos.
     */
    public static void setTiempo(long tiempo) {
        TimeAttack.tiempo = tiempo;
    }

    /**
     * Establece si el temporizador está activo o no.
     *
     * @param startTime Indica si el temporizador está activo.
     */
    public void setStartTime(boolean startTime) {
        TimeAttack.startTime = startTime;
    }


    /**
     * Obtiene el mejor tiempo registrado y lo guarda en las preferencias si es mejor que el tiempo anterior.
     * Actualiza el tiempo formateado para mostrarlo en la interfaz.
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
     * Obtiene el último tiempo registrado y actualiza el color de la etiqueta correspondiente en la interfaz.
     *
     * @return El último tiempo registrado en formato de cadena.
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