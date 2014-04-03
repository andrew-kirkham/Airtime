package com.airtime;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.airtime.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Favorites extends Activity {

	public static final String FILENAME = "favorites";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);
		loadFavorites();
	}

	private void loadFavorites() {
		try {
			FileInputStream favs = openFileInput(FILENAME);
			favs.read();
			favs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void storeFavorite(Show favorite) {
		FileOutputStream fos;
		try {
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(favorite.toString().getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.favorites, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void sortByShow(View v){

	}
	public void sortByAirtime(View v){
		
	}
	public void sortByNetwork(View v){
		
	}
	public void sortByLastEp(View v){
		
	}
}
