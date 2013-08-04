package it.amattioli.workstate.actions;
import junit.framework.*;

public class NegatedGuardTest extends TestCase {
  private NegatedGuard testingGuard;
  private Guard baseGuard;
  
  public void setUp() {
    baseGuard = new TrueGuard();
    testingGuard = new NegatedGuard(baseGuard);
  }
  
  public void testCheck() {
    assertFalse(testingGuard.check(null,null));
  }
  
  public void testPriority() {
    assertEquals(baseGuard.getPriority(),testingGuard.getPriority());
  }
  
  public void testBaseGuard() {
    assertTrue(testingGuard.negates(baseGuard));
  }
  
  public void testEquals() {
    assertTrue(testingGuard.equals(testingGuard));
    assertFalse(testingGuard.equals(baseGuard));
  }

}
