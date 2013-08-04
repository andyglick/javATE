package it.amattioli.applicate.commands.executors;

import it.amattioli.applicate.commands.Command;

/**
 * A simple executor that immediately calls the {@link Command#doCommand()} method.
 * 
 * @author andrea
 *
 */
public class SimpleExecutor extends AbstractCommandExecutor {

	@Override
	protected void beforeCommand(Command cmd) {
		// Does nothing
	}
	
	@Override
	protected void afterCommand(Command cmd) {
		// Does nothing
	}

}
