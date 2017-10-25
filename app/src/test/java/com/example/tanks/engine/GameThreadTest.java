package com.example.tanks.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
    private SurfaceView surfaceView;

    @Mock
    private SurfaceHolder surfaceHolder;

    @Mock
    private Canvas canvas;

    private GameThread testedClass;

    private CountDownLatch countDownLatch;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        setupCounterForLockCanvasMethod();
        testedClass = new GameThread(surfaceHolder, surfaceView);
    }

    private void setupCounterForLockCanvasMethod() {
        countDownLatch = new CountDownLatch(5);
        when(surfaceHolder.lockCanvas()).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                countDownLatch.countDown();
                return canvas;
            }
        });
    }

    @Test
    public void testIfStarted_ThenThreadIsRefreshingUI() throws InterruptedException {
        Thread thread = givenStartedGameThread();
        whenWaitForCountCounterDownAndInterrupt(thread, countDownLatch);
        thenThereWasSomeUI_UpdatesRecorded();
    }

    @Test
    public void testIfInterrupted_ThenThreadIsNoMoreRefreshingUI() throws InterruptedException {
        Thread gameThread = givenStartedGameThread();
        whenInterruptAndWaitForPossibleUIRefresh(gameThread, countDownLatch);
        thenZeroRefreshOccursAfterInterrupt();
    }

    private void whenWaitForCountCounterDownAndInterrupt(Thread thread,
                                                         CountDownLatch countDownLatch) throws InterruptedException {
        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        thread.interrupt();
    }

    private void thenZeroRefreshOccursAfterInterrupt() {
        verifyZeroInteractions(surfaceHolder, surfaceView);
    }

    private void whenInterruptAndWaitForPossibleUIRefresh(Thread gameThread,
                                                          CountDownLatch countDownLatch) throws InterruptedException {
        gameThread.interrupt();
        reset(surfaceView, surfaceHolder);
        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
    }

    private void thenThereWasSomeUI_UpdatesRecorded() {
        verify(surfaceHolder, atLeastOnce()).lockCanvas();
        verify(surfaceView, atLeastOnce()).draw(canvas);
    }

    private Thread givenStartedGameThread() {
        Thread thread = new Thread(testedClass);
        thread.start();
        return thread;
    }
}
