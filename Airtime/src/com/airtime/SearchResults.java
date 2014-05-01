package com.airtime;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * An activity to show the search results from the favorite activity
 * @author Andrew
 *
 */
public class SearchResults extends Activity {

	private SearchAdapter adapter;
	private ArrayList<Show> showResults;
	private Show selectedShow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); //allows the spinning wheel to be shown
		setContentView(R.layout.activity_search_results);

        ActionBar actionBar = getActionBar(); 
        actionBar.setDisplayHomeAsUpEnabled(true); //enable a back button over the Airtime logo
 
        handleIntent(getIntent());
	}
	 
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    /**
     * Handle the search query
     */
    private void handleIntent(Intent intent) {
        showResults = new ArrayList<Show>();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            ActionBar ab = getActionBar();
            ab.setTitle("Results for: " + query);
            
            if (isOnline()) new SearchTask().execute(query, "en");
            else displayNetworkAlert();
            
            return;
        }
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
     * Display an alert to the user informing them of no results. Returns to the Favorites view
     */
    private void displayNoResults(){
    	new AlertDialog.Builder(this)
        .setTitle("Warning!")
        .setMessage("No results found. Please try again")
        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                finish();
            }
         })
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show();
    }
    
    /**
     * Verify each result in the returned series is usuable.
     * Shows are not usuable if they do not have a name or an ID
     */
    private void filterResults() {
    	for (Show s : showResults){
    		if (s.Name.equals("NOT_FOUND")) showResults.remove(s);
    		if (s.Id == -1) showResults.remove(s);
    	}
    }
    
	/**
	 * Initialize the adapter and populate the listview with show data
	 */
    private void populateList() {
		ListView listView = (ListView) findViewById(R.id.listView);
		adapter = new SearchAdapter(this, showResults);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override 
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				Show show = (Show) adapter.getItem(position);
				DetailsTask task = new DetailsTask(getApplicationContext());
				task.execute(String.valueOf(show.Id));
				try {
					Show result = task.get(10, TimeUnit.SECONDS);
					Intent intent = new Intent(SearchResults.this, DetailedFavorite.class); 
		            intent.putExtra("Show", result);
		            int requestCode = 1; // Or some number you choose
		            startActivityForResult(intent, requestCode);
				} catch (Exception e) {
					Log.e("error retrieving details", e.getMessage());
				}
			}
		});
	}

    /**
     * Query TheTvDb in the background for the list of shows matching a query.
     * NOTES: Since queries cannot be done in the main thread, an asynchronous task must be used. The main thread displays
     * a spinner wheel until this thread returns.
     * @author Andrew
     * 
     */
    private class SearchTask extends AsyncTask<String, Void, ArrayList<Show>>{
    	@Override
    	protected void onPreExecute(){
    		setProgressBarIndeterminateVisibility(true);
    	}
		@Override
		protected ArrayList<Show> doInBackground(String... query) {
			try {
				SeriesSearchHandler tvdbApiSearch = new SeriesSearchHandler();
				return tvdbApiSearch.searchSeries(query[0], query[1]);

			}catch (Exception e){
				Log.e("Querying TheTvDb failed", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Show> results){
			if (results == null) { //nothing was found matching the query
				displayNoResults();
	    		setProgressBarIndeterminateVisibility(false);
				return;
			}
			showResults = results;
			filterResults();
			if (showResults.size() == 0) displayNoResults(); //nothing usuable was found matching the query
			else populateList();
    		setProgressBarIndeterminateVisibility(false); //stop the spinning wheel
		}
	}
}