package air.time.airtime;

import java.util.Date;
import java.util.Objects;

public class Show {
	public String Name;
	public Date Airtime;
	public Date LastEpisode;
	public String Network;
	
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof Show)) return false;
		Show s = (Show)obj;
		if (s.Name.equals(Name)) return false;
		if (s.Airtime != Airtime) return false;
		if (s.LastEpisode != LastEpisode) return false;
		if (s.Network.equals(Network)) return false;
		return true;
	}
	@Override
	public int hashCode() {
		return Objects.hash(Name, Airtime, LastEpisode, Network);
	}
}
