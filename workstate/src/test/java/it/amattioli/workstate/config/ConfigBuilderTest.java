package it.amattioli.workstate.config;
import it.amattioli.workstate.actions.MockStateAction;
import it.amattioli.workstate.actions.StateAction;
import it.amattioli.workstate.core.MetaInitialState;
import it.amattioli.workstate.core.MetaMachine;
import it.amattioli.workstate.core.MetaSequentialState;
import it.amattioli.workstate.core.MetaSimpleState;
import it.amattioli.workstate.core.MetaState;
import static it.amattioli.workstate.config.ConfigBuilder.StateType.*;

import java.util.*;
import junit.framework.*;

public class ConfigBuilderTest extends TestCase {
  
  public void testBuildInitialState() {
    ConfigBuilder builder = new ConfigBuilder(null,"machineid");
    builder.newState(MACHINE,"top","machineid");
    builder.newState(INITIAL,"","id");
    builder.endState();
    builder.endState();
    MetaMachine result = builder.getResult();
    Collection<MetaState> subStates = result.getSubstates();
    assertEquals(1,subStates.size());
    for (MetaState curr: subStates) {
      assertTrue(curr instanceof MetaInitialState);
    }
  }
  
  public void testBuildSimpleState() {
    ConfigBuilder builder = new ConfigBuilder(null,"machineid");
    builder.newState(MACHINE,"top","machineid");
    builder.newState(SIMPLE,"SIMPLE","id");
    builder.setEntryAction("it.amattioli.workstate.actions.MockStateAction()");
    builder.setExitAction("it.amattioli.workstate.actions.MockStateAction()");
    builder.endState();
    builder.endState();
    MetaMachine result = builder.getResult();
    MetaState subState = result.getSubstate("SIMPLE");
    assertTrue(subState instanceof MetaSimpleState);
    StateAction entryAction = ((MetaSimpleState)subState).getEntryAction();
    assertTrue(entryAction instanceof MockStateAction);
    StateAction exitAction = ((MetaSimpleState)subState).getExitAction();
    assertTrue(exitAction instanceof MockStateAction);
  }
  
  public void testBuildSequentialState() {
    ConfigBuilder builder = new ConfigBuilder(null,"machineid");
    builder.newState(MACHINE,"top","machineid");
    builder.newState(SEQUENTIAL,"SEQ","idSeq");
    builder.setEntryAction("it.amattioli.workstate.actions.MockStateAction()");
    builder.setExitAction("it.amattioli.workstate.actions.MockStateAction()");
    builder.newState(INITIAL,"","id");
    builder.endState();
    builder.newState(SIMPLE,"SIMPLE","idSimple");
    builder.endState();
    builder.endState();
    builder.endState();
    MetaMachine result = builder.getResult();
    MetaState subState = result.getSubstate("SEQ");
    assertTrue(subState instanceof MetaSequentialState);
    MetaState subSubState = ((MetaSequentialState)subState).getSubstate("SIMPLE");
    assertTrue(subSubState instanceof MetaSimpleState);
  }
  
  public void testStateAttribute() {
    ConfigBuilder builder = new ConfigBuilder(null,"machineid");
    builder.newState(MACHINE,"top","machineid");
    builder.newState(SIMPLE,"SIMPLE","id");
    builder.newAttribute("attr","String","");
    builder.endAttribute();
    builder.endState();
    builder.endState();
    MetaMachine result = builder.getResult();
    MetaSimpleState subState = (MetaSimpleState)result.getSubstate("SIMPLE");
    assertTrue(subState.isAllowedAttribute("attr"));
  }
  
  public void testEvent() {
    ConfigBuilder builder = new ConfigBuilder(null,"machineid");
    builder.newEvent("event","eventid");
    builder.endEvent();
    builder.newState(MACHINE,"top","machineid");
    builder.endState();
    MetaMachine result = builder.getResult();
    assertTrue(result.knowsEvent("event"));
  }
  
}
