package com.mygdx.game.controller;

import com.badlogic.gdx.math.MathUtils;


public class MakeMaze {
    public static boolean checkEnvirons(char[][] mazeMap, int row, int column, int m, int n) {
        int counter = 0;
        if (column + 1 < n) {
            if (mazeMap[2 * row + 1][2 * column + 3] == '2') {
                counter++;
            }
        }
        if (row + 1 < m) {
            if (mazeMap[2 * row + 3][2 * column + 1] == '2') {
                counter++;
            }
        }
        if (column - 1 >= 0) {
            if (mazeMap[2 * row + 1][2 * column - 1] == '2') {
                counter++;
            }
        }
        if (row - 1 >= 0) {
            if (mazeMap[2 * row - 1][2 * column + 1] == '2') {
                counter++;
            }
        }
        return counter > 0;
    }

    public static boolean isPassable(char[][] mazeMap, int row, int column, int m, int n) {
        if (column >= n || column < 0 || row >= m || row < 0) {
            return false;
        } else {
            if (mazeMap[2 * row + 1][2 * column + 1] != '2') {
                return false;
            }
        }
        return checkEnvirons(mazeMap, row, column, m, n);
    }

    public static void makeMaze(char[][] mazeMap, int currentRow, int currentColumn, int m, int n) {
        doMakeMazeState1(mazeMap, currentRow, currentColumn, m, n);
    }

    private static void doMakeMazeState1(char[][] mazeMap, int currentRow, int currentColumn, int m, int n) {
        if (isPassable(mazeMap, currentRow, currentColumn + 1, m, n)) {
            goTo1(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow + 1, 2 * currentColumn + 2,
                    currentRow, currentColumn + 1);
        }
        if (isPassable(mazeMap, currentRow + 1, currentColumn, m, n)) {
            goTo1(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow + 2, 2 * currentColumn + 1,
                    currentRow + 1, currentColumn);
        }
        if (isPassable(mazeMap, currentRow, currentColumn - 1, m, n)) {
            goTo1(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow + 1, 2 * currentColumn,
                    currentRow, currentColumn - 1);
        }
        if (isPassable(mazeMap, currentRow - 1, currentColumn, m, n)) {
            goTo1(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow, 2 * currentColumn + 1,
                    currentRow - 1, currentColumn);
        } else {
            mazeMap[2 * currentRow + 1][2 * currentColumn + 1] = '*';
        }
    }

    private static void doMakeMazeState2(char[][] mazeMap, int currentRow, int currentColumn, int m, int n) {
        if (isPassable(mazeMap, currentRow + 1, currentColumn, m, n)) {
            goTo2(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow + 2, 2 * currentColumn + 1,
                    currentRow + 1, currentColumn);
        }
        if (isPassable(mazeMap, currentRow, currentColumn - 1, m, n)) {
            goTo2(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow + 1, 2 * currentColumn,
                    currentRow, currentColumn - 1);
        }
        if (isPassable(mazeMap, currentRow - 1, currentColumn, m, n)) {
            goTo2(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow, 2 * currentColumn + 1,
                    currentRow - 1, currentColumn);
        }
        if (isPassable(mazeMap, currentRow, currentColumn + 1, m, n)) {
            goTo2(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow + 1, 2 * currentColumn + 2,
                    currentRow, currentColumn + 1);
        } else {
            mazeMap[2 * currentRow + 1][2 * currentColumn + 1] = '*';
        }
    }

    private static void doMakeMazeState3(char[][] mazeMap, int currentRow, int currentColumn, int m, int n) {
        if (isPassable(mazeMap, currentRow, currentColumn - 1, m, n)) {
            goTo3(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow + 1, 2 * currentColumn,
                    currentRow, currentColumn - 1);
        }
        if (isPassable(mazeMap, currentRow - 1, currentColumn, m, n)) {
            goTo3(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow, 2 * currentColumn + 1,
                    currentRow - 1, currentColumn);
        }
        if (isPassable(mazeMap, currentRow, currentColumn + 1, m, n)) {
            goTo3(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow + 1, 2 * currentColumn + 2,
                    currentRow, currentColumn + 1);
        }
        if (isPassable(mazeMap, currentRow + 1, currentColumn, m, n)) {
            goTo3(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow + 2, 2 * currentColumn + 1,
                    currentRow + 1, currentColumn);
        } else {
            mazeMap[2 * currentRow + 1][2 * currentColumn + 1] = '*';
        }
    }

