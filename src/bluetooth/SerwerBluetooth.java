package bluetooth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

public class SerwerBluetooth implements Runnable  {
    private final BluetoothServerSocket mmServerSocket;
	private volatile static String odebrane="brak";
	private volatile String danedowyslania="brak";
	boolean isFree=true;
	private volatile static PrintWriter out;
    public SerwerBluetooth() {
    	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();     
        BluetoothServerSocket tmp = null;
        try {
        	UUID uuid=UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Usługa witająca", uuid);
        } catch (IOException e) { e.printStackTrace();}
        mmServerSocket = tmp;
    }
    public String getOdebrane()
    {
    	String wyslij=odebrane;
    	if(odebrane.length()>7)
    		odebrane="1;1;?";
    	return wyslij;
    	
    }
    public void wyslij(String dane)
    {
    	
    	danedowyslania=dane;
    	out.println(dane);
    }  

	@Override
	public void run() {
		Log.d("INFO","Uruchamiam serwer");
        BluetoothSocket socket = null;       
        
            try {
            	Log.d("INFO","Czekam na połączenie od clienta");
                socket = mmServerSocket.accept();
                Log.d("INFO","Mam clienta!");
                
                out = new PrintWriter(socket.getOutputStream(),true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             //   int i=0;
                while(true)
                {
                	
                	//out.println(danedowyslania);
                	String s=in.readLine();
                	odebrane=s;
                	               	
                	if(false)
                		break;
                	Log.d("INFO SERWER ",s);
                	//i++;
                }        
                
               
            } catch (IOException e) {
                
            }
            if (socket != null) {
               /*
                * Jakieś dodatkowe operacje na połączeniu
                * */
                try {
					mmServerSocket.close();
				} catch (Exception e) {					
					e.printStackTrace();
				}
                
            }
		// TODO Auto-generated method stub
		
	}

    }
 

