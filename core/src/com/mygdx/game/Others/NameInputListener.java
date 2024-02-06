package com.mygdx.game.Others;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class NameInputListener implements Input.TextInputListener {
    static private String playerName;

    @Override
    public void input(String text) {
        if (text != null) {
            playerName = text;
        } else {
            playerName = "Por lo visto no sabe poner su nombre";
        }
    }

    @Override
    public void canceled() {
        System.out.println("Entrada de texto cancelada");
    }

    public String getName() {
        Gdx.app.log("Nombre", playerName + " ");
        return playerName;
    }
}
