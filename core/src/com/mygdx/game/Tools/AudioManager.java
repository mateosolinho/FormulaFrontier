package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Class to manage music and sound effects in the game.
 */
public class AudioManager {

    /**
     * Music used on the menu screen.
     */
    private Music menuMusic;

    /**
     * Music used during the race in the game.
     */
    private Music raceMusic;

    /**
     * Starts playing menu music if music settings are enabled.
     */
    public void startMusicMenu() {
        if (PreferencesManager.getMusica()) {
            menuMusic = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/audioMenu.mp3"));
            menuMusic.setLooping(true);
            menuMusic.play();
        }
    }

    /**
     * Stops playing menu music.
     */
    public void stopMusicMenu() {
        menuMusic.stop();
    }

    /**
     * Start playing the traffic light music.
     */
    public void startSemaforoMusic() {
        Music semaforoMusic = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/semaforo.mp3"));
        semaforoMusic.play();
    }

    /**
     * Start playing the race music.
     */
    public void startMusicCarrera() {
        raceMusic = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/audioCarrera.mp3"));
        raceMusic.setVolume(0.3f);
        raceMusic.setLooping(true);
        raceMusic.play();
    }

    /**
     * Stops playing the race music.
     */
    public void stopMusicCarrera() {
        raceMusic.stop();
    }

    /**
     * Start the crash sound.
     */
    public void crashEffect() {
        Music crashSound = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/crash.mp3"));
        crashSound.setVolume(0.1f);
        crashSound.play();
    }
}
