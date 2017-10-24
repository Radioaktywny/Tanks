package com.example.tanks.engine.model;

/**
 * Created by Marcin on 19.10.2017.
 */

public enum DirectionOfMovement {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(0, 1),
    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    DOWN_RIGHT(1, -1),
    DOWN_LEFT(-1, -1);

    private int x;

    private int y;

    DirectionOfMovement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}