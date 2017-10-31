package com.example.tanks.engine;

import android.graphics.Bitmap;

/**
 * Created by Marcin on 31.10.2017.
 */

public class BitmapsForGame {

    private Bitmap playerBitmap;

    private Bitmap bulletSoftBitmap;

    private Bitmap bulletNukeBitmap;

    public BitmapsForGame(Bitmap playerBitmap, Bitmap bulletSoftBitmap, Bitmap bulletNukeBitmap) {
        this.playerBitmap = playerBitmap;
        this.bulletSoftBitmap = bulletSoftBitmap;
        this.bulletNukeBitmap = bulletNukeBitmap;
    }

    public Bitmap getBulletNukeBitmap() {
        return bulletNukeBitmap;
    }

    public Bitmap getBulletSoftBitmap() {
        return bulletSoftBitmap;
    }

    public Bitmap getPlayerBitmap() {
        return playerBitmap;
    }
}
