package com.airtime;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class WebImage implements Parcelable {

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
    this.url = "http://thetvdb.com/banners/" + url; // For banners: "http://thetvdb.com/banners/"
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
  @Override public int describeContents() {
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(id);
      dest.writeString(url);
      dest.writeString(thumbUrl);
      dest.writeParcelable(bitmap, 0);
  }
  
  public static final Parcelable.Creator<WebImage> CREATOR = new Parcelable.Creator<WebImage>() {
    public WebImage createFromParcel(Parcel in) {
      WebImage banner = new WebImage();
        banner.id = in.readString();
        banner.url = in.readString();
        banner.thumbUrl = in.readString();
        banner.bitmap = in.readParcelable(Bitmap.class.getClassLoader());

        return banner;
    }

    public WebImage[] newArray(int size) {
        return new WebImage[size];
    }
  };

}