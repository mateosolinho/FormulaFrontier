package com.mygdx.game.Gamemodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Tools.ButtonCreator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class TimeAttack {
    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:SS");
    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat dateFormatMarcas = new SimpleDateFormat("ss:SS");
    private static final Calendar cal = Calendar.getInstance();

    static public ArrayList<Long> tiempos = new ArrayList<>();
    static private boolean startTime = false;
    static private long tiempo = 0;
    long lastTime = 0;
    long bestTime = 0;

    public static String getTiempoActual() {
        if (startTime) {
            tiempo += Gdx.graphics.getDeltaTime() * 1000;
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

    public String getBestTime() {
        if (tiempos.isEmpty()) {
            return "Best: 00:00:00";
        }

        bestTime = Collections.min(tiempos);
        return "Best: " + formatTiempoMarcas(bestTime);
    }

    public String getLastTime() {
        if (tiempos.size() > 0) {
            lastTime = tiempos.get(tiempos.size() - 1);
        }

        if (bestTime > lastTime) {
            ButtonCreator.lblLastTime.setColor(Color.GREEN);
        } else if (bestTime < lastTime) {
            ButtonCreator.lblLastTime.setColor(Color.RED);
        } else {
            ButtonCreator.lblLastTime.setColor(Color.GREEN);
        }
        return "Last: " + formatTiempoMarcas(lastTime);
    }



}