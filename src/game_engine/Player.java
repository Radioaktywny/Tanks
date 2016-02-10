package game_engine;

import com.example.tanks.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Player extends GameObject {
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
	private Bitmap imagelast;

	public Player(Bitmap res, int width, int height, int numFrames, int armor, int health) {
		speed = 10;
		x = 200;
		y = 200;

		this.health = health;
		this.armor = armor;
		this.height = height;
		this.width = width;

		Bitmap[] image = new Bitmap[numFrames];
		spritesheet = res;
		imagelast=res;

		// for (int i = 0; i < image.length; i++)
		// {
		// image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width,
		// height);
		// }
		// animation.setFrames(image);
		// animation.setDelay(10);
		startTime = System.nanoTime();

	}

	public void setUp(boolean b) {
		up = b;

	}

	public void setDown(boolean b) {
		down = b;
		up = false;
		right = false;
		left = false;

	}

	public void setLeft(boolean b) {
		down = false;
		up = false;
		right = false;
		left = b;
	}

	public void setRight(boolean b) {
		down = false;
		up = false;
		right = b;
		left = false;

	}

	public void update() {
		// long elapsed = (System.nanoTime()-startTime)/1000000;
		// animation.update();

		if (up) {
			y = y - speed;
		}
		if (down) {
			y = y + speed;
		}
		if (right) {
			x = x + speed;
		}
		if (left) {
			x = x - speed;
		}

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

	public void draw(Canvas canvas) {
		if (getKierunek() != null)
			imagelast = odwroc_czolg(getKierunek());
		canvas.drawBitmap(imagelast, x, y, null);
	}

	public int getHealth() {
		return health;
	}

	public boolean getPlaying() {
		return playing;
	}

	public void setPlaying(boolean b) {
		playing = b;
	}

	public void setNo() {
		// TODO Auto-generated method stub
		up = false;
		down = false;
		left = false;
		right = false;
	}

	public void setHealth(int x) {
		// TODO Auto-generated method stub
		health = health - x;
	}

	private Bitmap odwroc_czolg(String kierunek) {
		int obroc = 0;
		if (kierunek.equals("prawa")) {
			obroc = 180;
		} else if (kierunek.equals("dol")) {
			obroc = 270;
		} else if (kierunek.equals("gora")) {
			obroc = 90;
		}
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