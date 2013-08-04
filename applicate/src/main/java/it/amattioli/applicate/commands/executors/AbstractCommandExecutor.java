package it.amattioli.applicate.commands.executors;

import it.amattioli.applicate.commands.Command;
import it.amattioli.applicate.commands.CommandExecutor;
import it.amattioli.applicate.commands.ExecutorAwareCommand;

public abstract class AbstractCommandExecutor implements CommandExecutor {

	@Override
	public void execute(Command cmd) {
		prepareCommand(cmd);
		beforeCommand(cmd);
		cmd.doCommand();
		afterCommand(cmd);
	}
	
	protected abstract void beforeCommand(Command cmd);
	
	protected abstract void afterCommand(Command cmd);

	private void prepareCommand(Command cmd) {
		if (cmd instanceof ExecutorAwareCommand) {
			((ExecutorAwareCommand)cmd).setCommandExecutor(this);
		}
	}
	
}
