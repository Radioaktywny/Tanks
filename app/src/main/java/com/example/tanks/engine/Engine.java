package com.example.tanks.engine;

import android.graphics.Canvas;
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
public class Engine {

    private final BitmapsForGame bitmapsForGame;
    private Player player;

    private List<GameObject> listOfGameObjects;

    Engine(BitmapsForGame bitmapsForGame) {
        this.bitmapsForGame = bitmapsForGame;
        initPlayers(this.bitmapsForGame);
    }

    private void initPlayers(BitmapsForGame bitmapsForGame) {
        Player bot = PlayerFactory.createBot(bitmapsForGame.getPlayerBitmap());
        player = PlayerFactory.createPlayer(bitmapsForGame.getPlayerBitmap());
        listOfGameObjects = Arrays.asList(player, (GameObject) bot);
        new BotController(bot);
    }

    void updateGameObjects() {
        for (Updatable gameObj : listOfGameObjects) {
            gameObj.move();
        }
    }

    void drawGameObjects(Canvas canvas){
        for (Updatable gameObj: listOfGameObjects) {
            gameObj.draw(canvas);
        }
    }

    void prepareShootButton(ImageButton shootButton) {
        shootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOfGameObjects.add(ShootFactory.createSoftBullet(player, bitmapsForGame.getBulletSoftBitmap()));
            }
        });

        shootButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listOfGameObjects.add(ShootFactory.createNukeBullet(player, bitmapsForGame.getBulletNukeBitmap()));
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
