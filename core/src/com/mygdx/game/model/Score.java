package com.mygdx.game.model;

import com.mygdx.game.controller.SortScore;

import java.util.ArrayList;
import java.util.Collections;

public class Score {

    private int score;
    private String owner;

    public static ArrayList allScores = new ArrayList<>();

    public Score (int score, String user) {
        this.owner = user;
        this.score = score;
        if (allScores == null) {
            allScores = new ArrayList<>();
        }
        allScores.add(this);
    }

    public static void sortAllScores(ArrayList<Score> highScores) {
        allScores.sort(new SortScore());
        highScores.sort(new SortScore());
    }

    public int getScore() {
        return score;
    }

    public User getOwner() {
        return User.getUserByUsername(owner);
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
