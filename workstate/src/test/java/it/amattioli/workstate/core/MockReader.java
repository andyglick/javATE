package it.amattioli.workstate.core;

import java.io.*;

public class MockReader implements it.amattioli.workstate.config.Configuration, Serializable {
  private transient MetaMachine machine;
  private String id;
  
  public void setMachine(MetaMachine machine) {
    this.machine = machine;
  }
  
  public void setId(String id) {
	  this.id = id;
  }
  
  public String getId() {
	  return id;
  }
  
  public void setSource(String source) {
  }
  
  public MetaMachine read() {
   return machine;
  }
  
  public boolean equals(Object o) {
    return o.getClass().equals(this.getClass());
  }
  
  public int hashCode() {
    return 1;
  }
}
