package it.amattioli.applicate.commands.executors;

import it.amattioli.applicate.commands.AbstractCommand;
import it.amattioli.applicate.commands.ApplicationException;
import junit.framework.TestCase;

public class SimpleExecutorTest extends TestCase {

	public void testExecute() {
		MockCommand cmd = new MockCommand();
		SimpleExecutor exec = new SimpleExecutor();
		exec.execute(cmd);
		assertTrue(cmd.done);
	}
	
	private static class MockCommand extends AbstractCommand {
		public boolean done = false;
		
		@Override
		public void doCommand() throws ApplicationException {
			done = true;
			super.doCommand();
		}
		
	}
	
}
