package it.amattioli.workstate.config;

public class MetaConcurrentStateBuilderTest extends AbstractMetaCompositeStateBuilderTest {
  
  public void setUp() {
    compositeStateBuilder = new MetaConcurrentStateBuilder(TESTING_TAG,TESTING_ID);
    super.setUp();
  }
  
}
