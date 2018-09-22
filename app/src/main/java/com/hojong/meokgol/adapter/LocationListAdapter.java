package com.hojong.meokgol.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationListAdapter extends BaseAdapter
{
	private List<Location> locationDataList = new ArrayList<>();

	@Override
	public int getCount() {
		return locationDataList.size() ;
	}

	@Override
	public View getView(int position, View locationView, ViewGroup parent) {
		final Context context = parent.getContext();

		if (locationView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			locationView = inflater.inflate(R.layout.layout_location_list_item, parent, false);
		}

		// get view
		ImageView imageView = locationView.findViewById(R.id.location_img);
		// get data
		Location location = locationDataList.get(position);
		// set data to view
        if (location.bmp != null) {
            Log.d(this.toString(), location.bmp.toString());
            imageView.setImageBitmap(location.bmp);
        }

		return locationView;
	}

	@Override
	public long getItemId(int position) {
		return position ;
	}
	
	@Override
	public Object getItem(int position) {
		return locationDataList.get(position) ;
	}

	public void addItem(Location location) {
		locationDataList.add(location);
	}

	public void clear() {
	    locationDataList.clear();
    }
}