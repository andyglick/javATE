package it.amattioli.workstate.info;

/**
 * An object that can receive a {@link Visitor}.
 * When a {@link Visitor} needs to visit an object it will receive a {@link Receiver}
 * instance. To visit other objects like children nodes in a tree, the {@link Visitor}
 * will call the {@link #receive(Visitor)} method of the visited object.
 * 
 */
public interface Receiver {
  
  public void receive(Visitor visitor);
  
}
