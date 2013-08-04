package it.amattioli.workstate.actions;
import junit.framework.*;

public class BeanShellGuardTest extends TestCase {
  
  public void setUp() {
  }
  
  public void testTrueGuard() throws Exception {
    AttributeHandler event = new MockAttributeHandler();
    event.setAttribute("param1","Stringa1");
    event.setAttribute("param2","Stringa2");
    AttributeHandler state = new MockAttributeHandler();
    state.setAttribute("attr1","Stringa1");
    BeanShellGuard guard = new BeanShellGuard("attr1.equals(param1)");
    assertTrue(guard.check(event,state));
  }
  
  public void testFalseGuard() throws Exception {
    AttributeHandler event = new MockAttributeHandler();
    event.setAttribute("param1","Stringa1");
    event.setAttribute("param2","Stringa2");
    AttributeHandler state = new MockAttributeHandler();
    state.setAttribute("attr1","Stringa1");
    BeanShellGuard guard = new BeanShellGuard("attr1.equals(param2)");
    assertFalse(guard.check(event,state));
  }
  
}
