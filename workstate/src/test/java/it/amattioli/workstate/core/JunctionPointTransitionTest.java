package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.MockStateAction;
import it.amattioli.workstate.actions.MockTransitionAction;
import it.amattioli.workstate.actions.TrueGuard;

public class JunctionPointTransitionTest extends AbstractTransitionTest {
    
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
    MetaSimpleState simple3 = new MetaSimpleState("SimpleState3",null,null);
    sequential.addMetaState(simple3);
    MetaJunctionPoint jPoint = new MetaJunctionPoint();
    sequential.addMetaState(jPoint);
    testingMetaEvent = new MetaEvent("TestEvent");
    testingTransitionAction = new MockTransitionAction();
    testingTransition = new ExternalTransition(testingMetaEvent,simple1,jPoint,testingTransitionAction,null);
    Transition jTransition1 = new ExternalTransition(null,jPoint,simple2,null,new TrueGuard());
    Transition jTransition2 = new ExternalTransition(null,jPoint,simple3,null,null);
    testingState = (SequentialState)sequential.newState(null);
    testingState.enter();
  }
  
}
