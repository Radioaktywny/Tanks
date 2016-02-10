package game_engine;


import java.util.ArrayList;

import com.example.tanks.R;
import com.zerokol.views.JoystickView;
import com.zerokol.views.JoystickView.OnJoystickMoveListener;

import activities_menu.MainActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import bot.Bot;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback  
{
	//public TextView txtplayerHP;
	//public TextView txtprzeciwnikHP;
	private View v;
	private String ruch_czolgu;
	private TextView txtplayerHP;
 	private TextView  txtprzeciwnikHP;
	private MainThread thread;
    private Player player;
    private JoystickView joystick;
	Handler handgamepanel ;
	private Explosion explosion;
	private ArrayList<Bullet> lista = new ArrayList();
	private Bot player2;
	private int [] zakres= new int[2];
	boolean dostelem=false;
	private Bitmap scaled;
	private double scaleBTM[]=new double[2];
	private int hitBoxTank[]=new int[2];
	private int explosionBox[]=new int[2];
	private Explosion explosion1;
    public GamePanel(Context context, View v2)
    {	
        super(context);
        v=v2;
        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        joystick = (JoystickView) findViewById(R.id.joystickView);
        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

  @Override
    public void surfaceDestroyed(SurfaceHolder holder){
      boolean retry = true;
      while(retry)
      {
          try{thread.setRunning(false);
              thread.join();
          }catch(InterruptedException e){e.printStackTrace();}
          retry = false;
      }

  }
  private void createBg()
	{
		Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.tlo);
		float scale = (float) background.getHeight() / (float) getHeight();
		Log.d("INFO ", String.valueOf(getHeight()));
		int newWidth = Math.round(background.getWidth() / scale);
		int newHeight = Math.round(background.getHeight() / scale);
		// bg = new Background(BitmapFactory.decodeResource(getResources(),
		// R.drawable.tlo));
		// bg.setVector(-5);
		// we can safely start the game loop
		scaled = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);
	}
  @Override
    public void surfaceCreated(SurfaceHolder holder)
  {
	  createBg();
		createSizeOfExplosion();
		scaleBTM[1]=40.0/540.0;
		scaleBTM[0]=40.0/897.0;
		
		hitBoxTank[0]=(int)((float)getWidth() * scaleBTM[0]);
		hitBoxTank[1]=(int)((float)getHeight() * scaleBTM[1]);
      // bg.setVector(-5);
	  // we can safely start the game loop
      thread.setRunning(true);
      thread.start();
      player = new Player(Bitmap.createScaledBitmap((BitmapFactory.decodeResource(getResources(), R.drawable.tank2)),hitBoxTank[0],hitBoxTank[1],true), 40, 40, 30, 100, 100,(int)(hitBoxTank[0]/7));
      player2 = new Bot(Bitmap.createScaledBitmap((BitmapFactory.decodeResource(getResources(), R.drawable.tank2)),hitBoxTank[0],hitBoxTank[1],true), 40, 40, 30, 100, 100,(int)(hitBoxTank[0]/7),(int)(float)getWidth(),(int)(float)getHeight());
      player.setPlaying(true);
      txtplayerHP= (TextView) v.findViewById(R.id.player_HP);
  	  txtprzeciwnikHP= (TextView) v.findViewById(R.id.przeciwnik_HP);
      txtprzeciwnikHP.setText("BOT_HP:100");
      txtplayerHP.setText("HP:100");
  }
  private void createSizeOfExplosion() {
		// TODO Auto-generated method stub
		scaleBTM[1]=64.0/540.0;
		scaleBTM[0]=64.0/897.0;		
		explosionBox[0]=(int)((float)getWidth() * scaleBTM[0]);
		explosionBox[1]=(int)((float)getHeight() * scaleBTM[1]);
	}

  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
	return false;

  }
    	
        public void steruj(String steruj)
        {	ruch_czolgu=steruj;
        
        	if(steruj.equals("lewa"))
        	{
        				player.setLeft(true);   			
            			      	
        	}
        	else if(steruj.equals("prawa"))
        	{
        				player.setRight(true);   			
            		        	
        	}
        	else if(steruj.equals("dol"))
        	{
        				player.setDown(true);   			
            		  
        	}
        
            else if(steruj.equals("gora"))
            {
        			player.setUp(true);
        		
            }    		
            else
            {
            	player.setNo();
            }
        	
    	
    }
    public void update()
    {
    	 
    	if(player.getPlaying())
    	{			if (explosion != null)
			explosion.update();
		if(explosion1!=null)explosion1.update();
    		bot_strzelaj();//musialem mu tu wrzucic :(
    		int licz=1;
    		player2.update(player);    		
    		checkMove(player);
    		try{
    		if(!lista.isEmpty())
    		{	for(int i=0 ; i<lista.size() ; i++)
    			{
    			lista.get(i).update();
    			}
    		}
    		}catch(Exception e)
    		{
    			Log.d("Przycisk", e.getMessage());
    			player.update();
        		player2.update(player);
    		}
    		

    		
    	}

    }
    public void checkMove(Player player) {
		if (player.getY() > (int)(hitBoxTank[1]/2) && player.getY() < getHeight() - (int)((hitBoxTank[1]/2)*3) && player.getX() > (int)(hitBoxTank[0]/2)
				&& player.getX() < getWidth() - (int)((hitBoxTank[0]/2)*3)) {
			player.update();
		} else {
			if (player.getY() >= (int)(hitBoxTank[1]/2) && (ruch_czolgu.equals("gora")))
				player.update();
			if (player.getY() <= getHeight() - 60 && (ruch_czolgu.equals("dol")))
				player.update();
			if (player.getX() >= (int)(hitBoxTank[0]/2) && (ruch_czolgu.equals("lewa")))
				player.update();
			if (player.getX() <= getWidth() - (int)((hitBoxTank[0]/2)*3) && (ruch_czolgu.equals("prawa")))
				player.update();
		}
	}
    	

    
   
    private void bot_strzelaj() 
    {
    int power=10;
    int speed = (int)(hitBoxTank[0]/3);
	if(player2.getX() == player.getX())
	{
		if(player2.getY() < player.getY())
		{
			lista.add(new Bullet(odwrocony_obrazek_strzalu("dol", "pocisk_1"), player2.getX() + (int)(hitBoxTank[0]/2) -5, player2.getY() + hitBoxTank[1]+5, 0, 1, power, speed));
		} 
		else if (player2.getY() > player.getY())
		{	
			lista.add(new Bullet(odwrocony_obrazek_strzalu("gora", "pocisk_1"), player2.getX() + (int)(hitBoxTank[0]/2) -5, player2.getY() - hitBoxTank[1]-3, 0, -1, power, speed));
		}
	} 
	if(player2.getY() == player.getY())
	{
		if(player2.getX() < player.getX())
		{
			lista.add(new Bullet(odwrocony_obrazek_strzalu("prawa", "pocisk_1"), player2.getX() + hitBoxTank[0]+3,
					player2.getY() + (int)(hitBoxTank[1]/2)-5 /*do poprawy*/, 1, 0, power, speed));	
		}
		else if (player2.getX() > player.getY())
		{	
			lista.add(new Bullet(odwrocony_obrazek_strzalu("lewa", "pocisk_1"), player2.getX() - hitBoxTank[0]-3, player2.getY() + (int)(hitBoxTank[1]/2) -5, -1, 0, power, speed));
		}
	}	
	}

	@Override
    public void draw(Canvas canvas)
    {
		canvas.drawBitmap(scaled, 0, 0, null);
    float scaleFactorX= getWidth()/(getWidth()*1.f);
	float scaleFactorY=getHeight()/(getHeight()*1.f);
    if(canvas!=null)
    {
    	final int savedState = canvas.save();    		
		canvas.scale(scaleFactorX, scaleFactorY);    		
    	if(player2.getHealth()> 0)
    		player2.draw(canvas);
    	if(player.getHealth() > 0)
    		player.draw(canvas);	
    	if(!lista.isEmpty())
    	{	
    		for(int i=0 ; i<lista.size() ; i++)
    		{
    		lista.get(i).draw(canvas);
   			sprawdz_czy_trafilem(false,i, canvas);//funkcja wymaga poprawek stylistycznych
   			sprawdz_czy_trafilem(true,i, canvas);
    		}
    	}
    	if (explosion != null)
			explosion.draw(canvas);
		if (explosion1 != null)
			explosion1.draw(canvas);
		canvas.restoreToCount(savedState);
    }
    	
    }

	private void sprawdz_czy_trafilem(boolean Czy_jestem_graczem, int i, Canvas canvas) {
		if (Czy_jestem_graczem == true) {
			Bot zmienny = player2;
			int zakresX = zmienny.getX() - lista.get(i).getX();
			int zakresY = zmienny.getY() - lista.get(i).getY();
			if (zakresX >= -hitBoxTank[0] && zakresX <= 0 && zakresY >= -hitBoxTank[1] && zakresY <= 0) {
				Log.d("dostal", "X " + String.valueOf(zakresX) + "Y " + String.valueOf(zakresY) + "zycie" + "BOT:"
						+ String.valueOf(zmienny.getHealth() - lista.get(i).getPower()));
				explosion1 = new Explosion(Bitmap.createScaledBitmap((BitmapFactory.decodeResource(getResources(), R.drawable.explosion)),explosionBox[0]*4, explosionBox[1]*4,true),
						player2.getX()+(int)((hitBoxTank[0]-explosionBox[0])/2), player2.getY() + (int)((hitBoxTank[1]-explosionBox[1])/2), explosionBox[0],explosionBox[1],/*explosionBox[0], explosionBox[1],*/ 16);
				player2.setHealth(lista.get(i).getPower());
				lista.remove(i);
			}
		} else {
			Player zmienny = player;
			int zakresX = zmienny.getX() - lista.get(i).getX();
			int zakresY = zmienny.getY() - lista.get(i).getY();
			if (zakresX >= -hitBoxTank[0] && zakresX <= 0 && zakresY >= -hitBoxTank[1] && zakresY <= 0) {
				Log.d("dostal", "X " + String.valueOf(zakresX) + "Y " + String.valueOf(zakresY) + "zycie" + "PLEYER:"
						+ String.valueOf(player.getHealth() - lista.get(i).getPower()));

				explosion = new Explosion(Bitmap.createScaledBitmap((BitmapFactory.decodeResource(getResources(), R.drawable.explosion)),explosionBox[0]*4, explosionBox[1]*4,true),
						player.getX()+(int)((hitBoxTank[0]-explosionBox[0])/2), player.getY() + (int)((hitBoxTank[1]-explosionBox[1])/2), explosionBox[0],explosionBox[1],/*explosionBox[0], explosionBox[1],*/ 16);

				player.setHealth(lista.get(i).getPower());
				lista.remove(i);
			}
		}
	}

	public void strzel(String ostatni_ruch_czolgu,String rodzaj_pocisku) 
	{	
		try {
			int speed = 1;
			int power = 1;
			if (rodzaj_pocisku.equals("pocisk_1")) {
				speed = (int)(hitBoxTank[0]/3);
				power = 10;
			} else if (rodzaj_pocisku.equals("nuke")) {
				speed = (int)(hitBoxTank[0]/4);
				power = 50;
			}
		if(ostatni_ruch_czolgu.equals("prawa"))
			lista.add(new Bullet(odwrocony_obrazek_strzalu(ostatni_ruch_czolgu, rodzaj_pocisku), player.getX() + hitBoxTank[0]+3,
					player.getY() + (int)(hitBoxTank[1]/2)-5 /*do poprawy*/, 1, 0, power, speed));
		else if(ostatni_ruch_czolgu.equals("lewa"))
			lista.add( new Bullet(odwrocony_obrazek_strzalu(ostatni_ruch_czolgu, rodzaj_pocisku),
					player.getX() - hitBoxTank[0]-3, player.getY() + (int)(hitBoxTank[1]/2) -5, -1, 0, power, speed));
		else if(ostatni_ruch_czolgu.equals("gora"))
			lista.add( new Bullet(odwrocony_obrazek_strzalu(ostatni_ruch_czolgu, rodzaj_pocisku),
					player.getX() + (int)(hitBoxTank[0]/2) -5, player.getY() - hitBoxTank[1]-3, 0, -1, power, speed));
		else if(ostatni_ruch_czolgu.equals("dol"))
			lista.add(new Bullet(odwrocony_obrazek_strzalu(ostatni_ruch_czolgu, rodzaj_pocisku),
					player.getX() + (int)(hitBoxTank[0]/2) -5, player.getY() + hitBoxTank[1]+5, 0, 1, power, speed));
		}catch(Exception e)
		{
			//Log.d("przycisk", e.getMessage());
		}
	}
    private Bitmap odwrocony_obrazek_strzalu(String kierunek,String rodzaj_pocisku) {
		int obroc=0;
		Bitmap bitmapOrg=BitmapFactory.decodeResource(getResources(), R.drawable.pocisk_1);;
		if(kierunek.equals("lewa"))
		{
			obroc=180;
		}
		else if(kierunek.equals("dol"))
		{
			obroc=90;
		}
		else if(kierunek.equals("gora"))
		{
			obroc=270;
		}
		if(rodzaj_pocisku.equals("pocisk_1")){
		bitmapOrg =BitmapFactory.decodeResource(getResources(), R.drawable.pocisk_1);
		}else if(rodzaj_pocisku.equals("nuke")){
		bitmapOrg =BitmapFactory.decodeResource(getResources(), R.drawable.pocisk_nuke);
		}
		 int width = bitmapOrg.getWidth();
		    int height = bitmapOrg.getHeight();
		    int newWidth = bitmapOrg.getWidth();
		    int newHeight = bitmapOrg.getHeight();;
		    // calculate the scale - in this case = 0.4f
		    float scaleWidth = ((float) newWidth) / width;
		    float scaleHeight = ((float) newHeight) / height;	    
		    // createa matrix for the manipulation
		    Matrix matrix = new Matrix();
		    // resize the bit map
		    matrix.postScale(scaleWidth, scaleHeight);
		    // rotate the Bitmap
		    matrix.postRotate(obroc);
		    // recreate the new Bitmap
		    Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
		return resizedBitmap;
	}

	public void gui() {
		/** MEtoda dziala w watku do wyswietlania hp ale nie wiem jakim cudem wie kiedy trafiles zanim trafisz XD **/
		txtprzeciwnikHP.setText("BOT_HP:"+String.valueOf(player2.getHealth()));
		txtplayerHP.setText("HP:"+String.valueOf(player.getHealth()));
		
		// TODO Auto-generated method stub
		
	}


    

}