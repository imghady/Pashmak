package com.mygdx.game.controller;

import com.mygdx.game.model.Score;

import java.util.Comparator;

public class SortScore implements Comparator<Score> {

    public int compare(Score a,Score b) {
        return (a.getScore() - b.getScore());
    }
}
