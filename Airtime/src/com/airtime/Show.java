package com.airtime;

import java.util.Date;

/**
 * A TV show that is currently running
 * @author Andrew
 *
 */
public class Show {
	
	/**
	 * A status of the TV show
	 * Continuing - currently airing
	 * Returning - not currently airing but coming back
	 * Ended - ended or canceled and not coming back
	 * @author REDBULL
	 *
	 */
	public enum Status{
		Continuing, 
		Returning, 
		Ended
	};
	/**
	 * Name of show
	 */
	public String Name;
	/**
	 * Next episode to air
	 */
	public Date NextEpisode;
	/**
	 * Last episode to air
	 */
	public Date LastEpisode;
	/**
	 * Network of the show
	 */
	public String Network;

	/**
	 * The current airing status of the TV show
	 */
	public Status Status;
	
	/**
	 * Image url for selected show
	 */
	public String ImageUrl;
	
	/**
	 * The unique id of the TV show. Used by feed and to find images.
	 */
	public int Id;
	
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof Show)) return false;
		Show s = (Show)obj;
		if (!(s.Name.equals(Name))) return false;
		if (s.NextEpisode != NextEpisode) return false;
		if (s.LastEpisode != LastEpisode) return false;
		if (!(s.Network.equals(Network))) return false;
		if (s.Status != Status) return false;
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
	
	public String toString(){
		return String.format("%s, %tD, %tD, %s\n", Name, NextEpisode, LastEpisode, Network);
	}
}
