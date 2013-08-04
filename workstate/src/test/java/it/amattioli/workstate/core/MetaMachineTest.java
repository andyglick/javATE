package it.amattioli.workstate.core;

import it.amattioli.workstate.config.Registry;

import java.io.*;

public class MetaMachineTest extends MetaSequentialStateTest {
  private MockReader reader = new MockReader();
  
  public void setUp() {
    testingState = new MetaMachine("top", reader, "top", null, null);
    reader.setMachine((MetaMachine)testingState);
  }
  
  public void testSerialize() throws Exception {
    MetaMachine original = Registry.instance().getMetaMachine(reader);
    ByteArrayOutputStream baOut = new ByteArrayOutputStream();
    ObjectOutputStream oOut = new ObjectOutputStream(baOut);
    oOut.writeObject(original);
    ByteArrayInputStream baIn = new ByteArrayInputStream(baOut.toByteArray());
    ObjectInputStream oIn = new ObjectInputStream(baIn);
    MetaMachine reloaded = (MetaMachine)oIn.readObject();
    assertTrue(reloaded == original);
  }
  
  public void testOwnerAttribute() throws Exception {
    assertTrue(((MetaMachine)testingState).isAllowedAttribute(MetaMachine.OWNER_TAG));
  }

  public void testFindMetaState() {
	  assertEquals(testingState, ((MetaMachine)testingState).findMetaState("top"));
  }
}
