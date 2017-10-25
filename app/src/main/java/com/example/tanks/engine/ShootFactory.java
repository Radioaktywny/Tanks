package com.example.tanks.engine;

import com.example.tanks.engine.model.BulletNuke;
import com.example.tanks.engine.model.BulletSoft;
import com.example.tanks.engine.model.Player;

/**
 * Created by Marcin on 25.10.2017.
 */

public class ShootFactory {

    public static BulletSoft createSoftBullet(Player player) {
        return new BulletSoft(player.getPosition(), null, player.getDirectionOfMovement());
    }

    public static BulletNuke createNukeBullet(Player player) {
        return new BulletNuke(player.getPosition(), null, player.getDirectionOfMovement());
    }
}
