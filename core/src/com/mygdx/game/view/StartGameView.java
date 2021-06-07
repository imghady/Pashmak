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
import com.mygdx.game.model.Map;
import com.mygdx.game.model.User;

public class StartGameView  implements Screen, Input.TextInputListener {
    SpriteBatch batch;
    final Pashmak game;
    OrthographicCamera camera;
    BitmapFont text;
    Texture mute;
    Texture unmute;
    boolean isMute;
    Music music;
    int numberOfHealth = 4;
    String holder;
    Texture maps;
    Texture backButton;
    User user;
    Texture health;
    int message = 0;
    boolean isHardMode = false;
    Texture lastGame;
    Texture regular;
    Texture hard;

    public StartGameView(Pashmak pashmak, User currentLoggedInUser, boolean isMute) {
        this.user = currentLoggedInUser;
        this.game = pashmak;
        text = new BitmapFont(Gdx.files.internal("times.fnt"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        mute = new Texture("mute.png");
        unmute = new Texture("unmute.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        maps = new Texture("maps.png");
        backButton = new Texture("back.png");
        health = new Texture("health.png");
        lastGame = new Texture("lastgame.png");
        hard = new Texture("hard.png");
        regular = new Texture("regular.png");
    }


    @Override
    public void input(String text) {
        this.holder = text;

        numberOfHealth = Integer.parseInt(holder);
    }

    @Override
    public void canceled() {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

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

        if (isHardMode) {
            batch.begin();
            batch.draw(hard, 600, 10, hard.getWidth(), hard.getHeight());
            batch.end();
        } else {
            batch.begin();
            batch.draw(regular, 600, 10, regular.getWidth(), regular.getHeight());
            batch.end();
        }

        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 70 && Gdx.input.getY() > 70 - mute.getHeight()) {
                isMute = !isMute;
            }
        }

        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() > 600 && Gdx.input.getX() < 600 + regular.getWidth()
                    && Gdx.input.getY() < 790 && Gdx.input.getY() > 790 - regular.getHeight()) {
                isHardMode = !isHardMode;
            }
        }

        batch.begin();
        batch.draw(maps, 100, 300, maps.getWidth(), maps.getHeight());
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
        batch.draw(health, 200, 500, health.getWidth(), health.getHeight());
        text.draw(batch, Integer.toString(numberOfHealth), 500, 550);
        batch.end();

        if (Gdx.input.justTouched()) {
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

        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() > 200 && Gdx.input.getX() < 200 + health.getWidth()
                    && Gdx.input.getY() < 300 && Gdx.input.getY() > 300 - health.getHeight()) {
                Gdx.input.getTextInput(this, "enter number of health", "", "");
            }
        }


        if (Gdx.input.justTouched()) {
            if (Gdx.input.getY() > 500 - maps.getHeight() && Gdx.input.getY() < 500) {
                if (numberOfHealth <= 0) {
                    message = 1;
                } else {
                    if (Gdx.input.getX() > 100 && Gdx.input.getX() < 250) {
                        music.pause();
                        game.setScreen(new GamePlayView(game, user, isMute, Map.map1, numberOfHealth, isHardMode, 0));
                        dispose();
                    } else if (Gdx.input.getX() > 250 && Gdx.input.getX() < 400) {
                        music.pause();
                        game.setScreen(new GamePlayView(game, user, isMute, Map.map2, numberOfHealth, isHardMode, 0));
                        dispose();
                    } else if (Gdx.input.getX() > 400 && Gdx.input.getX() < 550) {
                        music.pause();
                        game.setScreen(new GamePlayView(game, user, isMute, Map.map3, numberOfHealth, isHardMode, 0));
                        dispose();
                    } else if (Gdx.input.getX() > 550 && Gdx.input.getX() < 700) {
                        music.pause();
                        game.setScreen(new GamePlayView(game, user, isMute, Map.map4, numberOfHealth, isHardMode, 0));
                        dispose();
                    }
                }
            }
        }


        if (message == 1) {
            batch.begin();
            text.setColor(Color.RED);
            text.draw(batch, "please enter health number.", 200, 380);
            batch.end();
        }

        if (user != null) {
            if (user.getLastGameScore() > 0 && user.getLastGameHealth() > 0) {
                batch.begin();
                batch.draw(lastGame, 200, 600, lastGame.getWidth(), lastGame.getHeight());
                batch.end();

                if (Gdx.input.justTouched()) {
                    if (Gdx.input.getX() > 200 && Gdx.input.getX() < 200 + lastGame.getWidth()
                            && Gdx.input.getY() < 200 && Gdx.input.getY() > 200 - lastGame.getHeight()) {
                        music.pause();
                        game.setScreen(new GamePlayView(game, user, isMute, user.getLastGameMap(), user.getLastGameHealth(), isHardMode, user.getLastGameScore()));
                        dispose();
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
}
