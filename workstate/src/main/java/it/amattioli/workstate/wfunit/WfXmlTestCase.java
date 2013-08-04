package it.amattioli.workstate.wfunit;
import java.util.List;

public abstract class WfXmlTestCase extends WorkflowTestCase  {

  public WfXmlTestCase(String name) {
    super(name);
  }

  public List loadEvents() throws Exception {
    XmlTestEventsReader reader = new XmlTestEventsReader(getFileName());
    return reader.readEvents();
  }

  public abstract String getFileName();
  
}
