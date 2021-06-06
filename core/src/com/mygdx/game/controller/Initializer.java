package com.mygdx.game.controller;

import com.google.gson.Gson;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.User;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class Initializer {
    public static void addUsers() throws IOException {
        File directoryPath = new File("users");
        File[] filesList = directoryPath.listFiles();
        assert filesList != null;
        for (File file : filesList) {

            Gson gson = new Gson();
            Reader reader = new FileReader(file);
            User user = gson.fromJson(reader, User.class);
            User.addToAllUsers(user);

        }

        File directoryPath1 = new File("scores");
        File[] filesList1 = directoryPath1.listFiles();
        assert filesList1 != null;
        for (File file : filesList1) {

            Gson gson = new Gson();
            Reader reader = new FileReader(file);
            Score score = gson.fromJson(reader, Score.class);
            Score.allScores.add(score);

        }

    }
}
