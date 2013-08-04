package it.amattioli.workstate.testmachine;

/**
 * 
 * @author a.mattioli
 */
public class FromSuperToSubstateTest extends AbstractMachineTest {

  public String getMachineResource() throws Exception {
    return "it/amattioli/workstate/testmachine/FromSuperToSubstateMachine.xml";
  }

  public String getFileName() {
    return "it/amattioli/workstate/testmachine/FromSuperToSubstateEvents.xml";
  }
  
  public void testAll() throws Exception {
    this.stepAll();
    this.assertState("ObjectList");
  }

}
