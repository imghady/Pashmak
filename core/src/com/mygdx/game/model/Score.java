package com.mygdx.game.model;

import com.mygdx.game.controller.SortScore;

import java.util.ArrayList;
import java.util.Collections;

public class Score {

    private int score;
    private User owner;

    public static ArrayList<Score> allScores = new ArrayList<>();

    public Score (int score, User user) {
        this.owner = user;
        this.score = score;
        allScores.add(this);
    }

    public void sortAllScores() {
        allScores.sort(new SortScore());
    }

    public int getScore() {
        return score;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
