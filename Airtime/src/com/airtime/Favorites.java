package com.airtime;

import java.util.ArrayList;
import com.airtime.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * The activity class for the favorites view
 * @author Andrew
 *
 */
public class Favorites extends Activity {

	private ArrayList<Show> favorites;
	private ArrayAdapter<Show> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);
		
		favorites = new ArrayList<Show>();		
		File f = new File(this);
		favorites = f.loadFavorites();
		addShowsToTable();
	}
	
	private void addShowsToTable() {
		adapter = new ArrayAdapter<Show>(this, R.layout.activity_favorites, favorites);
		ListView v = (ListView) findViewById(R.id.listView);
		adapter.notifyDataSetChanged();
		v.setAdapter(adapter);
	}
	
	/**
	 * Sorts favorites by show name
	 * @param View
	 */
	public void sortByShow(View v){
	}
	
	/**
	 * Sorts favorites by airtime 
	 * @param View
	 */
	public void sortByAirtime(View v){
		
	}
	
	/**
	 * Sort favorites by network
	 * @param View
	 */
	public void sortByNetwork(View v){
		
	}
	
	/**
	 * Sort favorites by last episode 
	 * @param View
	 */
	public void sortByLastEp(View v){
		
	}
}
