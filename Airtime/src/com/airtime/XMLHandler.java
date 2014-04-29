package com.airtime;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Bill
 *
 */
public class XMLHandler extends DefaultHandler {

  String elementValue = null;
  Boolean elementOn = false;
  public static XMLGetSet data = null;

  /**
   * @return XMLGetSet
   */
  public static XMLGetSet getXMLData() {
      return data;
  }

  /**
   * @param data
   */
  public static void setXMLData(XMLGetSet data) {
      XMLHandler.data = data;
  }
  
  /** 
   * This will be called when the tags of the XML starts.
   **/
  @Override
  public void startElement(String uri, String localName, String qName,
          Attributes attributes) throws SAXException {

      elementOn = true;

      if (localName.equals("Data"))
      {
          data = new XMLGetSet();
      } else if (localName.equals("Series")) {
        
      }
  }

  /** 
   * This will be called when the tags of the XML end.
   **/
  @Override
  public void endElement(String uri, String localName, String qName)
          throws SAXException {

      elementOn = false;

      /** 
       * Sets the values after retrieving the values from the XML tags
       * */
      
      if (localName.equalsIgnoreCase("id"))
          data.setID(Integer.parseInt(elementValue));
      else if (localName.equalsIgnoreCase("SeriesName"))
          data.setName(elementValue);
      else if (localName.equalsIgnoreCase("Status"))
          data.setStatus(elementValue);
      else if (localName.equalsIgnoreCase("Network"))
          data.setNetwork(elementValue);
      else if (localName.equalsIgnoreCase("fanart"))
          data.setImageURL(elementValue);
      
      
      //
      //TODO: set last episode and next episode
      //
  }

  /** 
   * This is called to get the tags value
   **/
  @Override
  public void characters(char[] ch, int start, int length)
          throws SAXException {

      if (elementOn) {
          elementValue = new String(ch, start, length);
          elementOn = false;
      }

  }
}
