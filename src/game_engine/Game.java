package game_engine;

import java.io.IOException;

import com.example.tanks.R;
import com.zerokol.views.JoystickView;
import com.zerokol.views.JoystickView.OnJoystickMoveListener;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity implements OnTouchListener{

	private Button b_up,b_down,b_left,b_right;
	private ImageView tank;
	private String kierunek="nic";
	private String Ostatni_ruch_czolgu;
	FrameLayout game;
	RelativeLayout GameButtons;
	public JoystickView joystick;
	private GamePanel gameView;
	
	Handler handgame ;
	private MediaPlayer mPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	        //turn title off
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        //set to full screen
	        
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        View v = getLayoutInflater().inflate(R.layout.przyciski_layout, null);   
		game = new FrameLayout(this);  
		gameView = new GamePanel(this ,v); 

		GameButtons =new RelativeLayout(this);
		RelativeLayout GameButtons = new RelativeLayout(this);	
		
		joystick = (JoystickView) findViewById(R.id.joystickView);
		GameButtons.addView(v);
		game.addView(gameView);  
		game.addView(GameButtons); 
	
		setContentView(game);
		setRequestedOrientation((ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)/2);
		init_joistick((JoystickView) findViewById(R.id.joystickView));
		music();
		init_button_strzal((ImageButton) findViewById(R.id.strzal));
}
	private void init_button_strzal(ImageButton imageButton) {
		imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gameView.strzel(Ostatni_ruch_czolgu,"pocisk_1");				
			}
		});
		imageButton.setOnLongClickListener(new OnLongClickListener() {
			
			
			@Override
			public boolean onLongClick(View v) {
				gameView.strzel(Ostatni_ruch_czolgu,"nuke");
				return true;
			}
		});
	}
	private void music() 
	{
		// TODO Auto-generated method stub
		 mPlayer = new MediaPlayer();
	       try {
	    	   AssetManager mngr = getAssets();
	    	   AssetFileDescriptor afd = mngr.openFd("music.mp3");
	          mPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
	          mPlayer.prepare();
		} catch (IllegalArgumentException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
	    //   mPlayer.start();
	}
	private void init_joistick(JoystickView joystick){
		joystick.setOnJoystickMoveListener(new OnJoystickMoveListener() {
			@Override
			public void onValueChanged(int angle, int power, int direction) {
				switch (direction) {
				case JoystickView.FRONT:
					Ostatni_ruch_czolgu="gora";
					gameView.steruj("gora");
					break;
				case JoystickView.FRONT_RIGHT:
					gameView.steruj("");
					Ostatni_ruch_czolgu="";
					break;
				case JoystickView.LEFT:
					gameView.steruj("prawa");
					Ostatni_ruch_czolgu="prawa";
					break;
				case JoystickView.RIGHT_BOTTOM:
					gameView.steruj("");
					Ostatni_ruch_czolgu="";
					break;
				case JoystickView.BOTTOM:
					Ostatni_ruch_czolgu="dol";
					gameView.steruj("dol");
					
					break;
				case JoystickView.BOTTOM_LEFT:
					Ostatni_ruch_czolgu="";
					gameView.steruj("");
					break;
				case JoystickView.RIGHT:
					Ostatni_ruch_czolgu="lewa";
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
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mPlayer.pause();
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mPlayer.stop();
		super.onDestroy();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
	//	mPlayer.start();
		super.onResume();
	}
	public int getMetrics(String x_or_y)
	{
		DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if(x_or_y.equals("x"))
        return metrics.widthPixels;
        if(x_or_y.equals("y"))
        return metrics.heightPixels;
        return 0;
	}
}
