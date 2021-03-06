package com.airtime;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class File {
	
	private Context c;
	private static final String FILENAME = "favorites";
	private java.io.File favorites;
	
	/**
	 * Constructor for File. Creates a new favorites file if one does not exist
	 * @param context
	 */
	public File(Context context){
		c = context;
		favorites = new java.io.File(c.getFilesDir(), FILENAME);
	}

	/**
	 * Load the favorite TV shows from filesystem
	 * @return An ArrayList of all stored shows
	 */
	public ArrayList<Show> loadFavorites() {
		ArrayList<Show> shows = new ArrayList<Show>();
		try {
			FileInputStream in = c.openFileInput(FILENAME);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		    shows = parseStoredFavorites(reader);
		    
		} catch (Exception e) {
			Log.e("error loading favorites", e.getMessage());
		}
		return shows;
	}
	
	private ArrayList<Show> parseStoredFavorites(BufferedReader reader) throws IOException {
		ArrayList<Show> shows = new ArrayList<Show>();
		String line = reader.readLine();
		while (line != null){
			String[] vals = line.split(",");
			shows.add(parseShow(vals));
			line = reader.readLine();
		}
		return shows;
	}
	
	private Show parseShow(String[] vals) {
		Show s = new Show();
		s.Id = Integer.parseInt(vals[0]);
		return s;
	}
	
	/**
	 * Store a favorite show to the filesystem
	 * @param The show to store
	 */
	public void storeFavorite(Show favorite) {
		FileOutputStream fos;
		try {
			fos = c.openFileOutput(FILENAME, Context.MODE_APPEND);
			fos.write(favorite.toString().getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	//should be converted to take show object later once it is properly reconstructed in detailed favs
	public void removeLineFromFile(Show s) {
		try {
		    java.io.File tempFile = new java.io.File(favorites.getAbsolutePath() + ".tmp");
		    
		    BufferedReader reader = new BufferedReader(new FileReader(favorites));
		    PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
			String line = null;
			
		    while ((line = reader.readLine()) != null){
				if(!(s.toString().trim().equalsIgnoreCase(line.trim()))){
					pw.println(line);
			        pw.flush();
				}
		    }   
		    pw.close();
		    reader.close();	    
		    //Delete the original file
		    favorites.delete(); 
		    //Rename the new file to the filename the original file had.
		    tempFile.renameTo(favorites);
		  }	
		catch (FileNotFoundException ex) {
		      ex.printStackTrace();
		    }
		catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Clears the favorites file entirely. FOR TESTING ONLY
	 */
	public void clearFavorites(){
		FileOutputStream fos;
		try {
			fos = c.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Load the image file from the filesystem
	 * @param The Show for which image to load
	 * @return a Bitmap image
	 */
	public static Bitmap loadImage(Show s){
		Bitmap myBitmap;
		String filepath = String.format(Locale.US,"%d.jpg", s.Id);
		java.io.File imgFile = new java.io.File(filepath);
		if(imgFile.exists()){
		    myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		}
		else myBitmap = null; //should we make a default image?
		return myBitmap;
	}
}
