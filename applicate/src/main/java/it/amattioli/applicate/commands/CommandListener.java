package it.amattioli.applicate.commands;

import java.util.EventListener;

/**
 * A listener that can be added to a command to intercept command events fired
 * during command execution.
 * 
 * @author andrea
 *
 */
public interface CommandListener extends EventListener {
	
	/**
	 * This method will be called every time a {@link CommandEvent} is fired by
	 * the command to which this listener has been added.
	 * 
	 * @param e the fired event
	 */
	public void commandDone(CommandEvent e);

}
