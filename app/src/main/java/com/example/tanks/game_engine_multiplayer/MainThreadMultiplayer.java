package com.example.tanks.game_engine_multiplayer;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThreadMultiplayer extends Thread
{
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanelMultiplayer GamePanelMultiplayer;
    private boolean running;
    public static Canvas canvas;

    public MainThreadMultiplayer(SurfaceHolder surfaceHolder, GamePanelMultiplayer GamePanelMultiplayer)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.GamePanelMultiplayer = GamePanelMultiplayer;
    }
    @Override
    public void run()
    {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount =0;
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                	this.GamePanelMultiplayer.secondplayer();
                    this.GamePanelMultiplayer.update();
                    this.GamePanelMultiplayer.draw(canvas);
                    this.GamePanelMultiplayer.gui();
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
            finally{
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }




            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS)
            {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount =0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
    public void setRunning(boolean b)
    {
        running=b;
    }
}