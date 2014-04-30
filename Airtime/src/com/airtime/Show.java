package com.airtime;

import java.util.Calendar;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A TV show that is currently running
 * @author Andrew
 */

public class Show implements Parcelable {
	
	/**
	 * Name of show
	 */
	public String Name;
	/**
	 * Next episode to air
	 */
	public Calendar NextEpisode;
	/**
	 * Last episode to air
	 */
	public Calendar LastEpisode;
	/**
	 * Network of the show
	 */
	public String Network;

	/**
	 * The current airing status of the TV show
	 */
	public String Status;
	
	/**
	 * Image url for selected show
	 */
	public String ImageUrl;
	
	/**
	 * The unique id of the TV show. Used by feed and to find images.
	 */
	public int Id;
	
	public String airDay;
	
	public String airTime;
	
	/**
	 * Whether this show is a stored favorite
	 */
	public Boolean IsAFavorite;
	
	public String getStatus() {
    return Status;
  }
  public void setStatus(String status) {
    this.Status = status;
  }
  public String getNetwork() {
    return Network;
  }
  public void setNetwork(String network) {
    this.Network = network;
  }
  public int getId() {
    return Id;
  }
  public void setId(int id) {
    this.Id = id;
  }
  public String getName() {
    return Name;
  }
  public void setName(String name) {
    this.Name = name;
  }
  public Calendar getLastEpisode() {
    return LastEpisode;
  }
  public void setLastEpisode(Calendar lastep) {
    this.LastEpisode = lastep;
  }
  public Calendar getNextEpisode() {
    return NextEpisode;
  }
  public void setNextEpisode(Calendar nextep) {
    this.NextEpisode = nextep;
  }
  public String getAirDay() {
    return airDay;
  }
  public void setAirDay(String airDay) {
    this.airDay = airDay;
  }
  public String getAirTime() {
    return airTime;
  }
  public void setAirTime(String airTime) {
    this.airTime = airTime;
  }
  
//  public WebImage getBanner() {
//    if (banner == null)
//      banner = new WebImage();
//    return banner;
//  }
//  public void setBanner(WebImage banner) {
//    this.banner = banner;
//  }
	
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof Show)) return false;
		Show s = (Show)obj;
		if (!(s.Name.equals(Name))) return false;
		if (s.NextEpisode.compareTo(NextEpisode) != 0) return false;
		if (s.LastEpisode.compareTo(LastEpisode) != 0) return false;
		if (!(s.Network.equals(Network))) return false;
		if (!(s.Status.equals(Status))) return false;
		if (s.Id != Id) return false;
		if (s.IsAFavorite != IsAFavorite) return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((LastEpisode == null) ? 0 : LastEpisode.hashCode());
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + ((Network == null) ? 0 : Network.hashCode());
		result = prime * result
				+ ((NextEpisode == null) ? 0 : NextEpisode.hashCode());
		result = prime * result + ((Status == null) ? 0 : Status.hashCode());
		return result;
	}
	
	/**
	 * Convert show object to string
	 * Required for storing to a text file
	 */
	public String toString(){
		return String.format(Locale.US, "%s, %d, %d, %s, %s\n", Name, NextEpisode.getTimeInMillis(), LastEpisode.getTimeInMillis(), Network, Status);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Network);
        dest.writeString(Status);
        dest.writeInt(Id);
        dest.writeLong(NextEpisode.getTimeInMillis());
        dest.writeLong(LastEpisode.getTimeInMillis());
        dest.writeByte((byte) (IsAFavorite ? 1 : 0));
	}
	
	public static final Parcelable.Creator<Show> CREATOR = new Parcelable.Creator<Show>() {
        public Show createFromParcel(Parcel in) {
        	Show s = new Show();
            s.Name = in.readString();
            s.Network = in.readString();
            s.Status = in.readString();
            s.Id = in.readInt();
            
            Calendar c = Calendar.getInstance();
            s.NextEpisode = (Calendar)c.clone();
            s.NextEpisode.setTimeInMillis(in.readLong());
            
            s.LastEpisode = (Calendar)c.clone();
            s.LastEpisode.setTimeInMillis(in.readLong());
            s.IsAFavorite = in.readByte() != 0; 
            return s;
        }

        public Show[] newArray(int size) {
            return new Show[size];
        }
    };
}
