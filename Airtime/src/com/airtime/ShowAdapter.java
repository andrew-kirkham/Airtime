package com.airtime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ImageReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowAdapter extends BaseAdapter {

	private Activity activity;
    private ArrayList<Show> data;
    private static LayoutInflater inflater=null;
    public ImageReader imageLoader; 
    
    public ShowAdapter(Activity a, ArrayList<Show> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
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
        DateFormat date = new SimpleDateFormat("MM/dd HH:mm", Locale.US);
        airtime.setText(String.format("%s on %s", date.format(s.NextEpisode.getTime()), s.Network));
        
        Bitmap image = File.loadImage(s);
        ImageView myImage = (ImageView) vi.findViewById(R.id.list_image);
        if (image != null) myImage.setImageBitmap(image);
        
        return vi;
	}

}
