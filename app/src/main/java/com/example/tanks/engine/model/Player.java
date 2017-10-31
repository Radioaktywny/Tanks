package com.example.tanks.engine.model;

/**
 * Created by Marcin on 24.10.2017.
 */

public class Player extends GameObject implements Playable {

    public Player(Position position, Image image, Direction direction) {
        super(position, image, direction);
    }

    @Override
    protected int speed() {
        return Speed.PLAYER.getSpeedValue();
    }

    @Override
    public void changeDirection(Direction direction) {
        super.changeDirection(direction);
    }
}
