package it.amattioli.workstate.actions;

import it.amattioli.workstate.exceptions.WorkflowException;

public class MockStateAction extends AbstractStateAction {
  private int calls = 0;
  private int undoCalls = 0;
  private boolean fail = false;
  private String id;
  
  public MockStateAction() {
  }
  
  public MockStateAction(String id) {
    this.id = id;
  }
  
  public void doAction(AttributeHandler state) throws WorkflowException {
    //System.out.println("Executing State Action "+id);
    calls++;
    if (fail) {
      //System.out.println("Failing State Action "+id);
      throw new WorkflowException("ERROR","Message");
    }
  }
  
  public void undoAction(AttributeHandler state) {
    //System.out.println("Undoing State Action "+id);
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
