package it.amattioli.applicate.commands;

public interface UndoableCommand extends Command {

	public void undoCommand();
	
}
