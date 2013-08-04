package it.amattioli.workstate.config;
import it.amattioli.workstate.core.MetaAttribute;
import junit.framework.*;

public class MetaEventBuilderTest extends TestCase {
  private static final String TESTING_TAG = "TAG";
  private static final String TESTING_ID = "ID";
  private static final String TESTING_ATTR = "attr";
  private MetaEventBuilder builder;
  
  public void setUp() {
    builder = new MetaEventBuilder(TESTING_TAG,TESTING_ID);
    MetaAttribute attr = new MetaAttribute(TESTING_ATTR,"String","initial");
    builder.addAttribute(attr);
  }
  
  public void testId() {
    assertEquals(TESTING_ID,builder.getId());
  }
  
  public void testTag() {
    assertEquals(TESTING_TAG,builder.getBuiltEvent().getTag());
  }
  
  public void testAttribute() {
    assertTrue(builder.getBuiltEvent().isAllowedParameter(TESTING_ATTR));
  }

}
