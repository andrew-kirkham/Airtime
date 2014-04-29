package com.airtime;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
		//receive show passed on intent
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
		
		final Button button = (Button) findViewById(R.id.favoritesButton);
	    button.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            // Perform action on click
	        	button.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_red));
	        	button.setText("Remove from Favorites");
	        }
	    });
	}	
}
