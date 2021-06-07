package com.mygdx.game.model;

import com.mygdx.game.controller.Finisher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class User {

    public static ArrayList<User> allUsers = new ArrayList<>();

    private String username;
    private String password;
    private int highScore = 0;
    private int lastGameScore = 0;
    private int LastGameHealth = 0;
    private int[][] lastGameMap;

    public User (String username, String password) throws IOException {
        this.username = username;
        this.password = password;
        allUsers.add(this);
        Finisher.finish();
    }

    public boolean isNewHighScore (int newScore) {
        if (newScore > this.highScore) {
            this.highScore = newScore;
            return true;
        }
        return false;
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean isPasswordCorrect (String username, String password) {
        User user = User.getUserByUsername(username);
        assert user != null;
        return password.equals(user.getPassword());
    }

    public int getHighScore() {
        return highScore;
    }

    public static void addToAllUsers(User user) {
        allUsers.add(user);
    }

    public static void deleteUser(User user) {
        allUsers.remove(user);
        for (User allUser : allUsers) {
            if (allUser.getUsername().equals(user.getUsername())) {
                allUsers.remove(user);
            }
        }
        System.out.println("111");
        String fileAddress = "users/" + user.getUsername() + ".json";
        File userFile = new File(fileAddress);
        userFile.delete();
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getLastGameScore() {
        return lastGameScore;
    }

    public void setLastGameScore(int lastGameScore) {
        this.lastGameScore = lastGameScore;
    }

    public int getLastGameHealth() {
        return LastGameHealth;
    }

    public void setLastGameHealth(int lastGameHealth) {
        LastGameHealth = lastGameHealth;
    }

    public int[][] getLastGameMap() {
        return lastGameMap;
    }

    public void setLastGameMap(int[][] lastGameMap) {
        this.lastGameMap = lastGameMap;
    }
}
