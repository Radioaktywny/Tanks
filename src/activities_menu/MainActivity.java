package activities_menu;

import com.example.tanks.R;

import view.JoystickViewDemoActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import bluetooth.Blutacz_main;
import bluetooth.Blutaczmenu;
import game_engine.Game;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void startgame(View view)
	{
        Intent activity= new Intent(MainActivity.this,Game.class);
		//Intent activity= new Intent(MainActivity.this,JoystickViewDemoActivity.class);
		startActivity(activity); 
		
	}
	public void startBlutacz(View view)
	{
		Intent activity= new Intent(MainActivity.this,Blutacz_main.class);
		//Intent activity= new Intent(MainActivity.this,JoystickViewDemoActivity.class);
		startActivity(activity); 
		
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
}
