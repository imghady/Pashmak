package com.mygdx.game.model;

import org.graalvm.compiler.replacements.arraycopy.ArrayCopy;

public class Map {

    public static int[][] map1 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 1, 0, 1, 1, 0, 7},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {7, 1, 1, 0, 1, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};


    public static int[][] map2 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 7, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 1, 0, 1, 1, 0, 0},
            {0, 0, 1, 0, 1, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0, 0, 0},
            {1, 1, 1, 0, 1, 0, 1, 1, 7, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

    public static int[][] map3 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 7, 0, 0, 0, 0, 0, 0, 7},
            {0, 1, 1, 0, 1, 0, 1, 1, 0, 0},
            {0, 0, 1, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 7, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};


    public static int[][] map4 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 7, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 1, 0, 1, 1, 0, 0},
            {0, 1, 1, 0, 1, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 1, 1, 0},
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};


    public static int[][] getMap1() {
        int[][] mapHolder = new int[8][10];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                mapHolder[i][j] = map1[i][j];
            }
        }
        return mapHolder;
    }

    public static int[][] getMap2() {
        int[][] mapHolder = new int[8][10];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                mapHolder[i][j] = map2[i][j];
            }
        }
        return mapHolder;
    }

    public static int[][] getMap3() {
        int[][] mapHolder = new int[8][10];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                mapHolder[i][j] = map3[i][j];
            }
        }
        return mapHolder;
    }

    public static int[][] getMap4() {
        int[][] mapHolder = new int[8][10];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                mapHolder[i][j] = map4[i][j];
            }
        }
        return mapHolder;
    }


}
