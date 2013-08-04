package it.amattioli.workstate.core;

public class MetaConcurrentStateTest extends AbstractMetaCompositeStateTest {
  
  public void setUp() {
    testingState = new MetaConcurrentState("ConcurrentState",null,null);
  }
  
  public void testAddSimpleMetaState() {
    MetaCompositeState testingState = (MetaCompositeState)this.testingState;
    MetaState subState = new MetaSimpleState("SubState",null,null);
    try {
      testingState.addMetaState(subState);
    } catch(IllegalArgumentException e) {
      return;
    }
    fail("Ho aggiunto un meta-stato semplice come regione di un meta-stato concorrente");
  }
  
}
