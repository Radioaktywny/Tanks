package bluetooth;

import game_engine.Bullet;

public class PrepareToMultiplayer 
{
	private String iWillsendIt;
	private String iGotThis;
	private int[] xy;
	private boolean newBullet=false;
	private int bulletPower=0;
	private int bulletDirection[]= new int[2];
	public PrepareToMultiplayer() {
		// TODO Auto-generated constructor stub
	}
	public void createStringToSend(Bullet bullet)
	{
		iWillsendIt=String.valueOf(xy[0])+";"+String.valueOf(xy[1]);
		int dir[]= bullet.getDirection();
		iWillsendIt=iWillsendIt+";"+bullet.getPower()+";"+dir[0]+";"+dir[1]+";?";
	}
	public void createStringToSend()
	{
		iWillsendIt=String.valueOf(xy[0])+";"+String.valueOf(xy[1])+";?";
	}
	public void setDirectionToSend(int []xy)
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
		int licznik=0;
		int lasti=0;
		for(int i=0;i<iGotThis.length();++i)
		{
		if(iGotThis.substring(i, i+1).equals(";"))
		{
		xy[licznik]=Integer.valueOf(iGotThis.substring(lasti,i));
		lasti=i+1;
		}
		if(licznik==2)
			if(iGotThis.subSequence(i+1, i+2).equals("?"))
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
			}
		}		
		return xy;	
	}
	public void sendTogame(String stringfromBT)
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
}
