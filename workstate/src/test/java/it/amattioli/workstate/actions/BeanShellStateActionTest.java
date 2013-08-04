package it.amattioli.workstate.actions;
import it.amattioli.workstate.exceptions.WorkflowException;
import junit.framework.*;

public class BeanShellStateActionTest extends TestCase {
  
  public void setUp() {
  }
  
  public void testCorrectAction() throws Exception {
    AttributeHandler state = new MockAttributeHandler();
    state.setAttribute("attr1","Stringa1");
    state.setAttribute("attr2",null);
    BeanShellStateAction action = new BeanShellStateAction("attr2 = attr1 + \" Ciao\"");
    action.doAction(state);
    assertEquals("Stringa1 Ciao",state.getAttribute("attr2"));
  }
  /*
  public void testWrongAction() throws Exception {
    AttributeHandler state = new MockAttributeHandler();
    state.setAttribute("attr1","Stringa1");
    state.setAttribute("attr2",null);
    BeanShellStateAction action = new BeanShellStateAction("pippo = attr1 + \" Ciao\"");
    try {
      action.doAction(state);
    } catch(WorkflowException e) {
      return;
    }
    fail("Ho settato un attributo inesistente in una action");
  }
  */
}
