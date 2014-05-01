package com.airtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

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
import android.widget.ArrayAdapter;
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
	private String[] sortTypes = { "Next Airtime", "Last Airtime", "Name" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);
		
		ActionBar bar = getActionBar();
		bar.setIcon(R.drawable.logo_final_icon); //set the action bar icon to be Airtime logo
		
		favorites = new ArrayList<Show>();
		favorites = populateTestFavorites();
//		File f = new File(this);
//		f.clearFavorites();
//		for (Show s : favorites){
//			f.storeFavorite(s);
//		}
		//favorites = f.loadFavorites();
		addShowsToTable();
		setAdapter();
		setActionBarDropDown();
	}

	/**
	 * Define the actions for the dropdown menu
	 */
	private void setActionBarDropDown() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_spinner_dropdown_item, sortTypes);

	    getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

	    ActionBar.OnNavigationListener navigationListener=new ActionBar.OnNavigationListener() {

	        @Override
	        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
	            if (sortTypes[itemPosition].equals("Name")) {
	                sortByName();
	                return true;
	            }
	            else if (sortTypes[itemPosition].equals("Next Airtime")) {
	                sortByNextAirtime();
	                return true;
	            }
	            else if (sortTypes[itemPosition].equals("Last Airtime")) {
	                sortByLastAirtime();
	                return true;
	            }
	            return false;
	        }
	    };
 
	    getActionBar().setListNavigationCallbacks(adapter, navigationListener);
	}
	
	public void removeFromFavoritesList(Show s){
		if(favorites.contains(s)){
			favorites.remove(s);
		}
	}
	
	public void addToFavoritesList(Show s){
		if(!(favorites.contains(s))){
			favorites.add(s);
		}
	}
	
	private void setAdapter() {
		ListView listView = (ListView) findViewById(R.id.listView);
		adapter = new ShowAdapter(this,  favorites);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override 
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				Show show = (Show) adapter.getItem(position);
	            Intent intent = new Intent(Favorites.this, DetailedFavorite.class); 
	            intent.putExtra("Show", show);
	            int requestCode = 1; // Or some number you choose
	            startActivityForResult(intent, requestCode);
			}
		});
	}
	
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		  // Collect data from the intent and use it
		  Bundle recdData = data.getExtras();
		  if(recdData.getParcelable("removeable") != null){
			  Show s = recdData.getParcelable("removeable");
			  removeFromFavoritesList(s);
		  }
		  if(recdData.getParcelable("addable") != null){
			  Show s = recdData.getParcelable("addable");
			  addToFavoritesList(s);
		  }
		   adapter.notifyDataSetChanged();
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
			Random r = new Random();
			s.Name = String.format("Test show name #%d", r.nextInt(100));
			s.Network = "NBC";
			s.NextEpisode = String.format("Friday 3/%d/2014 at 2:00 PM", i);
			s.LastEpisode = String.format("Friday 2/%d/2014 at 2:00 PM", i);
			s.AirDate = String.format("3/%d/2014", i+1);
			s.Status = Status.Ended;
			shows.add(s);
		}
		return shows;
	}
	
	/**
	 * Add the list of shows to the adapter to view them
	 */
	private void addShowsToTable() {
		adapter = new ShowAdapter(this, favorites);
		ListView v = (ListView) findViewById(R.id.listView);
		adapter.notifyDataSetChanged();
		v.setAdapter(adapter);
	}
	
	/**
	 * Sorts favorites by show name
	 */
	public void sortByName(){
		Collections.sort(favorites, new Comparator<Show>(){
		    public int compare(Show s1, Show s2) {
		        return s1.Name.compareToIgnoreCase(s2.Name);
		    }
		});
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * Sorts favorites by next airtime 
	 */
	public void sortByNextAirtime(){
		Collections.sort(favorites, new Comparator<Show>(){
		    public int compare(Show s1, Show s2) {
		        return s1.NextEpisode.compareTo(s2.NextEpisode);
		    }
		});
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * Sort favorites by last episode 
	 */
	public void sortByLastAirtime(){
		Collections.sort(favorites, new Comparator<Show>(){
		    public int compare(Show s1, Show s2) {
		        return s1.LastEpisode.compareTo(s2.LastEpisode);
		    }
		});
		adapter.notifyDataSetChanged();
	}
}
