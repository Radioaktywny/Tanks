package com.example.tanks.engine.model;

import android.graphics.Canvas;

/**
 * Created by Marcin on 24.10.2017.
 */

public abstract class GameObject implements Updatable {

    GameObject(Position position, Image image){
        this.position = position;
        this.direction = Direction.NO_DIRECTION;
        this.image=image;
    }

    GameObject(Position position, Image image, Direction direction) {
        this.position = position;
        this.image = image;
        this.direction = direction;
    }

    private Position position;

    private Direction direction;

    private Image image;

    protected abstract int speed();

    protected void changeDirection(Direction direction) {
        this.direction = direction;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }


    @Override
    public void move() {
        position = new Position(position.getWidth() + direction.getX() * speed(),
                position.getHeight() + direction.getY() * speed());
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(image.getBitmap(), getPosition().getWidth(), getPosition().getHeight(), null);
    }
}
