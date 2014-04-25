package com.airtime;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

/**
 * The activity class for the detailed favorites view
 * @author Jenny
 *
 */

public class DetailedFavorite extends Activity {

	Show favorite = new Show();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_favorite);
		//recieve show passed on intent
		Bundle recdData = getIntent().getExtras();
		
		TextView tvShowName = (TextView) findViewById(R.id.TVshowTitle);
		String name = recdData.getString("Name");
		tvShowName.setText(name);
		
		TextView tvShowNetwork = (TextView) findViewById(R.id.TVshowNetwork);
		String network = recdData.getString("Network");
		tvShowNetwork.setText(network);
		
		TextView tvShowlastAired = (TextView) findViewById(R.id.LastAriedText);
		String lastAired = recdData.getString("last Aired");
		tvShowlastAired.setText(lastAired);
		
		TextView tvShowNextEp = (TextView) findViewById(R.id.NextEpText);
		String nextEp = recdData.getString("Next Ep");
		tvShowNextEp.setText(nextEp);
		
		TextView tvShowStatus = (TextView) findViewById(R.id.StatusText);
		String status = recdData.getString("Status");
		tvShowStatus.setText(status);	
	}
}
