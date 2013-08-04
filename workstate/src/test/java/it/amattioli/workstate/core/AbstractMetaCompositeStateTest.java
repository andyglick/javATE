package it.amattioli.workstate.core;

import it.amattioli.workstate.core.MetaCompositeState;
import it.amattioli.workstate.core.MetaSequentialState;
import it.amattioli.workstate.core.MetaState;

public abstract class AbstractMetaCompositeStateTest extends AbstractMetaRealStateTest {
     
  public void testAddMetaState() {
    MetaCompositeState testingState = (MetaCompositeState)this.testingState;
    MetaState subState = new MetaSequentialState("SubState",null,null);
    testingState.addMetaState(subState);
    assertTrue(testingState.isAncestorOf(subState));
  }
  
  public void testAddDuplicateMetaState() {
    MetaCompositeState testingState = (MetaCompositeState)this.testingState;
    MetaState subState1 = new MetaSequentialState("SubState",null,null);
    testingState.addMetaState(subState1);
    MetaState subState2 = new MetaSequentialState("SubState",null,null);
    try {
      testingState.addMetaState(subState2);
    } catch(IllegalArgumentException e) {
      return;
    }
    fail("Ho aggiunto un meta-stato duplicato ad un meta-stato composto");
  }
  
  public void testAddNullMetaState() {
    MetaCompositeState testingState = (MetaCompositeState)this.testingState;
    try {
      testingState.addMetaState(null);
    } catch(NullPointerException e) {
      return;
    }
    fail("Ho aggiunto un meta-stato nullo ad un meta-stato composto");
  }
  
  public void testGetSubstate() {
    String subStateTag = "SubState";
    MetaCompositeState testingState = (MetaCompositeState)this.testingState;
    MetaState subState = new MetaSequentialState(subStateTag,null,null);
    testingState.addMetaState(subState);
    MetaState result = testingState.getSubstate(subStateTag);
    assertEquals(subState,result);
  }
  
  public void testGetWrongSubstate() {
    String subStateTag = "SubState";
    MetaCompositeState testingState = (MetaCompositeState)this.testingState;
    try {
      MetaState result = testingState.getSubstate(subStateTag);
    } catch(IllegalArgumentException e) {
      return;
    }
    fail("Ho cercato un metastato inesistente e l'ho trovato");
  }

}
