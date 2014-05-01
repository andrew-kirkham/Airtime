package com.airtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
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
		File f = new File(this);
		favorites = f.loadFavorites();
		if (!isOnline()) displayNetworkAlert();
		updateFavorites();
		addShowsToTable();
		setAdapter();
		setActionBarDropDown();
	}

	private void updateFavorites(){
		ArrayList<Show> updatedList = new ArrayList<Show>();
		for (Show fav : favorites){
			Show s = updateShow(fav.Id);
			if (s.equals(new Show())) continue;
			s.IsAFavorite = true;
			updatedList.add(s);
		}
		favorites = updatedList;
	}
	
	private Show updateShow(int id){
		Show result = new Show();
		DetailsTask task = new DetailsTask(this);
		task.execute(String.valueOf(id));
		try {
			result = task.get(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			Log.e("error retrieving details", e.getMessage());
		}
		return result;
	}
	
	/**
     * Inform the user of no network connection. Returns to the favorites view after the OK button is selected
     */
    private void displayNetworkAlert() {
    	new AlertDialog.Builder(this)
        .setTitle("Warning!")
        .setMessage("No network connection detected. Please connect to search.")
        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                finish();
            }
         })
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show();
	}

    /**
     * Checks whether the user is connected to the internet using wifi or through a data plan
     * @return connected status
     */
	public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
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
		        return s1.getNextEpisode().compareTo(s2.getNextEpisode());
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
		        return s1.getLastEpisode().compareTo(s2.getLastEpisode());
		    }
		});
		adapter.notifyDataSetChanged();
	}
}
