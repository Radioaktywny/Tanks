package com.example.tanks.game_engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bullet extends GameObject
{
	private int direction_y;
	private int direction_x;
	private int speed;
	private int power;
	private Bitmap spritesheet;
	public Bullet(Bitmap res, int x, int y , int direction_x,int direction_y,int power, int speed)
	{
		spritesheet=res;
		this.x=x;
		this.y=y;
		this.direction_x=direction_x;
		this.direction_y=direction_y;
		this.power=power;
		this.speed=speed;
	}
	
	public void update()
	{	
		x+=speed*direction_x;
		y+=speed*direction_y;
	}

	public void draw(Canvas canvas)
    {
        canvas.drawBitmap(spritesheet,x,y,null);
    }
	public int getPower()
	{
		return power;
	}

	public int[] getDirection() 
	{
		// TODO Auto-generated method stub
		return new int[]{direction_x,direction_y};
	}
}
