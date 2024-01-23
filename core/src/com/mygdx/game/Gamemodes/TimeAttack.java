package com.mygdx.game.Gamemodes;

import com.badlogic.gdx.Gdx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class TimeAttack {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("ss:SS");
    private static final Calendar cal = Calendar.getInstance();

    static public ArrayList<Long> tiempos = new ArrayList<>();
    static private boolean startTime = false;
    static private long tiempo = 0;
    long lastTime;

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

    public static void addNewTime() {
        tiempos.add(tiempo);
    }

    public static void setTiempo(long tiempo) {
        TimeAttack.tiempo = tiempo;
    }

    public void setStartTime(boolean startTime) {
        this.startTime = startTime;
    }

    public String getLastTime() {
        if (tiempos.isEmpty()) {
            return "No recorded times yet.";
        }

        long bestTime = Collections.min(tiempos);
        return "BEST TIME: " + formatTiempo(bestTime);
    }
}