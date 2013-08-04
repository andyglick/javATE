package it.amattioli.workstate.core;
import it.amattioli.workstate.core.MetaState;
import junit.framework.*;

public class AbstractMetaStateTest extends TestCase {
  protected MetaState testingState;
  
  public void testDescendFromNull() {
    try {
      testingState.descendFrom(null);
      fail("Non dovrebbe essere possibile verificare la discendenza da null");
    } catch(NullPointerException e) {
    }
  }
   
}
