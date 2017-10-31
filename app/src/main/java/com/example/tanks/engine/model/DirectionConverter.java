package com.example.tanks.engine.model;

import com.zerokol.views.JoystickView;

/**
 * Created by Marcin on 25.10.2017.
 */

public class DirectionConverter {

    public static Direction convert(int direction) {
        switch (direction) {
            case JoystickView.FRONT:
                return Direction.UP;
            case JoystickView.LEFT_FRONT:
                return Direction.UP_RIGHT;
            case JoystickView.LEFT:
                return Direction.RIGHT;
            case JoystickView.BOTTOM_LEFT:
                return Direction.DOWN_RIGHT;
            case JoystickView.BOTTOM:
                return Direction.DOWN;
            case JoystickView.RIGHT_BOTTOM:
                return Direction.DOWN_LEFT;
            case JoystickView.RIGHT:
                return Direction.LEFT;
            case JoystickView.FRONT_RIGHT:
                return Direction.UP_LEFT;
            default:
                return Direction.NO_DIRECTION;
        }
    }

}
