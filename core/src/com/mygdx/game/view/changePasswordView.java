package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Pashmak;
import com.mygdx.game.controller.Finisher;
import com.mygdx.game.model.User;

import java.io.IOException;

public class changePasswordView implements Screen, Input.TextInputListener {
    SpriteBatch batch;
    final Pashmak game;
    OrthographicCamera camera;
    BitmapFont text;
    Texture mute;
    Texture unmute;
    boolean isMute = false;
    User currentLoggedInUser;
    Texture backButton;
    Texture changePassword;
    String holder;
    boolean isChanged = false;
    Texture newPass;
    boolean isNull = false;
    Music music;


    public changePasswordView(Pashmak game, User currentLoggedInUser, boolean isMute) {
        this.isMute = isMute;
        this.currentLoggedInUser = currentLoggedInUser;
        this.game = game;
        text = new BitmapFont(Gdx.files.internal("times.fnt"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        mute = new Texture("mute.png");
        unmute = new Texture("unmute.png");
        backButton = new Texture("back.png");
        changePassword = new Texture("change.png");
        newPass = new Texture("newpass.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
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
        batch.draw(changePassword, 230, 200, changePassword.getWidth(), changePassword.getHeight());
        batch.draw(newPass, 270, 300, newPass.getWidth(), newPass.getHeight());
        text.draw(batch, "tap to change your password", 230, 500);


        if (isChanged) {
            text.setColor(Color.GREEN);
            text.draw(batch, "your password changed successfully.", 230, 400);
        }
        if (isNull) {
            text.setColor(Color.RED);
            text.draw(batch, "please enter a new password.", 230, 400);
        }
        batch.end();

        if (isMute) {
            batch.begin();
            batch.draw(mute, 10, 730, mute.getWidth(), mute.getHeight());
            music.pause();
            batch.end();
        } else {
            batch.begin();
            batch.draw(unmute, 10, 730, unmute.getWidth(), unmute.getHeight());
            music.play();
            batch.end();
        }

        if (Gdx.input.justTouched()) {
            if (Gdx.input.getY() > 790 - backButton.getHeight() && Gdx.input.getY() < 790) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    music.pause();
                    game.setScreen(new MainMenuView(game, currentLoggedInUser, isMute));
                    dispose();
                }
            }

            if (Gdx.input.getX() > 270 && Gdx.input.getX() < 270 + newPass.getWidth()
                    && Gdx.input.getY() < 500 && Gdx.input.getY() > 500 - newPass.getHeight()) {
                Gdx.input.getTextInput(this, "enter new password", "", "");
            }

            if (Gdx.input.getX() > 230 && Gdx.input.getX() < 230 + changePassword.getWidth()
                    && Gdx.input.getY() < 600 && Gdx.input.getY() > 600 - changePassword.getHeight()) {
                if (holder == null || holder.equals("")) {
                    isNull = true;
                    isChanged = false;
                } else {
                    currentLoggedInUser.setPassword(holder);
                    try {
                        Finisher.finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isChanged = true;
                    isNull = false;
                }
            }


            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 70 && Gdx.input.getY() > 70 - mute.getHeight()) {
                isMute = !isMute;
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
    }

    @Override
    public void canceled() {

    }
}
