package com.example.tanks.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageButton;

import com.example.tanks.R;
import com.zerokol.views.JoystickView;

/**
 * Created by Marcin on 23.10.2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private final View viewForControls;

    private GameConfiguration engineConfiguration;

    private Thread gameThread;

    private JoystickView joystick;

    private ImageButton shootButton;


    public GamePanel(Context context, View viewForControls) {
        super(context);
        this.viewForControls = viewForControls;
        engineConfiguration = new GameConfiguration();
        initJoystickAndShootButton();
    }

    private void initJoystickAndShootButton() {
        joystick = (JoystickView) findViewById(R.id.joystickView);
        shootButton = (ImageButton) findViewById(R.id.strzal);
        engineConfiguration.prepareJoystick(joystick);
        engineConfiguration.prepareShootButton(shootButton);
    }


    @Override
    public void draw(Canvas canvas) {
        engineConfiguration.updateGameObjects();
        super.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        GameThread gameRunnable = new GameThread(holder, this);
        gameThread = new Thread(gameRunnable);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.interrupt();
    }
}
