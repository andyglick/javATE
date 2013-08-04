package it.amattioli.workstate.config;

import it.amattioli.workstate.core.MetaJunctionPoint;

/**
 * @author a.mattioli
 *
 * Creato il Jan 30, 2005 alle 3:09:26 PM
 */
public class MetaJunctionPointBuilderTest extends AbstractMetaStateBuilderTest {

  public void setUp() throws Exception {
    stateBuilder = new MetaJunctionPointBuilder(TESTING_ID);
    super.setUp();
  }
  
  public void testBuiltClass() {
    assertEquals(MetaJunctionPoint.class,stateBuilder.getBuiltMetaState().getClass());
  }
  
}
