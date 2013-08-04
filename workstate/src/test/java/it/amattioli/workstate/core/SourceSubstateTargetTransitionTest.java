package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.MockStateAction;
import it.amattioli.workstate.actions.MockTransitionAction;

public class SourceSubstateTargetTransitionTest extends AbstractTransitionTest {
  private MockStateAction superExitAction; 
    
  public void setUp() throws Exception {
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
    subSeq.addMetaState(simple2);
    endMetaState = simple2;
    testingMetaEvent = new MetaEvent("TestEvent");
    testingTransitionAction = new MockTransitionAction("testingTransitionAction");
    testingTransition = new ExternalTransition(testingMetaEvent,subSeq,simple2,testingTransitionAction,null);    
    testingState = (SequentialState)sequential.newState(null);
    testingState.enter();
  }
  
  public void testExitAction() throws Exception {
    super.testExitAction();
    assertEquals(0,superExitAction.getCalls());
    assertEquals(0,superExitAction.getUndoCalls());
  }
  
  public void testFailingExitAction() throws Exception {
    super.testFailingExitAction();
    assertEquals(0,superExitAction.getCalls());
    assertEquals(0,superExitAction.getUndoCalls());
  }
  
}
