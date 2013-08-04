package it.amattioli.workstate.core;

public class MachineTest extends AbstractRealStateTest {
  private MetaMachine machine;
  private MetaSimpleState simple;
  
  public void setUp() throws Exception {
    machine = ConfigMother.standardMachine("top");
    testingMetaState = machine;
    simple = (MetaSimpleState)machine.getSubstate("SimpleState");
    mockEntryAction = null;
    mockExitAction = null;
    super.setUp();
  }
  
  public void testOwner() throws Exception {    
    Object owner = new MockOwner();
    Machine testingMachine = (Machine)testingState;
    testingMachine.setOwner(owner);
    testingState.enter();
    assertTrue(owner == testingMachine.getAttribute(MetaMachine.OWNER_TAG));
  }

}
