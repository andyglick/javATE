package it.amattioli.workstate.core;
import it.amattioli.workstate.actions.MockStateAction;
import it.amattioli.workstate.exceptions.WorkflowException;

import java.util.*;

public class SequentialStateTest extends AbstractRealStateTest {
  private MetaSequentialState sequential;
  //private MetaInitialState initial;
  private MetaSimpleState simple;
  
  public void setUp() throws Exception {
    /*
    sequential = new MetaSequentialState("SequentialState",mockEntryAction,mockExitAction); 
    testingMetaState = sequential;
    initial = new MetaInitialState();
    sequential.addMetaState(initial);
    simple = new MetaSimpleState("SimpleState",null,null);
    sequential.addMetaState(simple);
    Transition initTrans = new ExternalTransition(null,initial,simple,null,null);
    */
    sequential = ConfigMother.standardSequentialState("SequentialState",mockEntryAction,mockExitAction);
    testingMetaState = sequential;
    simple = (MetaSimpleState)sequential.getSubstate("SimpleState");
    super.setUp();
  }
  
  public void testNullEntryStack() throws Exception {
    SequentialState testingState = (SequentialState)this.testingState;
    testingState.enter();
    testingState.exitCurrent();
    try {
      testingState.enterCurrent(null);
    } catch(NullPointerException e) {
      return;
    }
    fail("Sono entrato in un sequential state con un entry stack nullo");
  }
  
  public void testEmptyEntryStack() throws Exception {
    SequentialState testingState = (SequentialState)this.testingState;
    testingState.enter();
    testingState.exitCurrent();
    try {
      testingState.enterCurrent(new Stack<MetaState>());
    } catch(IllegalArgumentException e) {
      return;
    }
    fail("Sono entrato in un sequential state con un entry stack vuoto");
  }
  
  public void testNotInactiveCurrent() throws Exception {
    SequentialState testingState = (SequentialState)this.testingState;
    testingState.enter();
    Stack<MetaState> entryStack = new Stack<MetaState>();
    entryStack.push(simple);
    try {
      testingState.enterCurrent(entryStack);
    } catch(IllegalStateException e) {
      return;
    }
    fail("Sono entrato in un sequential state il cui current e' ancora attivo");
  }
  
  public void testFailingSubstateEntryAction() throws Exception {
    ((MockStateAction)simple.getEntryAction()).doFail();
    RealState testingState = (RealState)this.testingState;
    try {
      testingState.enter();
    } catch(WorkflowException e) {
      assertFalse(testingState.isActive());
      if (mockEntryAction != null) {
        assertEquals(1,mockEntryAction.getUndoCalls());
      }
      return;
    }
    fail("La entry action è fallita ma non ho ricevuto l'eccezione");
  }
  
  public void testFailingSubstateExitAction() throws Exception {
    MockStateAction subExitAction = ((MockStateAction)simple.getExitAction());
    subExitAction.doFail();
    RealState testingState = (RealState)this.testingState;
    testingState.enter();
    try {
      testingState.exit();
    } catch(WorkflowException e) {
      assertTrue(testingState.isActive());
      assertEquals(1,subExitAction.getUndoCalls());
      if (mockExitAction != null) {
        assertEquals(0,mockExitAction.getUndoCalls());
      }
      return;
    }
    fail("La exit action è fallita ma non ho ricevuto l'eccezione");
  }
  
  public void testGetRedefinedAttribute() throws Exception {
    sequential.addAttribute(new MetaAttribute("redef",String.class,"val1"));
    simple.addAttribute(new MetaAttribute("redef",String.class,"val2"));
    SequentialState testingState = (SequentialState)this.testingState;
    testingState.enter();
    RealState substate = (RealState)testingState.getCurrentState();
    assertEquals("val2",substate.getAttribute("redef"));
    Map<String,Object> allAttrs = substate.getAllAttributes();
    assertEquals("val2",allAttrs.get("redef"));
  }
  
  public void testTerminate() throws Exception {
    MetaSimpleState simpleTarget = new MetaSimpleState("SimpleTarget",null,null);
    MockTransition complTrans = new MockTransition(null,sequential,simpleTarget,null,null);
    SequentialState testingState = (SequentialState)this.testingState;
    testingState.enter();
    testingState.terminate();
    assertTrue(testingState.isComplete());
    assertEquals(1,complTrans.getTimesPerformed());
  }
  
}
