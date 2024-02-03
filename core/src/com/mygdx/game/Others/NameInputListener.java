package com.mygdx.game.Others;

import com.badlogic.gdx.Input;

public class NameInputListener implements Input.TextInputListener {
    @Override
    public void input(String text) {
        System.out.println("Nombre ingresado: " + text);
    }

    @Override
    public void canceled() {
        System.out.println("Entrada de texto cancelada");
    }
}
