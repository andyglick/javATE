package it.amattioli.workstate.config;

public class MetaSimpleStateBuilderTest extends AbstractMetaRealStateBuilderTest {
  
  public void setUp() {
    realStateBuilder = new MetaSimpleStateBuilder(TESTING_TAG,TESTING_ID);
    super.setUp();
  }
  
}
