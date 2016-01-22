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
import android.widget.Button;

public class ServerFragment extends Fragment implements OnClickListener{
	private Button b2;
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
				new Thread(new SerwerBluetooth()).start();
				// TODO Auto-generated method stub
				
			}
		});	
		 
		return rootView;
	}
		public void dajSieWykryc(){
		Log.d("INFO","DAJ SIE WYKRYÆ");		
		Intent pokazSie = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		pokazSie.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		startActivity(pokazSie);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
