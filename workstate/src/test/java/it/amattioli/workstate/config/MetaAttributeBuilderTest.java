package it.amattioli.workstate.config;
import junit.framework.*;
import it.amattioli.workstate.core.MetaAttribute;

public class MetaAttributeBuilderTest extends TestCase {
  
  public void testStringMetaAttribute() throws Exception {
    MetaAttributeBuilder builder = new MetaAttributeBuilder("attr","String","\"valore\"");
    MetaAttribute result = builder.getBuiltAttribute();
    assertEquals("attr",result.getTag());
    assertEquals(String.class,result.getAttributeClass());
    assertEquals("valore",result.getInitialValue());
  }
  
  public void testStringArrayMetaAttribute() throws Exception {
    MetaAttributeBuilder builder = new MetaAttributeBuilder("attr","String[]","new String[] {\"valore1\",\"valore2\"}");
    MetaAttribute result = builder.getBuiltAttribute();
    String[] initial = (String[])result.getInitialValue();
    assertEquals("valore1",initial[0]);
    assertEquals("valore2",initial[1]);
  }
  
  public void testWrongInitialValue() {
    try {
      MetaAttributeBuilder builder = new MetaAttributeBuilder("attr","String","valore errato");
    } catch(IllegalArgumentException e) {
      return;
    }
    fail("Ho costruito un meta-attributo con un valore iniziale errato");
  }
  
}
