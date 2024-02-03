package com.mygdx.game.Others;

import com.badlogic.gdx.Input;

public class NameInputListener implements Input.TextInputListener {
    private String playerName;

    @Override
    public void input(String text) {
        if (text != null) {
            playerName = text;
        } else {
            playerName = "No sabe poner su nombre por lo visto";
        }
    }

    @Override
    public void canceled() {
        System.out.println("Entrada de texto cancelada");
    }

    public String getName() {
        return playerName;
    }
}
