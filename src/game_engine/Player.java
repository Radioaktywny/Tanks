package game_engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Player extends GameObject{
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

    public Player(Bitmap res, int width, int height, int numFrames,int armor,int health) {
    	speed=10;
        x = 1000;
        y = 250 ;
        
        
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

    public void setUp(boolean b){
    	up = b;
    	    
    }
    public void setDown(boolean b){
    	down = b;
    	up = false;
    	right = false;
    	left = false;    
    }
    public void setLeft(boolean b){
    	down = false;
    	up = false;
    	right = false;
    	left = b;    
    }
    public void setRight(boolean b){
    	down = false;
    	up = false;
    	right = b;
    	left = false;    
    }
    public void update()
    {
        //long elapsed = (System.nanoTime()-startTime)/1000000;
        //animation.update();

        if(up){
            y = y-speed;
        }
        if(down)
        {
        	y=y+speed;
        }
        if(right)
        {
        	x=x+speed;
        }
        if(left)
        {
        	x=x-speed;
        }

    }
    public String getKierunek()
    {
    	if(up)
    	return "gora";
    	else if(down)
        	return "dol";
    	else if(left)
        	return "lewa";
    	else if(right)
        	return "prawa";
		return null;
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
}