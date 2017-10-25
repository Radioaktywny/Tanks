package com.example.tanks.engine;

import android.view.View;
import android.widget.ImageButton;

import com.example.tanks.engine.model.DirectionConverter;
import com.example.tanks.engine.model.GameObject;
import com.example.tanks.engine.model.Player;
import com.example.tanks.engine.model.Updatable;
import com.zerokol.views.JoystickView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Marcin on 24.10.2017.
 */
public class GameConfiguration {

    private Player player;

    private Player bot;

    private List<GameObject> listOfGameObjects;

    GameConfiguration() {
        initPlayers();
    }

    private void initPlayers() {
        player = new Player(null, null, null);
        bot = new Player(null, null, null);
        //todo this is comming to factory, do not know yet how to create hitbox
        listOfGameObjects = Arrays.asList(player, (GameObject) bot);
        new BotController(bot);
    }

    void updateGameObjects() {
        for (Updatable gameObj : listOfGameObjects) {
            gameObj.move();
        }
    }

    void prepareShootButton(ImageButton shootButton) {
        shootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOfGameObjects.add(ShootFactory.createSoftBullet(player));
            }
        });

        shootButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listOfGameObjects.add(ShootFactory.createNukeBullet(player));
                return true;
            }
        });
    }

    void prepareJoystick(JoystickView joystick) {
        joystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(int angle, int power, int direction) {
                player.changeDirection(DirectionConverter.convert(direction));
            }
        }, JoystickView.DEFAULT_LOOP_INTERVAL);
    }
}
