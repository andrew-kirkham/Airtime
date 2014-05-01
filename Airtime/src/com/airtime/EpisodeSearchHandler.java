package com.airtime;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;


public class EpisodeSearchHandler extends DefaultHandler {

    private StringBuilder sb;
    private ArrayList<Episode> episodes = null;
    private Episode e = null;
    private int showId;
    
    @Override
    public void startElement(String uri, String name, String qName, Attributes atts) {
    	name = name.trim().toLowerCase(Locale.getDefault());        // format the current element name
      	sb = new StringBuilder();           // Reset the string builder
      	if (name.equals("episode")){           // If this is a new node, create a new instance
      		e = new Episode();
      		e.ShowId = showId;
      	}
    }
    
    // SAX parsers may return all contiguous character data in a single chunk, or they may split it into several chunks
    // Therefore we must aggregate the data here, and set it in endElement() function
    @Override
    public void characters(char ch[], int start, int length) {
    	String chars = (new String(ch).substring(start, start + length));
    	sb.append(chars);
    }
    
    @Override
    public void endElement(String uri, String name, String qName) throws SAXException {
    	try {
    		name = name.trim().toLowerCase(Locale.getDefault());
    		if (e == null) return;
    		switch (name){
    			case "episode":
    				episodes.add(e);
    				break;
	    		case "episodename":
	    			e.Name = sb.toString();
	    			break;
	    		case "episodenumber":
	    			e.EpisodeNumber = Integer.valueOf(sb.toString());
	    			break;
	    		case "seasonnumber":
	    			e.SeasonNumber = Integer.valueOf(sb.toString());
	    			break;
	    		case "firstaired":
	    			e.setAirDate(sb.toString());
	    			break;
    			default:
    				break;
    		}

    	} catch (Exception e) {
        	Log.e("xml.handlers.SeriesHandler", "Error:" + e.toString());
    	}
    }
    
  public ArrayList<Episode> getInfo(int seriesId) {
      try {

        URL url = new URL("http://thetvdb.com/api/0A41C0DEA5531762/series/" + String.valueOf(seriesId) + "/all/en.xml");
		episodes = new ArrayList<Episode>();
		this.showId = seriesId;

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader xr = sp.getXMLReader();
        xr.setContentHandler(this);
        xr.parse(new InputSource(url.openStream()));

        return episodes;
    } catch (Exception e) {
        Log.e("xml.handlers.SeriesHandler", "Error:" + e.toString());
        return null;
    }
  }
}