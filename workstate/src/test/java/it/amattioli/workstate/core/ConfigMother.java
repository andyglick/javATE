package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.MockStateAction;
import it.amattioli.workstate.actions.StateAction;

public class ConfigMother {
  
  private static void initSequential(MetaSequentialState sequential) {
    MetaInitialState initial = new MetaInitialState();
    sequential.addMetaState(initial);
    MetaSimpleState simple = new MetaSimpleState("SimpleState",new MockStateAction(),new MockStateAction());
    sequential.addMetaState(simple);
    Transition initTrans = new ExternalTransition(null,initial,simple,null,null);
  }

  public static MetaSequentialState standardSequentialState(String tag, StateAction entry, StateAction exit) {
    MetaSequentialState sequential = new MetaSequentialState(tag,entry,exit); 
    /*
    MetaInitialState initial = new MetaInitialState();
    sequential.addMetaState(initial);
    MetaSimpleState simple = new MetaSimpleState("SimpleState",new MockStateAction(),new MockStateAction());
    sequential.addMetaState(simple);
    Transition initTrans = new ExternalTransition(null,initial,simple,null,null);
    */
    initSequential(sequential);
    return sequential;
  }
  
  public static MetaConcurrentState standardConcurrentState(String tag, StateAction entry, StateAction exit) {
    MetaConcurrentState concurrent = new MetaConcurrentState(tag,entry,exit);
    MetaSequentialState region1 = standardSequentialState("region1",null,null);
    concurrent.addMetaState(region1);
    MetaSequentialState region2 = standardSequentialState("region2",null,null);
    concurrent.addMetaState(region2);
    return concurrent;
  }
  
  public static MetaMachine standardMachine(String tag) {
    MetaMachine sequential = new MetaMachine(tag, new MockReader(), tag, null, "it.amattioli.workstate.core.MockOwner");
    initSequential(sequential);
    return sequential;
  }
  
}
