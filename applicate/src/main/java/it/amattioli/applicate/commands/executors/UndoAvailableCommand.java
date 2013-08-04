package it.amattioli.applicate.commands.executors;

import it.amattioli.applicate.commands.AbstractCommand;
import it.amattioli.applicate.commands.ApplicationException;
import it.amattioli.applicate.commands.CommandExecutor;
import it.amattioli.applicate.commands.ExecutorAwareCommand;

/**
 * Check if there is something to undo. Send this command to an {@link UndoableExecutor}
 * to check if the executor has at least one command that can be undone. After executing
 * the command the client can check the result with {@link #checkResult()}.
 * 
 * @author andrea
 *
 */
@UndoIgnore
public class UndoAvailableCommand extends AbstractCommand implements ExecutorAwareCommand {
	private CommandExecutor executor;
	private boolean result;
	
	@Override
	public void setCommandExecutor(CommandExecutor executor) {
		this.executor = executor;
	}
	
	/**
	 * Check if the executor has at least one command that can be undone.
	 * 
	 * @throws IllegalStateException if the executor is not an {@link UndoableExecutor} 
	 */
	@Override
	public void doCommand() throws ApplicationException {
		if (!(executor instanceof UndoableExecutor)) {
			throw new IllegalStateException("UndoCommand can be used only with UndoableExecutor");
		}
		result = ((UndoableExecutor)executor).isUndoAvailable();
		super.doCommand();
	}
	
	public boolean checkResult() {
		return result;
	}
	
}
