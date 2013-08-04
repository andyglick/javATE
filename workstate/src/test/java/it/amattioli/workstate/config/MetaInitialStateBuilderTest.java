package it.amattioli.workstate.config;

import it.amattioli.workstate.core.MetaInitialState;

public class MetaInitialStateBuilderTest extends AbstractMetaStateBuilderTest {
  
  public void setUp() throws Exception {
    stateBuilder = new MetaInitialStateBuilder(TESTING_ID);
    super.setUp();
  }
  
  public void testBuiltClass() {
    assertEquals(MetaInitialState.class,stateBuilder.getBuiltMetaState().getClass());
  }
 
}
