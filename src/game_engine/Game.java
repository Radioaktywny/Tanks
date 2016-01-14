package game_engine;

import com.example.tanks.R;

import android.app.Activity;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class Game extends Activity implements OnTouchListener{

	private Button b_up,b_down,b_left,b_right;
	private ImageView tank;
	private String kierunek="nic";
	FrameLayout game;
	RelativeLayout GameButtons;
	private GamePanel gameView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	        //turn title off
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        //set to full screen
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        
	//	setContentView(new GamePanel(this));
			game = new FrameLayout(this);  
			gameView = new GamePanel(this); 
			GameButtons = new RelativeLayout(this);  
		  RelativeLayout GameButtons = new RelativeLayout(this);
		  Button butOne = new Button(this);  
		  butOne.setText("PRZYCISKDZIALA");  
		  butOne.setId(123456);
		  RelativeLayout.LayoutParams b1 = new LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT,  RelativeLayout.LayoutParams.WRAP_CONTENT); 
		  RelativeLayout.LayoutParams params = new LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT);  
		  GameButtons.setLayoutParams(params);  
		  GameButtons.addView(butOne);       
		  b1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);  
		  b1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		  butOne.setLayoutParams(b1); 
		  game.addView(gameView);  
		  game.addView(GameButtons);  
		  setContentView(game); 
//		init();
		
	}

	private void init() {
		b_up=(Button) findViewById(R.id.button2);
		b_down=(Button) findViewById(R.id.button4);
		b_left=(Button) findViewById(R.id.button3);
		b_right=(Button) findViewById(R.id.button5);
		tank=(ImageView) findViewById(R.id.imageView1);
		b_up.setOnTouchListener(this);
		b_down.setOnTouchListener(this);
		b_left.setOnTouchListener(this);
		b_right.setOnTouchListener(this);
		// TODO Auto-generated method stub
		
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
