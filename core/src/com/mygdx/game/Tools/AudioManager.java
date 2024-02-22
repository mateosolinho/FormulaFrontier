package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Clase encargada de gestionar la música y los efectos de sonido en el juego
 */
public class AudioManager {

    /**
     * Música utilizada en la pantalla del menú.
     */
    private Music menuMusic;

    /**
     * Música utilizada durante la carrera en el juego.
     */
    private Music raceMusic;

    /**
     * Inicia la reproducción de la música del menú si la configuración de música está habilitada.
     */
    public void startMusicMenu() {
        if (PreferencesManager.getMusica()) {
            menuMusic = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/audioMenu.mp3"));
            menuMusic.setLooping(true);
            menuMusic.play();
        }
    }

    /**
     * Detiene la reproducción de la música del menú.
     */
    public void stopMusicMenu() {
        menuMusic.stop();
    }

    /**
     * Inicia la reproducción de la música del semáforo.
     */
    public void startSemaforoMusic() {
        Music semaforoMusic = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/semaforo.mp3"));
        semaforoMusic.play();
    }

    /**
     * Inicia la reproducción de la música de la carrera.
     */
    public void startMusicCarrera() {
        raceMusic = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/audioCarrera.mp3"));
        raceMusic.setVolume(0.3f);
        raceMusic.setLooping(true);
        raceMusic.play();
    }

    /**
     * Detiene la reproducción de la música de la carrera.
     */
    public void stopMusicCarrera() {
        raceMusic.stop();
    }
}
