package com.airtime;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * @author Bill
 *
 */
public class XMLSAXParser {
     
    private static final String APIKEY = "7C892A3B48BD8F4B";
    private static final String feedUrl = "http://www.thetvdb.com/";
    
    public Show getShow(String name) {
        System.out.println(name);
        int id = getSeriesId(name);
        Show show = null;
        
        try {
          URL showUrl = new URL(feedUrl+APIKEY+"/series/"+id+"/all/");
          System.out.println(showUrl.toString());
          XMLGetSet data = readFromFeed(showUrl);
          show = data.getShow();
        }
        catch (MalformedURLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        return show;
    }
    
    public int getSeriesId(String name) {
        int id = 0;
        URL getSeriesIdUrl;
        
        try {
          getSeriesIdUrl = new URL(feedUrl+"/api/GetSeries.php?seriesname=" + name + "/");
          System.out.println(getSeriesIdUrl.toString());
          XMLGetSet data = readFromFeed(getSeriesIdUrl);
          id = data.getID();
        }
        catch (MalformedURLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        return id;
    }
    
    private XMLGetSet readFromFeed(URL url) {
      try {
          SAXParserFactory saxPF = SAXParserFactory.newInstance();
          SAXParser saxP = saxPF.newSAXParser();
          XMLReader xmlR = saxP.getXMLReader();
          
          XMLHandler myXMLHandler = new XMLHandler();
          xmlR.setContentHandler(myXMLHandler);
          xmlR.parse(new InputSource(url.openStream()));
      }
      catch (Exception e) {
          System.out.println(e);
      }
      
      return XMLHandler.data;
    }
}
