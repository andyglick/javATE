package it.amattioli.workstate.core;

public class MetaSequentialStateTest extends AbstractMetaCompositeStateTest {
  
  public void setUp() {
    testingState = new MetaSequentialState("SequentialState",null,null);
  }
  
  public void testAddDuplicateInitialState() {
    MetaCompositeState testingState = (MetaCompositeState)this.testingState;
    MetaState subState = new MetaInitialState();
    testingState.addMetaState(subState);
    MetaState duplicate = new MetaInitialState();
    try {     
      testingState.addMetaState(subState);
    } catch(IllegalArgumentException e) {
      return;
    }
    fail("Ho aggiunto uno stato iniziale ad un meta-stato sequenziale che gia' ne possedeva uno");
  }
  
}
