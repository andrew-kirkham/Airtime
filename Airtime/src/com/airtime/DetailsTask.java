package com.airtime;

import android.os.AsyncTask;
import android.util.Log;

public class DetailsTask extends AsyncTask<String, Void, Show> {
    
	@Override
  	protected Show doInBackground(String... query) {
		try {
			int showId = Integer.parseInt(query[0]);
			
			// 	Lookup basic series info
			SeriesDetailsHandler infoQuery = new SeriesDetailsHandler();
			Show s = infoQuery.getInfo(showId);
			EpisodeSearchHandler episodeQuery = new EpisodeSearchHandler();
			s.Episodes = episodeQuery.getInfo(s.Id);

        /// IMAGE STUFF
//        Bitmap bitmap;
//        BitmapFileCache fileCache = new BitmapFileCache(ctx, cacheSize);
//
//        if (fileCache.contains(seriesInfo.getBanner().getId())){
//          bitmap = fileCache.get(seriesInfo.getBanner().getId());
//        }else{
//          BitmapWebUtil web = new BitmapWebUtil(ctx);
//          bitmap = web.downloadBitmap(seriesInfo.getBanner().getUrl());
//        fileCache.put(seriesInfo.getBanner().getId(), bitmap);
//      }
//      seriesInfo.getBanner().setBitmap(bitmap);

//      //There is no need to load the poster at this time.     
//      
//      if (fileCache.contains(seriesInfo.getPoster().getId())){
//          bitmap = fileCache.get(seriesInfo.getPoster().getId());
//        }else{
//          BitmapWebUtil web = new BitmapWebUtil(ctx);
//          bitmap = web.downloadBitmap(seriesInfo.getPoster().getUrl());
//        fileCache.put(seriesInfo.getPoster().getId(), bitmap);
//      }
//      seriesInfo.getPoster().setBitmap(bitmap);

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
