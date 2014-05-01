package com.airtime;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

public class DetailsTask extends AsyncTask<String, Void, Show> {
	
	private Context ctx;
    private long cacheSize = 10;
    
    public DetailsTask(Context context) {
        ctx = context;
    }
    
	@Override
  	protected Show doInBackground(String... query) {
		try {
			int showId = Integer.parseInt(query[0]);
			
			// 	Lookup basic series info
			SeriesDetailsHandler infoQuery = new SeriesDetailsHandler();
			Show s = infoQuery.getInfo(showId);
			EpisodeSearchHandler episodeQuery = new EpisodeSearchHandler();
			s.Episodes = episodeQuery.getInfo(s.Id);

			Bitmap bitmap;
            BitmapFileCache fileCache = new BitmapFileCache(ctx, cacheSize);
  
            if (fileCache.contains(s.Banner.getId())){
              bitmap = fileCache.get(s.Banner.getId());
            }else{
              BitmapWebUtil web = new BitmapWebUtil(ctx);
              bitmap = web.downloadBitmap(s.Banner.getUrl());
            fileCache.put(s.Banner.getId(), bitmap);
            }
            
            s.Banner.setBitmap(bitmap);

			return s;
		}catch (Exception e){
			Log.e("LoadSeriesDataTask", "doInBackground:" + e.getMessage());
		}
			return null;
	}

  @Override
  protected void onPostExecute(Show s){
  }
}
