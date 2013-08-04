package it.amattioli.workstate.core;

public class ConcurrentStateTest extends AbstractRealStateTest {
  private MetaConcurrentState concurrent;
  
  public void setUp() throws Exception {
    concurrent = ConfigMother.standardConcurrentState("ConcurrentState",mockEntryAction,mockExitAction);
    testingMetaState = concurrent;
    super.setUp();
  }

}
