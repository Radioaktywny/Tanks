package com.example.tanks.engine;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;

import com.example.tanks.R;
import com.zerokol.views.JoystickView;

/**
 * Created by Marcin on 23.10.2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private final View viewForControls;

    private Engine engine;

    private Thread gameThread;

    private JoystickView joystick;

    private ImageButton shootButton;


    public GamePanel(Context context, View viewForControls) {
        super(context);
        this.viewForControls = viewForControls;
        //viewforControls - potrzebne zeby pokazac Hp i takie tam
        // co do tego co nizej to wyczytalem ze przekazywanie contextu do innych klas moze powodowac memory leaks
        engine = new Engine(defineImagesUsedForGames());
        initJoystickAndShootButton();
    }

    //warto przemyslec jak by to zrobic w lepszy sposob
    private BitmapsForGame defineImagesUsedForGames() {
        return new BitmapsForGame(BitmapFactory.decodeResource(getResources(), R.drawable.tank2),
                BitmapFactory.decodeResource(getResources(), R.drawable.pocisk_1),
                BitmapFactory.decodeResource(getResources(), R.drawable.pocisk_nuke));
    }

    private void initJoystickAndShootButton() {
        joystick = (JoystickView) findViewById(R.id.joystickView);
        shootButton = (ImageButton) findViewById(R.id.strzal);
        engine.prepareJoystick(joystick);
        engine.prepareShootButton(shootButton);
    }


    @Override
    public void draw(Canvas canvas) {
        engine.updateGameObjects();
        engine.drawGameObjects(canvas);
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
