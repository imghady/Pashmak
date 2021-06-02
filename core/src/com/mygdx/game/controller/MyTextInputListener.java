package com.mygdx.game.controller;

import com.badlogic.gdx.Input;

public class MyTextInputListener implements Input.TextInputListener {

    public String holder;

    @Override
    public void input (String text) {
        this.holder = text;
    }

    @Override
    public void canceled () {
    }
}
