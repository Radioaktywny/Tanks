package com.example.tanks.engine.model;

import android.graphics.Canvas;

/**
 * Created by Marcin on 24.10.2017.
 */

public interface Updatable {

    void move();

    void draw(Canvas canvas);

}