package it.amattioli.workstate.testmachine;

/**
 * 
 * @author a.mattioli
 */
public class FromSimpleToSequentialTest extends AbstractMachineTest {

  public String getMachineResource() throws Exception {
    return "it/amattioli/workstate/testmachine/FromSimpleToSequentialMachine.xml";
  }

  public String getFileName() {
    return "it/amattioli/workstate/testmachine/FromSimpleToSequentialEvents.xml";
  }
  
  public void testAll() throws Exception {
    this.stepAll();
    this.assertState("ObjectList");
  }

}
