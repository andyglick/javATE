package it.amattioli.workstate.config;
import it.amattioli.workstate.actions.BeanShellGuard;
import it.amattioli.workstate.actions.BeanShellTransitionAction;
import it.amattioli.workstate.actions.MockTransitionAction;
import it.amattioli.workstate.actions.NegatedGuard;
import it.amattioli.workstate.actions.TrueGuard;
import it.amattioli.workstate.core.MetaEvent;
import it.amattioli.workstate.core.MetaState;
import it.amattioli.workstate.core.Transition;
import junit.framework.*;

public abstract class AbstractTransitionBuilderTest extends TestCase {
  protected TransitionBuilder builder;
  protected MetaState start;
  protected MetaState end;
  protected MetaEvent event;
  
  public void testStartState() {
    Transition result = builder.getBuiltTransition();
    assertTrue(result.isStartState(start));
  }
  
  public void testEndState() {
    Transition result = builder.getBuiltTransition();
    assertTrue(result.isEndState(end));
  }
  
  public void testAction() {
    builder.setAction("it.amattioli.workstate.actions.MockTransitionAction()");
    Transition result = builder.getBuiltTransition();
    assertTrue(result.isAction(new MockTransitionAction()));
  }
  
  public void testBeanShellAction() {
    builder.setAction("{ a = 1; }");
    Transition result = builder.getBuiltTransition();
    assertTrue(result.isAction(new BeanShellTransitionAction("a = 1;")));
  }
  
  public void testGuard() {
    builder.setGuard("it.amattioli.workstate.actions.TrueGuard()");
    Transition result = builder.getBuiltTransition();
    assertTrue(result.isGuard(new TrueGuard()));
  }
  
  public void testNegatedGuard() {
    builder.setGuard("!it.amattioli.workstate.actions.TrueGuard()");
    Transition result = builder.getBuiltTransition();
    assertTrue(result.isGuard(new NegatedGuard(new TrueGuard())));
  }
  
  public void testBeanShellGuard() {
    builder.setGuard("{ a == 1; }");
    Transition result = builder.getBuiltTransition();
    assertTrue(result.isGuard(new BeanShellGuard("a == 1;")));
  }
}
