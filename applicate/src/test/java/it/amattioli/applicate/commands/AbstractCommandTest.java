package it.amattioli.applicate.commands;

import it.amattioli.applicate.properties.MockPropertyChangeListener;
import junit.framework.TestCase;

public class AbstractCommandTest extends TestCase {

	public void testCommandListener() {
		AbstractCommand cmd = new AbstractCommand() {};
		MockCommandListener listener = new MockCommandListener();
		cmd.addCommandListener(listener);
		cmd.doCommand();
		assertTrue(listener.isCommandDoneReceived());
	}
	
	public void testPropertyChangeListener() {
		AbstractCommand cmd = new AbstractCommand() {};
		MockPropertyChangeListener listener = new MockPropertyChangeListener("property1");
		cmd.addPropertyChangeListener(listener);
		cmd.firePropertyChange("property1", "old", "new");
		assertTrue(listener.isPropertyChangeNotified());
	}
	
}
