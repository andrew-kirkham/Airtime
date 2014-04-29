package com.airtime;

import com.airtime.Show.Status;

/**
 * Get and Set values for TV shows using thetvdb.com
 * @author Bill
 *
 */
public class XMLGetSet {
  
  public Show s;
  
  private static final String IMAGE_URL = "http://www.thetvdb.com/banners/";
  
  /**
   * XMLGetSet constructor
   */
  public XMLGetSet() {
    s = new Show();
  }
  
  public Show getShow() {
    return s;
  }
  
  /**
   * Set TV show name
   * @param showName
   */
  public void setName(String showName) {
    s.Name = showName;
  }
  
  public String getName() {
    return s.Name;
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
  
  public int getID() {
    return s.Id;
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
  
  public Status getStatus() {
    return s.Status;
  }
  
  /**
   * set airing network for the show
   * @param net
   */
  public void setNetwork(String net) {
    s.Network = net;
  }
  
  public String getNetwork() {
    return s.Network;
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
