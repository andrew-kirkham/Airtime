package com.airtime;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;

import com.airtime.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
	public static final String FILENAME = "favorites";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);
		
		favorites = new ArrayList<>();
		addShow(new String[]{"Name", "Network", "2014-4-10", "2014-3-3"});
		addShowsToTable();
		loadFavorites();
	}

	
	/**
	 * Load the favorites from the filesystem and display them to the screen
	 */
	public void loadFavorites() {
		try {
			FileInputStream in = openFileInput(FILENAME);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		    parseStoredFavorites(reader);
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		addShowsToTable();
	}
	
	private void addShowsToTable() {
		adapter = new ArrayAdapter<Show>(this, R.layout.activity_favorites, favorites);
		ListView v = (ListView) findViewById(R.id.listView);
		v.setAdapter(adapter);
	}

	private void parseStoredFavorites(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		while (line != null){
			String[] vals = line.split(",");
			addShow(vals);
			line = reader.readLine();
		}
		
	}

	private void addShow(String[] vals) {
		Show s = new Show();
		s.Name = vals[0];
		s.Network = vals[1];
		s.NextEpisode = Date.valueOf(vals[2]);
		s.LastEpisode = Date.valueOf(vals[3]);
		favorites.add(s);
	}

	/**
	 * Store a favorite show to the filesystem
	 * @param show object
	 */
	public void storeFavorite(Show favorite) {
		FileOutputStream fos;
		try {
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(favorite.toString().getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.favorites, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
