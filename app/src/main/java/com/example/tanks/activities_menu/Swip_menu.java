package com.example.tanks.activities_menu;
import com.example.tanks.R;

import android.content.Context;
import android.media.Image;
import android.media.session.PlaybackState.CustomAction;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Swip_menu extends PagerAdapter{
	public int [] obrazkimenu = {R.xml.online , R.xml.offline };
	private Context context;
	private LayoutInflater layoutinflator;
	
	public Swip_menu(Context context) 
	{
		this.context=context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return obrazkimenu.length;
	}




	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((LinearLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		Log.d("zwrot", "skladam");
		layoutinflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View item_view=layoutinflator.inflate(R.layout.swip_menu , container , false);
		ImageView imageView = (ImageView) item_view.findViewById(R.id.obrazek_w_menu);		
		imageView.setImageResource(obrazkimenu[position]);
		container.addView(item_view);
		return imageView;
	}


	
	@Override
	public boolean isViewFromObject(View view, Object o) {
		// TODO Auto-generated method stub
		Boolean cos;
		cos=(view.findViewById(R.id.obrazek_w_menu) == o);
	
		Log.d("zwrot", String.valueOf(cos));
		return cos;
	}	
	

}
