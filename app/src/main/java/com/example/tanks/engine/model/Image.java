package com.example.tanks.engine.model;

import android.graphics.Bitmap;

/**
 * Created by Marcin on 31.10.2017.
 */

public class Image {

    private Bitmap bitmap;
    private HitBox hitBox;
    private Direction direction;

    public Image(Bitmap bitmap, HitBox hitBox) {
        this.bitmap = bitmap;
        this.hitBox = hitBox;
        direction=Direction.RIGHT;
    }

    public Image(Bitmap bitmap, HitBox hitBox, Direction direction) {
        this.bitmap = bitmap;
        this.hitBox = hitBox;
        this.direction = direction;
    }

    public void reverseImage(Direction direction){
        //todo reversing image
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public HitBox getHitBox() {
        return hitBox;
    }
}
