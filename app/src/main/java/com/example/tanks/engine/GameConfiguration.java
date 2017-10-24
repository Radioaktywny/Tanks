package com.example.tanks.engine;

import android.view.View;
import android.widget.ImageButton;

import com.example.tanks.engine.model.DirectionOfMovement;
import com.example.tanks.engine.model.Playable;
import com.example.tanks.engine.model.Player;
import com.example.tanks.engine.model.Updatable;
import com.zerokol.views.JoystickView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Marcin on 24.10.2017.
 */
public class GameConfiguration {

    private Playable player;

    private Playable bot;

    private List<Updatable> listOfGameObjects;

    GameConfiguration() {
        initPlayers();
    }

    private void initPlayers() {
        player = new Player(null, null, null);
        bot = new Player(null, null, null);
        listOfGameObjects = Arrays.asList((Updatable) player, (Updatable) bot);
        new BotController(bot);
    }

    void updateGameObjects() {
        for (Updatable gameObj : listOfGameObjects) {
            gameObj.move();
        }
    }

    void prepareShootButton(ImageButton shootButton) {
        //todo add listeners
        shootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.shoot(null, 1);
            }
        });
        shootButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player.shoot(null, 2);
                return true;
            }
        });
    }

    void prepareJoystick(JoystickView joystick) {
        joystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {

            @Override
            public void onValueChanged(int angle, int power, int direction) {
                player.changeDirection(DirectionOfMovement.DOWN);
                //todo conversion betweeen direction and Direction of movement
            }
        }, JoystickView.DEFAULT_LOOP_INTERVAL);
    }
}
