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
import com.mygdx.game.controller.SortScore;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.User;

import java.util.ArrayList;

public class ScoreboardView implements Screen {
    SpriteBatch batch;
    final Pashmak game;
    OrthographicCamera camera;
    BitmapFont text;
    Texture backButton;
    User user;
    boolean isMute;
    Texture mute;
    Texture unmute;
    Music music;

    public ScoreboardView(Pashmak game, User user, boolean isMute) {
        this.isMute = isMute;
        this.user = user;
        this.game = game;
        text = new BitmapFont(Gdx.files.internal("times.fnt"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        backButton = new Texture("back.png");
        mute = new Texture("mute.png");
        unmute = new Texture("unmute.png");
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
        batch.end();

        batch.begin();
        text.draw(batch, "Scoreboard : ", 200, 700);
        ArrayList<Score> highScores = Score.allScores;
        highScores.sort(new SortScore());
        Score.sortAllScores(highScores);
        if (highScores.size() > 10) {
            int i = 1, j = 0;
            for (int counter = highScores.size() - 1; counter >= highScores.size() - 10; counter--) {
                text.draw(batch, i + "- " + highScores.get(counter).getOwner().getUsername() + " - score : " + highScores.get(counter).getScore(), 200, 650 - 50 * j);
                if (highScores.get(counter).getScore() != highScores.get(counter - 1).getScore()) {
                    i++;
                }
                j++;
            }
        } else {
            if (highScores.size() > 0) {
                int i = 1, j = 0;
                for (int counter = highScores.size() - 1; counter >= 0; counter--) {
                    text.draw(batch, i + "- " + highScores.get(counter).getOwner().getUsername() + " - score : " + highScores.get(counter).getScore(), 200, 650 - 50 * j);
                    if (counter - 1 >= 0) {
                        if (highScores.get(counter).getScore() != highScores.get(counter - 1).getScore()) {
                            i++;
                        }
                    }
                    j++;
                }
            }
        }
        batch.end();


        if (Gdx.input.justTouched()) {
            if (Gdx.input.getY() > 790 - backButton.getHeight() && Gdx.input.getY() < 790) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    game.setScreen(new MainMenuView(game, user, isMute));
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
