package it.amattioli.workstate.wfunit;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import java.io.*;
import java.util.*;

public class XmlTestEventHandler extends DefaultHandler {

  private static final String ELEMENTO = "elemento";
  private static final String ARRAY = "array";
  private static final String ATTRIBUTO = "attributo";
  private static final String EVENTO = "evento";
  private static final String NAME = "name";

  private List eventList = new LinkedList();
  private TestEvent currEvent;
  private String attrName;
  private CharArrayWriter cDataBuffer = new CharArrayWriter();
  private ArrayList array = new ArrayList();

  public XmlTestEventHandler() {
  }

  public List getEventList() {
    return eventList;
  }

  public void startElement(String namespaceURI, String localName, String qName, Attributes attr) {
    if (qName.equals(EVENTO)) {
      currEvent = new TestEvent(attr.getValue(NAME));
    } else if (qName.equals(ATTRIBUTO)) {
      attrName = attr.getValue(NAME);
      cDataBuffer.reset();
    } else if (qName.equals(ARRAY)) {
      attrName = attr.getValue(NAME);
      array.clear();
    } else if (qName.equals(ELEMENTO)) {
      cDataBuffer.reset();
    }
  }

  public void characters(char[] ch, int start, int length) {
    cDataBuffer.write(ch, start, length );
  }

  public void endElement(String namespaceURI, String localName, String qName) {
    if (EVENTO.equals(qName)) {
      eventList.add(currEvent);
    } else if (ATTRIBUTO.equals(qName)) {
      currEvent.addAttribute(attrName, cDataBuffer.toString());
    } else if (ELEMENTO.equals(qName)) {
      array.add(cDataBuffer.toString());
    } else if (ARRAY.equals(qName)) {
      currEvent.addAttribute(attrName, array.toArray());
    }
  }

}
