package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Pashmak;
import com.mygdx.game.model.Map;

public class WelcomeMenuView extends Game implements Screen {

    SpriteBatch batch;
    final Pashmak game;
    OrthographicCamera camera;
    Texture registerAndLoginButton;
    BitmapFont text;
    Texture mute;
    Texture unmute;
    boolean isMute = false;
    Texture play;
    Music music;


    public WelcomeMenuView(final Pashmak pashmak) {
        game = pashmak;
        text = new BitmapFont(Gdx.files.internal("times.fnt"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        registerAndLoginButton = new Texture("login-register.png");
        mute = new Texture("mute.png");
        unmute = new Texture("unmute.png");
        play = new Texture("play.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

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
        text.draw(batch, "welcome to pashmak\nplease register or login to play", 200, 700);
        batch.end();

        batch.begin();
        text.draw(batch, "Developed by Amirreza Ghadyani", 20, 40);
        batch.end();

        batch.begin();
        batch.draw(registerAndLoginButton, 200, 150, 400, registerAndLoginButton.getHeight());
        batch.end();

        batch.begin();
        batch.draw(play, 300, 350, play.getWidth(), play.getHeight());
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

        if (Gdx.input.isTouched()) {
            if (Gdx.input.getY() > 600 && Gdx.input.getY() < 650) {
                if (Gdx.input.getX() > 200 && Gdx.input.getX() < 400) {
                    music.pause();
                    game.setScreen(new LoginView(game, isMute));
                    dispose();
                } else if (Gdx.input.getX() > 400 && Gdx.input.getX() < 600) {
                    music.pause();
                    game.setScreen(new RegisterView(game, isMute));
                    dispose();
                }

            }

            if (Gdx.input.justTouched()) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                        && Gdx.input.getY() < 70 && Gdx.input.getY() > 70 - mute.getHeight()) {
                    isMute = !isMute;
                }
            }

            if (Gdx.input.getX() > 300 && Gdx.input.getX() < 300 + play.getWidth()
                    && Gdx.input.getY() < 450 && Gdx.input.getY() > 450 - play.getHeight()) {
                music.pause();
                game.setScreen(new StartGameView(game, null, isMute));
                dispose();
            }

        }

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
