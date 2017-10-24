package com.example.tanks.engine.model;

/**
 * Created by Marcin on 23.10.2017.
 */
public interface Playable {

    void shoot(DirectionOfMovement directionOfMovement, int type);

    void changeDirection(DirectionOfMovement directionOfMovement);

}
