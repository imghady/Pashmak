package com.mygdx.game.controller;


import com.google.gson.Gson;
import com.mygdx.game.model.User;



import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Finisher {
    public static void finish() throws IOException {
        ArrayList<User> allUsers = User.allUsers;
        for (User user : allUsers) {

            Gson gson = new Gson();
            String fileAddress = "users/" + user.getUsername() + ".json";

            try (FileWriter writer = new FileWriter(fileAddress)) {
                gson.toJson(user, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
