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
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    private MainThread thread;
    private Background bg;
    private Player player;
    private JoystickView joystick;
	Handler handgamepanel ;
	private Explosion explosion;
	private ArrayList<Bullet> lista = new ArrayList();
	private Player player2;
	private int [] zakres= new int[2];
	boolean dostelem=false;
    public GamePanel(Context context)
    {
        super(context);
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

  @Override
    public void surfaceCreated(SurfaceHolder holder)
  {
	  
	  bg= new Background(BitmapFactory.decodeResource(getResources(), R.drawable.tlo));
      // bg.setVector(-5);
	  // we can safely start the game loop
      thread.setRunning(true);
      thread.start();
      player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.tank),30,40,30,100,100);
      player2 = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.tank),100,400,30,100,100);
      player.setPlaying(true);
      
      

  }
  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
	return false;

  }
    	
        public void steruj(String steruj)
        {	
        
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
    	{
    		bg.update();
    		player.update();
    		player2.update();
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
    			bg.update();
        		player.update();
        		player2.update();
    		}
    		

    		
    	}
    	
    	
    	
    	
    }
    	
    	

    
   
    @Override
    public void draw(Canvas canvas)
    {
    	float scaleFactorX= getWidth()/(getWidth()*1.f);
		float scaleFactorY=getHeight()/(getHeight()*1.f);
    	if(canvas!=null)
    	{
    		final int savedState = canvas.save();    		
			canvas.scale(scaleFactorX, scaleFactorY);    		
    		bg.draw(canvas);
    		player.draw(canvas);
    		if(player2.getHealth()>0)
    		player2.draw(canvas);
    		if(!lista.isEmpty())
    		{	for(int i=0 ; i<lista.size() ; i++)
    			{
    			lista.get(i).draw(canvas);
    			Log.d("gra player2", String.valueOf(player2.getX()));
    			Log.d("gra bomba", String.valueOf(lista.get(i).getX()));
    			int zakresX= player2.getX() - lista.get(i).getX();
    			int zakresY =player2.getY() - lista.get(i).getY();
    			if(zakresX<0)
    			{
    				zakresX=zakresX*-1;
    			}
    			if(zakresY<0)
    			{
    				zakresY=zakresY*-1;	
    			}
    				
    			if(zakresX > 10 && zakresX < 100  && zakresY > 5 && zakresY< 100)
    			{	Log.d("dostal", "X "+String.valueOf(zakresX)+"Y "+String.valueOf(zakresY)+"zycie"+String.valueOf(player2.getHealth()));
    			
    				explosion = new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),player2.getX(),
    			              player2.getY()-30, 64, 64, 16);
    				explosion.update();
    				explosion.draw(canvas);
    				
    				player2.setHealth(lista.get(i).getPower());
    				lista.remove(i);
    			}
    			}
    		}
    		//}
    		canvas.restoreToCount(savedState);
    	}
    	
    }

    public void strzel(String ostatni_ruch_czolgu) {
		try{
		if(ostatni_ruch_czolgu.equals("prawa"))
		lista.add(new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.pocisk_1), player.getX(), player.getY()+40 ,1,0));
		else if(ostatni_ruch_czolgu.equals("lewa"))
		lista.add(new Bullet(odwrocony_obrazek_strzalu(ostatni_ruch_czolgu), player.getX(), player.getY()+40 ,-1,0));
		else if(ostatni_ruch_czolgu.equals("gora"))
		lista.add(new Bullet(odwrocony_obrazek_strzalu(ostatni_ruch_czolgu), player.getX()+40, player.getY() ,0,-1));
		else if(ostatni_ruch_czolgu.equals("dol"))
		lista.add(new Bullet(odwrocony_obrazek_strzalu(ostatni_ruch_czolgu), player.getX()+40, player.getY() ,0,1));
		}catch(Exception e)
		{
			Log.d("przycisk", e.getMessage());
		}
		
		 
		}
    private Bitmap odwrocony_obrazek_strzalu(String kierunek) {
		int obroc=0;
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
		// TODO Auto-generated method stub
		Bitmap bitmapOrg =BitmapFactory.decodeResource(getResources(), R.drawable.pocisk_1);
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
		    Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
		                      width, height, matrix, true);
		return resizedBitmap;
	}
    

}