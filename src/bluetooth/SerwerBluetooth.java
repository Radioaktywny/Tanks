package bluetooth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class SerwerBluetooth implements Runnable {
    private final BluetoothServerSocket mmServerSocket;
	private String odebrane;
	private String wyslij;
 
    public SerwerBluetooth() {
    	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();     
        BluetoothServerSocket tmp = null;
        try {
        	UUID uuid=UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Usługa witająca", uuid);
        } catch (IOException e) { }
        mmServerSocket = tmp;
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
    	Log.d("INFO","Uruchamiam serwer");
        BluetoothSocket socket = null;       
        
            try {
            	Log.d("INFO","Czekam na połączenie od clienta");
                socket = mmServerSocket.accept();
                Log.d("INFO","Mam clienta!");
                /*Utworzenie strumieni wejściowego i wyjściowego*/  
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while(true)
                {
                	out.println(wyslij);
                	String s=in.readLine();
                	odebrane=s;           	
                	if(false)
                		break;
                	Log.d("INFO ",odebrane);
                	
                }        
                out.println("Witaj kolego!");
               // BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  
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
        }
    }
 

