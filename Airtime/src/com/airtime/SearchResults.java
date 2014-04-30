package com.airtime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.airtime.model.Series;

import android.app.Activity;
import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
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

	private ShowAdapter adapter;
	private ArrayList<Show> showResults;
    /**
     * Handle the search query
     */
    private void handleIntent(Intent intent) {
        showResults = new ArrayList<Show>();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
            TheTVDBApi api = new TheTVDBApi();
            //List<Series> results = api.searchSeries(query, "en");
            List<Series> results = populateFakeData(query);
//            for (Series s : results){
//            	addSeriesToList(s);
//            }
            addShowsToList(query);
            addShowsToTable();
            setAdapter();
            return;
        }
    }
    
    private void addShowsToTable() {
		adapter = new ShowAdapter(this, showResults);
		ListView v = (ListView) findViewById(R.id.listView);
		adapter.notifyDataSetChanged();
		v.setAdapter(adapter);
	}
    
    private void setAdapter() {
		ListView listView = (ListView) findViewById(R.id.listView);
		adapter = new ShowAdapter(this, showResults);
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
    
    private List<Series> populateFakeData(String query) {
		List<Series> fake = new ArrayList<Series>();
		for (int i = 0; i<10;i++){
			Series s = new Series();
			s.setNetwork("NBC");
			s.setSeriesName(query + " " + i);
			fake.add(s);
		}
		return fake;
	}

    private void addShowsToList(String query){
    	for (int i = 0; i<10;i++){
	    	Show s = new Show();
	    	s.Name = query + " " + i;
	    	s.Network = "Balls";
	    	s.LastEpisode = Calendar.getInstance();
	    	s.NextEpisode = Calendar.getInstance();
	    	s.Status = Status.Continuing;
	    	s.IsAFavorite = false;
	    	showResults.add(s);
    	}
    }
    
	private void addSeriesToList(Series s){
    	Show show = new Show();
    	show.Name = s.getSeriesName();
    	show.Network = s.getNetwork();
    	show.NextEpisode = Calendar.getInstance();
    	show.LastEpisode = Calendar.getInstance();
		show.Status = Status.Ended;
    }
}
