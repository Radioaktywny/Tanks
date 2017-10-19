package com.example.tanks.bluetooth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

public class ClientBluetooth implements Runnable{
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private volatile static String odebrane="brak";
    private volatile String danedowyslania="brak";
    private volatile static PrintWriter out;
    public volatile static boolean connected=false;
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

		try {     
        	Log.d("INFO","Pr�ba po��czenia");
        	BluetoothAdapter.getDefaultAdapter();
            mmSocket.connect();
            connected=true;
            Log.d("INFO","Po��czono z socketem");
            BufferedReader in = new BufferedReader(new InputStreamReader(mmSocket.getInputStream()));
            out = new PrintWriter(mmSocket.getOutputStream(),true);
            int i=0;
            while(true)
            {
            	
            	String s=in.readLine();
            	//out.println(danedowyslania);
            	odebrane=s;  
            	
            	if(false)
            		break;
            	Log.d("INFO KLIENT",s);
            	++i;
            }           
            
        } catch (Exception ce) {
            try {
                mmSocket.close();
            } catch (Exception cle) { }            
        }
 
		// TODO Auto-generated method stub
		
	}
}
