package com.mygdx.game.controller;

import com.google.gson.Gson;
import com.mygdx.game.model.User;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

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
    }
}
