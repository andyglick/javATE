package it.amattioli.applicate.commands.executors;

import it.amattioli.applicate.commands.AbstractCommand;
import it.amattioli.applicate.commands.UndoableCommand;

public class MockUndoableCommand extends AbstractCommand implements UndoableCommand {
	private boolean undone = false;
	
	public boolean isUndone() {
		return undone;
	}

	@Override
	public void undoCommand() {
		undone = true;		
	}

}
