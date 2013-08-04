package it.amattioli.applicate.commands;

import java.util.EventObject;

public class CommandEvent extends EventObject {
	private CommandResult result;
	private Throwable exc;

	public CommandEvent(Command source, CommandResult result) {
		super(source);
		this.result = result;
	}

	public CommandEvent(Command source, CommandResult result, Throwable exc) {
		this(source, result);
		this.exc = exc;
	}

	public CommandResult getResult() {
		return result;
	}

	public Throwable getExc() {
		return exc;
	}

	@Override
	public String toString() {
		return "CommandEvent[source=" + getSource() + " result=" + result + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CommandEvent)) {
			return false;
		}
		CommandEvent other = (CommandEvent) obj;
		if (getSource() == null) {
			if (other.getSource() != null) {
				return false;
			}
		} else if (!getSource().equals(other.getSource())) {
			return false;
		}
		if (result == null) {
			if (other.result != null) {
				return false;
			}
		} else if (!result.equals(other.result)) {
			return false;
		}
		return true;
	}
	
}
