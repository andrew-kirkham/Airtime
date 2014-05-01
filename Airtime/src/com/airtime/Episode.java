package com.airtime;

import java.util.Calendar;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

public class Episode implements Parcelable {
	public int Id;
	public int ShowId;
	public int SeasonNumber;
	public int EpisodeNumber;
	public int AirYear;
	public int AirMonth;
	public int AirDay;
	public String Name;
	
	public Episode(){
		AirYear = 0;
		AirMonth = 0;
		AirDay = 0;
		Id = 0;
	}
	
	/**
	 * set the airdate of an episode with a string
	 * @param date string in form yyyy-mm-dd
	 */
	public void setAirDate(String date){
		String[] vals = date.split("-");
		AirYear = Integer.parseInt(vals[0]);
		AirMonth = Integer.parseInt(vals[1]);
		AirDay = Integer.parseInt(vals[2]);
	}
	
	public boolean before(Time t){
		if (AirYear > t.year) return false;
		if (AirYear == t.year){
			if (AirMonth > t.month + 1) return false;
			if (AirMonth == t.month + 1){
				if (AirDay >= t.monthDay) return false;
			}
		}
		return true;
	}
	
	public int compareTo(Episode e){
		if (this.before(e)) return -1;
		if (this.after(e)) return 1;
		return 0;
	}
	
	public boolean after(Time t){
		if (AirYear > t.year) return true;
		if (AirYear == t.year){
			if (AirMonth > t.month + 1) return true;
			if (AirMonth == t.month + 1){
				if (AirDay >= t.monthDay) return true;
			}
		}
		return false;
	}
	
	public boolean after(Episode e){
		if (AirYear > e.AirYear) return true;
		if (AirYear == e.AirYear){
			if (AirMonth > e.AirMonth) return true;
			if (AirMonth == e.AirMonth){
				if (AirDay >= e.AirDay) return true;
			}
		}
		return false;
	}
	
	public boolean before(Episode e){
		if (AirYear > e.AirYear) return false;
		if (AirYear == e.AirYear){
			if (AirMonth > e.AirMonth) return false;
			if (AirMonth == e.AirMonth){
				if (AirDay >= e.AirDay) return false;
			}
		}
		return true;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(Id);
		dest.writeInt(ShowId);
		dest.writeInt(SeasonNumber);
		dest.writeInt(EpisodeNumber);
		dest.writeInt(AirYear);
		dest.writeInt(AirMonth);
		dest.writeInt(AirDay);
		dest.writeString(Name);
	}
	
	public static final Parcelable.Creator<Episode> CREATOR = new Parcelable.Creator<Episode>() {
        public Episode createFromParcel(Parcel in) {
        	Episode e = new Episode();
        	e.Id = in.readInt();
        	e.ShowId = in.readInt();
        	e.SeasonNumber = in.readInt();
        	e.EpisodeNumber = in.readInt();
        	e.AirYear = in.readInt();
        	e.AirMonth = in.readInt();
        	e.AirDay = in.readInt();
        	e.Name = in.readString();
            return e;
        }

        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };
}
