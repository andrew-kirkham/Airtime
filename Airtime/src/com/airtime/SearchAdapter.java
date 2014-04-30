package com.airtime;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {

	private Activity activity;
    private ArrayList<Show> data;
    private static LayoutInflater inflater=null;
    
    public SearchAdapter(Activity a, ArrayList<Show> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
	public int getCount() {
    	return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView airtime = (TextView)vi.findViewById(R.id.airtime); // airtime
 
        Show s = data.get(position);
 
        title.setText(s.Name);
        airtime.setText(s.Network);
        
        return vi;
	}

}
