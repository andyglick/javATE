package it.amattioli.workstate.wfunit;
import java.util.List;

public interface TestEventsReader {

  public List readEvents() throws ReadException;
  
}
