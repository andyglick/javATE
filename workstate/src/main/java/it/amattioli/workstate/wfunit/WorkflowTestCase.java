package it.amattioli.workstate.wfunit;
import it.amattioli.workstate.core.Machine;
import it.amattioli.workstate.exceptions.ErrorMessage;
import it.amattioli.workstate.exceptions.ErrorMessages;
import it.amattioli.workstate.exceptions.WorkflowException;

import java.util.List;
import java.util.ListIterator;
import junit.framework.TestCase;

public abstract class WorkflowTestCase extends TestCase {
  private List events;
  protected Machine machine;
  private ListIterator eventsIterator;

  public WorkflowTestCase(String name) {
    super(name);
  }

  public abstract Machine loadMachine() throws Exception;

  public abstract List loadEvents() throws Exception;

  protected void setUp() throws Exception {
    machine = loadMachine();
    events = loadEvents();
    eventsIterator = this.events.listIterator();
  }
  
  public boolean hasMoreSteps() {
    return eventsIterator.hasNext();
  }

  public void step() throws WorkflowException {
    if (eventsIterator.hasNext()) {
      TestEvent currEvent = (TestEvent)eventsIterator.next();
      machine.postEvent(currEvent.getEventName(),currEvent.getAttributes());
      machine.processEvents();
    } else {
      throw ErrorMessages.newIllegalStateException(ErrorMessage.NO_MORE_STEPS);
    }
  }

  public void step(int stepNumber) throws WorkflowException {
    for (int i=0; i<stepNumber; i++) {
      step();
    }
  }

  public void stepAll() throws WorkflowException {
    while (hasMoreSteps()) {
      step();
    }
  }

  public void assertAttribute(String stateName, String attrName, Object expected) {
    AttributeRetriever retriever = new AttributeRetriever(stateName,attrName);
    retriever.visit(machine);
    Object val = retriever.getResult();
    this.assertEquals(expected,val);
  }
  
  public void assertState(String stateName) {
    StateFinder finder = new StateFinder(stateName);
    finder.visit(machine);
    this.assertTrue(finder.found());
  }

}
