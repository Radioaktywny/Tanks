package game_engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bullet extends GameObject
{
	private int direction_y;
	private int direction_x;
	private int speed;
	private int power;
	private Bitmap spritesheet;
	public Bullet(Bitmap res, int width, int height)
	{
		spritesheet=res;
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
	
}
