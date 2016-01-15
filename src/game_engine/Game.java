package game_engine;

import com.example.tanks.R;
import com.zerokol.views.JoystickView;
import com.zerokol.views.JoystickView.OnJoystickMoveListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class Game extends Activity implements OnTouchListener{

	private Button b_up,b_down,b_left,b_right;
	private ImageView tank;
	private String kierunek="nic";
	FrameLayout game;
	RelativeLayout GameButtons;
	public JoystickView joystick;
	private GamePanel gameView;
	Handler handgame ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	        //turn title off
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        //set to full screen
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
 
		game = new FrameLayout(this);  
		gameView = new GamePanel(this); 
		GameButtons =new RelativeLayout(this);
		RelativeLayout GameButtons = new RelativeLayout(this);	
		View v = getLayoutInflater().inflate(R.layout.przyciski_layout, null);
		joystick = (JoystickView) findViewById(R.id.joystickView);
		GameButtons.addView(v);
		game.addView(gameView);  
		game.addView(GameButtons);  
		setContentView(game);
		init_joistick((JoystickView) findViewById(R.id.joystickView));					
}
	void init_joistick(JoystickView joystick){
		joystick.setOnJoystickMoveListener(new OnJoystickMoveListener() {
			@Override
			public void onValueChanged(int angle, int power, int direction) {
				switch (direction) {
				case JoystickView.FRONT:
					gameView.steruj("gora");
					break;
				case JoystickView.FRONT_RIGHT:
					gameView.steruj("");
					break;
				case JoystickView.LEFT:
					gameView.steruj("prawa");
					break;
				case JoystickView.RIGHT_BOTTOM:
					gameView.steruj("");
					break;
				case JoystickView.BOTTOM:
					gameView.steruj("dol");
					break;
				case JoystickView.BOTTOM_LEFT:
					gameView.steruj("");
					break;
				case JoystickView.RIGHT:
					gameView.steruj("lewa");
					break;
				case JoystickView.LEFT_FRONT:
					gameView.steruj("");
					break;
				default:
					gameView.steruj("nie rob nic prosze cie");
				}
			}
		}, JoystickView.DEFAULT_LOOP_INTERVAL);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		if(v.getId()==R.id.button2)
		{
			kierunek="up";
			return true;
		}
		else if(v.getId()==R.id.button3)
		{
			kierunek="left";
			return true;
		}
		else if(v.getId()==R.id.button4)
		{
			kierunek="down";
			return true;
		}
		else if(v.getId()==R.id.button5)
		{
			kierunek="right";
			return true;
		}
		kierunek="nic";
		return false;
	}
}
