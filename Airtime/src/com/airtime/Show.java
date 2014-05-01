package com.airtime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

/**
 * A TV show that is currently running
 * @author Andrew
 */

public class Show implements Parcelable {
	
	/**
	 * Name of show
	 */
	public String Name = "Unknown Show";
	
	public String AirTime;
	public String AirDayOfWeek = "";
	
	private String NextEpisode = getNextEp();
	private Episode NextEp;
	private Episode LastEp;
	private String LastEpisode = getLastEp();
	
	public ArrayList<Episode> Episodes;
	
	public Show(){
		Episodes = new ArrayList<Episode>();
		Banner = new WebImage();
	}
	
	public String getNextEp(){
		NextEp = getNextEpisode();
		if (NextEp.AirYear == 0) return "Not Scheduled";
		return String.format("S%dE%d: %s\n%s %s/%s/%s\n%s", NextEp.SeasonNumber, NextEp.EpisodeNumber, NextEp.Name, 
				AirDayOfWeek, NextEp.AirMonth, NextEp.AirDay, NextEp.AirYear, AirTime);
	}
	
	private Episode getNextEpisode() {
		Time today = new Time();
		today.setToNow();
		Episode nextEp = new Episode();
		if (Episodes == null) return new Episode();
		for (Episode e : Episodes) {
			if (e.before(today)) continue; //earlier episodes
			if (nextEp.AirYear == 0) nextEp = e; 
			if (e.after(nextEp)) continue; //later episodes
			nextEp = e;
		}
		return nextEp;
	}

	public String getLastEp(){
		LastEp = getLastEpisode();
		if (LastEp.AirYear == 0) return "Unknown";
		return String.format("S%dE%d: %s\n%s %s/%s/%s\n%s", LastEp.SeasonNumber, LastEp.EpisodeNumber, LastEp.Name, 
				AirDayOfWeek, LastEp.AirMonth, LastEp.AirDay, LastEp.AirYear, AirTime);
	}
	
	private Episode getLastEpisode(){
		Time today = new Time();
		today.setToNow();
		Episode lastEp = new Episode();
		if (Episodes == null) return new Episode();
		for (Episode e : Episodes) {
			if (e.after(today)) continue; //later episodes
			if (lastEp.AirYear == 0) lastEp = e; 
			if (e.before(lastEp)) continue; //later episodes
			lastEp = e;
		}
		return lastEp;
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
		return String.format(Locale.US, "%d\n", Id);
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
        dest.writeString(AirDayOfWeek);
        dest.writeString(AirTime);
        dest.writeList(Episodes);
        dest.writeParcelable(Banner, 0);
	}
	
	public static final Parcelable.Creator<Show> CREATOR = new Parcelable.Creator<Show>() {
        public Show createFromParcel(Parcel in) {
        	Show s = new Show();
            s.Name = in.readString();
            s.Network = in.readString();
            s.Status = in.readString();
            s.Id = in.readInt();
            
            s.IsAFavorite = in.readByte() != 0;
            
            s.AirDayOfWeek = in.readString();
            s.AirTime = in.readString();
            ArrayList<Episode> ep = new ArrayList<Episode>();
            in.readList(ep, Episode.class.getClassLoader());
            s.Episodes = ep;
            s.Banner = in.readParcelable(WebImage.class.getClassLoader());
            return s;
        }

        public Show[] newArray(int size) {
            return new Show[size];
        }
    };
}
