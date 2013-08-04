package it.amattioli.workstate.actions;

public class TrueGuard extends AbstractGuard {
  
  public boolean check(AttributeReader event, AttributeReader state) {
    return true;
  }
  
}
