package com.example.tanks.engine.model;

/**
 * Created by Marcin on 24.10.2017.
 */

public enum Speed {
    PLAYER(5),
    BULLET_SOFT(15),
    BULLET_NUKE(10);

    private int speedValue;

    Speed(int speedValue) {
        this.speedValue = speedValue;
    }

    public int getSpeedValue() {
        return speedValue;
    }
}
