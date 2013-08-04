package it.amattioli.applicate.commands.executors;

import it.amattioli.applicate.commands.NullCommand;
import junit.framework.TestCase;

public class UndoableExecutorTest extends TestCase {

	public void testUndoNotAvailableIfNoCommandExecuted() {
		UndoableExecutor exec = new UndoableExecutor();
		assertFalse(exec.isUndoAvailable());
	}
	
	public void testUndoAvailableIfUndoableCommandExecuted() {
		UndoableExecutor exec = new UndoableExecutor();
		exec.execute(new MockUndoableCommand());
		assertTrue(exec.isUndoAvailable());
	}
	
	public void testUndoNotAvailableIfNonUndoableCommandExecuted() {
		UndoableExecutor exec = new UndoableExecutor();
		exec.execute(new MockUndoableCommand());
		exec.execute(new NullCommand());
		assertFalse(exec.isUndoAvailable());
	}
	
	public void testUndo() {
		UndoableExecutor exec = new UndoableExecutor();
		MockUndoableCommand cmd = new MockUndoableCommand();
		exec.execute(cmd);
		exec.undo();
		assertTrue(cmd.isUndone());
	}
	
	public void testSize() {
		UndoableExecutor exec = new UndoableExecutor();
		exec.setSize(2);
		MockUndoableCommand cmd = new MockUndoableCommand();
		exec.execute(cmd);
		exec.execute(cmd);
		exec.execute(cmd);
		exec.undo();
		exec.undo();
		assertFalse(exec.isUndoAvailable());
	}

}
