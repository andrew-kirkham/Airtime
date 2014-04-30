package com.airtime;

import java.net.URL;
import java.net.URLEncoder;
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

/**
 * Queries TheTvDb for all shows matching the given query and handles the parsing of the XML
 * @author Andrew
 *
 */
public class SeriesSearchHandler extends DefaultHandler {
	private Show currentSeries = null;
    private ArrayList<Show> seriesList = null;
    private StringBuilder sb;
    
    @Override
	public void startElement(String uri, String name, String qName, Attributes atts) {
	    name = name.trim().toLowerCase(Locale.getDefault());
	    sb = new StringBuilder();

	    if (name.equals("series")){
	    	currentSeries = new Show();
    	}
    }
    
	@Override
	public void characters(char ch[], int start, int length) {
		String chars = (new String(ch).substring(start, start + length));
		sb.append(chars);
	}


    @Override
    /**
     * Processing to do after the entire file has been read in
     */
	public void endElement(String uri, String name, String qName) throws SAXException {
		try {
			name = name.trim().toLowerCase(Locale.getDefault());

			if (name.equals("series")){
				seriesList.add(currentSeries);			
			}else if (name.equals("id")){
				currentSeries.Id = Integer.valueOf(sb.toString());
			}else if (name.equals("seriesname")){
				currentSeries.Name = sb.toString();
			}else if (name.equals("network")){
				currentSeries.Network = sb.toString();
			}
		} catch (Exception e) {
				Log.e("xml.handlers.SeriesHandler", e.toString());
		}
		if (currentSeries.Name == null) currentSeries.Name = "NOT_FOUND";
		if (currentSeries.Network == null) currentSeries.Network = "Unknown Network";
	}
    
    /**
     * Search TheTvDb for all shows matching a given string
     * @param seriesName The string to match for. All shows containing the string will be returned
     * @param languagePreference The desired language to search for. Ex 'en' for English
     * @return
     */
	public ArrayList<Show> searchSeries(String seriesName, String languagePreference) {
	    try {
	    	URL url = new URL("http://www.thetvdb.com/api/GetSeries.php?seriesname=" + URLEncoder.encode(seriesName,"UTF-8") + "&language=en");		//http://www.thetvdb.com/api/GetSeries.php?seriesname=
	    	
			seriesList = new ArrayList<Show>();

		    SAXParserFactory spf = SAXParserFactory.newInstance();
		    SAXParser sp = spf.newSAXParser();
		    XMLReader xr = sp.getXMLReader();
		    xr.setContentHandler(this);
		    xr.parse(new InputSource(url.openStream()));

		    return seriesList;
		} catch (Exception e) {
			Log.e("xml.handlers.SeriesSearchHandler", e.toString());
		    return null;
		}
	}
}
