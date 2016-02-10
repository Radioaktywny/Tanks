package bluetooth;

import android.util.Log;
import game_engine.Bullet;

public class PrepareToMultiplayer 
{
	private volatile String iWillsendIt;
	private volatile String iGotThis="brak";
	private int[] xy=new int[]{1,1};
	private boolean newBullet=false;
	private int bulletPower=0;
	private int bulletDirection[]= new int[]{1,1};
	public PrepareToMultiplayer() {
		// TODO Auto-generated constructor stub
	}
	private void createStringToSend(Bullet bullet)
	{
		iWillsendIt=String.valueOf(xy[0])+";"+String.valueOf(xy[1]);
		int dir[]= bullet.getDirection();
		iWillsendIt=iWillsendIt+";"+bullet.getPower()+";"+dir[0]+";"+dir[1]+";?";
	}
	private synchronized void  createStringToSend()
	{
		iWillsendIt=String.valueOf(xy[0])+";"+String.valueOf(xy[1])+";?";
	}
	public synchronized void setDirectionToSend(int []xy)
	{
		this.xy=xy;
	}
	public int[] getDirectionFromBT()
	{
		return getDirectionFromString();
	}
	private int [] getDirectionFromString()
	{
		int xy[]=new int[2];
		xy[0]=1;xy[1]=1;
		int licznik=0;
		int lasti=0;
		for(int i=0;i<iGotThis.length();++i)
		{
		if(iGotThis.substring(i, i+1).equals(";"))
		{
		xy[licznik]=Integer.valueOf(iGotThis.substring(lasti,i));
		lasti=i+1;
		++licznik;
		}
		if(licznik==2)
		{
			if(iGotThis.substring(i+1, i+2).equals("?"))
			break;
			else
			{
				newBullet=true;
				for(int j=i+1;j<iGotThis.length();++j)
				{
					if(iGotThis.substring(j,j+1).equals(";"))
					{
						if(licznik==2)						
							bulletPower=Integer.valueOf(iGotThis.substring(lasti,j));
						if(licznik==3)
							bulletDirection[0]=Integer.valueOf(iGotThis.substring(lasti,j));
						if(licznik==4)
							bulletDirection[1]=Integer.valueOf(iGotThis.substring(lasti,j));
							++licznik;
							lasti=j+1;
							
					}
					
					}
				break;
			}
		}
		
		}		
		return xy;	
	}
	public synchronized void  sendTogame(String stringfromBT)
	{
		
		this.iGotThis=stringfromBT;		
	}
	public int[] getBullet()
	{
		if(newBullet)
		{
			newBullet=false;
			return new int[]{bulletPower,bulletDirection[0],bulletDirection[1]};
		}
		else
		{
			return null;
		}
	}
	public synchronized String sendToThread()
	{		
		createStringToSend();
		return iWillsendIt;
	}
	public String sendToThread(Bullet bullet)
	{
		createStringToSend(bullet);
		return iWillsendIt;
	}
}
