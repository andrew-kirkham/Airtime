package com.airtime;

import com.airtime.Show.Status;

/**
 * Get and Set values for TV shows using thetvdb.com
 * @author Bill
 *
 */
public class XMLGetSet {
  
  public Show s;
  
  private String IMAGE_URL = "http://www.thetvdb.com/banners/";
  
  /**
   * XMLGetSet constructor
   */
  public XMLGetSet() {
    s = new Show();
  }
  
  /**
   * Set TV show name
   * @param showName
   */
  public void setName(String showName) {
    s.Name = showName;
  }
  
  /**
   * Set show Id.
   * Used for grabbing more information from thetvdb.com
   *
   * @param id
   */
  public void setID(int id) {
    s.Id = id;
  }
  
  /**
   * Set the current status of the show
   * @param status
   */
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
  
  /**
   * set airing network for the show
   * @param net
   */
  public void setNetwork(String net) {
    s.Network = net;
  }
  
  /**
   * set next episode
   * Possible Implementation: See today's date and check within 7days from now else throw not enough information
   */
  public void setNextEpisode() {
    
  }
  
  /**
   * set last aired episode 
   */
  public void setLastEpisode() {
    
  }
  
  /**
   * set Image URL
   * @param url
   */
  public void setImageURL(String url) {
      IMAGE_URL.concat(url);
      s.ImageUrl = IMAGE_URL;
  }
}
