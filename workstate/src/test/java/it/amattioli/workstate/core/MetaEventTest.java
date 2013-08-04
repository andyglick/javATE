package it.amattioli.workstate.core;

import java.util.*;
import junit.framework.*;

public class MetaEventTest extends TestCase {
  private MetaEvent testingMetaEvent;
  
  public void setUp() throws Exception {
    testingMetaEvent = new MetaEvent("Evento");
    MetaAttribute param = new MetaAttribute("param",String.class,"");
    testingMetaEvent.addParameter(param);
  }
  
  public void testParameter() {
    assertTrue(testingMetaEvent.isAllowedParameter("param"));
  }
  
  public void testValidParameter() throws Exception {
    testingMetaEvent.checkValidParameter("param","string value");
  }
  
  public void testNotValidParameterTag() throws Exception {
    try {
      testingMetaEvent.checkValidParameter("not exists","string value");
    } catch(IllegalArgumentException e) {
      return;
    }
    fail("Sono riuscito ad assegnare un valore ad un parametro inesistente");
  }
  
  public void testNotValidParameterValue() throws Exception {
    try {
      testingMetaEvent.checkValidParameter("param",new Integer(1));
    } catch(ClassCastException e) {
      return;
    }
    fail("Sono riuscito ad assegnare un Integer ad un parametro String");
  }
  
  public void testInitParameters() throws Exception {
    MetaAttribute param = new MetaAttribute("param1",String.class,"init value");
    testingMetaEvent.addParameter(param);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("param","param value");
    Map<String,Object> initParams = testingMetaEvent.initParameters(params);
    assertEquals("param value",initParams.get("param"));
    assertEquals("init value",initParams.get("param1"));
  }
  
}
