package it.amattioli.workstate.core;

import it.amattioli.workstate.exceptions.KeyedMessage;
import it.amattioli.workstate.exceptions.WorkflowException;

public class MockEventValidator implements EventValidator {
  private boolean fail = false;
  private int calls = 0;
  
  public MockEventValidator(boolean fail) {
    this.fail = fail;
  }
  
  public void validate(Event event) throws WorkflowException {
    calls++;
    if (fail) {
      throw new WorkflowException("EVENT_EXCEPTION",new KeyedMessage("MOCK_KEY"));
    }
  }
  
  public int getCalls() {
    return calls;
  }
  
  public void reset() {
    calls = 0;
  }
  
}
