package com.example.tanks.engine.model;

/**
 * Created by Marcin on 19.10.2017.
 */

public class Position {

    private int width;

    private int height;

    public Position(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
