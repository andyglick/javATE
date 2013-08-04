package it.amattioli.workstate.config;
import junit.framework.*;

public abstract class AbstractMetaStateBuilderTest extends TestCase {
  protected static final String TESTING_ID = "1111";
  
  protected MetaStateBuilder stateBuilder;
  
  public void testId() {
    assertEquals(TESTING_ID, stateBuilder.getId());
  }

}
