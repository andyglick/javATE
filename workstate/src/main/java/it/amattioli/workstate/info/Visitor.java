package it.amattioli.workstate.info;

/**
 * An object that can visit data structures.
 * 
 */
public interface Visitor {
  
  public void visit(Receiver receiver);
  
}
