package com.airtime;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
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

	//Show favorite = new Show();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_favorite);
		//receive show passed on intent
		Bundle recdData = getIntent().getExtras();
		
		final Show s = recdData.getParcelable("Show");
		
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
		final Boolean isAFavorite = recdData.getBoolean("isAFavorite");
		setButton(button , isAFavorite, false, s);
	    button.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	setButton(button, !isAFavorite, true, s);
	        }
	    });
	}
	
	public void setButton(Button button, Boolean fav, Boolean click, Show s){
		File f = new File(this);
		Favorites list = new Favorites();
		//if(button.getText().equals("Add To Favorites") ){
		if(fav){
			button.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_red));
        	button.setText("Remove from Favorites");
        	if(click){
        		//f.storeFavorite(s);
        	}
		}
		else{
			button.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_green));
        	button.setText("Add To Favorites");
        	if(click){ 
        		Intent intent = new Intent();
        		intent.putExtra("removeable", s);
        		setResult(RESULT_OK, intent);
        		finish();
        	}
		}	
	}
	public String createDateStrings(Calendar date){
		String stringDate;
		SimpleDateFormat formatter = new SimpleDateFormat(
                "EEE MMMM d \n hh:mm a", Locale.getDefault());
		stringDate = formatter.format(date.getTime()).toString();
		return stringDate; 
	}
}
