package it.amattioli.applicate.commands.executors;

import junit.framework.TestCase;

public class UndoCommandTest extends TestCase {

	public void testWithUndoableExecutor() {
		UndoableExecutor exec = new UndoableExecutor();
		MockUndoableCommand cmd = new MockUndoableCommand();
		exec.execute(cmd);
		exec.execute(new UndoCommand());
		assertTrue(cmd.isUndone());
		assertFalse(exec.isUndoAvailable());
	}
	
	public void testDoubleUndo() {
		UndoableExecutor exec = new UndoableExecutor();
		MockUndoableCommand cmd1 = new MockUndoableCommand();
		exec.execute(cmd1);
		MockUndoableCommand cmd2 = new MockUndoableCommand();
		exec.execute(cmd2);
		exec.execute(new UndoCommand());
		assertTrue(cmd2.isUndone());
		assertTrue(exec.isUndoAvailable());
		exec.execute(new UndoCommand());
		assertTrue(cmd1.isUndone());
		assertFalse(exec.isUndoAvailable());
	}
	
	public void testWithSimpleExecutor() {
		SimpleExecutor exec = new SimpleExecutor();
		try {
			exec.execute(new UndoCommand());
			fail("Should throw IllegalStateException");
		} catch(IllegalStateException e) {
			
		}
	}
	
}
