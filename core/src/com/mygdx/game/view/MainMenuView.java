package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Pashmak;
import com.mygdx.game.model.User;

public class MainMenuView implements Screen {
    SpriteBatch batch;
    final Pashmak game;
    OrthographicCamera camera;
    BitmapFont text;
    Texture mute;
    Texture unmute;
    boolean isMute = false;
    Texture play;
    Music music;
    User currentLoggedInUser;

    public MainMenuView(Pashmak game, User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
        this.game = game;
        text = new BitmapFont();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        mute = new Texture("mute.png");
        unmute = new Texture("unmute.png");
        play = new Texture("play.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
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
        batch.draw(play, 300, 350, play.getWidth(), play.getHeight());
        batch.end();

        batch.begin();
        text.draw(batch, "main menu\nusername : " + currentLoggedInUser.getUsername() + "\nyour high score : " + currentLoggedInUser.getHighScore(), 200, 700);
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

            if (Gdx.input.getX() > 300 && Gdx.input.getX() < 300 + play.getWidth()
                    && Gdx.input.getY() < 450 && Gdx.input.getY() > 450 - play.getHeight()) {
                game.setScreen(new GamePlayView(game, currentLoggedInUser));
                dispose();
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
