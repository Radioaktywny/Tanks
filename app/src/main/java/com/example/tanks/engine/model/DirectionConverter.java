package com.example.tanks.engine.model;

import com.zerokol.views.JoystickView;

/**
 * Created by Marcin on 25.10.2017.
 */

public class DirectionConverter {

    public static DirectionOfMovement convert(int direction) {
        switch (direction) {
            case JoystickView.FRONT:
                return DirectionOfMovement.UP;
            case JoystickView.LEFT_FRONT:
                return DirectionOfMovement.UP_RIGHT;
            case JoystickView.LEFT:
                return DirectionOfMovement.RIGHT;
            case JoystickView.BOTTOM_LEFT:
                return DirectionOfMovement.DOWN_RIGHT;
            case JoystickView.BOTTOM:
                return DirectionOfMovement.DOWN;
            case JoystickView.RIGHT_BOTTOM:
                return DirectionOfMovement.DOWN_LEFT;
            case JoystickView.RIGHT:
                return DirectionOfMovement.LEFT;
            case JoystickView.FRONT_RIGHT:
                return DirectionOfMovement.UP_LEFT;
            default:
                return DirectionOfMovement.NO_DIRECTION;
        }
    }

}
