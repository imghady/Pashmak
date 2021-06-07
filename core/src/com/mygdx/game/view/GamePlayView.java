package com.mygdx.game.view;

import java.io.IOException;
import java.lang.Math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Pashmak;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.controller.Finisher;
import com.mygdx.game.model.Map;
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
    int health;
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
    long hyperModeStartTime;
    int side1 = 1;
    int side2 = 2;
    int side3 = 3;
    int side4 = 0;
    Sound bananaEat;
    Sound pineappleEat;
    Music scream;
    boolean isMute;
    Texture mute;
    Texture unmute;
    Music music;
    Music fail;
    Music khastenabash;
    Music nani;
    Texture stop;
    Texture play;
    Texture sadGhost;
    boolean isHyperMode = false;
    Texture pineapple;
    Array<Rectangle> pineapples;
    int ghostKillNumber = 1;
    int direction1 = 0;
    int direction2 = 0;
    int direction3 = 0;
    int direction4 = 0;
    boolean isHardMode = false;
    Texture backButton;


    int[][] map;
    int[][] mapHolder = new int[8][10];


    public GamePlayView(Pashmak pashmak, User currentLoggedInUser, boolean isMute, int[][] map, int health, boolean isHardMode, int score) {
        this.score = score;
        this.isHardMode = isHardMode;
        this.health = health;
        this.map = map;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                mapHolder[i][j] = map[i][j];
            }
        }
        this.currentLoggedInUser = currentLoggedInUser;
        this.isMute = isMute;
        text = new BitmapFont(Gdx.files.internal("times.fnt"));
        pacman = new Rectangle();
        pacman.x = 400 - 32 + 40;
        pacman.y = 400 - 10;
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
        bricks = new Array<>();
        bananas = new Array<>();
        pineapples = new Array<>();
        banana = new Texture("banana.png");
        ghost1Image = new Texture("ghost1.png");
        ghost2Image = new Texture("ghost2.png");
        ghost3Image = new Texture("ghost3.png");
        ghost4Image = new Texture("ghost4.png");
        stop = new Texture("stop.png");
        play = new Texture("play.png");
        sadGhost = new Texture("sadghost.png");
        pineapple = new Texture("pineapple.png");
        mute = new Texture("mute.png");
        unmute = new Texture("unmute.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        fail = Gdx.audio.newMusic(Gdx.files.internal("failed.mp3"));
        khastenabash = Gdx.audio.newMusic(Gdx.files.internal("khastenabashi.mp3"));
        nani = Gdx.audio.newMusic(Gdx.files.internal("nani.mp3"));
        bananaEat = Gdx.audio.newSound(Gdx.files.internal("bananaeat.wav"));
        pineappleEat = Gdx.audio.newSound(Gdx.files.internal("pineappleeat.wav"));
        scream = Gdx.audio.newMusic(Gdx.files.internal("scream.mp3"));
        backButton = new Texture("back.png");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        switch (state) {
            case RUN:

                if (isHyperMode) {

                    initial();
                    handleMusic();
                    printMap();
                    printGhostsHyperMode();
                    if (isHardMode) {
                        ghostsDirectionHardModeAndHyper();
                    } else {
                        ghostsDirection();
                    }
                    ghost1Move();
                    ghost2Move();
                    ghost3Move();
                    ghost4Move();
                    wallBlock();
                    pacmanMove();
                    setEdges();
                    increaseScore();
                    checkForPause();
                    checkForKillGhost();
                    checkForEndOfHyperMode();
                    saveGame();
                    handleBack();
                    handleEmptyMap();

                } else {

                    initial();
                    handleMusic();
                    printMap();
                    printGhosts();
                    if (isHardMode) {
                        ghostsDirectionHardMode();
                    } else {
                        ghostsDirection();
                    }
                    ghost1Move();
                    ghost2Move();
                    ghost3Move();
                    ghost4Move();
                    wallBlock();
                    pacmanMove();
                    setEdges();
                    increaseScore();
                    checkForPause();
                    checkForEndAndHurt();
                    saveGame();
                    handleEmptyMap();
                    handleBack();
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

    private void handleEmptyMap() {
        if (isMapEmpty()) {
            music.pause();
            game.setScreen(new GamePlayView(game, currentLoggedInUser, isMute, mapHolder, health + 1, isHardMode, score));
            dispose();
        }
    }

    private void handleBack() {
        if (Gdx.input.justTouched()) {
            if (Gdx.input.getY() > 790 - backButton.getHeight() && Gdx.input.getY() < 790) {
                if (Gdx.input.getX() > 10 && Gdx.input.getX() < 10 + backButton.getWidth()) {
                    if (currentLoggedInUser == null) {
                        music.pause();
                        game.setScreen(new WelcomeMenuView(game));
                    } else {
                        music.pause();
                        game.setScreen(new MainMenuView(game, currentLoggedInUser, isMute));
                    }
                    dispose();
                }
            }
        }
    }

    private void saveGame() {
        if (currentLoggedInUser != null) {
            currentLoggedInUser.setLastGameHealth(health);
            currentLoggedInUser.setLastGameScore(score);
            currentLoggedInUser.setLastGameMap(map);
            try {
                Finisher.finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isMapEmpty() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                if (map[i][j] == 7 || map[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void handleMusic() {
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
            pacman.x = 400 - 32 + 40;
            pacman.y = 400 - 10;
            health--;
            if (!isMute && health > 0) {
                nani.play();
            }
        }
        if (health == 0) {
            music.pause();
            game.setScreen(new GameEndView(game, score, currentLoggedInUser, isMute));
            dispose();
        }
    }

    private void checkForKillGhost() {
        if (pacman.overlaps(ghost1) || pacman.overlaps(ghost2) || pacman.overlaps(ghost3) || pacman.overlaps(ghost4)) {
            score += 200 * ghostKillNumber;
            if (!isMute) {
                scream.play();
            }
            setState(State.PAUSE);
            ghost1.setPosition(10, 180);
            ghost2.setPosition(11, 570);
            ghost3.setPosition(730, 180);
            ghost4.setPosition(730, 570);
            pacman.x = 400 - 32 + 40;
            pacman.y = 400 - 10;
            ghostKillNumber++;
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
        batch.draw(pacmanImage, pacman.x, pacman.y, 64, 64);
        batch.end();
        batch.begin();
        text.draw(batch, "score : " + score + "\nhealth : " + health, 100, 790);
        if (isHardMode) {
            text.setColor(Color.RED);
            text.draw(batch, "HARD MODE!" + health, 300, 790);
        }
        batch.end();

        batch.begin();
        batch.draw(stop, 720, 730, stop.getWidth(), stop.getHeight());
        batch.draw(backButton, 10, 10, backButton.getWidth(), backButton.getHeight());
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

    private void ghostsDirectionHardMode() {


        if (Math.abs(ghost1.x - pacman.x) < Math.abs(ghost1.y - pacman.y)) {
            if (pacman.y > ghost1.y) {
                side1 = 3;
            } else {
                side1 = 2;
            }
        } else {
            if (pacman.x > ghost1.x) {
                side1 = 1;
            } else {
                side1 = 0;
            }
        }

        if (Math.abs(ghost2.x - pacman.x) < Math.abs(ghost2.y - pacman.y)) {
            if (pacman.y > ghost2.y) {
                side2 = 3;
            } else {
                side2 = 2;
            }
        } else {
            if (pacman.x > ghost2.x) {
                side2 = 1;
            } else {
                side2 = 0;
            }
        }

        if (Math.abs(ghost3.x - pacman.x) < Math.abs(ghost3.y - pacman.y)) {
            if (pacman.y > ghost3.y) {
                side3 = 3;
            } else {
                side3 = 2;
            }
        } else {
            if (pacman.x > ghost3.x) {
                side3 = 1;
            } else {
                side3 = 0;
            }
        }

        if (Math.abs(ghost4.x - pacman.x) < Math.abs(ghost4.y - pacman.y)) {
            if (pacman.y > ghost4.y) {
                side4 = 3;
            } else {
                side4 = 2;
            }
        } else {
            if (pacman.x > ghost4.x) {
                side4 = 1;
            } else {
                side4 = 0;
            }
        }


    }

    private void ghostsDirectionHardModeAndHyper() {


        if (Math.abs(ghost1.x - pacman.x) < Math.abs(ghost1.y - pacman.y)) {
            if (pacman.y > ghost1.y) {
                side1 = 2;
            } else {
                side1 = 3;
            }
        } else {
            if (pacman.x > ghost1.x) {
                side1 = 0;
            } else {
                side1 = 1;
            }
        }

        if (Math.abs(ghost2.x - pacman.x) < Math.abs(ghost2.y - pacman.y)) {
            if (pacman.y > ghost2.y) {
                side2 = 2;
            } else {
                side2 = 3;
            }
        } else {
            if (pacman.x > ghost2.x) {
                side2 = 0;
            } else {
                side2 = 1;
            }
        }

        if (Math.abs(ghost3.x - pacman.x) < Math.abs(ghost3.y - pacman.y)) {
            if (pacman.y > ghost3.y) {
                side3 = 2;
            } else {
                side3 = 3;
            }
        } else {
            if (pacman.x > ghost3.x) {
                side3 = 0;
            } else {
                side3 = 1;
            }
        }

        if (Math.abs(ghost4.x - pacman.x) < Math.abs(ghost4.y - pacman.y)) {
            if (pacman.y > ghost4.y) {
                side4 = 2;
            } else {
                side4 = 3;
            }
        } else {
            if (pacman.x > ghost4.x) {
                side4 = 0;
            } else {
                side4 = 1;
            }
        }


    }

    private void ghost4Move() {
        int ghost4Direction1 = 0;
        int ghost4Direction2 = 0;
        int ghost4Direction3 = 0;
        int ghost4Direction4 = 0;
        for (Rectangle wall : bricks) {
            if (wall.overlaps(ghost4)) {
                if (Math.abs(ghost4.x - wall.x) < 40) {
                    if (ghost4.y > wall.y) {
                        ghost4Direction3 = 3;
                    }
                    if (ghost4.y < wall.y) {
                        ghost4Direction1 = 1;
                    }
                }
                if (Math.abs(ghost4.y - wall.y) < 40) {
                    if (ghost4.x > wall.x) {
                        ghost4Direction2 = 2;
                    } else {
                        ghost4Direction4 = 4;
                    }
                }
            }
        }
        if (side4 == 0 && ghost4Direction2 != 2) ghost4.x -= 130 * Gdx.graphics.getDeltaTime();
        if (side4 == 1 && ghost4Direction4 != 4) ghost4.x += 130 * Gdx.graphics.getDeltaTime();
        if (side4 == 2 && ghost4Direction3 != 3) ghost4.y -= 130 * Gdx.graphics.getDeltaTime();
        if (side4 == 3 && ghost4Direction1 != 1) ghost4.y += 130 * Gdx.graphics.getDeltaTime();
    }

    private void ghost3Move() {
        int ghost3Direction1 = 0;
        int ghost3Direction2 = 0;
        int ghost3Direction3 = 0;
        int ghost3Direction4 = 0;
        for (Rectangle wall : bricks) {
            if (wall.overlaps(ghost3)) {
                if (Math.abs(ghost3.x - wall.x) < 41) {
                    if (ghost3.y > wall.y) {
                        ghost3Direction3 = 3;

                    }
                    if (ghost3.y < wall.y) {
                        ghost3Direction1 = 1;
                    }
                }
                if (Math.abs(ghost3.y - wall.y) < 40) {
                    if (ghost3.x > wall.x) {
                        ghost3Direction2 = 2;

                    } else {
                        ghost3Direction4 = 4;
                    }
                }
            }
        }
        if (side3 == 0 && ghost3Direction2 != 2) ghost3.x -= 130 * Gdx.graphics.getDeltaTime();
        if (side3 == 1 && ghost3Direction4 != 4) ghost3.x += 130 * Gdx.graphics.getDeltaTime();
        if (side3 == 2 && ghost3Direction3 != 3) ghost3.y -= 130 * Gdx.graphics.getDeltaTime();
        if (side3 == 3 && ghost3Direction1 != 1) ghost3.y += 130 * Gdx.graphics.getDeltaTime();
    }

    private void ghost2Move() {
        int ghost2Direction1 = 0;
        int ghost2Direction2 = 0;
        int ghost2Direction3 = 0;
        int ghost2Direction4 = 0;
        for (Rectangle wall : bricks) {
            if (wall.overlaps(ghost2)) {
                if (Math.abs(ghost2.x - wall.x) < 40) {
                    if (ghost2.y > wall.y) {
                        ghost2Direction3 = 3;

                    }
                    if (ghost2.y < wall.y) {
                        ghost2Direction1 = 1;
                    }
                }
                if (Math.abs(ghost2.y - wall.y) < 41) {
                    if (ghost2.x > wall.x) {
                        ghost2Direction2 = 2;

                    } else {
                        ghost2Direction4 = 4;
                    }
                }
            }
        }
        if (side2 == 0 && ghost2Direction2 != 2) ghost2.x -= 130 * Gdx.graphics.getDeltaTime();
        if (side2 == 1 && ghost2Direction4 != 4) ghost2.x += 130 * Gdx.graphics.getDeltaTime();
        if (side2 == 2 && ghost2Direction3 != 3) ghost2.y -= 130 * Gdx.graphics.getDeltaTime();
        if (side2 == 3 && ghost2Direction1 != 1) ghost2.y += 130 * Gdx.graphics.getDeltaTime();
    }

    private void ghost1Move() {
        int ghost1Direction1 = 0;
        int ghost1Direction2 = 0;
        int ghost1Direction3 = 0;
        int ghost1Direction4 = 0;
        for (Rectangle wall : bricks) {
            if (wall.overlaps(ghost1)) {
                if (Math.abs(ghost1.x - wall.x) < 41) {
                    if (ghost1.y > wall.y) {
                        ghost1Direction3 = 3;

                    }
                    if (ghost1.y < wall.y) {
                        ghost1Direction1 = 1;
                    }
                }
                if (Math.abs(ghost1.y - wall.y) < 41) {
                    if (ghost1.x > wall.x) {
                        ghost1Direction2 = 2;

                    } else {
                        ghost1Direction4 = 4;
                    }
                }
            }
        }
        if (side1 == 0 && ghost1Direction2 != 2) ghost1.x -= 130 * Gdx.graphics.getDeltaTime();
        if (side1 == 1 && ghost1Direction4 != 4) ghost1.x += 130 * Gdx.graphics.getDeltaTime();
        if (side1 == 2 && ghost1Direction3 != 3) ghost1.y -= 130 * Gdx.graphics.getDeltaTime();
        if (side1 == 3 && ghost1Direction1 != 1) ghost1.y += 130 * Gdx.graphics.getDeltaTime();
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

    private void printGhostsHyperMode() {
        batch.begin();
        batch.draw(sadGhost, ghost1.x, ghost1.y);
        batch.end();
        batch.begin();
        batch.draw(sadGhost, ghost2.x, ghost2.y);
        batch.end();
        batch.begin();
        batch.draw(sadGhost, ghost3.x, ghost3.y);
        batch.end();
        batch.begin();
        batch.draw(sadGhost, ghost4.x, ghost4.y);
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
                    if (!isMute) {
                        bananaEat.play();
                    }
                }
            }
        }

        for (Rectangle pineapple1 : pineapples) {
            if (pineapple1.overlaps(pacman)) {
                ghostKillNumber = 1;
                hyperModeStartTime = System.currentTimeMillis();
                float banana1X = pineapple1.x;
                float banana1Y = pineapple1.y;
                int row = (int) ((banana1Y - 50) / 80);
                int column = (int) ((banana1X + 15) / 80);
                if (map[row][column] == 7) {
                    map[row][column] = 2;
                    isHyperMode = true;
                    if (!isMute) {
                        pineappleEat.play();
                    }
                }
            }
        }
    }

    private void wallBlock() {
        direction1 = 0;
        direction2 = 0;
        direction3 = 0;
        direction4 = 0;
        for (Rectangle wall : bricks) {
            if (wall.overlaps(pacman)) {
                if (Math.abs(pacman.x - wall.x) < 50) {
                    if (pacman.y > wall.y) {
                        direction3 = 3;

                    }
                    if (pacman.y < wall.y) {
                        direction1 = 1;
                    }
                }
                if (Math.abs(pacman.y - wall.y) < 50) {
                    if (pacman.x > wall.x) {
                        direction2 = 2;

                    } else {
                        direction4 = 4;
                    }
                }
            }
        }
    }

    private void pacmanMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && direction2 != 2) {
            pacman.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && direction4 != 4) {
            pacman.x += 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && direction3 != 3) {
            pacman.y -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && direction1 != 1) {
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
                    aBanana.x = column * 80 + 25;
                    aBanana.y = row * 80 + 110;
                    aBanana.height = 20;
                    aBanana.width = 20;
                    bananas.add(aBanana);
                    batch.end();
                } else if (map[row][column] == 7) {
                    batch.begin();
                    batch.draw(pineapple, column * 80 + 15, row * 80 + 100, 48, 48);
                    Rectangle aPineapple = new Rectangle();
                    aPineapple.x = column * 80 + 25;
                    aPineapple.y = row * 80 + 110;
                    aPineapple.height = 20;
                    aPineapple.width = 20;
                    pineapples.add(aPineapple);
                    batch.end();
                }
            }
        }
    }

    private void checkForEndOfHyperMode() {
        if (System.currentTimeMillis() - hyperModeStartTime > 10000) {
            isHyperMode = false;
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
