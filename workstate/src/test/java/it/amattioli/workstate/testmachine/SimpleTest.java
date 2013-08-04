package it.amattioli.workstate.testmachine;

/**
 * 
 * @author a.mattioli
 */
public class SimpleTest extends AbstractMachineTest {

  public String getMachineResource() throws Exception {
    return "it/amattioli/workstate/testmachine/SimpleMachine.xml";
  }

  public String getFileName() {
    return "it/amattioli/workstate/testmachine/SimpleEvents.xml";
  }
  
  public void testAll() throws Exception {
    this.stepAll();
    this.assertState("Stato2");
  }

}
