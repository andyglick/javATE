package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.MockStateAction;
import it.amattioli.workstate.actions.MockTransitionAction;
import it.amattioli.workstate.exceptions.WorkflowException;

public class InternalTransitionTest extends AbstractTransitionTest {
    
  public void setUp() throws Exception {
    MetaSequentialState sequential = new MetaSequentialState("TopState",null,null); 
    MetaInitialState initial = new MetaInitialState();
    sequential.addMetaState(initial);
    testingExitAction = new MockStateAction();
    testingEntryAction = new MockStateAction();
    MetaSimpleState simple1 = new MetaSimpleState("SimpleState1",testingEntryAction,testingExitAction);
    sequential.addMetaState(simple1);
    startMetaState = simple1;
    Transition initTrans = new ExternalTransition(null,initial,simple1,null,null);
    
    endMetaState = simple1;
    testingMetaEvent = new MetaEvent("TestEvent");
    testingTransitionAction = new MockTransitionAction();
    testingTransition = new InternalTransition(testingMetaEvent,simple1,testingTransitionAction,null);
    
    testingState = (SequentialState)sequential.newState(null);
    testingState.enter();
  }
  
  public void testExitAction() throws Exception {
    Event event = testingMetaEvent.newEvent(null);
    testingTransition.perform(event,getDeepCurrentState(testingState));
    assertEquals(0,testingExitAction.getCalls());
    assertEquals(0,testingExitAction.getUndoCalls());
  }
  
  public void testEntryAction() throws Exception {
    testingEntryAction.reset();
    Event event = testingMetaEvent.newEvent(null);
    testingTransition.perform(event,getDeepCurrentState(testingState));
    assertEquals(0,testingEntryAction.getCalls());
    assertEquals(0,testingEntryAction.getUndoCalls());
  }
  
  public void testFailingEntryAction() throws Exception {
    // Durante una InternalTransition la EntryAction non viene eseguiita
    // dunque non puo' fallire e non c'e' nulla da testare
  }
  
  public void testFailingAction() throws Exception {
    testingTransitionAction.doFail();
    testingEntryAction.reset();
    Event event = testingMetaEvent.newEvent(null);
    try {
      testingTransition.perform(event,getDeepCurrentState(testingState));
    } catch(WorkflowException e) {
      assertTrue(getDeepCurrentState(testingState).hasMetaState(startMetaState));
      assertEquals(0,testingExitAction.getCalls());
      assertEquals(0,testingExitAction.getUndoCalls());
      assertEquals(1,testingTransitionAction.getCalls());
      assertEquals(1,testingTransitionAction.getUndoCalls());
      assertEquals(0,testingEntryAction.getCalls());
      assertEquals(0,testingEntryAction.getUndoCalls());
      return;
    }
    fail("La transition action è fallita ma non ho ricevuto l'eccezione");
  }
  
  public void testFailingExitAction() throws Exception {
    // Durante una InternalTransition la ExitAction non viene eseguiita
    // dunque non puo' fallire e non c'e' nulla da testare
  }
  
}
