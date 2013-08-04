package it.amattioli.workstate.config;

import it.amattioli.workstate.core.MetaEvent;
import it.amattioli.workstate.core.MetaSimpleState;

public class ExternalTransitionBuilderTest extends AbstractTransitionBuilderTest {

  public void setUp() {
    start = new MetaSimpleState("START",null,null);
    end = new MetaSimpleState("END",null,null);
    event = new MetaEvent("EVENT");
    builder = new ExternalTransitionBuilder(event,start,end);
  }

}
