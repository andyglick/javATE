package it.amattioli.workstate.wfunit;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class XmlTestEventsReader implements TestEventsReader  {
  private String fileName;
  private static final SAXParserFactory PARSER_FACTORY = SAXParserFactory.newInstance();

  public XmlTestEventsReader(String fileName) {
    this.fileName = fileName;
  }

  public List readEvents() throws ReadException {
    List result = null;
    try {
      SAXParser parser = PARSER_FACTORY.newSAXParser();
      XmlTestEventHandler testHandler = new XmlTestEventHandler();
      InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
      parser.parse(input,testHandler);
      result = testHandler.getEventList();
    } catch(ParserConfigurationException ce) {
      throw new ReadException(ce);
    } catch(SAXException se) {
      throw new ReadException(se);
    } catch(IOException fe) {
      throw new ReadException(fe);
    }
    return result;
  }

}
