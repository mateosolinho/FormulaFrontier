package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * Clase encaragda de gestionar las preferences de la aplicación y la persistencia de datos.
 */
public class PreferencesManager {
    /**
     * Formato de fecha para representar el tiempo en formato "HH:mm:ss".
     */
    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * Instancia de Calendar utilizada para trabajar con la fecha y la hora del sistema.
     */
    private static final Calendar cal = Calendar.getInstance();

    /**
     * Método encargado de formatear el tiempo en mm al formato indicado.
     *
     * @param tiempo Unidad de tiempo en mm.
     * @return String de la cadena ya formateada con el tiempo.
     */
    public static String formatTiempo(long tiempo) {
        cal.setTimeInMillis(tiempo - 3600000);
        return dateFormat.format(cal.getTime());
    }

    /**
     * Preferencias que almmacenan los datos en la aplicación.
     */
    static Preferences prefs = Gdx.app.getPreferences("Datos");

    /**
     * Guarda el número de vueltas totoles.
     *
     * @param vueltasTotales Número total de vueltas que se vaan a guardar.
     */
    public void guardarVueltas(int vueltasTotales) {
        prefs.putInteger("laps", vueltasTotales);
        prefs.flush();
    }

    /**
     * Obtiene el número de vueltas totales.
     *
     * @return El número de vueltas totales si está disponible, sino devuelve 0.
     */
    public int getVueltas() {
        if (!prefs.contains("laps")) {
            return 0;
        }
        return prefs.getInteger("laps");
    }

    /**
     * Guarda el tiempo en milisegundos.
     *
     * @param tiempo1 Tiempo en milisegundos que se va a guardar.
     */
    public void guardarTiempo1Milis(long tiempo1) {
        prefs.putLong("tiempo1Milis", tiempo1);
        prefs.flush();
    }

    /**
     * Obtiene el tiempo en milisegundos.
     *
     * @return Devuelve el tiempo en milisegundos si esta disponible, sino devuelve 0.
     */
    public static long getTiempo1Milis() {
        if (!prefs.contains("tiempo1Milis")) {
            return 0;
        }
        return prefs.getLong("tiempo1Milis");
    }

    /**
     * Guarda el tiempo total de la sesión.
     *
     * @param tiempoTotal Tiempo totol de la sesión a guardar en milisegundos.
     */
    public void guardarTiempoTotal(long tiempoTotal) {
        prefs.putLong("tiempoTotal", tiempoTotal);
        prefs.flush();
    }

    /**
     * Obtiene el tiempo total en milisegundos.
     *
     * @return Devuelve el tiempo en milisegundos si está disponible, sino devuelve 0.
     */
    public String getTiempoTotal() {
        if (!prefs.contains("tiempoTotal")) {
            return "00:00:00";
        }
        return formatTiempo(prefs.getLong("tiempoTotal"));
    }

    /**
     * Guarda la preferencia de música.
     *
     * @param musica La preferencia de música que se va a guardar.
     */
    public void guardarMusica(boolean musica) {
        prefs.putBoolean("musica", musica);
        prefs.flush();
    }

    /**
     * Obtiene la preferencia de música.
     *
     * @return La preferencia de música si está disponible, de lo contrario, devuelve true.
     */
    public static boolean getMusica() {
        if (!prefs.contains("musica")) {
            return true;
        }
        return prefs.getBoolean("musica");
    }

    /**
     * Guarda la preferencia de vibración.
     *
     * @param vibracion La preferencia de vibración que se va a guardar.
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
     * Obtiene la preferencia de vibración.
     *
     * @return La preferencia de vibración si está disponible, de lo contrario, devuelve true.
     */
    public static boolean getVibracion() {
        if (!prefs.contains("vibracion")) {
            return true;
        }
        return prefs.getBoolean("vibracion");
    }

    /**
     * Establece la preferencia de idioma inglés.
     *
     * @param ingles El valor booleano que indica si se prefiere el idioma inglés.
     */
    public static void setIngles(boolean ingles) {
        Gdx.app.log("ingles", ingles + " valor guardado");
        prefs.putBoolean("ingles", ingles);
        prefs.flush();
    }

    /**
     * Obtiene la preferencia de idioma inglés.
     *
     * @return True si la preferencia de idioma es inglés, de lo contrario, false.
     */
    public static boolean getIngles() {
        return prefs.getBoolean("ingles");
    }

    /**
     * Obtiene el nombre del archivo de propiedades basado en la preferencia de idioma.
     *
     * @return El nombre del archivo de propiedades ("textos_en" para inglés o "textos_es" para español).
     */
    public static String getPropertyFile() {
        return getIngles() ? "textos_en" : "textos_es";
    }

    /**
     * Obtiene el Locale basado en la preferencia de idioma.
     *
     * @return El objeto Locale que representa el idioma seleccionado (inglés o español).
     */
    public static Locale getLocale() {
        return getIngles() ? new Locale("en") : new Locale("es");
    }
}