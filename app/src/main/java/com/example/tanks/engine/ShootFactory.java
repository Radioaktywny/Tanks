package com.example.tanks.engine;

import android.graphics.Bitmap;

import com.example.tanks.engine.model.BulletNuke;
import com.example.tanks.engine.model.BulletSoft;
import com.example.tanks.engine.model.HitBox;
import com.example.tanks.engine.model.Image;
import com.example.tanks.engine.model.Player;

/**
 * Created by Marcin on 25.10.2017.
 */

class ShootFactory {

    static BulletSoft createSoftBullet(Player player, Bitmap bitmap) {
//        Bitmap image = Bitmap.createScaledBitmap(bitmap, HitBox.BULLET_SOFT.getWidth(), HitBox.BULLET_SOFT.getHeigh(), true);
        return new BulletSoft(player.getPosition(), new Image(bitmap, HitBox.BULLET_SOFT), player.getDirection());
    }

    static BulletNuke createNukeBullet(Player player, Bitmap bitmap) {
//        Bitmap image = Bitmap.createScaledBitmap(bitmap, HitBox.BULLET_NUKE.getWidth(), HitBox.BULLET_NUKE.getHeigh(), true);
        return new BulletNuke(player.getPosition(), new Image(bitmap, HitBox.BULLET_NUKE), player.getDirection());
    }
}
