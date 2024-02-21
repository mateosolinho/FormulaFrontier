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

public class TimeAttack {
    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:SS");
    private static final Calendar cal = Calendar.getInstance();

    static public ArrayList<Long> tiempos = new ArrayList<>();
    static private boolean startTime = false;
    static private long tiempo = 0;
    static private long tiempoTotal = 0;
    long lastTime = 0;
    long bestTime = 0;
    static PreferencesManager preferencesManager = new PreferencesManager();

    public static void resetTimes() {
        tiempos.clear();
        startTime = false;
        tiempo = 0;
    }

    public static String getTiempoActual() {
        if (startTime) {
            tiempo += Gdx.graphics.getDeltaTime() * 1000;
            tiempoTotal += Gdx.graphics.getDeltaTime() * 1000;
            preferencesManager.guardarTiempoTotal(tiempoTotal);
        }
        return formatTiempo(tiempo);
    }

    public static String formatTiempo(long tiempo) {
        cal.setTimeInMillis(tiempo);
        return dateFormat.format(cal.getTime());
    }

    public static String formatTiempoMarcas(long tiempo) {
        cal.setTimeInMillis(tiempo);
        return dateFormat.format(cal.getTime());
    }

    public static void addNewTime() {
        tiempos.add(tiempo);
    }

    public static void setTiempo(long tiempo) {
        TimeAttack.tiempo = tiempo;
    }

    public void setStartTime(boolean startTime) {
        TimeAttack.startTime = startTime;
    }

    public void getBestTime() {
        if (tiempos.isEmpty()) {
            return;
        }
        bestTime = Collections.min(tiempos);
        Gdx.app.log("besty", bestTime + " ");

        long bestTimeFromPreferences = PreferencesManager.getTiempo1Milis();

        Gdx.app.log("besty", bestTimeFromPreferences + " ");
        if (bestTime < bestTimeFromPreferences || bestTimeFromPreferences == 0) {
            preferencesManager.guardarTiempo1Milis(bestTime);
        }

        formatTiempo(PreferencesManager.getTiempo1Milis());
    }

    public String getLastTime() {
        if (tiempos.size() > 0) {
            lastTime = tiempos.get(tiempos.size() - 1);
        }

        Gdx.app.log("timepob" , lastTime + " ");
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