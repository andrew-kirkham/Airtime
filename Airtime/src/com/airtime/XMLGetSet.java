package com.airtime;

import com.airtime.Show.Status;

public class XMLGetSet {
  
  public Show s;
  
  private String IMAGE_URL = "http://www.thetvdb.com/banners/";
  
  public XMLGetSet() {
    s = new Show();
  }
  
  public void setName(String showName) {
    s.Name = showName;
  }
  
  public void setID(int id) {
    s.Id = id;
  }
  
  public void setStatus(String status) {
    if(status == Status.Continuing.toString()) {
      s.Status = Status.Continuing;
    }
    else if(status == Status.Returning.toString()) {
      s.Status = Status.Returning;
    }
    else if(status == Status.Ended.toString()) {
      s.Status = Status.Ended;
    }
  }
  
  public void setNetwork(String net) {
    s.Network = net;
  }
  
  public void setNextEpisode() {
    
  }
  
  public void setLastEpisode() {
    
  }
  
  public void setImageURL(String url) {
      IMAGE_URL.concat(url);
      s.ImageUrl = IMAGE_URL;
  }
}
