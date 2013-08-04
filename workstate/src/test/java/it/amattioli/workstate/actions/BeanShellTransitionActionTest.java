package it.amattioli.workstate.actions;
import junit.framework.*;

public class BeanShellTransitionActionTest extends TestCase {
  
  public void setUp() {
  }
  
  public void testCorrectAction() throws Exception {
    AttributeHandler event = new MockAttributeHandler();
    event.setAttribute("param1","Stringa1");
    AttributeHandler state = new MockAttributeHandler();
    state.setAttribute("attr1",null);
    BeanShellTransitionAction action = new BeanShellTransitionAction("attr1 = param1 + \" Ciao\"");
    action.doAction(event,state);
    assertEquals("Stringa1 Ciao",state.getAttribute("attr1"));
  }
  
}
