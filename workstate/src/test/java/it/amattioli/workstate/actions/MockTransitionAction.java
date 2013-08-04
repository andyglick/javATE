package it.amattioli.workstate.actions;

import it.amattioli.workstate.exceptions.WorkflowException;

public class MockTransitionAction extends AbstractTransitionAction {
  private int calls = 0;
  private int undoCalls = 0;
  private boolean fail = false;
  private String id;
  
  public MockTransitionAction() {
  }
  
  public MockTransitionAction(String id) {
    this.id = id;
  }
  
  public void doAction(AttributeReader event, AttributeHandler state) throws WorkflowException {
    //System.out.println("Executing transition action "+id);
    calls++;
    if (fail) {
      //System.out.println("Failing transition action "+id);
      throw new WorkflowException("ERROR","Message");
    }
  }
  
  public void undoAction(AttributeReader event, AttributeHandler state) {
    //System.out.println("Undoing transition action "+id);
    undoCalls++;
  }
  
  public int getCalls() {
    return calls;
  }
  
  public int getUndoCalls() {
    return undoCalls;
  }
  
  public void doFail() {
    fail = true;
  }
  
  public void reset() {
    calls = 0;
    undoCalls = 0;
    fail = false;
  }
  
}
