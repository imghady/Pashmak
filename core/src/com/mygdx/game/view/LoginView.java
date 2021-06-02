package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Pashmak;
import com.mygdx.game.controller.MyTextInputListener;
import com.mygdx.game.model.User;

import java.io.IOException;

public class LoginView implements Screen, Input.TextInputListener {

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
    Texture login;
    int message = 0;

    public LoginView(Pashmak game) {
        this.game = game;
        text = new BitmapFont(Gdx.files.internal("times.fnt"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        backButton = new Texture("back.png");
        registerButtons = new Texture("registerButtons.png");
        login = new Texture("login.png");
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
        text.draw(batch, "Login menu\ncomplete fields to login:", 200, 700);
        batch.end();

        batch.begin();
        batch.draw(registerButtons, 200, 400, registerButtons.getWidth(), registerButtons.getHeight());
        batch.end();

        batch.begin();
        batch.draw(login, 200, 250, login.getWidth(), login.getHeight());
        text.draw(batch, username + "\n\n" + password, 350, 525);
        batch.end();

        if (message == 1) {
            batch.begin();
            text.draw(batch, "please complete fields.", 200, 380);
            batch.end();
        } else if (message == 2) {
            batch.begin();
            text.setColor(Color.RED);
            text.draw(batch, "username : " + username + " does not exist!", 200, 380);
            batch.end();
        } else if (message == 3) {
            batch.begin();
            text.setColor(Color.GREEN);
            text.draw(batch, "login successfully.", 200, 380);
            batch.end();
        } else if (message == 4) {
            batch.begin();
            text.setColor(Color.RED);
            text.draw(batch, "password is incorrect!", 200, 380);
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
                        System.out.println(holder);
                    }
                    if (Gdx.input.getY() > 400 - registerButtons.getHeight() / 2 && Gdx.input.getY() < 400) {
                        isHolderPassword = true;
                        Gdx.input.getTextInput(this, "password", "", "");
                        System.out.println(holder);
                    }
                }

                if (Gdx.input.getY() > 550 - login.getHeight() && Gdx.input.getY() < 550) {
                    if (Gdx.input.getX() > 200 && Gdx.input.getX() < 200 + login.getWidth()) {
                        if (username != null && password != null) {
                            if (User.getUserByUsername(username) == null) {
                                message = 2;
                            } else {
                                if (User.isPasswordCorrect(username, password)) {
                                    message = 3;
                                    game.setScreen(new MainMenuView(game, User.getUserByUsername(username)));
                                    dispose();
                                } else {
                                    message = 4;
                                }
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
