package com.airtime;
import android.graphics.Bitmap;

public class WebImage {

  private String id;
  private String url;
  private String thumbUrl;
  Bitmap bitmap;


  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getThumbUrl() {
    if (HasThumb())
      return thumbUrl;
    else
      return url;
  }
  public void setThumbUrl(String thumbUrl) {
    this.thumbUrl = thumbUrl;
  }
  public Bitmap getBitmap() {
    return bitmap;
  }
  public void setBitmap(Bitmap bitmap) {
    this.bitmap = bitmap;
  }


  public WebImage(){}

  public WebImage(String id, String url, String thumbUrl){
    this.id = id;
    this.url = url;
    this.thumbUrl = thumbUrl;
  }

  public boolean HasThumb(){
    if (thumbUrl == null || thumbUrl.equals(""))
      return false;
    else
      return true;
  }


}