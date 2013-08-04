package it.amattioli.workstate.core;
import it.amattioli.workstate.actions.MockStateAction;
import it.amattioli.workstate.actions.MockTransitionAction;
import it.amattioli.workstate.actions.TrueGuard;
import it.amattioli.workstate.exceptions.WorkflowException;
import junit.framework.*;

public abstract class AbstractTransitionTest extends TestCase {
  protected MetaEvent testingMetaEvent;
  protected Transition testingTransition;
  protected MockStateAction testingExitAction;
  protected MockTransitionAction testingTransitionAction;
  protected MockStateAction testingEntryAction;
  protected SequentialState testingState;
  protected MetaState startMetaState;
  protected MetaState endMetaState;
  
  public State getDeepCurrentState(SequentialState testingState) {
    State result = testingState.getCurrentState();
    if (result instanceof SequentialState) {
      result = getDeepCurrentState((SequentialState)result);
    }
    return result;
  }
  
  public void testEqualsSelf() {
    assertTrue(testingTransition.equals(testingTransition));
  }
  
  public void testSelfHasSameTrigger() {
    assertTrue(testingTransition.hasSameTrigger(testingTransition));
  }
  
  public void testDifferentEventHasNotSameTrigger() {
    MetaEvent otherMetaEvent = new MetaEvent("OtherEvent");
    Transition otherTransition = new ExternalTransition(otherMetaEvent,startMetaState,endMetaState,testingTransitionAction,null);
    assertFalse(testingTransition.hasSameTrigger(otherTransition));
  }
  
  public void testDifferentGuardHasNotSameTrigger() {
    Transition otherTransition = new ExternalTransition(testingMetaEvent,startMetaState,endMetaState,testingTransitionAction,new TrueGuard());
    assertFalse(testingTransition.hasSameTrigger(otherTransition));
  }
        
  public void testFinalState() throws Exception {
    Event event = testingMetaEvent.newEvent(null);
    testingTransition.perform(event,getDeepCurrentState(testingState));
    assertTrue(getDeepCurrentState(testingState).hasMetaState(endMetaState));
  }
  
  public void testExitAction() throws Exception {
    Event event = testingMetaEvent.newEvent(null);
    testingTransition.perform(event,getDeepCurrentState(testingState));
    assertEquals(1,testingExitAction.getCalls());
    assertEquals(0,testingExitAction.getUndoCalls());
  }
  
  public void testTransitionAction() throws Exception {
    Event event = testingMetaEvent.newEvent(null);
    testingTransition.perform(event,getDeepCurrentState(testingState));
    assertEquals(1,testingTransitionAction.getCalls());
    assertEquals(0,testingTransitionAction.getUndoCalls());
  }
  
  public void testEntryAction() throws Exception {
    Event event = testingMetaEvent.newEvent(null);
    testingTransition.perform(event,getDeepCurrentState(testingState));
    assertEquals(1,testingEntryAction.getCalls());
    assertEquals(0,testingEntryAction.getUndoCalls());
  }
  
  public void testFailingAction() throws Exception {
    testingTransitionAction.doFail();
    Event event = testingMetaEvent.newEvent(null);
    try {
      testingTransition.perform(event,getDeepCurrentState(testingState));
    } catch(WorkflowException e) {
      assertTrue(getDeepCurrentState(testingState).hasMetaState(startMetaState));
      assertEquals(1,testingExitAction.getCalls());
      assertEquals(1,testingExitAction.getUndoCalls());
      assertEquals(1,testingTransitionAction.getCalls());
      assertEquals(1,testingTransitionAction.getUndoCalls());
      assertEquals(0,testingEntryAction.getCalls());
      assertEquals(0,testingEntryAction.getUndoCalls());
      return;
    }
    fail("La transition action è fallita ma non ho ricevuto l'eccezione");
  }
  
  public void testFailingEntryAction() throws Exception {
    testingEntryAction.doFail();
    Event event = testingMetaEvent.newEvent(null);
    try {
      testingTransition.perform(event,getDeepCurrentState(testingState));
    } catch(WorkflowException e) {
      assertTrue(getDeepCurrentState(testingState).hasMetaState(startMetaState));
      assertEquals(1,testingExitAction.getCalls());
      assertEquals(1,testingExitAction.getUndoCalls());
      assertEquals(1,testingTransitionAction.getCalls());
      assertEquals(1,testingTransitionAction.getUndoCalls());
      assertEquals(1,testingEntryAction.getCalls());
      assertEquals(1,testingEntryAction.getUndoCalls());
      return;
    }
    fail("La entry action è fallita ma non ho ricevuto l'eccezione");
  }
  
  public void testFailingExitAction() throws Exception {
    testingExitAction.doFail();
    Event event = testingMetaEvent.newEvent(null);
    try {
      testingTransition.perform(event,getDeepCurrentState(testingState));
    } catch(WorkflowException e) {
      assertTrue(getDeepCurrentState(testingState).hasMetaState(startMetaState));
      assertEquals(1,testingExitAction.getCalls());
      assertEquals(1,testingExitAction.getUndoCalls());
      assertEquals(0,testingTransitionAction.getCalls());
      assertEquals(0,testingTransitionAction.getUndoCalls());
      assertEquals(0,testingEntryAction.getCalls());
      assertEquals(0,testingEntryAction.getUndoCalls());
      return;
    }
    fail("La exit action è fallita ma non ho ricevuto l'eccezione");
  }
  
}
