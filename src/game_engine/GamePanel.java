package game_engine;


import com.example.tanks.R;
import com.zerokol.views.JoystickView;
import com.zerokol.views.JoystickView.OnJoystickMoveListener;

import activities_menu.MainActivity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
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
      player.setPlaying(true);
      

  }
  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
  	if(event.getAction()==MotionEvent.ACTION_DOWN)
  	{
  		System.out.println(event.getRawY()+"  "+event.getRawX());
  		
  		System.out.println(getHeight()+" " +getWidth());
  		if(event.getRawY()>(getHeight()-200))
  		{
  			if(event.getRawX()<((getWidth()/4)))
  			{
  				player.setLeft(true);   			
      			return true;        	
  			}
  			else if(event.getRawX()>(getWidth()-(getWidth()/4)))
  			{
  				player.setRight(true);   			
      			return true;        	
  			}
  			else 
  			{
  				player.setDown(true);   			
      			return true;   
  			}
  		}
  		else if(event.getRawY()>(getHeight()-400))
  		{
  			player.setUp(true);
  			return true;
  		}    		
  		   		
  	}
  	if(event.getAction()==MotionEvent.ACTION_UP)
  	{
  		player.setNo();	   	
  		return super.onTouchEvent(event);
  	}
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
    		canvas.restoreToCount(savedState);
    	}
    	
    }
}