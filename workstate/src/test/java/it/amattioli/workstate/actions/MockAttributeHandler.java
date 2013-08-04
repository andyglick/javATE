package it.amattioli.workstate.actions;
import it.amattioli.workstate.exceptions.WorkflowException;

import java.util.*;

public class MockAttributeHandler implements AttributeHandler {
  private Map<String,Object> attributes = new HashMap<String,Object>();

  public Object getAttribute(String tag) {
    return attributes.get(tag);
  }
  
  public Map<String,Object> getAllAttributes() {
    return attributes;
  }
  
  public void setAttribute(String tag, Object value) throws WorkflowException {
    attributes.put(tag,value);
  }

}
