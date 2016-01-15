package bluetooth;


import java.util.Set;

import com.example.tanks.R;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ClientFragment extends Fragment {

	private Button b3;
	private Button b1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{

		View rootView = inflater.inflate(R.layout.client_widok, container, false);
		b1=(Button)rootView.findViewById(R.id.button1);	
		b3=(Button)rootView.findViewById(R.id.button3);	
			b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				wykryjInne();
				// TODO Auto-generated method stub
				
			}
		});
		 b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pokazSparowane();
							
			}
		});
		return rootView;
	}
	private final BroadcastReceiver odbiorca= new BroadcastReceiver() {		
		@Override
		public void onReceive(Context context, Intent i) {
			String akcja = 	i.getAction();
			if(BluetoothDevice.ACTION_FOUND.equals(akcja)){		
	              BluetoothDevice device = i.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	              String status="";	              
	              if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
	            	  status="nie sparowane";
	              }else{
	            	  status="sparowane";
	              }
	            	  Log.d("INFO","znaleziono urządzenie: "+device.getName()+" - "+device.getAddress()+" - "+status);	         
	      }			
		}
	};
	public void wykryjInne(){
		Log.d("INFO","Szukam innych urządzeń (ok 12s)");
		IntentFilter filtr = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		getActivity().registerReceiver(odbiorca, filtr);
		BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
		ba.startDiscovery();
	}
	public void pokazSparowane(){
		/*
		 * Wyświetlanie listy sparowanych urządzeń, niezależnie od tego czy są 
		 * właśnie podłączone czy w ogóle włączone.
		 * */
		Log.d("INFO","Sparowane dla tego urządzenia");
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();		
		if (pairedDevices.size() > 0) {	
			for (BluetoothDevice device : pairedDevices) {				
				Log.d("INFO",device.getName()+" - "+device.getAddress());			
			}
		}
}
}
