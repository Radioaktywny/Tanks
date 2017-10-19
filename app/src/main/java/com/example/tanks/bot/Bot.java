package com.example.tanks.bot;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.animation.Animation;

import com.example.tanks.game_engine.GameObject;
import com.example.tanks.game_engine.Player;


public class Bot extends GameObject {
	//nie wolno zmienia� speeda ! xd jak przestawisz to musisz w losuj Liczbe odpowiednio zmienic
    private Bitmap spritesheet;
    private int health;
    private int armor;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean playing;
    private Animation animation;
	private int speed;
	private int podazamX;
	private int podazamY;
	int szer;
	private int wys;
	private Bitmap imagelast;
	public Bot(Bitmap res, int width, int height, int numFrames,int armor,int health,int speed,int szer,int wys) {
    	this.speed=speed;
        
        
        this.szer=szer;
        this.wys=wys;
        this.health=health;
        this.armor = armor;
        this.height = height;
        this.width = width;
        
        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;
        imagelast=res;

//        for (int i = 0; i < image.length; i++)
//        {
//            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
//        }
//		animation.setFrames(image);
//        animation.setDelay(10);
        x = losujLiczbe("x");
        y = losujLiczbe("y");
        podazamX=losujLiczbe("x");
        podazamY=losujLiczbe("y");

    }

  
    public void update(Player player)
    {
         	
        	if(podazamX > x)
        	{	
        		setNo();
        		right=true;
        		x=x+speed;
        	}
        	else if(podazamX < x){
        		setNo();
        		left=true;
        		x=x-speed;
        	}
        	if(podazamY > y)
        	{
        		setNo();
        		down=true;
        		 y = y+speed;
        	}else if (podazamY < y){
        		setNo();
        		up=true;
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
    	if (getKierunek() != null)
			imagelast = odwroc_czolg(getKierunek());
        canvas.drawBitmap(imagelast,x,y,null);
    }
    public int getHealth(){return health;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}

	private void setNo() {
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
	public String getKierunek() {
		if (up)
			return "gora";
		else if (down)
			return "dol";
		else if (left)
			return "lewa";
		else if (right)
			return "prawa";
		return null;
	}
	private int losujLiczbe(String x_y)
	{
			if(x_y.equals("x"))
			return 30+(new Random().nextInt((szer/speed)-6))*speed;
			else
			return 30+(new Random().nextInt((wys/speed)-6))*speed;
	}
	private Bitmap odwroc_czolg(String kierunek) {
		int obroc = 0;
		if (kierunek.equals("dol")) 
		{
			if(kierunek.equals("prawa"))
				obroc=225;
			else if(kierunek.equals("lewa"))
				obroc=315;
			else
			obroc = 270;
		} else if (kierunek.equals("gora")) 
		{
			if(kierunek.equals("prawa"))
				obroc=135;
			else if(kierunek.equals("lewa"))
				obroc=45;
			else
			obroc = 90;
		}
		else if(kierunek.equals("prawa"))
			obroc=180;
		
		int width = spritesheet.getWidth();
		int height = spritesheet.getHeight();
		int newWidth = spritesheet.getWidth();
		int newHeight = spritesheet.getHeight();

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
		Bitmap resizedBitmap = Bitmap.createBitmap(spritesheet, 0, 0, width, height, matrix, true);
		return resizedBitmap;
	}
}