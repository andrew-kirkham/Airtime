package com.airtime;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
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

	private SearchAdapter adapter;
	private ArrayList<Show> showResults;
    /**
     * Handle the search query
     */
    private void handleIntent(Intent intent) {
        showResults = new ArrayList<Show>();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            
            if (isOnline()) new SearchTask().execute(query, "en");
            else displayNetworkAlert();
            
            return;
        }
    }

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

	public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    
    private class SearchTask extends AsyncTask<String, Void, ArrayList<Show>>{
    	@Override
    	protected void onPreExecute(){
    		setProgressBarIndeterminateVisibility(true);
    	}
		@Override
		protected ArrayList<Show> doInBackground(String... query) {

			try {
				// Search the tvdb API
				SeriesSearchHandler tvdbApiSearch = new SeriesSearchHandler();
				return tvdbApiSearch.searchSeries(query[0], query[1]);

			}catch (Exception e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Show> results){
			if (results == null) {
				displayNoResults();
	    		setProgressBarIndeterminateVisibility(false);
				return;
			}
			showResults = results;
			filterResults();
			if (showResults.size() == 0) displayNoResults();
			else updateTable();
    		setProgressBarIndeterminateVisibility(false);
		}
	}
    
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
    
    private void updateTable(){
		addShowsToTable();
		setAdapter();
    }
    
    private void filterResults() {
    	for (Show s : showResults){
    		if (s.Name.equals("NOT_FOUND")) showResults.remove(s);
    		if (s.Id == -1) showResults.remove(s);
    	}
    }
    
	private void addShowsToTable() {
		adapter = new SearchAdapter(this, showResults);
		ListView v = (ListView) findViewById(R.id.listView);
		adapter.notifyDataSetChanged();
		v.setAdapter(adapter);
	}
    
    private void setAdapter() {
		ListView listView = (ListView) findViewById(R.id.listView);
		adapter = new SearchAdapter(this, showResults);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override 
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				Show show = (Show) adapter.getItem(position);
	            Intent intent = new Intent(SearchResults.this, DetailedFavorite.class); 
	            intent.putExtra("Show", show);
	            startActivity(intent);   
			}
		});
	}
}
