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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
	 * Load the favorites from the filesystem and display them to the screen
	 */
	public ArrayList<Show> loadFavorites() {
		ArrayList<Show> shows = new ArrayList<Show>();
		try {
			FileInputStream in = c.openFileInput(FILENAME);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		    shows = parseStoredFavorites(reader);
		    
		} catch (IOException e) {
			e.printStackTrace();
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
		s.Name = vals[0];
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(Long.parseLong(vals[1].trim()));
		s.LastEpisode = (Calendar)c.clone();
		c.setTimeInMillis(Long.parseLong(vals[2].trim()));
		s.NextEpisode = (Calendar)c.clone();
		s.Network = vals[3];
		s.Status = vals[4];
		return s;
	}
	
	/**
	 * Store a favorite show to the filesystem
	 * @param show object
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
	public void removeLineFromFile(String lineToRemove) {
		try {
			java.io.File favorites = new java.io.File(c.getFilesDir(), FILENAME);
		    java.io.File tempFile = new java.io.File(favorites.getAbsolutePath() + ".tmp");
		    
		    BufferedReader reader = new BufferedReader(new FileReader(favorites));
		    PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
		   
		    String line = null;
			
		    while ((line = reader.readLine()) != null){
				if(!line.trim().equals(lineToRemove)){
					pw.println(line);
			        pw.flush();
				}
		    }   
		    pw.close();
		    reader.close();
		    
		    //Delete the original file
		    if (!favorites.delete()) {
		        System.out.println("Could not delete file");
		        return;
		      } 
		      
		    //Rename the new file to the filename the original file had.
		    if (!tempFile.renameTo(favorites)){
		    	System.out.println("Could not rename file");
		    }
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
