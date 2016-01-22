package bluetooth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ClientBluetooth implements Runnable {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private String odebrane;
    private String wyslij;
    public ClientBluetooth(BluetoothDevice device) {       
        BluetoothSocket tmp = null;
        mmDevice = device;
        try {            
        	UUID uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
            tmp = device.createRfcommSocketToServiceRecord(uuid);
        } catch (Exception e) { }
        mmSocket = tmp;
    }
    public synchronized String getOdebrane()
    {
    	return odebrane;
    }
    public synchronized void wyslij(String dane)
    {
    	wyslij=dane;
    }
    public void run() {
         try {     
        	Log.d("INFO","Próba po³¹czenia");
            mmSocket.connect();
            Log.d("INFO","Po³¹czono z socketem");
            BufferedReader in = new BufferedReader(new InputStreamReader(mmSocket.getInputStream()));
            PrintWriter out = new PrintWriter(mmSocket.getOutputStream(),true);
            int i=0;
            while(true)
            {
            	i++;
            	String s=in.readLine();
            	out.println(wyslij);
            	odebrane=s;           	
            	if(false)
            		break;
            	Log.d("INFO"+i,odebrane);
            	wyslij(i+"Klient Maciek jest fajny blutacz super dziala kurde nie wiem jak ale to dziala omg omg xdd");
            }           
            
        } catch (Exception ce) {
            try {
                mmSocket.close();
            } catch (Exception cle) { }            
        }
 
    } 
}
