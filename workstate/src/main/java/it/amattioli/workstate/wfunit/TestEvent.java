package it.amattioli.workstate.wfunit;
import java.util.Map;
import java.util.HashMap;

public class TestEvent {

  private Map attributes = new HashMap();
  private String eventName;

  public TestEvent(String eventName) {
    this.eventName = eventName;
  }

  public String getEventName() {
    return eventName;
  }

  public Map getAttributes() {
    return attributes;
  }

  public void addAttribute(String attrName, Object attrValue) {
    attributes.put(attrName,attrValue);
  }
  
}
