package com.example.tanks.engine.model;

/**
 * Created by Marcin on 24.10.2017.
 */

public abstract class GameObject implements Updatable {

    public GameObject(Position position, HitBox hitBox, DirectionOfMovement directionOfMovement) {
        this.position = position;
        this.hitBox = hitBox;
        this.directionOfMovement = directionOfMovement;
    }

    private Position position;

    private HitBox hitBox;

    private DirectionOfMovement directionOfMovement;

    protected abstract int speed();

    protected void changeDirection(DirectionOfMovement directionOfMovement) {
        this.directionOfMovement = directionOfMovement;
    }

    public Position getPosition() {
        return position;
    }

    public DirectionOfMovement getDirectionOfMovement() {
        return directionOfMovement;
    }


    @Override
    public void move() {
        position = new Position(position.getWidth() + directionOfMovement.getX() * speed(),
                position.getHeight() + directionOfMovement.getY() * speed());
    }
}