    private static void doMakeMazeState4(char[][] mazeMap, int currentRow, int currentColumn, int m, int n) {
        if (isPassable(mazeMap, currentRow - 1, currentColumn, m, n)) {
            goTo4(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow, 2 * currentColumn + 1,
                    currentRow - 1, currentColumn);
        }
        if (isPassable(mazeMap, currentRow, currentColumn + 1, m, n)) {
            goTo4(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow + 1, 2 * currentColumn + 2,
                    currentRow, currentColumn + 1);
        }
        if (isPassable(mazeMap, currentRow + 1, currentColumn, m, n)) {
            goTo4(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow + 2, 2 * currentColumn + 1,
                    currentRow + 1, currentColumn);
        }
        if (isPassable(mazeMap, currentRow, currentColumn - 1, m, n)) {
            goTo4(mazeMap, currentRow, currentColumn, m, n, 2 * currentRow + 1, 2 * currentColumn,
                    currentRow, currentColumn - 1);
        } else {
            mazeMap[2 * currentRow + 1][2 * currentColumn + 1] = '*';
        }
    }


    private static void goTo1(char[][] mazeMap, int currentRow, int currentColumn, int m,
                              int n, int wallBreakRow, int wallBreakColumn, int rowToCall, int columnToCall) {
        mazeMap[2 * currentRow + 1][2 * currentColumn + 1] = '*';
        mazeMap[wallBreakRow][wallBreakColumn] = '0';
        doMakeMazeState1(mazeMap, rowToCall, columnToCall, m, n);
    }

    private static void goTo2(char[][] mazeMap, int currentRow, int currentColumn, int m,
                              int n, int wallBreakRow, int wallBreakColumn, int rowToCall, int columnToCall) {
        mazeMap[2 * currentRow + 1][2 * currentColumn + 1] = '*';
        mazeMap[wallBreakRow][wallBreakColumn] = '0';
        doMakeMazeState2(mazeMap, rowToCall, columnToCall, m, n);
    }

    private static void goTo3(char[][] mazeMap, int currentRow, int currentColumn, int m,
                              int n, int wallBreakRow, int wallBreakColumn, int rowToCall, int columnToCall) {
        mazeMap[2 * currentRow + 1][2 * currentColumn + 1] = '*';
        mazeMap[wallBreakRow][wallBreakColumn] = '0';
        doMakeMazeState3(mazeMap, rowToCall, columnToCall, m, n);
    }

    private static void goTo4(char[][] mazeMap, int currentRow, int currentColumn, int m,
                              int n, int wallBreakRow, int wallBreakColumn, int rowToCall, int columnToCall) {
        mazeMap[2 * currentRow + 1][2 * currentColumn + 1] = '*';
        mazeMap[wallBreakRow][wallBreakColumn] = '0';
        doMakeMazeState4(mazeMap, rowToCall, columnToCall, m, n);
    }

    public static int[][] getRandom() {
        int m = 5, n = 6;
        char[][] mazeMap = new char[2 * m + 1][2 * n + 1];

        refreshMap(m, n, mazeMap);
        int currentRow = (int) ((m - 1) * Math.random());
        int currentColumn = (int) ((n - 1) * Math.random());
        int randomDirection = (int) ((3) * Math.random());
        move(m, n, mazeMap, currentRow, currentColumn, randomDirection);

        int[][] map = new int[8][10];

        initialMap(mazeMap, map);
        randomize(map);

        return map;
    }

    private static void initialMap(char[][] mazeMap, int[][] map) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 13; j++) {
                if (mazeMap[i][j] == '*') {
                    mazeMap[i][j] = '0';
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                if (mazeMap[i + 1][j + 1] == '1') {
                    map[i][j] = 1;
                } else {
                    map[i][j] = 0;
                }
            }
        }
    }

    private static void randomize(int[][] map) {
        map[1][0] = 0;
        map[1][9] = 0;
        map[6][0] = 0;
        map[6][9] = 0;
        map[4][5] = 0;
        map[3][5] = 0;

        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 0;

        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 7;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 7;
        map[MathUtils.random(0, 7)][MathUtils.random(0, 9)] = 7;
    }

    private static void move(int m, int n, char[][] mazeMap, int currentRow, int currentColumn, int randomDirection) {
        switch (randomDirection) {
            case 0:
                doMakeMazeState1(mazeMap, currentRow, currentColumn, m, n);
                break;
            case 1:
                doMakeMazeState2(mazeMap, currentRow, currentColumn, m, n);
                break;
            case 2:
                doMakeMazeState3(mazeMap, currentRow, currentColumn, m, n);
                break;
            case 3:
                doMakeMazeState4(mazeMap, currentRow, currentColumn, m, n);
                break;
        }
    }

    private static void refreshMap(int m, int n, char[][] mazeMap) {
        for (int i = 0; i < 2 * m + 1; i++) {
            for (int j = 0; j < 2 * n + 1; j++) {
                if (i % 2 == 1 && j % 2 == 1) {
                    mazeMap[i][j] = '2';
                } else {
                    mazeMap[i][j] = '1';
                }
            }
        }
    }
}
