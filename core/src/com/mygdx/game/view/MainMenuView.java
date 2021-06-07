package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Pashmak;
import com.mygdx.game.model.Map;
import com.mygdx.game.model.User;

public class MainMenuView implements Screen, Input.TextInputListener {
    SpriteBatch batch;
    final Pashmak game;
    OrthographicCamera camera;
    BitmapFont text;
    Texture mute;
    Texture unmute;
    boolean isMute;
    Texture play;
    Texture scoreboard;
    Music music;
    User currentLoggedInUser;
    Texture logout;
    Texture deleteAccount;
    String holder = "";
    boolean isPrintDeleteAccount = false;
    Texture changePassword;
    Texture bin;

    public MainMenuView(Pashmak game, User currentLoggedInUser, boolean isMute) {
        this.isMute = isMute;
        this.currentLoggedInUser = currentLoggedInUser;
        this.game = game;
        text = new BitmapFont(Gdx.files.internal("times.fnt"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        mute = new Texture("mute.png");
        unmute = new Texture("unmute.png");
        play = new Texture("play.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        scoreboard = new Texture("scoreboard.png");
        logout = new Texture("logout.png");
        deleteAccount = new Texture("deleteaccount.png");
        changePassword = new Texture("change.png");
        bin = new Texture("bin.png");
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
        batch.draw(scoreboard, 215, 230, (float) (scoreboard.getWidth() * 0.8), (float) (scoreboard.getHeight() * 0.8));
        batch.draw(logout, 0, 0, logout.getWidth(), logout.getHeight());
        batch.draw(deleteAccount, 500, 0, deleteAccount.getWidth(), deleteAccount.getHeight());
        batch.draw(changePassword, 230, 150, changePassword.getWidth(), changePassword.getHeight());
        batch.end();

        batch.begin();
        text.draw(batch, "main menu\nusername : " + currentLoggedInUser.getUsername() + "\nyour high score : " + currentLoggedInUser.getHighScore(), 200, 700);

        if (isPrintDeleteAccount) {
            text.draw(batch, "tap again to delete!", 430, 100);
            batch.draw(bin, 700, 50, bin.getWidth(), bin.getHeight());
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
            if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + mute.getWidth()
                    && Gdx.input.getY() < 70 && Gdx.input.getY() > 70 - mute.getHeight()) {
                isMute = !isMute;
            }

            if (Gdx.input.getX() > 300 && Gdx.input.getX() < 300 + play.getWidth()
                    && Gdx.input.getY() < 450 && Gdx.input.getY() > 450 - play.getHeight()) {
                music.pause();
                game.setScreen(new StartGameView(game, currentLoggedInUser, isMute));
                dispose();
            }

            if (Gdx.input.getX() > 215 && Gdx.input.getX() < 215 + scoreboard.getWidth()
                    && Gdx.input.getY() < 570 && Gdx.input.getY() > 570 - scoreboard.getHeight()) {
                music.pause();
                game.setScreen(new ScoreboardView(game, currentLoggedInUser, isMute));
                dispose();
            }

            if (Gdx.input.getX() > 230 && Gdx.input.getX() < 230 + changePassword.getWidth()
                    && Gdx.input.getY() < 650 && Gdx.input.getY() > 650 - changePassword.getHeight()) {
                music.pause();
                game.setScreen(new changePasswordView(game, currentLoggedInUser, isMute));
                dispose();
            }

            if (Gdx.input.getX() > 700 && Gdx.input.getX() < 700 + bin.getWidth()
                    && Gdx.input.getY() < 750 && Gdx.input.getY() > 750 - bin.getHeight()) {
                if (holder.equals(currentLoggedInUser.getPassword())) {
                    User.deleteUser(currentLoggedInUser);
                    User.allUsers.remove(currentLoggedInUser);
                    music.pause();
                    game.setScreen(new WelcomeMenuView(game));
                    dispose();
                } else {
                    isPrintDeleteAccount = false;
                }
            }

            if (Gdx.input.getX() > 0 && Gdx.input.getX() < logout.getWidth()
                    && Gdx.input.getY() < 800 && Gdx.input.getY() > 800 - logout.getHeight()) {
                music.pause();
                game.setScreen(new WelcomeMenuView(game));
                dispose();
            }

            if (Gdx.input.getX() > 500 && Gdx.input.getX() < 500 + deleteAccount.getWidth()
                    && Gdx.input.getY() < 800 && Gdx.input.getY() > 800 - deleteAccount.getHeight()) {
                Gdx.input.getTextInput(this, "enter your password", "", "");
                isPrintDeleteAccount = true;

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
