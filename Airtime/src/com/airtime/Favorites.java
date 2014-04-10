package com.airtime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.airtime.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ListView;

/**
 * The activity class for the favorites view
 * @author Andrew
 *
 */
public class Favorites extends Activity {

	private ArrayList<Show> favorites;
	private ShowAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);
		
		favorites = new ArrayList<Show>();
		favorites = populateTestFavorites();
		File f = new File(this);
		f.clearFavorites();
		for (Show s : favorites){
			f.storeFavorite(s);
		}
		favorites = f.loadFavorites();
		addShowsToTable();
	}
	
	private ArrayList<Show> populateTestFavorites(){
		ArrayList<Show> shows = new ArrayList<Show>();
		for (int i = 0; i < 20; i++){
			Show s = new Show();
			s.Name = String.format("Test show name #%d", i);
			s.Network = "NBC";
			s.NextEpisode = new Date(Date.UTC(2014, 2, i, i, i, 0));
			shows.add(s);
		}
		return shows;
	}
	
	private void addShowsToTable() {
		adapter = new ShowAdapter(this, favorites);
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
