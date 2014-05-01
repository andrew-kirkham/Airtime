package com.airtime;

import java.net.URL;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;


public class SeriesDetailsHandler extends DefaultHandler {

    private StringBuilder sb;
    private Show currentSeries;
    
    @Override
    public void startElement(String uri, String name, String qName, Attributes atts) {
    	name = name.trim().toLowerCase(Locale.getDefault());        // format the current element name
      	sb = new StringBuilder();           // Reset the string builder

      	if (name.equals("series")){           // If this is a new node, create a new instance
      		currentSeries = new Show();
      		currentSeries.AirDate = "3/14/2014";
      	}
      	if (name.equals("episode")){
      		Log.d("xml parse", "episode found");
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
    		
    		switch (name){
	    		case "id":
	    			currentSeries.Id = Integer.valueOf(sb.toString());
	    			break;
	    		case "seriesname":
	    			currentSeries.Name = sb.toString();
	    			break;
	    		case "network":
	    			currentSeries.Network = sb.toString();
	    			break;
	    		case "status":
	    			currentSeries.Status = sb.toString();
	    			break;
	    		case "airs_time":
	    			currentSeries.AirTime = sb.toString();
	    			break;
	    		case "airs_dayofweek":
	    			currentSeries.AirDayOfWeek = sb.toString();
	    			break;
	    		case "banner":
	    		  currentSeries.Banner.setUrl(sb.toString());
	    		  break;
    		}
    		currentSeries.LastEpisode = "DO ME";
      
//    else if (name.equals("banner")){
//      currentSeries.getBanner().setUrl(AppSettings.BANNER_URL + sb.toString());
//    }else if (name.equals("poster")){
//      currentSeries.getPoster().setUrl(AppSettings.BANNER_URL + sb.toString());
//    }
    

    	} catch (Exception e) {
        	Log.e("xml.handlers.SeriesHandler", "Error:" + e.toString());
    	}
    }
    
  public Show getInfo(int seriesId) {
      try {

        URL url = new URL("http://thetvdb.com/api/0A41C0DEA5531762/series/" + String.valueOf(seriesId) + "/all/en.xml");

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader xr = sp.getXMLReader();
        xr.setContentHandler(this);
        xr.parse(new InputSource(url.openStream()));

        return currentSeries;
    } catch (Exception e) {
        Log.e("xml.handlers.SeriesHandler", "Error:" + e.toString());
        return null;
    }
  }
}