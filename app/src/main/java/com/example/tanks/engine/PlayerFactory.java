package com.example.tanks.engine;

import android.graphics.Bitmap;

import com.example.tanks.engine.model.Direction;
import com.example.tanks.engine.model.HitBox;
import com.example.tanks.engine.model.Image;
import com.example.tanks.engine.model.Player;
import com.example.tanks.engine.model.Position;

/**
 * Created by Marcin on 31.10.2017.
 */

public class PlayerFactory {

    static Player createPlayer(Bitmap bitmap) {
//        Bitmap image = Bitmap.createScaledBitmap(bitmap, HitBox.PLAYER.getWidth(), HitBox.PLAYER.getHeigh(), true);
        return new Player(new Position(100, 100), new Image(bitmap, HitBox.PLAYER), Direction.NO_DIRECTION);
    }

    static Player createBot(Bitmap bitmap) {
//        Bitmap image = Bitmap.createScaledBitmap(bitmap, HitBox.PLAYER.getWidth(), HitBox.PLAYER.getHeigh(), true);
        return new Player(new Position(400, 100), new Image(bitmap, HitBox.PLAYER), Direction.NO_DIRECTION);

    }
}
