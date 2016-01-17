package bot;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.animation.Animation;
import game_engine.GameObject;
import game_engine.Player;


public class Bot extends GameObject{
	//nie wolno zmieniaæ speeda ! xd jak przestawisz to musisz w losuj Liczbe odpowiednio zmienic
    private Bitmap spritesheet;
    private int health;
    private int armor;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean playing;
    private Animation animation;
    private long startTime;
	private int speed;
	private int podazamX;
	private int podazamY;
	public Bot(Bitmap res, int width, int height, int numFrames,int armor,int health) {
    	speed=10;
        x = losujLiczbe("x");
        y = losujLiczbe("y");
        podazamX=losujLiczbe("x");
        podazamY=losujLiczbe("y");
        
        
        this.health=health;
        this.armor = armor;
        this.height = height;
        this.width = width;
        
        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

//        for (int i = 0; i < image.length; i++)
//        {
//            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
//        }
//		animation.setFrames(image);
//        animation.setDelay(10);
        startTime = System.nanoTime();

    }

  
    public void update(Player player)
    {
        //long elapsed = (System.nanoTime()-startTime)/1000000;
        //animation.update();
    
    //    if(player.getY()  < 600 && player.getY()  > 0){
        	
       // new Random().nextInt();
    //	Log.d("BOT", "x"+String.valueOf(x) +"y"+String.valueOf(x)+"ide:"+String.valueOf(podazamX)+"y"+String.valueOf(podazamY));
    	
        	if(podazamX > x)
        	{	
        		
        		x=x+speed;
        	}
        	else if(podazamX < x){
        		x=x-speed;
        	}
        	if(podazamY > y)
        	{
        		 y = y+speed;
        	}else if (podazamY < y){
        		y=y-speed;
        	}
        	if(podazamX == x && podazamY == y )
        	{
        		podazamX=losujLiczbe("x");
        		podazamY=losujLiczbe("");
        	}
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(spritesheet,x,y,null);
    }
    public int getHealth(){return health;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}

	public void setNo() {
		// TODO Auto-generated method stub
		up=false;
		down = false;
    	left = false;
    	right = false;
	}
	public void setHealth(int x) {
		// TODO Auto-generated method stub
		health=health-x;
	}
	
	private int losujLiczbe(String x_y)
	{
			if(x_y.equals("x"))
			return 50+(new Random().nextInt(200))*speed;
			else
			return 60+(new Random().nextInt(120))*speed;
	}
}