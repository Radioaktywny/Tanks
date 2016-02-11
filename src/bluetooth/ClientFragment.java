package bluetooth;

import java.util.ArrayList;
import java.util.Set;

import com.example.tanks.R;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import game_engine_multiplayer.Game_multiplayer;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ClientFragment extends Fragment implements ListAdapter {
	private ListView listView;
	private ArrayList<String> sparowane = new ArrayList<String>();
	private ArrayList<String> niesparowane = new ArrayList<String>();
	private Button b3;
	private Button b1;
	private ArrayAdapter<String> adaArr;
	private boolean czy_wyswietlic_niesparowane=false;
	private ProgressBar progres;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.client_widok, container, false);
		init(rootView);
		return rootView;
	}
	private void init(View rootView)
	{
		b1 = (Button) rootView.findViewById(R.id.button1);
		b3 = (Button) rootView.findViewById(R.id.button3);
		listView = (ListView) rootView.findViewById(R.id.listView1);
		progres= (ProgressBar)rootView.findViewById(R.id.progressBar1);
		progres.setVisibility(0x000008);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
            {
            	polacz(((TextView) view).getText().toString());
            	Toast.makeText(getActivity().getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }			
        });
    
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				wykryjInne(czy_wyswietlic_niesparowane);

			}
		});
		b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pokazSparowane();

			}
		});
		
	}
	private final BroadcastReceiver odbiorca = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent i) {
			String akcja = i.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(akcja)) {
				BluetoothDevice device = i.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				String status = "";
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					status = "nie sparowane";
					niesparowane.add(device.getName());
				} else {
					status = "sparowane";
					sparowane.add(device.getName());
				}
				Log.d("INFO",
						"znaleziono urządzenie: " + device.getName() + " - " + device.getAddress() + " - " + status);
			}
		}
	};
	

	public void wykryjInne(boolean czy_wyswietlic_niesparowane) {
		if(czy_wyswietlic_niesparowane)
		{
		ArrayList<String> cache=niesparowane;
		
		adaArr = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1,cache);
		adaArr.notifyDataSetChanged();
		listView.setAdapter(adaArr);
		this.czy_wyswietlic_niesparowane=false;
		b1.setText("szukaj niesparowanych urzadzen");
		progres.setVisibility(0x000008);
		}
		else
		{
		niesparowane.clear();
		Log.d("INFO","Szukam innych urządzeń (ok 12s)");
		IntentFilter filtr = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		getActivity().registerReceiver(odbiorca, filtr);
		BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
		ba.startDiscovery();
		this.czy_wyswietlic_niesparowane=true;
		b1.setText("pokaz znalezione urzadzenia");
		progres.setVisibility(1);
		}
		
	}
	public void pokazSparowane() {
		
			sparowane.clear();
			Log.d("INFO", "Sparowane dla tego urządzenia");
			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
			if (pairedDevices.size() > 0) {
				for (BluetoothDevice device : pairedDevices) {
					Log.d("INFO", device.getName() + " - " + device.getAddress());
					sparowane.add(device.getName());
				}
			}
			adaArr = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1,sparowane);
			adaArr.notifyDataSetChanged();
			listView.setAdapter(adaArr);
					
	}
	private void polacz(String MAC) 
	{
		if(sparowane.contains(MAC))
		{
			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
			if (pairedDevices.size() > 0) {
				for (BluetoothDevice device : pairedDevices) {
					Log.d("INFO", device.getName() + " - " + device.getAddress());
					if(MAC.equals(device.getName().toString()))
					{
						MAC=device.getAddress();
						Game_multiplayer gm= new Game_multiplayer();
						Intent activity= new Intent(getActivity(),gm.getClass());
						activity.putExtra("MAC", MAC);
						startActivity(activity); 
						getActivity().finish();
						break;
					}
				}
			}		
		}
		else
		{
			//NIE WIEM CO TU ZROBIC NO ALE XD JEST TAK JAK JEST
		Intent btSettingsIntent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
	    getActivity();
		startActivityForResult(btSettingsIntent, Context.BIND_ABOVE_CLIENT);
		}
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return false;
	}
}
