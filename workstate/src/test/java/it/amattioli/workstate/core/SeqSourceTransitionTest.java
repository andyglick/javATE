package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.MockStateAction;
import it.amattioli.workstate.actions.MockTransitionAction;
import it.amattioli.workstate.exceptions.WorkflowException;

public class SeqSourceTransitionTest extends AbstractTransitionTest {
  private MockStateAction superExitAction; 
    
  public void setUp() throws Exception {
    //System.out.println("Start SeqSourceTransitionTest setUp");
    MetaSequentialState sequential = new MetaSequentialState("TopState",null,null); 
    MetaInitialState initial = new MetaInitialState();
    sequential.addMetaState(initial);
    superExitAction = new MockStateAction("superExitAction");
    MetaSequentialState subSeq = new MetaSequentialState("SubSequential",new MockStateAction("SubSequential Entry Action"),superExitAction);
    sequential.addMetaState(subSeq);
    Transition initTrans = new ExternalTransition(null,initial,subSeq,null,null);
    MetaInitialState subInitial = new MetaInitialState();
    subSeq.addMetaState(subInitial);
    testingExitAction = new MockStateAction("testingExitAction");
    MetaSimpleState simple1 = new MetaSimpleState("SimpleState1",new MockStateAction("SimpleState1 EntryAction"),testingExitAction);
    subSeq.addMetaState(simple1);
    Transition subInitTrans = new ExternalTransition(null,subInitial,simple1,null,null);
    startMetaState = simple1;
    testingEntryAction = new MockStateAction("testingEntryAction");
    MetaSimpleState simple2 = new MetaSimpleState("SimpleState2",testingEntryAction,new MockStateAction("SimpleState2 Exit Action"));
    sequential.addMetaState(simple2);
    endMetaState = simple2;
    testingMetaEvent = new MetaEvent("TestEvent");
    testingTransitionAction = new MockTransitionAction("testingTransitionAction");
    testingTransition = new ExternalTransition(testingMetaEvent,simple1,simple2,testingTransitionAction,null);    
    testingState = (SequentialState)sequential.newState(null);
    testingState.enter();
    //System.out.println("End SeqSourceTransitionTest setUp");
  }
  
  public void testExitAction() throws Exception {
    super.testExitAction();
    assertEquals(1,superExitAction.getCalls());
    assertEquals(0,superExitAction.getUndoCalls());
  }
  
  public void testFailingExitAction() throws Exception {
    //System.out.println("Start super.testFailingAction");
    super.testFailingExitAction();
    //System.out.println("End super.testFailingAction");
    assertEquals(0,superExitAction.getCalls());
    assertEquals(0,superExitAction.getUndoCalls());
  }
  
  public void testFailingSuperExitAction() throws Exception {
    superExitAction.doFail();
    Event event = testingMetaEvent.newEvent(null);
    try {
      testingTransition.perform(event,getDeepCurrentState(testingState));
    } catch(WorkflowException e) {
      assertTrue(getDeepCurrentState(testingState).hasMetaState(startMetaState));
      assertEquals(1,testingExitAction.getCalls());
      assertEquals(1,testingExitAction.getUndoCalls());
      assertEquals(1,superExitAction.getCalls());
      assertEquals(1,superExitAction.getUndoCalls());
      assertEquals(0,testingTransitionAction.getCalls());
      assertEquals(0,testingTransitionAction.getUndoCalls());
      assertEquals(0,testingEntryAction.getCalls());
      assertEquals(0,testingEntryAction.getUndoCalls());
      return;
    }
    fail("La exit action è fallita ma non ho ricevuto l'eccezione");
  }
  
}
