package it.amattioli.workstate.wfunit;

import it.amattioli.workstate.core.RealState;
import it.amattioli.workstate.info.Receiver;
import it.amattioli.workstate.info.Visitor;

public class AttributeRetriever implements Visitor {
  private String stateName;
  private String attrName;
  private Object result;
  
  public AttributeRetriever(String stateName, String attrName) {
    this.stateName = stateName;
    this.attrName = attrName;
  }
  
  public void visit(Receiver receiver) {
    RealState state = (RealState)receiver;
    if (state.getTag().equals(stateName)) {
      result = state.getAttribute(attrName);
    } else {
      receiver.receive(this);
    }
  }
  
  public Object getResult() {
    return result;
  }
  
}
