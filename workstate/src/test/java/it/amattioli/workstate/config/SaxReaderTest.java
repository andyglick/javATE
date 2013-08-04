package it.amattioli.workstate.config;

import junit.framework.TestCase;

/**
 * @author a.mattioli
 *
 * Creato il Jan 30, 2005 alle 3:43:26 PM
 */
public class SaxReaderTest extends TestCase {
  private XmlConfiguration reader;
  private XmlConfiguration eqReader = new XmlConfiguration("filename","id");
  private XmlConfiguration diffReader = new XmlConfiguration("filename1","id1");
  
  public void setUp() {
    reader = new XmlConfiguration("filename","id");
  }
  
  public void testId() {
    assertEquals("id",reader.getId());
  }
  
  public void testEquals() {
    assertTrue(reader.equals(reader));
    assertTrue(reader.equals(eqReader));
    assertTrue(eqReader.equals(reader));
    assertFalse(reader.equals(diffReader));
  }
  
  public void testHashCode() {
    assertEquals(reader.hashCode(),eqReader.hashCode());
    assertFalse(reader.hashCode() == diffReader.hashCode());
  }

}
