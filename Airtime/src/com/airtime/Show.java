package com.airtime;

import java.util.Date;

/**
 * A TV show that is currently running
 * @author Andrew
 *
 */
public class Show {
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
	
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof Show)) return false;
		Show s = (Show)obj;
		if (s.Name.equals(Name)) return false;
		if (s.NextEpisode != NextEpisode) return false;
		if (s.LastEpisode != LastEpisode) return false;
		if (s.Network.equals(Network)) return false;
		return true;
	}
	@Override
	public int hashCode() {
		return 0;
		//return Objects.hash(Name, Airtime, LastEpisode, Network);
	}
	
	public String toString(){
		return String.format("{0}, {1}, {2}, {3}", Name, NextEpisode, LastEpisode, Network);
	}
}
