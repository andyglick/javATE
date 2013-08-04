package it.amattioli.applicate.commands.executors;

import junit.framework.TestCase;

public class UndoAvailableCommandTest extends TestCase {

	public void testWhenUndoAvailable() {
		UndoableExecutor exec = new UndoableExecutor();
		MockUndoableCommand cmd = new MockUndoableCommand();
		exec.execute(cmd);
		UndoAvailableCommand check = new UndoAvailableCommand();
		exec.execute(check);
		assertTrue(check.checkResult());
	}
	
	public void testTwoConsecutiveChecks() {
		UndoableExecutor exec = new UndoableExecutor();
		MockUndoableCommand cmd = new MockUndoableCommand();
		exec.execute(cmd);
		UndoAvailableCommand check1 = new UndoAvailableCommand();
		exec.execute(check1);
		assertTrue(check1.checkResult());
		UndoAvailableCommand check2 = new UndoAvailableCommand();
		exec.execute(check2);
		assertTrue(check2.checkResult());
	}
	
	public void testWhenUndoNotAvailable() {
		UndoableExecutor exec = new UndoableExecutor();
		UndoAvailableCommand check = new UndoAvailableCommand();
		exec.execute(check);
		assertFalse(check.checkResult());
	}
	
	public void testWithSimpleExecutor() {
		SimpleExecutor exec = new SimpleExecutor();
		try {
			exec.execute(new UndoAvailableCommand());
			fail("Should throw IllegalStateException");
		} catch(IllegalStateException e) {
			
		}
	}
	
}
