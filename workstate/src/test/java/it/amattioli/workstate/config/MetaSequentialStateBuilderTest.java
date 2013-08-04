package it.amattioli.workstate.config;

public class MetaSequentialStateBuilderTest extends AbstractMetaCompositeStateBuilderTest {
  
  public void setUp() {
    compositeStateBuilder = new MetaSequentialStateBuilder(TESTING_TAG,TESTING_ID);
    super.setUp();
  }
  
}
