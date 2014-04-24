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
		//recieve show passed on intent??
		Bundle recdData = getIntent().getExtras();
		String myVal = recdData.getString("my.package.dataToPass");
		
		TextView tvShowName = (TextView) findViewById(R.id.TVshowTitle);
		tvShowName.setText(favorite.Name);
		
		TextView tvShowNetwork = (TextView) findViewById(R.id.TVshowNetwork);
		tvShowNetwork.setText(favorite.Network);
		
		TextView lastAired = (TextView) findViewById(R.id.LastAriedText);
		//tvShowNetwork.setText(favorite.LastEpisode);;
		
		TextView nextEp = (TextView) findViewById(R.id.NextEpText);
		//tvShowNetwork.setText(favorite.NextEpisode);
		
		TextView status = (TextView) findViewById(R.id.StatusText);
		//tvShowNetwork.setText(favorite.Status);
		
		
	}
}
