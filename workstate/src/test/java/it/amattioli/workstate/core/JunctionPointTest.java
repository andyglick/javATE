package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.TrueGuard;

public class JunctionPointTest extends AbstractStateTest {
  private MockTransition activated;
  private MockTransition nonActivated;
  
  public void setUp() throws Exception {
    testingMetaState = new MetaJunctionPoint();
    activated = new MockTransition(null,testingMetaState,null,null,new TrueGuard());
    nonActivated = new MockTransition(null,testingMetaState,null,null,null);
    super.setUp();
  }
  
  public void testActivatedTransition() throws Exception {
    testingState.enter();
    assertEquals(1,activated.getTimesPerformed());
    assertEquals(0,nonActivated.getTimesPerformed());
  }
  
}
