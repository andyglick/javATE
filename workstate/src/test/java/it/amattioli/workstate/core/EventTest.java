package it.amattioli.workstate.core;
import it.amattioli.workstate.exceptions.WorkflowException;
import junit.framework.*;

public class EventTest extends TestCase {
  private MetaEvent metaEvent;
  private Event event;
  
  public void setUp() throws Exception {
    metaEvent = new MetaEvent("Evento");
    MetaAttribute param = new MetaAttribute("param",String.class,"init value");
    metaEvent.addParameter(param);
    event = metaEvent.newEvent(null);
  }
  
  public void testMetaEvent() {
    assertTrue(event.hasMetaEvent(metaEvent));
  }
  
  public void testAllowedParameter() {
    assertTrue(event.isAllowedParameter("param"));
    assertFalse(event.isAllowedParameter("noparam"));
  }
  
  public void testParameterValue() {
    assertEquals("init value",event.getParameter("param"));
  }
  
  public void testValidEvent() throws Exception {
    MockEventValidator validator = new MockEventValidator(false);
    metaEvent.addValidator(validator);
    event = metaEvent.newEvent(null);
    assertEquals(1,validator.getCalls());
  }
  
  public void testInvalidEvent() throws Exception {
    MockEventValidator validator = new MockEventValidator(true);
    metaEvent.addValidator(validator);
    try {
      event = metaEvent.newEvent(null);
    } catch(WorkflowException e) {
      return;
    }
    fail("La validazione di un evento e' fallita ma non ho ricevuto eccezione");
  }
  
}
