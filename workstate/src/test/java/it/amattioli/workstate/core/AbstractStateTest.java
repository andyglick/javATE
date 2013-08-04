package it.amattioli.workstate.core;
import it.amattioli.workstate.core.MetaSimpleState;
import it.amattioli.workstate.core.MetaState;
import it.amattioli.workstate.core.State;
import junit.framework.*;

public abstract class AbstractStateTest extends TestCase {
  protected MetaState testingMetaState;
  protected State testingState;
  
  public void setUp() throws Exception {
    testingState = testingMetaState.newState(null);
  }
  
  public void testMetaState() {
    assertTrue(testingState.hasMetaState(testingMetaState));
  }
  
  public void testWrongMetaState() {
    assertFalse(testingState.hasMetaState(new MetaSimpleState("WrongState",null,null)));
  }
  
  public void testEnterNewState() throws Exception {
    testingState.enter();
    assertTrue(testingState.isActive());
  }
  
  public void testEnterActiveState() throws Exception {
    testingState.enter();
    try {
      testingState.enter();
    } catch(IllegalStateException e) {
      return;
    }
    fail("Sono entrato in uno stato gia' attivo");
  }
  
  public void testExitActiveState() throws Exception {
    testingState.enter();
    testingState.exit();
    assertFalse(testingState.isActive());
  }
  
  public void testExitNonActiveState() throws Exception {
    testingState.enter();
    testingState.exit();
    try {
      testingState.exit();
    } catch(IllegalStateException e) {
      return;
    }
    fail("Sono uscito da uno stato gia' inattivo");
  }
  
  public void testEquals() throws Exception {
    State otherState = testingMetaState.newState(null);
    testingState.enter();
    otherState.enter();
    assertEquals(testingState,otherState);
  }
  
  public void testInactiveEquals() {
    State otherState = testingMetaState.newState(null);
    assertEquals(testingState,otherState);
  }
   
}
