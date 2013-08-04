package it.amattioli.applicate.commands.executors;

import it.amattioli.applicate.commands.AbstractCommand;
import it.amattioli.applicate.commands.ApplicationException;
import it.amattioli.applicate.commands.CommandExecutor;
import it.amattioli.applicate.commands.ExecutorAwareCommand;

/**
 * Command used to undo other commands. Send this command to an {@link UndoableExecutor}
 * to undo the last command executed by the executor.
 * 
 * @author andrea
 *
 */
@UndoIgnore
public class UndoCommand extends AbstractCommand implements ExecutorAwareCommand {
	private CommandExecutor executor;
	
	@Override
	public void setCommandExecutor(CommandExecutor executor) {
		this.executor = executor;
	}

	/**
	 * Undo the last command executed by the executor that is calling it.
	 * 
	 * @throws IllegalStateException if the executor is not an {@link UndoableExecutor} 
	 */
	@Override
	public void doCommand() throws ApplicationException {
		if (!(executor instanceof UndoableExecutor)) {
			throw new IllegalStateException("UndoCommand can be used only with UndoableExecutor");
		}
		((UndoableExecutor)executor).undo();
		super.doCommand();
	}

}
