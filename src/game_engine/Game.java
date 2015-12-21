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
import android.widget.ImageView;

public class Game extends Activity implements OnTouchListener{

	private Button b_up,b_down,b_left,b_right;
	private ImageView tank;
	private String kierunek="nic";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	        //turn title off
	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	        //set to full screen
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new GamePanel(this));
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
