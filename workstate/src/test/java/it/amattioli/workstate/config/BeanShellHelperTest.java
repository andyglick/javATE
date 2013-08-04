package it.amattioli.workstate.config;
import it.amattioli.workstate.actions.MockStateAction;
import junit.framework.*;
import bsh.EvalError;

public class BeanShellHelperTest extends TestCase {
  
  public void testNewAction() throws Exception {
    Object result = BeanShellHelper.evalExpr("new it.amattioli.workstate.actions.MockStateAction()");
    assertTrue(result instanceof MockStateAction);
  }
  
  public void testNewInteger() throws Exception {
    Object result = BeanShellHelper.evalExpr("new Integer(1)");
    assertEquals(new Integer(1),result);
  }
  
  public void testSyntaxError() {
    try {
      Object result = BeanShellHelper.evalExpr("espressione errata");
    } catch(EvalError e) {
      return;
    }
    fail("Ho valutato senza errore un'espressione eraata");
  }
  
}
