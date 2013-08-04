package it.amattioli.workstate.testmachine;

/**
 * 
 * @author a.mattioli
 */
public class ReferenceTest extends AbstractMachineTest {

  public String getMachineResource() throws Exception {
    return "it/amattioli/workstate/testmachine/ReferenceMachine.xml";
  }

  public String getFileName() {
    return "it/amattioli/workstate/testmachine/ReferenceEvents.xml";
  }
  
  public void testAll() throws Exception {
    this.stepAll();
    this.assertState("SuperStato3");
  }

}
