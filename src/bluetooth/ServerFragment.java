package bluetooth;

import com.example.tanks.R;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;

public class ServerFragment extends Fragment implements OnClickListener{
	TextView t2;
	Button b2;
	private Button b3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.server_widok, container, false);
		 //t2= (TextView)rootView.findViewById(R.id.textView2);
		 b2= (Button)rootView.findViewById(R.id.button2);
		 
		 b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dajSieWykryc();		
				//t2.setText("Uruchomiono serwer");
				new SerwerBluetooth().start();
				// TODO Auto-generated method stub
				
			}
		});	
		 
		return rootView;
	}
	public void wykryj(View view)
	{
		
		
		
		
		
	}
	public void dajSieWykryc(){
		Log.d("INFO","Daję się wykryć!");		
		Intent pokazSie = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		pokazSie.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		startActivity(pokazSie);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
