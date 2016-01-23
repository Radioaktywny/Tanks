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

public class SerwerBluetooth extends AsyncTask<Void, String, Void>  {
    private final BluetoothServerSocket mmServerSocket;
	private volatile String odebrane="brak";
	private volatile String danedowyslania="brak";
 
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
    	Log.d("odebrane", odebrane);
    	return odebrane;
    	
    }
    public void wyslij(String dane)
    {
    	danedowyslania=dane;
    }  
	@Override
	protected Void doInBackground(Void... params) 
	{

    	Log.d("INFO","Uruchamiam serwer");
        BluetoothSocket socket = null;       
        
            try {
            	Log.d("INFO","Czekam na połączenie od clienta");
                socket = mmServerSocket.accept();
                Log.d("INFO","Mam clienta!");
                
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while(true)
                {
                	
                	out.println(danedowyslania);
                	String s=in.readLine();
                	onProgressUpdate(s);
                	               	
                	if(false)
                		break;
                	Log.d("INFO SERWER ",odebrane);
                	
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
		return null;
	}
	@Override
    protected void onProgressUpdate(String... s) {		
		odebrane=s[0]; 	
		Log.d("odebrane", odebrane);
		
	}

    }
 

