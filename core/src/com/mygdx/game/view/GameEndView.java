package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Pashmak;
import com.mygdx.game.model.User;

public class GameEndView implements Screen {
    int score;
    SpriteBatch batch;
    final Pashmak game;
    OrthographicCamera camera;
    BitmapFont text;
    Texture backButton;
    User user;
    boolean isNewHighScore = false;

    public GameEndView(Pashmak game, int score, User user) {
        this.user = user;
        this.score = score;
        this.game = game;
        text = new BitmapFont();
        text.setColor(Color.RED);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        backButton = new Texture("back.png");
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

        if (user == null) {

        } else {
            if (user.isNewHighScore(score)) {
                isNewHighScore = true;
            }
        }

        if (isNewHighScore) {
            batch.begin();
            text.setColor(Color.GREEN);
            text.draw(batch, "you have new high score!", 300, 400);
            batch.end();
        }

        if (Gdx.input.isTouched()) {
            if (Gdx.input.getY() > 790 - backButton.getHeight() && Gdx.input.getY() < 790) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    if (user == null) {
                        game.setScreen(new WelcomeMenuView(game));
                        dispose();
                    } else {
                        game.setScreen(new MainMenuView(game, user));
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
