package it.amattioli.workstate.config;

import it.amattioli.workstate.core.MetaEvent;
import it.amattioli.workstate.core.MetaSimpleState;

public class InternalTransitionBuilderTest extends AbstractTransitionBuilderTest {

  public void setUp() {
    start = new MetaSimpleState("START",null,null);
    end = start;
    event = new MetaEvent("EVENT");
    builder = new InternalTransitionBuilder(event,start);
  }

}
