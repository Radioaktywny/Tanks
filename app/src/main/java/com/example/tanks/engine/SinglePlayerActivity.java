package com.example.tanks.engine;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.example.tanks.R;

/**
 * Created by Marcin on 24.10.2017.
 */
public class SinglePlayerActivity extends Activity implements View.OnTouchListener {

    private GamePanel gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = createFrameLayoutWithGameView();
        setContentView(frameLayout);
    }

    private FrameLayout createFrameLayoutWithGameView() {
        View v = getLayoutInflater().inflate(R.layout.przyciski_layout, null);
        FrameLayout frameLayout = new FrameLayout(this);
        gameView = new GamePanel(this, v);
        frameLayout.addView(gameView);
        return frameLayout;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

}
