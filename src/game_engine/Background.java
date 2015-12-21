package game_engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background 

{

	private Bitmap image;
	private int x,y;
	private int dx=0;
	public Background(Bitmap res){
		image = res;
		
	}
	public void update()
	{
		
		x+=dx;
		
	}
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(image, x, y,null);
	}
	public void setVector(int dx)
	{
		this.dx=dx;
	}
}
