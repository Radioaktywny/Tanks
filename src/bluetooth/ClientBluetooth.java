package bluetooth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

public class ClientBluetooth extends AsyncTask<Void, String, Void> {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private volatile String odebrane="brak";
    private volatile String danedowyslania="brak";
    public ClientBluetooth(BluetoothDevice device) {       
        BluetoothSocket tmp = null;
        mmDevice = device;
        try {            
        	UUID uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
            tmp = device.createRfcommSocketToServiceRecord(uuid);
        } catch (Exception e) {e.printStackTrace(); }
        mmSocket = tmp;
    }
    public synchronized String getOdebrane()
    {
    	return odebrane;
    }
    public synchronized void wyslij(String dane)
    {
    	danedowyslania=dane;
    }
   
	@Override
	protected Void doInBackground(Void... params) {
		try {     
        	Log.d("INFO","Próba po³¹czenia");
        	BluetoothAdapter.getDefaultAdapter();
            mmSocket.connect();
            Log.d("INFO","Po³¹czono z socketem");
            BufferedReader in = new BufferedReader(new InputStreamReader(mmSocket.getInputStream()));
            PrintWriter out = new PrintWriter(mmSocket.getOutputStream(),true);
            
            while(true)
            {
            	
            	String s=in.readLine();
            	out.println(danedowyslania);
            	onProgressUpdate(s);  
            	
            	if(false)
            		break;
            	Log.d("INFO KLIENT",odebrane);
            	
            }           
            
        } catch (Exception ce) {
            try {
                mmSocket.close();
            } catch (Exception cle) { }            
        }
 
		// TODO Auto-generated method stub
		return null;
	} 
	@Override
	protected void onProgressUpdate(String... values) {
		// TODO Auto-generated method stub
		odebrane=values[0];
	}
}
