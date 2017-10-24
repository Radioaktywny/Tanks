package com.example.tanks.engine.model;

/**
 * Created by Marcin on 24.10.2017.
 */

public class BulletNuke extends GameObject {

    public BulletNuke(Position position, HitBox hitBox, DirectionOfMovement directionOfMovement) {
        super(position, hitBox, directionOfMovement);
    }

    @Override
    protected int speed() {
        return Speed.BULLET_NUKE.getSpeedValue();
    }

}