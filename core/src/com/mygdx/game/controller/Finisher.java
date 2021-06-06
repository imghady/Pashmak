package com.mygdx.game.controller;


import com.google.gson.Gson;
import com.mygdx.game.model.Score;
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

    public static void writeScores()  throws IOException {
        ArrayList<Score> allScores = Score.allScores;
        int counter = 1;
        if (allScores.size() <= 10) {
            for (int i = allScores.size() - 1; i >= 0; i--) {

                Gson gson = new Gson();
                String fileAddress = "scores/" + counter + ".json";

                try (FileWriter writer = new FileWriter(fileAddress)) {
                    gson.toJson(allScores.get(i), writer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                counter++;
            }
        } else {
            for (int i = allScores.size() - 1; i >= allScores.size() - 10; i--) {

                Gson gson = new Gson();
                String fileAddress = "scores/" + counter + ".json";

                try (FileWriter writer = new FileWriter(fileAddress)) {
                    gson.toJson(allScores.get(i), writer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                counter++;
            }
        }
    }

}
