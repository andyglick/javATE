package it.amattioli.workstate.exceptions;

import junit.framework.TestCase;

/**
 * @author a.mattioli
 *
 * Creato il Jan 29, 2005 alle 7:01:35 PM
 */
public class WorkflowExceptionTest extends TestCase {
  
  public void testSimpleException() {
    WorkflowException e = new WorkflowException("tag","message");
    assertEquals("tag",e.getTag());
    assertEquals("message",e.getMessage());
    assertEquals(e,e);
  }
  
  public void testCausedException() {
    Exception cause = new NullPointerException();
    WorkflowException e = new WorkflowException("tag",cause);
    assertEquals("tag",e.getTag());
    assertEquals(cause,e.getCause());
    assertEquals(1,e.getCauses().size());
    assertTrue(e.getCauses().contains(cause));
    assertEquals(e,e);
  }
  
  public void testAddCause() {
    Exception mainCause = new NullPointerException();
    WorkflowException e = new WorkflowException("tag",mainCause);
    Exception otherCause = new NullPointerException();
    e.addCause(otherCause);
    assertEquals(mainCause,e.getCause());
    assertEquals(2,e.getCauses().size());
    assertTrue(e.getCauses().contains(mainCause));
    assertTrue(e.getCauses().contains(otherCause));
  }
  
  public void testKeyedException() {
    KeyedMessage msg = new KeyedMessage("MSG_KEY");
    WorkflowException e = new WorkflowException("tag",msg);
    assertEquals(msg,e.getKeyedMessage());
    assertEquals(e,e);
  }
  
  public void testAddParameter() {
    KeyedMessage msg = new KeyedMessage("MSG_KEY");
    WorkflowException e = new WorkflowException("tag",msg);
    e.addParameter("param","value");
    assertTrue(e.getKeyedMessage().getParameters().containsKey("param"));
  }
  
  public void testKeyedCauseException() {
    KeyedMessage msg = new KeyedMessage("MSG_KEY");
    SimpleKeyedException cause = new SimpleKeyedException(msg);
    WorkflowException e = new WorkflowException("tag",cause);
    assertEquals(msg,e.getKeyedMessage());
    assertEquals(e,e);
  }
  
  public void testKeyedCauseParameter() {
    KeyedMessage msg = new KeyedMessage("MSG_KEY");
    SimpleKeyedException cause = new SimpleKeyedException(msg);
    WorkflowException e = new WorkflowException("tag",cause);
    e.addParameter("param","value");
    assertTrue(e.getKeyedMessage().getParameters().containsKey("param"));
  }
  
}
