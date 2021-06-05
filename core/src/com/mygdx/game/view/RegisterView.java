package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Pashmak;
import com.mygdx.game.model.User;

import java.io.IOException;

public class RegisterView implements Screen, Input.TextInputListener {
    SpriteBatch batch;
    final Pashmak game;
    OrthographicCamera camera;
    BitmapFont text;
    Texture backButton;
    Texture registerButtons;
    String username;
    String password;
    String holder;
    boolean isHolderUsername = false;
    boolean isHolderPassword = false;
    Texture register;
    int message = 0;
    boolean isFirstTime = true;

    public RegisterView(Pashmak game) {
        this.game = game;
        text = new BitmapFont(Gdx.files.internal("times.fnt"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        backButton = new Texture("back.png");
        registerButtons = new Texture("registerButtons.png");
        register = new Texture("register.png");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.end();


        batch.begin();
        text.draw(batch, "Register Menu\ncomplete fields to register", 200, 700);
        text.draw(batch, username + "\n\n" + password, 300, 500);
        batch.end();

        batch.begin();
        batch.draw(registerButtons, 200, 400, registerButtons.getWidth(), registerButtons.getHeight());
        batch.end();

        batch.begin();
        batch.draw(register, 250, 250, register.getWidth(), register.getHeight());
        text.draw(batch, username + "\n\n" + password, 350, 525);
        batch.end();


        if (message == 1) {
            batch.begin();
            text.draw(batch, "please complete fields.", 200, 380);
            batch.end();
        } else if (message == 2) {
            try {
                if (isFirstTime) {
                    new User(username, password);
                    isFirstTime = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            batch.begin();
            text.setColor(Color.GREEN);
            text.draw(batch, "register successfully\nwelcome " + username, 200, 380);
            batch.end();
        } else if (message == 3) {
            batch.begin();
            text.setColor(Color.RED);
            text.draw(batch, "username : " + username + " is already exists!", 200, 380);
            batch.end();
        }


        if (Gdx.input.isTouched()) {
            if (Gdx.input.getY() > 790 - backButton.getHeight() && Gdx.input.getY() < 790) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    game.setScreen(new WelcomeMenuView(game));
                    dispose();
                }
            }

            if (Gdx.input.justTouched()) {
                if (Gdx.input.getX() > 200 && Gdx.input.getX() < 200 + registerButtons.getWidth()) {
                    if (Gdx.input.getY() > 400 - registerButtons.getHeight() && Gdx.input.getY() < 400 - registerButtons.getHeight() / 2) {
                        isHolderUsername = true;
                        Gdx.input.getTextInput(this, "username", "", "");
                    }
                    if (Gdx.input.getY() > 400 - registerButtons.getHeight() / 2 && Gdx.input.getY() < 400) {
                        isHolderPassword = true;
                        Gdx.input.getTextInput(this, "password", "", "");
                    }
                }

                if (Gdx.input.getY() > 550 - register.getHeight() && Gdx.input.getY() < 550) {
                    if (Gdx.input.getX() > 250 && Gdx.input.getX() < 250 + register.getWidth()) {
                        if (username != null && password != null) {
                            if (User.getUserByUsername(username) == null) {
                                message = 2;
                            } else {
                                message = 3;
                            }
                        } else {
                            message = 1;
                        }
                    }
                }
            }


        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void input(String text) {
        this.holder = text;

        if (isHolderUsername) {
            username = holder;
            isHolderUsername = false;
        }

        if (isHolderPassword) {
            password = holder;
            isHolderPassword = false;
        }
    }

    @Override
    public void canceled() {

    }
}
