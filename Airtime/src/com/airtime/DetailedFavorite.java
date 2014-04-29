package com.airtime;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.net.ParseException;
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
		final String name = recdData.getString("Name");
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
		
		
	
		//Add Status later!!!!!!!!!!!!!!!!
		reconstructShow(name, network, lastAired, nextEp, status);
		
		final Button button = (Button) findViewById(R.id.favoritesButton);
		final Boolean isAFavorite = recdData.getBoolean("isAFavortie");
		setButton(button , isAFavorite, false);
	    button.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	setButton(button, !isAFavorite, true);
	        }
	    });
	}
	
	public void reconstructShow(String name, String network, String lastEp, String nextEp, String status) {
		SimpleDateFormat formatter = new SimpleDateFormat();
		Calendar lastCalendar = Calendar.getInstance();
		Calendar nextCalendar = (Calendar) lastCalendar.clone();
		Date last, next;
			try {
				next = (Date) formatter.parse(nextEp);
				last = (Date) formatter.parse(lastEp);
				lastCalendar.setTime(last);
				nextCalendar.setTime(next);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}			
		favorite.Name = name;
		favorite.Network = network;		
		favorite.LastEpisode = lastCalendar;
		favorite.NextEpisode = nextCalendar;
		favorite.Status = status;
	}
	
	public void setButton(Button button, Boolean fav, Boolean click){
		File f = new File(this);
		Favorites list = new Favorites();
		//if(button.getText().equals("Add To Favorites") ){
		if(fav){
			button.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_red));
        	button.setText("Remove from Favorites");
        	if(click){
        		f.storeFavorite(favorite);
        		list.removeFromFavoritesList(favorite);
        	}
		}
		else{
			button.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_green));
        	button.setText("Add To Favorites");
        	if(click){
        		f.removeLineFromFile(favorite.Name);
        	}
		}	
	}
}
