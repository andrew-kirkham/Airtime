package com.airtime;

import java.util.ArrayList;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;

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
		
		ActionBar bar = getActionBar();
		bar.setIcon(R.drawable.logo_final_icon);
		
		favorites = new ArrayList<Show>();
		favorites = populateTestFavorites();
		File f = new File(this);
		f.clearFavorites();
		for (Show s : favorites){
			f.storeFavorite(s);
		}
		favorites = f.loadFavorites();
		addShowsToTable();
		setAdapter();
	}
	
	private void setAdapter() {
		ListView listView = (ListView) findViewById(R.id.listView);
		adapter = new ShowAdapter(this,  populateTestFavorites());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override 
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				Show show = (Show) adapter.getItem(position);
	            Intent intent = new Intent(Favorites.this, DetailedFavorite.class); 
	            intent.putExtra("Name", show.Name);
	            intent.putExtra("Network", show.Network);
	            intent.putExtra("last Aired", show.LastEpisode);
	            intent.putExtra("Next Ep", show.NextEpisode);
	            intent.putExtra("Status", show.Status);	   
	            
	            startActivity(intent);   
			}
		});
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favorites_actions, menu);
 
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
 
        return super.onCreateOptionsMenu(menu);
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
