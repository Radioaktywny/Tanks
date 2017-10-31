package com.example.tanks.engine.model;

/**
 * Created by Marcin on 24.10.2017.
 */

public class BulletNuke extends GameObject {

    public BulletNuke(Position position, Image image, Direction direction) {
        super(position, image, direction);
    }

    @Override
    protected int speed() {
        return Speed.BULLET_NUKE.getSpeedValue();
    }

}
