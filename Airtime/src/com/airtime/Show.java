package com.airtime;

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
	public String Name = "Unknown Show";
	
	public String AirDate;
	public String AirTime;
	public String AirDayOfWeek = "";
	
	public String NextEpisode = getNextEp();
	public String LastEpisode = "Unknown";
	
	public String getNextEp(){
		if (AirDate == null) return "Not Scheduled";
		else if (AirTime == null) return String.format("%s %s", AirDayOfWeek, AirDate);
		return String.format("%s %s at %s", AirDayOfWeek, AirDate, AirTime); //ex Friday 3/14/2013 at 9:00 PM
	}
	
	/**
	 * Network of the show
	 */
	public String Network = "Unknown Network";

	/**
	 * The current airing status of the TV show
	 */
	public String Status = "Unknown Status";
	
	/**
	 * Image url for selected show
	 */
	public WebImage Banner;
	
	/**
	 * The unique id of the TV show. Used by feed and to find images.
	 */
	public int Id;
	
	/**
	 * Whether this show is a stored favorite
	 */
	public Boolean IsAFavorite = false;

	
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof Show)) return false;
		Show s = (Show)obj;
		if (!(s.Name.equals(Name))) return false;
		if (!(s.NextEpisode.equals(NextEpisode))) return false;
		if (!(s.LastEpisode.equals(LastEpisode))) return false;
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
		return String.format(Locale.US, "%s, %s, %s, %s, %s\n", Name, NextEpisode, LastEpisode, Network, Status);
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
        dest.writeByte((byte) (IsAFavorite ? 1 : 0));
        dest.writeString(AirDate);
        dest.writeString(AirDayOfWeek);
        dest.writeString(AirTime);
	}
	
	public static final Parcelable.Creator<Show> CREATOR = new Parcelable.Creator<Show>() {
        public Show createFromParcel(Parcel in) {
        	Show s = new Show();
            s.Name = in.readString();
            s.Network = in.readString();
            s.Status = in.readString();
            s.Id = in.readInt();
            
            s.IsAFavorite = in.readByte() != 0;
            
            s.AirDate = in.readString();
            s.AirDayOfWeek = in.readString();
            s.AirTime = in.readString();
            return s;
        }

        public Show[] newArray(int size) {
            return new Show[size];
        }
    };
}
