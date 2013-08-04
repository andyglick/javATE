package it.amattioli.applicate.sessions;

import java.util.HashSet;
import java.util.Set;

import it.amattioli.applicate.commands.Command;
import it.amattioli.applicate.commands.executors.SimpleExecutor;

public class MockCommandExecutor extends SimpleExecutor {
	private Set<Command> executedCommands = new HashSet<Command>();
	
	public boolean executed(Command cmd) {
		return executedCommands.contains(cmd);
	}
	
	@Override
	public void execute(Command cmd) {
		super.execute(cmd);
		executedCommands.add(cmd);
	}

}
