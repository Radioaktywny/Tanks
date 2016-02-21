package activities_menu;

import com.example.tanks.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import bluetooth.Blutacz_main;
import game_engine.Game;

public class MainActivity extends Activity {
	public ViewPager viewpager;
	Swip_menu adapter;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewpager = (ViewPager) findViewById(R.id.menu_pager);
		adapter = new Swip_menu(this);	
		viewpager.setAdapter(adapter);
		
	}
	public void nacisnieto(View view)
	{	Log.d("dupa" , String.valueOf(viewpager.getCurrentItem()));
		if(viewpager.getCurrentItem() == 1)
		{
			Intent activity= new Intent(MainActivity.this,Game.class);
			//Intent activity= new Intent(MainActivity.this,JoystickViewDemoActivity.class);
			startActivity(activity); 
		}
		if(viewpager.getCurrentItem() == 0)
		{
			Intent activity= new Intent(MainActivity.this,Blutacz_main.class);
			//Intent activity= new Intent(MainActivity.this,JoystickViewDemoActivity.class);
			startActivity(activity);  
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
//	public void startgame(View view)
//	{
//        Intent activity= new Intent(MainActivity.this,Game.class);
//		//Intent activity= new Intent(MainActivity.this,JoystickViewDemoActivity.class);
//		startActivity(activity); 
//		
//	}
//	public void startBlutacz(View view)
//	{
//		Intent activity= new Intent(MainActivity.this,Blutacz_main.class);
//		//Intent activity= new Intent(MainActivity.this,JoystickViewDemoActivity.class);
//		startActivity(activity); 
//		
//	}
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
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//	}
//	@Override
//	protected void onDestroy() {
//		
//		// TODO Auto-generated method stub
//		super.onDestroy();
//	}
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub		
//		super.onResume();
//	}
}
