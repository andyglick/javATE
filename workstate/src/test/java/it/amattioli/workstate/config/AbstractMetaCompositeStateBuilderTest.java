package it.amattioli.workstate.config;

import it.amattioli.workstate.core.MetaCompositeState;
import it.amattioli.workstate.core.MetaSequentialState;
import it.amattioli.workstate.core.MetaState;

public abstract class AbstractMetaCompositeStateBuilderTest extends AbstractMetaRealStateBuilderTest {
  protected static final String SUBSTATE_TAG = "SUBSTATE_TAG";
  
  protected MetaCompositeStateBuilder compositeStateBuilder;
  
  public void setUp() {
    MetaState subState = new MetaSequentialState(SUBSTATE_TAG,null,null);
    compositeStateBuilder.addSubstate(subState);
    realStateBuilder = compositeStateBuilder;
    super.setUp();
  }
  
  public void testSubstate() {
    assertTrue(((MetaCompositeState)compositeStateBuilder.getBuiltMetaState()).isSubstate(SUBSTATE_TAG));
  }
  
}
