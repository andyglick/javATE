package it.amattioli.workstate.core;
import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.core.MetaAttribute;
import it.amattioli.workstate.core.MetaRealState;
import it.amattioli.workstate.core.RealState;
import it.amattioli.workstate.core.StateMemento;
import it.amattioli.workstate.exceptions.*;

public abstract class AbstractRealStateTest extends AbstractStateTest {
  private static final String CHECKED_ATTRIBUTE = "attr";
  private static final String INITIAL_ATTRIBUTE = "initial";
  protected MockStateAction mockEntryAction = new MockStateAction();
  protected MockStateAction mockExitAction = new MockStateAction();
  
  public void setUp() throws Exception {
    if (mockEntryAction != null) {
      mockEntryAction.reset();
    }
    if (mockExitAction != null) {
      mockExitAction.reset();
    }
    MetaAttribute attr = new MetaAttribute(CHECKED_ATTRIBUTE,String.class,INITIAL_ATTRIBUTE);
    MetaRealState metastate = (MetaRealState)testingMetaState;
    metastate.addAttribute(attr);
    super.setUp();
  }
  
  public void testEntryAction() throws Exception {
    if (mockEntryAction != null) {
      RealState testingState = (RealState)this.testingState;
      testingState.enter();
      assertEquals(1,mockEntryAction.getCalls());
    }
  }
  
  public void testReExit() throws Exception {
    RealState testingState = (RealState)this.testingState;
    testingState.enter();
    testingState.reExit();
    assertFalse(testingState.isActive());
    if (mockEntryAction != null) {
      assertEquals(1,mockEntryAction.getUndoCalls());
    }
  }
  
  public void testFailingEntryAction() throws Exception {
    if (mockEntryAction != null) {
      mockEntryAction.doFail();
      RealState testingState = (RealState)this.testingState;
      try {
        testingState.enter();
      } catch(WorkflowException e) {
        assertFalse(testingState.isActive());
        assertEquals(1,mockEntryAction.getUndoCalls());
        return;
      }
      fail("La entry action e` fallita ma non ho ricevuto l'eccezione");
    }
  }
  
  public void testExitAction() throws Exception {
    if (mockExitAction != null) {
      RealState testingState = (RealState)this.testingState;
      testingState.enter();
      testingState.exit();
      assertEquals(1,mockExitAction.getCalls());
    }
  }
  
  public void testReEnter() throws Exception {
    RealState testingState = (RealState)this.testingState;
    testingState.enter();
    testingState.exit();
    testingState.reEnter();
    assertTrue(testingState.isActive());
    if (mockExitAction != null) {
      assertEquals(1,mockExitAction.getUndoCalls());
    }
  }
  
  public void testFailingExitAction() throws Exception {
    if (mockExitAction != null) {
      mockExitAction.doFail();
      RealState testingState = (RealState)this.testingState;
      testingState.enter();
      try {
        testingState.exit();
      } catch(WorkflowException e) {
        assertTrue(testingState.isActive());
        assertEquals(1,mockExitAction.getUndoCalls());
        return;
      }
      fail("La exit action e` fallita ma non ho ricevuto l'eccezione");
    }
  }
  
  public void testInitialAttribute() throws Exception {
    RealState testingState = (RealState)this.testingState;
    testingState.enter();
    assertEquals(INITIAL_ATTRIBUTE,testingState.getAttribute(CHECKED_ATTRIBUTE));
  }
  
  public void testSetAttribute() throws Exception {
    String expected = "value";
    RealState testingState = (RealState)this.testingState;
    testingState.enter();
    testingState.setAttribute(CHECKED_ATTRIBUTE,expected);
    assertEquals(expected,testingState.getAttribute(CHECKED_ATTRIBUTE));
  }
  
  public void testSetInvalidAttribute() throws Exception {
    RealState testingState = (RealState)this.testingState;
    testingState.enter();
    try {
      testingState.setAttribute(CHECKED_ATTRIBUTE,new Integer(1));
    } catch(ClassCastException e) {
      return;
    }
    fail("Ho settato un attributo di stato String con un Integer");
  }
  
  public RealState restoreState(StateMemento memento) throws Exception {
    RealState restoredState = (RealState)testingMetaState.newState(null);
    restoredState.restore(memento);
    return restoredState;
  }
  
  public void testRestoredStateAttribute() throws Exception {
    testingState.enter();
    StateMemento memento = testingState.getMemento();
    RealState restoredState = restoreState(memento);
    assertEquals(testingState,restoredState);
  }
   
  /*
  public void testRestoredStateAttribute() throws Exception {
    testingState.enter();
    StateMemento memento = testingState.getMemento();
    RealState restoredState = restoreState(memento);
    assertEquals(testingState.getAttribute(CHECKED_ATTRIBUTE),restoredState.getAttribute(CHECKED_ATTRIBUTE));
  }
  */
  
}
