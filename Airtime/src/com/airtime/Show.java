package com.airtime;

import java.util.Date;

public class Show {
	public String Name;
	public Date NextEpisode;
	public Date LastEpisode;
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
