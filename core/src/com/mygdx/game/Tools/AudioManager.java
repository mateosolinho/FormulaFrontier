package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.Game;

public class AudioManager {
    Music menuMusic;
    Music semaforoMusic;
    Music raceMusic;

    public void startMusicMenu() {
        if (Game.musica) {
            menuMusic = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/audioMenu.mp3"));
            menuMusic.setLooping(true);
            menuMusic.play();
        }
    }

    public void stopMusicMenu() {
        menuMusic.stop();
    }

    public void startSemaforoMusic() {
        semaforoMusic = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/semaforo.mp3"));
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

//    public void startCocheAcelerando() {
//        cocheAcelerando = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/carAudio.mp3"));
//        cocheAcelerando.setVolume(0.3f);
//        menuMusic.setLooping(true);
//        cocheAcelerando.play();
//    }
//
//    public void stopAudioAcelerando() {
//        cocheAcelerando.stop();
//    }

//    public void startCocheReduciendo() {
//        cocheReduciendo = Gdx.audio.newMusic(Gdx.files.internal("AudioEffects/audioReducir.mp3"));
//        cocheReduciendo.setVolume(0.3f);
//        menuMusic.setLooping(true);
//        cocheReduciendo.play();
//    }
//
//    public void stopAudioReduciendo() {
//        if (cocheReduciendo != null) {
//            cocheReduciendo.stop();
//        }
//    }
}
