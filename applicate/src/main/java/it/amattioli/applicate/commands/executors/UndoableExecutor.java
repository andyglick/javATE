package it.amattioli.applicate.commands.executors;

import java.util.ArrayDeque;
import java.util.Deque;

import it.amattioli.applicate.commands.Command;
import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.commands.CommandListener;
import it.amattioli.applicate.commands.CommandResult;
import it.amattioli.applicate.commands.UndoableCommand;
import it.amattioli.dominate.proxies.ProxyUtils;

public class UndoableExecutor extends AbstractCommandExecutor {
	private int size = -1;
	private Deque<UndoableCommand> undoStack = new ArrayDeque<UndoableCommand>();
	private CommandListener cmdListener = new CommandListener() {
		
		@Override
		public void commandDone(CommandEvent e) {
			Command cmd = (Command)e.getSource();
			if (cmd instanceof UndoableCommand) {
				undoStack.push((UndoableCommand)cmd);
				if (size > 0 && size < undoStack.size()) {
					undoStack.removeFirst();
				}
			} else if(!toBeIgnored(cmd)) {
				undoStack.clear();
			}
		}
		
		@Override
		public String toString() {
			return "UndoableExecutor.cmdListener";
		}
		
	};
	
	private boolean toBeIgnored(Command cmd) {
		return ProxyUtils.unProxyClass(cmd.getClass()).isAnnotationPresent(UndoIgnore.class);
	}
	
	@Override
	protected void beforeCommand(Command cmd) {
		cmd.addCommandListener(cmdListener, CommandResult.SUCCESSFUL);		
	}
	
	@Override
	protected void afterCommand(Command cmd) {
		// Nothing to do
	}
	
	public void undo() {
		if (isUndoAvailable()) {
			undoStack.pop().undoCommand();
		}
	}

	public boolean isUndoAvailable() {
		return !undoStack.isEmpty();
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
