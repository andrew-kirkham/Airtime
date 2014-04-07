package com.airtime;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;

/**
 * The settings view that contains all of the user settings.
 * Settings are saved through the default Android settings storage
 * @author Andrew
 *
 */
public class Settings extends Activity {

	/**
	 * Whether or not notifications are enabled
	 */
	public boolean NotificationsEnabled;
	/**
	 * The default time zone adjustment
	 */
	public Date Timezone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}
	
	/**
	 * Edit whether notifications are enabled
	 * @param val
	 */
	public void editNotifications(boolean val){
		NotificationsEnabled = val;
	}
	
	/**
	 * Get the default time zone adjustment
	 * @return Timezone
	 */
	public Date getTimeZone(){
		return Timezone;
	}
	
	/**
	 * Set the default time zone adjustment
	 * @param val
	 */
	public void setTimeZone(Date val){
		Timezone = val;
	}
}
