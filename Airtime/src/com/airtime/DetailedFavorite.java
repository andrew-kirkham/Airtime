package com.airtime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
		
		Show s = recdData.getParcelable("Show");
		TextView tvShowName = (TextView) findViewById(R.id.TVshowTitle);
		tvShowName.setText(s.Name);
		
		TextView tvShowNetwork = (TextView) findViewById(R.id.TVshowNetwork);
		tvShowNetwork.setText(s.Network);
		
		TextView tvShowlastAired = (TextView) findViewById(R.id.LastAriedText);
		tvShowlastAired.setText(createDateStrings(s.LastEpisode));
		
		TextView tvShowNextEp = (TextView) findViewById(R.id.NextEpText);
		tvShowNextEp.setText(createDateStrings(s.NextEpisode));
		
		TextView tvShowStatus = (TextView) findViewById(R.id.StatusText);
		tvShowStatus.setText(s.Status);	
		
		final Button button = (Button) findViewById(R.id.favoritesButton);
	    button.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            // Perform action on click
	        	button.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_red));
	        	button.setText("Remove from Favorites");
	        }
	    });
	}	
	public String createDateStrings(Calendar date){
		String stringDate;
		SimpleDateFormat formatter = new SimpleDateFormat(
                "EEE MMMM d \n hh:mm a", Locale.getDefault());
		stringDate = formatter.format(date.getTime()).toString();
		return stringDate; 
	}
}
