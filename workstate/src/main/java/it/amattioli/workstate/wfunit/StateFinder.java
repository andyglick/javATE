package it.amattioli.workstate.wfunit;

import it.amattioli.workstate.core.RealState;
import it.amattioli.workstate.info.Receiver;
import it.amattioli.workstate.info.Visitor;

/**
 * 
 * @author a.mattioli
 */
public class StateFinder implements Visitor {
  private String stateName;
  private boolean result = false;
  
  public StateFinder(String stateName) {
    this.stateName = stateName;
  }

  /**
   *
   */
  public void visit(Receiver receiver) {
    RealState state = (RealState)receiver;
    if (state.getTag().equals(stateName)) {
      result = true;
    } else {
      receiver.receive(this);
    }
  }
  
  public boolean found() {
    return result;
  }

}
