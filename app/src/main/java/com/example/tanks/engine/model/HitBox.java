package com.example.tanks.engine.model;

/**
 * Created by Marcin on 24.10.2017.
 */

public enum HitBox {
    PLAYER(40, 35),
    BULLET_SOFT(28, 7),
    BULLET_NUKE(25, 9);

    private final int width;
    private final int heigh;

    HitBox(int width, int heigh) {
        this.width = width;
        this.heigh = heigh;
    }

    public int getWidth() {
        return width;
    }

    public int getHeigh() {
        return heigh;
    }
}
