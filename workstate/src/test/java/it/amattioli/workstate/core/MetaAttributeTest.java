package it.amattioli.workstate.core;
import it.amattioli.workstate.exceptions.WorkflowException;
import junit.framework.*;

public class MetaAttributeTest extends TestCase {
  
  public void testAttributeTag() throws Exception {
    MetaAttribute metaAttr = new MetaAttribute("tag",String.class,"");
    assertEquals("tag",metaAttr.getTag());
  }
  
  public void testAttributeClass() throws Exception {
    MetaAttribute metaAttr = new MetaAttribute("tag",String.class,"");
    assertEquals(String.class,metaAttr.getAttributeClass());
  }
  
  public void testIllegalAttributeClass() throws Exception {
    try {
      MetaAttribute metaAttr = new MetaAttribute("tag","it.confor.NonExistentClass","");
    } catch(IllegalArgumentException e) {
      return;
    }
    fail("Ho creato un attributo con una classe inesistente");
  }
  
  public void testAttributeInitial() throws Exception {
    MetaAttribute metaAttr = new MetaAttribute("tag",String.class,"initial");
    assertEquals("initial",metaAttr.getInitialValue());
  }
  
  public void testIllegalAttributeInitial() throws Exception {
    try {
      MetaAttribute metaAttr = new MetaAttribute("tag","String",new Integer(1));
    } catch(ClassCastException e) {
      return;
    }
    fail("Ho creato un attributo con un initial value di classe diversa");
  }
  
  public void testValidAttribute() throws Exception {
    MetaAttribute metaAttr = new MetaAttribute("tag",String.class,"initial");
    try {
      metaAttr.checkValidAttribute("valid");
    } catch(ClassCastException e) {
      fail("Ho ottenuto una ClassCastException nonostante la classe dell'attributo sia corretta");
    }
  }
  
  public void testInvalidAttribute() throws Exception {
    MetaAttribute metaAttr = new MetaAttribute("tag",String.class,"initial");
    try {
      metaAttr.checkValidAttribute(new Integer(1));
    } catch(ClassCastException e) {
      return;
    }
    fail("Ho settato un attributo String con un Integer");
  }
  
  public void testStringAttribute() throws Exception {
    MetaAttribute metaAttr = new MetaAttribute("tag","String",null);
    assertEquals(String.class,metaAttr.getAttributeClass());
  }
  
  public void testStringArrayAttribute() throws Exception {
    MetaAttribute metaAttr = new MetaAttribute("tag","String[]",null);
    assertTrue(metaAttr.getAttributeClass().isArray());
    assertEquals(String.class,metaAttr.getAttributeClass().getComponentType());
  }
  
  public void testAttributeToString() throws Exception {
    MetaAttribute metaAttr = new MetaAttribute("tag","String","initial");
    assertEquals("tag:java.lang.String=initial",metaAttr.toString());
  }
  
  public void testValidation() throws Exception {
    MetaAttribute metaAttr = new MetaAttribute("tag",String.class,"");
    MockAttributeValidator validator = new MockAttributeValidator(false); 
    metaAttr.addValidator(validator);
    metaAttr.checkValidAttribute("value");
    assertEquals(1,validator.getCalls());
  }
  
  public void testFailedValidation() throws Exception {
    MetaAttribute metaAttr = new MetaAttribute("tag",String.class,"");
    MockAttributeValidator validator = new MockAttributeValidator(true); 
    metaAttr.addValidator(validator);
    try {
      metaAttr.checkValidAttribute("value");
    } catch(WorkflowException e) {
      return;
    }
    fail("Validazione corretta nonostante il fallimento del validator");
  }
  
}
