package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.MockStateAction;
import it.amattioli.workstate.actions.MockTransitionAction;
import it.amattioli.workstate.exceptions.WorkflowException;

public class SeqTargetTransitionTest extends AbstractTransitionTest {
  private MockStateAction superEntryAction; 
    
  public void setUp() throws Exception {
    MetaSequentialState sequential = new MetaSequentialState("TopState",null,null); 
    MetaInitialState initial = new MetaInitialState();
    sequential.addMetaState(initial);
    testingExitAction = new MockStateAction();
    MetaSimpleState simple1 = new MetaSimpleState("SimpleState1",new MockStateAction(),testingExitAction);
    sequential.addMetaState(simple1);
    startMetaState = simple1;
    Transition initTrans = new ExternalTransition(null,initial,simple1,null,null);
    superEntryAction = new MockStateAction();
    MetaSequentialState subSeq = new MetaSequentialState("SubSequential",superEntryAction,null);
    sequential.addMetaState(subSeq);
    testingEntryAction = new MockStateAction();
    MetaSimpleState simple2 = new MetaSimpleState("SimpleState2",testingEntryAction,new MockStateAction());
    subSeq.addMetaState(simple2);
    endMetaState = simple2;
    testingMetaEvent = new MetaEvent("TestEvent");
    testingTransitionAction = new MockTransitionAction();
    testingTransition = new ExternalTransition(testingMetaEvent,simple1,simple2,testingTransitionAction,null);
    
    testingState = (SequentialState)sequential.newState(null);
    testingState.enter();
  }
  
  public void testEntryAction() throws Exception {
    super.testEntryAction();
    assertEquals(1,superEntryAction.getCalls());
    assertEquals(0,superEntryAction.getUndoCalls());
  }
  
  public void testFailingEntryAction() throws Exception {
    super.testFailingEntryAction();
    assertEquals(1,superEntryAction.getCalls());
    assertEquals(1,superEntryAction.getUndoCalls());
  }
  
  public void testFailingSuperEntryAction() throws Exception {
    superEntryAction.doFail();
    Event event = testingMetaEvent.newEvent(null);
    try {
      testingTransition.perform(event,getDeepCurrentState(testingState));
    } catch(WorkflowException e) {
      assertTrue(getDeepCurrentState(testingState).hasMetaState(startMetaState));
      assertEquals(1,testingExitAction.getCalls());
      assertEquals(1,testingExitAction.getUndoCalls());
      assertEquals(1,testingTransitionAction.getCalls());
      assertEquals(1,testingTransitionAction.getUndoCalls());
      assertEquals(1,superEntryAction.getCalls());
      assertEquals(1,superEntryAction.getUndoCalls());
      assertEquals(0,testingEntryAction.getCalls());
      assertEquals(0,testingEntryAction.getUndoCalls());
      return;
    }
    fail("La entry action è fallita ma non ho ricevuto l'eccezione");
  }
  
}
