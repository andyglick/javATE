package it.amattioli.applicate.commands;

/**
 * An interface that must be implemented by a {@link Command} that has to know the executor
 * that is going to execute it.
 * The executor is responsible for injecting itself in the command before calling the 
 * {@link Command#doCommand()} method.
 * 
 * @author andrea
 *
 */
public interface ExecutorAwareCommand extends Command {

	public void setCommandExecutor(CommandExecutor executor);
	
}
