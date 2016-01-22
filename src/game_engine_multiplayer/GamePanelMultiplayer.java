package game_engine_multiplayer;

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
import bluetooth.ClientBluetooth;
import bluetooth.PrepareToMultiplayer;
import bluetooth.SerwerBluetooth;
import bot.Bot;
import game_engine.Background;
import game_engine.Bullet;
import game_engine.Explosion;
import game_engine.MainThread;
import game_engine.Player;

public class GamePanelMultiplayer extends SurfaceView implements SurfaceHolder.Callback {
	// public TextView txtplayerHP;
	// public TextView txtprzeciwnikHP;
	private View v;
	private String ruch_czolgu;
	private TextView txtplayerHP;
	private TextView txtprzeciwnikHP;
	private MainThreadMultiplayer thread;
	private Background bg;
	private Player player;
	private JoystickView joystick;
	Handler handgamepanel;
	private Explosion explosion;
	private ArrayList<Bullet> lista = new ArrayList();
	private Player player2;
	private int[] zakres = new int[2];
	boolean dostelem = false;
	SerwerBluetooth serwer=null;
	ClientBluetooth client=null;
	private PrepareToMultiplayer multiStrings= new PrepareToMultiplayer();
	public GamePanelMultiplayer(Context context, View v2,SerwerBluetooth serwer) {
		super(context);
		v = v2;
		// add the callback to the surfaceholder to intercept events
		getHolder().addCallback(this);
		thread = new MainThreadMultiplayer(getHolder(), this);
		joystick = (JoystickView) findViewById(R.id.joystickView);
		// make gamePanel focusable so it can handle events
		setFocusable(true);
		this.serwer=serwer;
	}
	public GamePanelMultiplayer(Context context, View v2,ClientBluetooth client) {
		super(context);
		v = v2;
		// add the callback to the surfaceholder to intercept events
		getHolder().addCallback(this);
		thread = new MainThreadMultiplayer(getHolder(), this);
		joystick = (JoystickView) findViewById(R.id.joystickView);
		// make gamePanel focusable so it can handle events
		setFocusable(true);
		this.client=client;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				thread.setRunning(false);
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			retry = false;
		}

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.tlo));
		// bg.setVector(-5);
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
		player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.tank), 30, 40, 30, 100, 100);
		player2 = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.tank), 100, 400, 30, 100, 100);
		player.setPlaying(true);
		txtplayerHP = (TextView) v.findViewById(R.id.player_HP);
		txtprzeciwnikHP = (TextView) v.findViewById(R.id.przeciwnik_HP);
		txtprzeciwnikHP.setText("BOT_HP:100");
		txtplayerHP.setText("HP:100");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;

	}
	
	public void steruj(String steruj) {
		ruch_czolgu = steruj;

		if (steruj.equals("lewa")) {
			player.setLeft(true);
			multiStrings.setDirectionToSend(new int[]{0,1});

		} else if (steruj.equals("prawa")) {
			player.setRight(true);
			multiStrings.setDirectionToSend(new int[]{2,1});

		} else if (steruj.equals("dol")) {
			player.setDown(true);
			multiStrings.setDirectionToSend(new int[]{1,0});
		}
		else if (steruj.equals("gora")) {
			player.setUp(true);
			multiStrings.setDirectionToSend(new int[]{1,2});

		} else {
			player.setNo();
			multiStrings.setDirectionToSend(new int[]{1,1});
		}
		
		if(serwer!=null)
			serwer.wyslij(multiStrings.sendToThread());
		else
			client.wyslij(multiStrings.sendToThread());

	}
	public void secondplayer()
	{
		multiStrings.sendTogame(client.getOdebrane());
		int i[]=multiStrings.getDirectionFromBT();
		if (i[0]==0) {
			player2.setLeft(true);

		} else if (i[0]==2) {
			player2.setRight(true);

		} else if (i[1]==0) {
			player2.setDown(true);

		}
		else if (i[1]==2) {
			player2.setUp(true);

		}		
		else {
			player2.setNo();
		}	
		
	}
	
	public void update() {

		if (player.getPlaying()) {
			bg.update();
			checkMove(player);
			checkMove(player2);
			// Log.d("METRYCKA",""); tu na dole dostosowalem do swojego ekrany
			// narazie nie zrpobilem auto pobierania rozmiaru
			// Log.d("PLAYER", "X"+String.valueOf(player.getX()) +
			// "Y"+String.valueOf(player.getY()));
			// x10
			try {
				if (!lista.isEmpty()) {
					for (int i = 0; i < lista.size(); i++) {
						lista.get(i).update();
					}
				}
			} catch (Exception e) {
				Log.d("Przycisk", e.getMessage());
				bg.update();
				player.update();
				player2.update();
			}
		}
	}

	public void checkMove(Player player) {
		if (player.getY() > 40 && player.getY() < 1280 && player.getX() > 40 && player.getX() < 2250) {
			player.update();
			// player2.update(player);
		} else {
			if (player.getY() <= 40 && (!ruch_czolgu.equals("gora")))
				player.update();
			if (player.getY() >= 1280 && (!ruch_czolgu.equals("dol")))
				player.update();
			if (player.getX() <= 40 && (!ruch_czolgu.equals("lewa")))
				player.update();
			if (player.getX() >= 2250 && (!ruch_czolgu.equals("prawa")))
				player.update();
		}
	}

	@Override
	public void draw(Canvas canvas) {
		float scaleFactorX = getWidth() / (getWidth() * 1.f);
		float scaleFactorY = getHeight() / (getHeight() * 1.f);
		if (canvas != null) {
			final int savedState = canvas.save();
			canvas.scale(scaleFactorX, scaleFactorY);
			bg.draw(canvas);
			if (player2.getHealth() > 0)
				player2.draw(canvas);
			if (player.getHealth() > 0)
				player.draw(canvas);
			if (!lista.isEmpty()) {
				for (int i = 0; i < lista.size(); i++) {
					lista.get(i).draw(canvas);
					sprawdz_czy_trafilem(false, i, canvas);// funkcja wymaga
															// poprawek
															// stylistycznych
					sprawdz_czy_trafilem(true, i, canvas);
				}
			}
			canvas.restoreToCount(savedState);
		}
	}

	private void sprawdz_czy_trafilem(boolean Czy_jestem_graczem, int i, Canvas canvas) {
		if (Czy_jestem_graczem == false) {
			Player zmienny = player2;
			int zakresX = zmienny.getX() - lista.get(i).getX();
			int zakresY = zmienny.getY() - lista.get(i).getY();
			if (zakresX >= -115 && zakresX <= 20 && zakresY >= -95 && zakresY <= 20) {
				Log.d("dostal", "X " + String.valueOf(zakresX) + "Y " + String.valueOf(zakresY) + "zycie" + "BOT:"
						+ String.valueOf(zmienny.getHealth() - lista.get(i).getPower()));
				explosion = new Explosion(BitmapFactory.decodeResource(getResources(), R.drawable.explosion),
						player2.getX(), player2.getY() - 30, 64, 64, 16);
				explosion.update();
				explosion.draw(canvas);
				player2.setHealth(lista.get(i).getPower());
				lista.remove(i);
			}
		} else {
			Player zmienny = player;
			int zakresX = zmienny.getX() - lista.get(i).getX();
			int zakresY = zmienny.getY() - lista.get(i).getY();
			if (zakresX >= -115 && zakresX <= 20 && zakresY >= -95 && zakresY <= 20) {
				Log.d("dostal", "X " + String.valueOf(zakresX) + "Y " + String.valueOf(zakresY) + "zycie" + "PLEYER:"
						+ String.valueOf(player.getHealth() - lista.get(i).getPower()));

				explosion = new Explosion(BitmapFactory.decodeResource(getResources(), R.drawable.explosion),
						player.getX(), player.getY() - 30, 64, 64, 16);
				explosion.update();
				explosion.draw(canvas);
				player.setHealth(lista.get(i).getPower());
				lista.remove(i);
			}
		}
	}

	public void strzel(String ostatni_ruch_czolgu, String rodzaj_pocisku) {
		try {
			int speed = 1;
			int power = 1;
			if (rodzaj_pocisku.equals("pocisk_1")) {
				speed = 13;
				power = 10;
			} else if (rodzaj_pocisku.equals("nuke")) {
				speed = 10;
				power = 50;
			}
			if (ostatni_ruch_czolgu.equals("prawa"))
				lista.add(new Bullet(odwrocony_obrazek_strzalu(ostatni_ruch_czolgu, rodzaj_pocisku),
						player.getX() + 120, player.getY() + 40, 1, 0, power, speed));
			else if (ostatni_ruch_czolgu.equals("lewa"))
				lista.add(new Bullet(odwrocony_obrazek_strzalu(ostatni_ruch_czolgu, rodzaj_pocisku),
						player.getX() - 120, player.getY() + 40, -1, 0, power, speed));
			else if (ostatni_ruch_czolgu.equals("gora"))
				lista.add(new Bullet(odwrocony_obrazek_strzalu(ostatni_ruch_czolgu, rodzaj_pocisku), player.getX() + 40,
						player.getY() - 120, 0, -1, power, speed));
			else if (ostatni_ruch_czolgu.equals("dol"))
				lista.add(new Bullet(odwrocony_obrazek_strzalu(ostatni_ruch_czolgu, rodzaj_pocisku), player.getX() + 40,
						player.getY() + 120, 0, 1, power, speed));
		} catch (Exception e) {
			Log.d("przycisk", e.getMessage());
		}
	}

	private Bitmap odwrocony_obrazek_strzalu(String kierunek, String rodzaj_pocisku) {
		int obroc = 0;
		Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.pocisk_1);
		;
		if (kierunek.equals("lewa")) {
			obroc = 180;
		} else if (kierunek.equals("dol")) {
			obroc = 90;
		} else if (kierunek.equals("gora")) {
			obroc = 270;
		}
		if (rodzaj_pocisku.equals("pocisk_1")) {
			bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.pocisk_1);
		} else if (rodzaj_pocisku.equals("nuke")) {
			bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.pocisk_nuke);
		}
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();
		int newWidth = bitmapOrg.getWidth();
		int newHeight = bitmapOrg.getHeight();
		;
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
		/**
		 * MEtoda dziala w watku do wyswietlania hp ale nie wiem jakim cudem wie
		 * kiedy trafiles zanim trafisz XD
		 **/
		txtprzeciwnikHP.setText("BOT_HP:" + String.valueOf(player2.getHealth()));
		txtplayerHP.setText("HP:" + String.valueOf(player.getHealth()));

		// TODO Auto-generated method stub

	}

}