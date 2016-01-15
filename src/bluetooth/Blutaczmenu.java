package bluetooth;

import java.util.Set;

import com.example.tanks.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Blutaczmenu extends Activity {

	Button b;
	Button b2;
	Button b3;
	Button b4;
	Button b5;
	TextView t1;
	TextView t2;
	EditText et1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blutaczmenu);
		b=(Button) findViewById(R.id.button1);
		b2=(Button) findViewById(R.id.button2);
		b3=(Button) findViewById(R.id.button3);
		b4=(Button) findViewById(R.id.button4);
		b5=(Button) findViewById(R.id.button5);
		t1=(TextView) findViewById(R.id.textView1);
		t2=(TextView) findViewById(R.id.textView2);
		et1=(EditText) findViewById(R.id.editText1);
		
		Log.d("INFO","Uruchomiono program");
		
		b.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				dajSieWykryc();		
				t2.setText("Uruchomiono serwer");
				new SerwerBluetooth().start();
			}
		});
		b2.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				wykryjInne();				
			}
		});
		
		b3.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
			}
		});
		b4.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {		
				
				t2.setText("Jo sem serwer!");
				new SerwerBluetooth().start();
			}
		});
		b5.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				t2.setText("Jo sem client!");
				pokazSparowane();				

				BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();		
				BluetoothDevice serwer = ba.getRemoteDevice(et1.getText().toString());      
				new ClientBluetooth(serwer).start();
			}
		});
		
		
		
		BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
		t1.setText("Twój mac: "+ba.getAddress());
		Log.d("INFO","Twój adres urządzenia: "+ba.getAddress());
		if(!ba.isEnabled()){
			Intent i=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(i, 1);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent i){
		if(resultCode==Activity.RESULT_OK){
			Log.d("INFO","Mamy zgodę!");
			BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();					
		}
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
	
	public void dajSieWykryc(){
		Log.d("INFO","Daję się wykryć!");		
		Intent pokazSie = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		pokazSie.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		startActivity(pokazSie);
	}
	
	
	public void wykryjInne(){
		Log.d("INFO","Szukam innych urządzeń (ok 12s)");
		IntentFilter filtr = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(odbiorca, filtr);
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
