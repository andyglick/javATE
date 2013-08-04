package it.amattioli.workstate.core;

import it.amattioli.workstate.exceptions.KeyedMessage;
import it.amattioli.workstate.exceptions.WorkflowException;

public class MockAttributeValidator implements AttributeValidator {
  private boolean fail = false;
  private int calls = 0;
  
  public MockAttributeValidator(boolean fail) {
    this.fail = fail;
  }
  
  public void validate(Object attribute) throws WorkflowException {
    calls++;
    if (fail) {
      throw new WorkflowException("ATTRIBUTE_EXCEPTION",new KeyedMessage("MOCK_KEY"));
    }
  }
  
  public int getCalls() {
    return calls;
  }
  
  public void reset() {
    calls = 0;
  }
  
}
