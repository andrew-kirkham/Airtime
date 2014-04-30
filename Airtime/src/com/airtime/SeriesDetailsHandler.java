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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


public class SeriesDetailsHandler extends DefaultHandler {

    private StringBuilder sb;
    private Show currentSeries;
    private Context context;

    public SeriesDetailsHandler(Context ctx){
      context = ctx;
    }
    
    @Override
  public void startElement(String uri, String name, String qName, Attributes atts) {
      name = name.trim().toLowerCase(Locale.getDefault());        // format the current element name
      sb = new StringBuilder();           // Reset the string builder

      if (name.equals("series")){           // If this is a new node, create a new instance
        currentSeries = new Show();
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

      if (name.equals("id")){
        currentSeries.setId(Integer.valueOf(sb.toString()));
//        currentSeries.getBanner().setId("S" + sb.toString());
//        currentSeries.getPoster().setId("P" + sb.toString());
      }
      
//      else if (name.equals("banner")){
//        currentSeries.getBanner().setUrl(AppSettings.BANNER_URL + sb.toString());
//      }else if (name.equals("poster")){
//        currentSeries.getPoster().setUrl(AppSettings.BANNER_URL + sb.toString());
//      }
      
      else if (name.equals("seriesname")){
        currentSeries.setName(sb.toString());
      }else if (name.equals("airs_dayofweek")){
        currentSeries.setAirDay(sb.toString());
      }else if (name.equals("airs_time")){
        currentSeries.setAirTime(sb.toString());
      }else if (name.equals("network")){
        currentSeries.setNetwork(sb.toString());
      }

    } catch (Exception e) {
        Log.e("xml.handlers.SeriesHandler", "Error:" + e.toString());
    }
  }
    
  public Show getInfo(int seriesId) {
      try {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String languageCode = settings.getString("language", "en");

      URL url = new URL("http://thetvdb.com/api/0A41C0DEA5531762/series/" + String.valueOf(seriesId) + "/" + languageCode + ".xml");    //http://thetvdb.com/api/0A41C0DEA5531762/series/<seriesid>/en.xml

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