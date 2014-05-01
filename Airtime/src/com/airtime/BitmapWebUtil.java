package com.airtime;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapWebUtil {

  Context context;

  public BitmapWebUtil(Context ctx){
    context = ctx;
  }

  /** This function creates the HTTP request and saves the image to the application's cache folder 
   * @throws IOException */
  public Bitmap downloadBitmap(String url) throws IOException {
       
       URL bitmapUrl = new URL(url);
       HttpURLConnection conn = (HttpURLConnection)bitmapUrl.openConnection();
       conn.setConnectTimeout(10000);
       conn.setReadTimeout(30000);
       conn.setInstanceFollowRedirects(true);

       InputStream iStream = conn.getInputStream();
       PatchedInputStream pStream = new PatchedInputStream(iStream);

       return BitmapFactory.decodeStream(pStream);
         
    }





  /* This fixes the issue outlined here: http://code.google.com/p/android/issues/detail?id=6066 */
  public static class PatchedInputStream extends FilterInputStream {
    public PatchedInputStream(InputStream in) {
      super(in);
    }
    public long skip(long n) throws IOException {
      long m = 0L;
      while (m < n) {
        long _m = in.skip(n-m);
        if (_m == 0L) break;
        m += _m;
      }
      return m;
    }
  }
}