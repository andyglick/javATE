package it.amattioli.workstate.config;

import it.amattioli.workstate.actions.MockStateAction;
import it.amattioli.workstate.core.MetaAttribute;
import it.amattioli.workstate.core.MetaRealState;

public abstract class AbstractMetaRealStateBuilderTest extends AbstractMetaStateBuilderTest {
  protected static final String TESTING_TAG = "STATE_TAG";
  protected static final String TESTING_ATTR = "attr";
  
  protected MetaRealStateBuilder realStateBuilder;
  
  public void setUp() {
    realStateBuilder.setEntryAction("it.amattioli.workstate.actions.MockStateAction()");
    realStateBuilder.setExitAction("it.amattioli.workstate.actions.MockStateAction()");
    MetaAttribute attr = new MetaAttribute(TESTING_ATTR,"String","initial");
    realStateBuilder.addAttribute(attr);
    stateBuilder = realStateBuilder;
  }
  
  public void testEntryAction() {
    assertTrue(((MetaRealState)realStateBuilder.getBuiltMetaState()).getEntryAction() instanceof MockStateAction);
  }
  
  public void testExitAction() {
    assertTrue(((MetaRealState)realStateBuilder.getBuiltMetaState()).getExitAction() instanceof MockStateAction);
  }
  
  public void testAttribute() {
    assertTrue(((MetaRealState)realStateBuilder.getBuiltMetaState()).isAllowedAttribute(TESTING_ATTR));
  }
  
  public void testTag() {
    assertEquals(TESTING_TAG, ((MetaRealState)realStateBuilder.getBuiltMetaState()).getTag());
  }

}
