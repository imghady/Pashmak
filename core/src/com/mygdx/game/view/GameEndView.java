package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
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
import com.mygdx.game.model.Score;
import com.mygdx.game.model.User;

import java.io.IOException;

public class GameEndView implements Screen {
    int score;
    SpriteBatch batch;
    final Pashmak game;
    OrthographicCamera camera;
    BitmapFont text;
    Texture backButton;
    User user;
    boolean isNewHighScore = false;
    boolean isFirstTime = true;
    boolean isMute;
    Texture mute;
    Texture unmute;
    Music music;
    Music fail;
    Music khastenabash;
    boolean isFirstTimeForEffect = true;

    public GameEndView(Pashmak game, int score, User user, boolean isMute) {
        this.isMute = isMute;
        this.user = user;
        this.score = score;
        this.game = game;
        text = new BitmapFont(Gdx.files.internal("times.fnt"));
        text.setColor(Color.RED);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        backButton = new Texture("back.png");
        mute = new Texture("mute.png");
        unmute = new Texture("unmute.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        fail = Gdx.audio.newMusic(Gdx.files.internal("failed.mp3"));
        khastenabash = Gdx.audio.newMusic(Gdx.files.internal("khastenabashi.mp3"));
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
        text.draw(batch, "you die :(\nyour score : " + score, 300, 500);
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
            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 70 && Gdx.input.getY() > 70 - mute.getHeight()) {
                isMute = !isMute;
            }
        }

        if (user == null) {
            if (isFirstTimeForEffect && !isMute) {
                fail.play();
                isFirstTimeForEffect = false;
            }
        } else {
            if (user.isNewHighScore(score)) {
                isNewHighScore = true;
                user.setHighScore(score);
                try {
                    Finisher.finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isFirstTime) {
                new Score(score, user.getUsername());
                isFirstTime = false;
                try {
                    Finisher.writeScores();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (isNewHighScore) {
            if (isFirstTimeForEffect && !isMute) {
                khastenabash.play();
                isFirstTimeForEffect = false;
            }
            batch.begin();
            text.setColor(Color.GREEN);
            text.draw(batch, "you have new high score!", 300, 400);
            batch.end();
        } else {
            if (isFirstTimeForEffect && !isMute) {
                fail.play();
                isFirstTimeForEffect = false;
            }
        }

        if (Gdx.input.isTouched()) {
            if (Gdx.input.getY() > 790 - backButton.getHeight() && Gdx.input.getY() < 790) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    if (user == null) {
                        music.pause();
                        game.setScreen(new WelcomeMenuView(game));
                    } else {
                        music.pause();
                        game.setScreen(new MainMenuView(game, user, isMute));
                    }
                    dispose();
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
}
