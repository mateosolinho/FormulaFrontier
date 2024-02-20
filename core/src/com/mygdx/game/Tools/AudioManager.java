package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager {
    private Music menuMusic;
    private Music raceMusic;

    public void startMusicMenu() {
        if (PreferencesManager.getMusica()) {
            menuMusic = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/audioMenu.mp3"));
            menuMusic.setLooping(true);
            menuMusic.play();
        }
    }

    public void stopMusicMenu() {
        menuMusic.stop();
    }

    public void startSemaforoMusic() {
        Music semaforoMusic = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/semaforo.mp3"));
        semaforoMusic.play();
    }

    public void startMusicCarrera() {
        raceMusic = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/audioCarrera.mp3"));
        raceMusic.setVolume(0.3f);
        raceMusic.setLooping(true);
        raceMusic.play();
    }

    public void stopMusicCarrera() {
        raceMusic.stop();
    }
}
