package it.amattioli.workstate.testmachine;

import it.amattioli.workstate.config.Registry;
import it.amattioli.workstate.config.XmlConfiguration;
import it.amattioli.workstate.core.Machine;
import it.amattioli.workstate.wfunit.WfXmlTestCase;

/**
 * 
 * @author a.mattioli
 */
public abstract class AbstractMachineTest extends WfXmlTestCase {

  public AbstractMachineTest() {
    super("");
  }
  
  /**
   *
   */
  public Machine loadMachine() throws Exception {
    XmlConfiguration r = new XmlConfiguration(getMachineResource(),"uiModel");
    Registry.instance().register(r);
    Machine machine = Registry.instance().newMachine("uiModel");
    return machine;
  }
  
  public abstract String getMachineResource() throws Exception;

}
