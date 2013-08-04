package it.amattioli.applicate.commands;

import junit.framework.TestCase;

public class CommandEventTest extends TestCase {

	public void testEquals() {
		Command cmd = new AbstractCommand() {};
		CommandEvent e1 = new CommandEvent(cmd, CommandResult.SUCCESSFUL);
		CommandEvent e2 = new CommandEvent(cmd, CommandResult.SUCCESSFUL);
		assertEquals(e1, e2);
	}
	
}
