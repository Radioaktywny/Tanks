package com.example.tanks.engine.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/**
 * Created by Marcin on 31.10.2017.
 */
public class GameObjectTest {


    private final int speed = 10;

    @Test
    public void testIfMoveWorksCorrectly() {
        GameObject gameObj = new SomeGameObj(new Position(100, 100), new Image(null, null));
        gameObj.changeDirection(Direction.RIGHT);
        gameObj.move();
        Assertions.assertThat(gameObj.getPosition().getHeight()).isEqualTo(100);
        Assertions.assertThat(gameObj.getPosition().getWidth()).isEqualTo(100 + speed);
    }

    @Test
    public void testIfThereIsNoDirection_ThenObjectIsInTheSamePosition(){
        GameObject gameObj = new SomeGameObj(new Position(100, 100), new Image(null, null));
        gameObj.changeDirection(Direction.NO_DIRECTION);
        gameObj.move();
        Assertions.assertThat(gameObj.getPosition().getHeight()).isEqualTo(100);
        Assertions.assertThat(gameObj.getPosition().getWidth()).isEqualTo(100);
    }


    class SomeGameObj extends GameObject {

        SomeGameObj(Position position, Image image) {
            super(position, image);
        }

        @Override
        protected int speed() {
            return speed;
        }
    }

}