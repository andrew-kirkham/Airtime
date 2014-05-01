package com.airtime;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class BitmapFileCache {
  protected File cacheDir;
  protected long maxSize;
  protected int resampleSize;
  protected float density;


  /** Constructor */
  public BitmapFileCache(Context context){
    init(context, 50000000);  // 50 MB
  }

  /** Constructor */
  public BitmapFileCache(Context context, long maxCacheSize){
    init(context, maxCacheSize);
    }
  /** Constructor Helper */
  private void init(Context context, long maxCacheSize){
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            cacheDir = new File(Environment.getExternalStorageDirectory() + File.separator + "cache", "TheTVDB");
    else
      cacheDir = context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
        
        this.maxSize = maxCacheSize;
        
        density = context.getResources().getDisplayMetrics().density;
        resampleSize = (int)(400 * density);
  }


  /** Get the cache directory */   
    public File getCacheDir() {
    return cacheDir;
  }

    /** Change where the cache lives. If it doesn't exist, it will be created */
  public void setCacheDir(File cacheDir) {
    this.cacheDir = cacheDir;
    if(!cacheDir.exists())
            cacheDir.mkdirs();
  }

  public long getMaxSize() {
    return maxSize;
  }
  public void setMaxSize(long maxSize) {
    this.maxSize = maxSize;
  }



  public boolean contains(String key){
    File file = new File(cacheDir, key);
    return file.exists();
  }

  public boolean containsThumb(String key){
    return contains(key + "T");
  }

  public Bitmap get(String key){
        File file = new File(cacheDir, key);
        FileInputStream iStream;
    try {
      iStream = new FileInputStream(file);
      return BitmapFactory.decodeStream(iStream);
    } catch (Throwable e) {
      Log.e("BitmapFileCache", "Throwable: " + e.getMessage());
      return null;       
    }
    }
  public Bitmap getThumb(String key){
        return get(key + "T");
    }

  public Bitmap getResampled(String key){
        File file = new File(cacheDir, key);
    return decodeSampledBitmapFromFile(file.getAbsolutePath(), resampleSize, resampleSize);
    }
  public Bitmap getThumbResampled(String key){
        return getResampled(key + "T");
    }

  public File getFile(String key){
    return new File(cacheDir, key);
  }
    
  public void put(String key, Bitmap item) throws IOException {
    if (item != null){
      File cachedFile = new File(cacheDir, key);
        if (!cachedFile.exists()) {
          cachedFile.createNewFile();
        }
        OutputStream oStream = new FileOutputStream(cachedFile);
      item.compress(Bitmap.CompressFormat.JPEG, 90, oStream);
      oStream.close();
    }
    trimCache();
    }

  public void putThumb(String key, Bitmap item) throws IOException {
      put(key + "T", item);
    }
    
    public void trimCache(){
      File[] files = FileUtil.getFilesByModifiedDate(cacheDir);
      long[] sizes = FileUtil.getFileSizes(files);
      
      int totalSize = 0;
      for (int i=0; i<sizes.length; i++)
        totalSize += sizes[i];
      
      
      for (int i=0; totalSize > maxSize && i<files.length; i++){
      try {
        files[i].delete();
        totalSize -= sizes[i];  
      }catch (Exception e) {}         
      }
    }
    
    public int getResampleSize(){
      return resampleSize;
    }
    public void setResampleSize(int size){
      resampleSize = (int)(size * density);
    }
    
    
    
    public void clear(){
        File[] files = cacheDir.listFiles();
        if(files == null)
            return;
        for(File f:files)
            f.delete();
    }
    
    
    // Methods to help with resampling
    
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

    // Raw height and width of image
      final int height = options.outHeight;
      final int width = options.outWidth;
      int inSampleSize = 1;

      if (height > reqHeight || width > reqWidth) {
          if (width > height) {
              inSampleSize = Math.round((float)height / (float)reqHeight);
          } else {
              inSampleSize = Math.round((float)width / (float)reqWidth);
          }
      }
      return inSampleSize;
  }

  private Bitmap decodeSampledBitmapFromFile(String fileName, int reqWidth, int reqHeight){

    // First decode with inJustDecodeBounds=true to check dimensions
      final BitmapFactory.Options options = new BitmapFactory.Options();
      options.inJustDecodeBounds = true;
      BitmapFactory.decodeFile(fileName, options);

      // Calculate inSampleSize
      options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

      // Decode bitmap with inSampleSize set
      options.inJustDecodeBounds = false;
      return BitmapFactory.decodeFile(fileName, options);
  }




  // In order to share an image, it seems that it must have a .jpg extension... lame.
  public String makeJPG(String key) throws FileNotFoundException {
    File src = getFile(key);
    File dest = new File(cacheDir, key + ".jpg");
    copyFile(src, dest);

    // for some reason the share intent doesn't like it when we start a path with /mnt/sdcard. It has to be /sdcard
    String path = dest.getAbsolutePath();
    if (path.startsWith("/mnt"))
      path = path.substring(4);
    return path;
  }

  private void copyFile(File srFile, File dtFile) throws FileNotFoundException {
      try{
        InputStream in = new FileInputStream(srFile);

        OutputStream out = new FileOutputStream(dtFile);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0){
          out.write(buf, 0, len);
        }
        in.close();
        out.close();
        System.out.println("File copied.");
      }
      catch(IOException e){
        e.printStackTrace();  
      }
  }
}