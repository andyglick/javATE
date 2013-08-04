package it.amattioli.workstate.core;

import it.amattioli.workstate.conversion.ConversionService;

import java.util.*;
import junit.framework.*;

public class EventRepositoryTest extends TestCase {
  private EventRepository repository;
  private MetaEvent metaEvent;
  
  public void setUp() {
    ConversionService conversionService = new ConversionService();
    repository = new EventRepository(conversionService);
    metaEvent = new MetaEvent("EVENTO");
    metaEvent.addParameter(new MetaAttribute("PARAM1",String.class,""));
    repository.addMetaEvent(metaEvent);
  }
  
  public void testNewSimpleEvent() throws Exception {
    Event event = repository.buildEvent("EVENTO",new HashMap<String, Object>());
    assertTrue(event.hasMetaEvent(metaEvent));
  }
  
  public void testNewParametrizedEvent() throws Exception {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("PARAM1","value");
    Event event = repository.buildEvent("EVENTO", params);
    assertTrue(event.hasMetaEvent(metaEvent));
    assertEquals("value",event.getParameter("PARAM1"));
  }
  
  public void testNewArrayParamEvent() throws Exception {
    MetaEvent metaEvent = new MetaEvent("EVENTO_ARRAY");
    metaEvent.addParameter(new MetaAttribute("PARAM1",String[].class,null));
    repository.addMetaEvent(metaEvent);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("PARAM1",new String[] {"value1","value","value3"});
    Event event = repository.buildEvent("EVENTO_ARRAY", params);
    assertTrue(event.hasMetaEvent(metaEvent));
    String[] realParams = (String[])event.getParameter("PARAM1");
    assertEquals(3,realParams.length);
    assertEquals("value1",realParams[0]);
  }
  
  public void testNewArrayOf1ParamEvent() throws Exception {
    MetaEvent metaEvent = new MetaEvent("EVENTO_ARRAY");
    metaEvent.addParameter(new MetaAttribute("PARAM1",String[].class,null));
    repository.addMetaEvent(metaEvent);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("PARAM1","value1");
    Event event = repository.buildEvent("EVENTO_ARRAY", params);
    assertTrue(event.hasMetaEvent(metaEvent));
    String[] realParams = (String[])event.getParameter("PARAM1");
    assertEquals(1,realParams.length);
    assertEquals("value1",realParams[0]);
  }
  
  public void testWrongEventName() throws Exception {
    Event event = repository.buildEvent("EVENTO_ERRATO",new HashMap<String, Object>());
    assertNull(event);
  }
  
  public void testIgnoredParamEvent() throws Exception {
    MetaEvent metaEvent = new MetaEvent("EVENTO_ARRAY");
    metaEvent.addParameter(new MetaAttribute("PARAM1",String.class,null));
    repository.addMetaEvent(metaEvent);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("PARAM1","value1");
    params.put("PARAM2","value2");
    Event event = repository.buildEvent("EVENTO_ARRAY", params);
    assertTrue(event.hasMetaEvent(metaEvent));
    try {
      Object p = event.getParameter("PARAM2"); 
    } catch(IllegalArgumentException e) {
      return;
    }
    fail();
  }
  
}
