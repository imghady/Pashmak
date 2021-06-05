package com.mygdx.game.view;

import java.time.LocalTime;
import java.util.Iterator;
import java.lang.Math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Pashmak;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.model.User;

public class GamePlayView implements Screen {

    SpriteBatch batch;
    Texture pacmanImage;
    private OrthographicCamera camera;
    User currentLoggedInUser;
    Rectangle pacman;
    BitmapFont text;
    Pashmak game;
    Texture brick;
    Array<Rectangle> bricks;
    Texture banana;
    Array<Rectangle> bananas;
    int score = 0;
    int health = 4;
    Texture ghost1Image;
    Rectangle ghost1;
    Texture ghost2Image;
    Rectangle ghost2;
    Texture ghost3Image;
    Rectangle ghost3;
    Texture ghost4Image;
    Rectangle ghost4;
    private State state = State.RUN;
    long time = System.currentTimeMillis();
    int side1 = 1;
    int side2 = 2;
    int side3 = 3;
    int side4 = 0;

    Texture stop;
    Texture play;
    Texture sadGhost;
    boolean isHyperMode = false;

    int[][] map1 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 1, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 1, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};


    int[][] map = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 1, 0, 1, 1, 0, 0},
            {0, 0, 1, 0, 1, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0, 0, 0},
            {1, 1, 1, 0, 1, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};


    public GamePlayView(Pashmak pashmak, User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
        text = new BitmapFont(Gdx.files.internal("times.fnt"));
        pacman = new Rectangle();
        pacman.x = 800 / 2 - 64 / 2 + 40;
        pacman.y = 800 / 2 - 64 / 2;
        pacman.width = 64;
        pacman.height = 64;
        ghost1 = new Rectangle();
        ghost1.x = 10;
        ghost1.y = 180;
        ghost1.width = 64;
        ghost1.height = 64;
        ghost2 = new Rectangle();
        ghost2.x = 10;
        ghost2.y = 570;
        ghost2.width = 64;
        ghost2.height = 64;
        ghost3 = new Rectangle();
        ghost3.x = 730;
        ghost3.y = 180;
        ghost3.width = 64;
        ghost3.height = 64;
        ghost4 = new Rectangle();
        ghost4.x = 730;
        ghost4.y = 570;
        ghost4.width = 64;
        ghost4.height = 64;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        pacmanImage = new Texture("droplet.png");
        game = pashmak;
        brick = new Texture("brick.png");
        bricks = new Array<Rectangle>();
        bananas = new Array<Rectangle>();
        banana = new Texture("banana.png");
        ghost1Image = new Texture("ghost1.png");
        ghost2Image = new Texture("ghost2.png");
        ghost3Image = new Texture("ghost3.png");
        ghost4Image = new Texture("ghost4.png");
        stop = new Texture("stop.png");
        play = new Texture("play.png");
        sadGhost = new Texture("sadghost.png");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        switch (state) {
            case RUN:

                if (isHyperMode) {

                } else {
                    initial();
                    printMap();
                    printGhosts();
                    ghostsDirection();
                    ghost1Move();
                    ghost2Move();
                    ghost3Move();
                    ghost4Move();
                    int pacmanDirection = 0;
                    pacmanDirection = wallBlock(pacmanDirection);
                    pacmanMove(pacmanDirection);
                    setEdges();
                    increaseScore();
                    checkForPause();
                    checkForEndAndHurt();
                }
                break;

            case PAUSE:
                pauseHandle();
                break;
            case RESUME:

                break;

            default:
                break;
        }

    }

    private void checkForPause() {
        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() > 720 && Gdx.input.getX() < 720 + stop.getWidth()
                    && Gdx.input.getY() < 70 && Gdx.input.getY() > 70 - stop.getHeight()) {
                setState(State.PAUSE);
            }
        }
    }

    private void checkForEndAndHurt() {
        if (pacman.overlaps(ghost1) || pacman.overlaps(ghost2) || pacman.overlaps(ghost3) || pacman.overlaps(ghost4)) {
            setState(State.PAUSE);
            ghost1.setPosition(10, 180);
            ghost2.setPosition(10, 570);
            ghost3.setPosition(730, 180);
            ghost4.setPosition(730, 570);
            health--;
        }
        if (health == 0) {
            game.setScreen(new GameEndView(game, score, currentLoggedInUser));
            dispose();
        }
    }

    private void pauseHandle() {
        batch.begin();
        batch.draw(play, 300, 350, play.getWidth(), play.getHeight());
        batch.end();
        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() > 300 && Gdx.input.getX() < 300 + play.getWidth()
                    && Gdx.input.getY() < 450 && Gdx.input.getY() > 450 - play.getHeight()) {
                setState(State.RUN);
            }
        }
    }

    private void initial() {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        camera.update();
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.end();
        batch.begin();
        pacman.width = 64;
        pacman.height = 64;
        batch.draw(pacmanImage, pacman.x, pacman.y);
        batch.end();
        batch.begin();
        text.draw(batch, "score : " + score + "\nhealth : " + health, 20, 790);
        batch.end();

        batch.begin();
        batch.draw(stop, 720, 730, stop.getWidth(), stop.getHeight());
        batch.end();
    }

    private void ghostsDirection() {
        long timeNow = System.currentTimeMillis();
        if (timeNow - time > 1000) {
            side1 = MathUtils.random(0, 3);
            side2 = MathUtils.random(0, 3);
            side3 = MathUtils.random(0, 3);
            side4 = MathUtils.random(0, 3);
            time = System.currentTimeMillis();
        }
    }

    private void ghost4Move() {
        int ghost4Direction = 0;
        for (Rectangle wall : bricks) {
            if (wall.overlaps(ghost4)) {
                if (Math.abs(ghost4.x - wall.x) < 50) {
                    if (ghost4.y > wall.y) {
                        ghost4Direction = 3;
                    }
                    if (ghost4.y < wall.y) {
                        ghost4Direction = 1;
                    }
                }
                if (Math.abs(ghost4.y - wall.y) < 50) {
                    if (ghost4.x > wall.x) {
                        ghost4Direction = 2;
                    } else {
                        ghost4Direction = 4;
                    }
                }
            }
        }
        if (side4 == 0 && ghost4Direction != 2) ghost4.x -= 200 * Gdx.graphics.getDeltaTime();
        if (side4 == 1 && ghost4Direction != 4) ghost4.x += 200 * Gdx.graphics.getDeltaTime();
        if (side4 == 2 && ghost4Direction != 3) ghost4.y -= 200 * Gdx.graphics.getDeltaTime();
        if (side4 == 3 && ghost4Direction != 1) ghost4.y += 200 * Gdx.graphics.getDeltaTime();
    }

    private void ghost3Move() {
        int ghost3Direction = 0;
        for (Rectangle wall : bricks) {
            if (wall.overlaps(ghost3)) {
                if (Math.abs(ghost3.x - wall.x) < 50) {
                    if (ghost3.y > wall.y) {
                        ghost3Direction = 3;

                    }
                    if (ghost3.y < wall.y) {
                        ghost3Direction = 1;
                    }
                }
                if (Math.abs(ghost3.y - wall.y) < 50) {
                    if (ghost3.x > wall.x) {
                        ghost3Direction = 2;

                    } else {
                        ghost3Direction = 4;
                    }
                }
            }
        }
        if (side3 == 0 && ghost3Direction != 2) ghost3.x -= 200 * Gdx.graphics.getDeltaTime();
        if (side3 == 1 && ghost3Direction != 4) ghost3.x += 200 * Gdx.graphics.getDeltaTime();
        if (side3 == 2 && ghost3Direction != 3) ghost3.y -= 200 * Gdx.graphics.getDeltaTime();
        if (side3 == 3 && ghost3Direction != 1) ghost3.y += 200 * Gdx.graphics.getDeltaTime();
    }

    private void ghost2Move() {
        int ghost2Direction = 0;
        for (Rectangle wall : bricks) {
            if (wall.overlaps(ghost2)) {
                if (Math.abs(ghost2.x - wall.x) < 50) {
                    if (ghost2.y > wall.y) {
                        ghost2Direction = 3;

                    }
                    if (ghost2.y < wall.y) {
                        ghost2Direction = 1;
                    }
                }
                if (Math.abs(ghost2.y - wall.y) < 50) {
                    if (ghost2.x > wall.x) {
                        ghost2Direction = 2;

                    } else {
                        ghost2Direction = 4;
                    }
                }
            }
        }
        if (side2 == 0 && ghost2Direction != 2) ghost2.x -= 200 * Gdx.graphics.getDeltaTime();
        if (side2 == 1 && ghost2Direction != 4) ghost2.x += 200 * Gdx.graphics.getDeltaTime();
        if (side2 == 2 && ghost2Direction != 3) ghost2.y -= 200 * Gdx.graphics.getDeltaTime();
        if (side2 == 3 && ghost2Direction != 1) ghost2.y += 200 * Gdx.graphics.getDeltaTime();
    }

    private void ghost1Move() {
        int ghost1Direction = 0;
        for (Rectangle wall : bricks) {
            if (wall.overlaps(ghost1)) {
                if (Math.abs(ghost1.x - wall.x) < 50) {
                    if (ghost1.y > wall.y) {
                        ghost1Direction = 3;

                    }
                    if (ghost1.y < wall.y) {
                        ghost1Direction = 1;
                    }
                }
                if (Math.abs(ghost1.y - wall.y) < 50) {
                    if (ghost1.x > wall.x) {
                        ghost1Direction = 2;

                    } else {
                        ghost1Direction = 4;
                    }
                }
            }
        }
        if (side1 == 0 && ghost1Direction != 2) ghost1.x -= 200 * Gdx.graphics.getDeltaTime();
        if (side1 == 1 && ghost1Direction != 4) ghost1.x += 200 * Gdx.graphics.getDeltaTime();
        if (side1 == 2 && ghost1Direction != 3) ghost1.y -= 200 * Gdx.graphics.getDeltaTime();
        if (side1 == 3 && ghost1Direction != 1) ghost1.y += 200 * Gdx.graphics.getDeltaTime();
    }

    private void printGhosts() {
        batch.begin();
        batch.draw(ghost1Image, ghost1.x, ghost1.y);
        batch.end();
        batch.begin();
        batch.draw(ghost2Image, ghost2.x, ghost2.y);
        batch.end();
        batch.begin();
        batch.draw(ghost3Image, ghost3.x, ghost3.y);
        batch.end();
        batch.begin();
        batch.draw(ghost4Image, ghost4.x, ghost4.y);
        batch.end();
    }

    private void setEdges() {
        if (pacman.x < 0) pacman.x = 0;
        if (pacman.x > 800 - 64) pacman.x = 800 - 64;
        if (pacman.y < 80) pacman.y = 80;
        if (pacman.y > 800 - 160) pacman.y = 800 - 160;

        if (ghost1.x <= 0) ghost1.x = 0;
        if (ghost1.x > 800 - 64) ghost1.x = 800 - 64;
        if (ghost1.y < 80) ghost1.y = 80;
        if (ghost1.y > 800 - 160) ghost1.y = 800 - 160;

        if (ghost2.x < 0) ghost2.x = 0;
        if (ghost2.x > 800 - 64) ghost2.x = 800 - 64;
        if (ghost2.y <= 80) ghost2.y = 80;
        if (ghost2.y > 800 - 160) ghost2.y = 800 - 160;

        if (ghost3.x < 0) ghost3.x = 0;
        if (ghost3.x > 800 - 64) ghost3.x = 800 - 64;
        if (ghost3.y < 80) ghost3.y = 80;
        if (ghost3.y >= 800 - 160) ghost3.y = 800 - 160;

        if (ghost4.x <= 0) ghost4.x = 0;
        if (ghost4.x > 800 - 64) ghost4.x = 800 - 64;
        if (ghost4.y < 80) ghost4.y = 80;
        if (ghost4.y >= 800 - 160) ghost4.y = 800 - 160;
    }

    private void increaseScore() {
        for (Rectangle banana1 : bananas) {
            if (banana1.overlaps(pacman)) {
                float banana1X = banana1.x;
                float banana1Y = banana1.y;
                int row = (int) ((banana1Y - 50) / 80);
                int column = (int) ((banana1X + 15) / 80);
                if (map[row][column] == 0) {
                    map[row][column] = 2;
                    score += 5;
                }
            }
        }
    }

    private int wallBlock(int direction) {
        for (Rectangle wall : bricks) {
            if (wall.overlaps(pacman)) {
                if (Math.abs(pacman.x - wall.x) < 40) {
                    if (pacman.y > wall.y) {
                        direction = 3;

                    }
                    if (pacman.y < wall.y) {
                        direction = 1;
                    }
                }
                if (Math.abs(pacman.y - wall.y) < 40) {
                    if (pacman.x > wall.x) {
                        direction = 2;

                    } else {
                        direction = 4;
                    }
                }
            }
        }
        return direction;
    }

    private void pacmanMove(int direction) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && direction != 2) {
            pacman.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && direction != 4) {
            pacman.x += 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && direction != 3) {
            pacman.y -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && direction != 1) {
            pacman.y += 200 * Gdx.graphics.getDeltaTime();
        }
    }

    private void printMap() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 10; column++) {
                if (map[row][column] == 1) {
                    batch.begin();
                    batch.draw(brick, column * 80, row * 80 + 80, 80, 80);
                    Rectangle aBrick = new Rectangle();
                    aBrick.x = column * 80;
                    aBrick.y = row * 80 + 80;
                    aBrick.height = 80;
                    aBrick.width = 80;
                    bricks.add(aBrick);
                    batch.end();
                } else if (map[row][column] == 0) {
                    batch.begin();
                    batch.draw(banana, column * 80 + 15, row * 80 + 100, 48, 48);
                    Rectangle aBanana = new Rectangle();
                    aBanana.x = column * 80;
                    aBanana.y = row * 80 + 80;
                    aBanana.height = 20;
                    aBanana.width = 20;
                    bananas.add(aBanana);
                    batch.end();
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        this.state = State.PAUSE;
    }

    @Override
    public void resume() {
        this.state = State.RESUME;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        pacmanImage.dispose();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
