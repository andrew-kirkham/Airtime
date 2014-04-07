package com.airtime;

import com.airtime.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * A screen that will display our logo and application name before the application is started
 * @author Andrew
 *
 */
public class Splash extends Activity {

	
	/**
	 * The time before the splash screen is no longer displayed 
	 */
	public static int TIME_OUT = 1500;
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, Favorites.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }
}
