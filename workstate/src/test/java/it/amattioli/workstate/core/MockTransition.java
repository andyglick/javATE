package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.Guard;
import it.amattioli.workstate.actions.TransitionAction;

public class MockTransition extends Transition {
  private int timesPerformed = 0;
  private Event lastEvent;
  private State lastState;
  
  public MockTransition(MetaEvent event, MetaState start, MetaState end, TransitionAction action, Guard guard) {
    super(event,start,end,action,guard);
  }
  
  public void perform(Event event, State currentState) {
    timesPerformed++;
  }
  
  public void reset() {
    timesPerformed = 0;
    lastEvent = null;
    lastState = null;
  }
  
  public int getTimesPerformed() {
    return timesPerformed;
  }
  
  public State getLastState() {
    return lastState;
  }
  
  public Event getLastEvent() {
    return lastEvent;
  }
  
}
