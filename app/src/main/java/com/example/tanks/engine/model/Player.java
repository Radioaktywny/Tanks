package com.example.tanks.engine.model;

/**
 * Created by Marcin on 24.10.2017.
 */

public class Player extends GameObject implements Playable {

    public Player(Position position, HitBox hitBox, DirectionOfMovement directionOfMovement) {
        super(position, hitBox, directionOfMovement);
    }

    @Override
    protected int speed() {
        return Speed.PLAYER.getSpeedValue();
    }

    @Override
    public void shoot(DirectionOfMovement directionOfMovement, int type) {

    }

    @Override
    public void changeDirection(DirectionOfMovement directionOfMovement) {

    }
}
