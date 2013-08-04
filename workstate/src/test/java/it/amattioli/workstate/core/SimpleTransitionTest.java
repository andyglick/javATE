package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.MockStateAction;
import it.amattioli.workstate.actions.MockTransitionAction;

public class SimpleTransitionTest extends AbstractTransitionTest {
    
  public void setUp() throws Exception {
    MetaSequentialState sequential = new MetaSequentialState("TopState",null,null); 
    MetaInitialState initial = new MetaInitialState();
    sequential.addMetaState(initial);
    testingExitAction = new MockStateAction();
    MetaSimpleState simple1 = new MetaSimpleState("SimpleState1",new MockStateAction(),testingExitAction);
    sequential.addMetaState(simple1);
    startMetaState = simple1;
    Transition initTrans = new ExternalTransition(null,initial,simple1,null,null);
    testingEntryAction = new MockStateAction();
    MetaSimpleState simple2 = new MetaSimpleState("SimpleState2",testingEntryAction,new MockStateAction());
    sequential.addMetaState(simple2);
    endMetaState = simple2;
    testingMetaEvent = new MetaEvent("TestEvent");
    testingTransitionAction = new MockTransitionAction();
    testingTransition = new ExternalTransition(testingMetaEvent,simple1,simple2,testingTransitionAction,null);
    
    testingState = (SequentialState)sequential.newState(null);
    testingState.enter();
  }
  
}
