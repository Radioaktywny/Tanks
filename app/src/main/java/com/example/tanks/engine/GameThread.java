package com.example.tanks.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameThread implements Runnable {

    private final int FPS;
    private long startTime;
    private final SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, SurfaceView surfaceView) {
        this.surfaceHolder = surfaceHolder;
        this.surfaceView = surfaceView;
        this.FPS = 30;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                startTime = System.nanoTime();
                updateUI();
                waitToMakeCorrectFPSNumber();
            }
        } catch (InterruptedException e) {
            System.out.println("Main game thread interrupted");
        }
    }

    private void updateUI() {
        canvas = null;
        try {
            lockCanvasAndUpdateUI();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            unlockCanvasAndPost();
        }
    }

    private void waitToMakeCorrectFPSNumber() throws InterruptedException {
        long targetTime = 1000 / FPS;
        long timeMillis = (System.nanoTime() - startTime) / 1000000;
        long waitTime = targetTime - timeMillis;
        Thread.sleep(waitTime);
    }

    private void lockCanvasAndUpdateUI() {
        canvas = this.surfaceHolder.lockCanvas();
        updateAndDrawOnUI();
    }

    private void updateAndDrawOnUI() {
        synchronized (surfaceHolder) {
            this.surfaceView.draw(canvas);
        }
    }

    private void unlockCanvasAndPost() {
        if (canvas != null) {
            try {
                surfaceHolder.unlockCanvasAndPost(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
