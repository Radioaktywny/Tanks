package com.example.tanks.engine;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Marcin on 23.10.2017.
 */
public class GameThreadTest {

    @Mock
    SurfaceView surfaceView;

    @Mock
    SurfaceHolder surfaceHolder;

    @Mock
    Canvas canvas;

    @InjectMocks
    GameThread testedClass;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(surfaceHolder.lockCanvas()).thenReturn(canvas);
        testedClass = new GameThread(surfaceHolder, surfaceView);
    }

    @Test
    public void testIfStarted_ThenThreadIsRefreshingUI() {
        Thread gameThread = givenStartedGameThread();
        whenWaitForASecondAndInterruptGameThread(gameThread);
        thenThereWasSomeUI_UpdatesRecorded();
    }

    @Test
    public void testIfInterrupted_ThenThreadIsNoMoreRefreshingUI() {
        Thread gameThread = givenStartedGameThread();
        whenWaitForASecondAndInterruptGameThread(gameThread);
        thenThereWasSomeUI_UpdatesRecorded();
        whenWaitingForSomeTime(100);
        reset(surfaceView,surfaceHolder);
        whenWaitingForSomeTime(2000);
        verifyZeroInteractions(surfaceHolder, surfaceView);
    }

    private void whenWaitingForSomeTime(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void thenThereWasSomeUI_UpdatesRecorded() {
        verify(surfaceHolder, atLeastOnce()).lockCanvas();
        verify(surfaceView, atLeastOnce()).draw(canvas);
    }

    private void whenWaitForASecondAndInterruptGameThread(Thread thread) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

    @NonNull
    private Thread givenStartedGameThread() {
        Thread thread = new Thread(testedClass);
        thread.start();
        return thread;
    }
}
