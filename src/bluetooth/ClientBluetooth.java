package bluetooth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ClientBluetooth extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
 
    public ClientBluetooth(BluetoothDevice device) {       
        BluetoothSocket tmp = null;
        mmDevice = device;
        try {            
        	UUID uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
            tmp = device.createRfcommSocketToServiceRecord(uuid);
        } catch (Exception e) { }
        mmSocket = tmp;
    }
 
    public void run() {
         try {     
        	Log.d("INFO","Próba po³¹czenia");
            mmSocket.connect();
            Log.d("INFO","Po³¹czono z socketem");
            BufferedReader in = new BufferedReader(new InputStreamReader(mmSocket.getInputStream()));
            String input = in.readLine();  
            Log.d("INFO","Serwer mówi: "+input);
            
        } catch (Exception ce) {
            try {
                mmSocket.close();
            } catch (Exception cle) { }            
        }
 
    } 
}
