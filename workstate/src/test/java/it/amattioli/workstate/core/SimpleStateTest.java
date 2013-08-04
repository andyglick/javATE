package it.amattioli.workstate.core;

public class SimpleStateTest extends AbstractRealStateTest {
  
  public void setUp() throws Exception {
    testingMetaState = new MetaSimpleState("SimpleState",mockEntryAction,mockExitAction);
    super.setUp();
  }
  
}
